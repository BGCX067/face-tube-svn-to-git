package com.dnb.webmash.facetube.server;

import java.util.ArrayList;
import java.util.List;

import com.dnb.webmash.facetube.client.requests.FaceTubeService;
import com.dnb.webmash.facetube.shared.StoreLink;
import com.dnb.webmash.facetube.shared.MediaType;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.Parameter;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchRequest.BatchRequestBuilder;
import com.restfb.batch.BatchResponse;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.restfb.types.Page;
import com.restfb.types.User;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class FaceTubeServiceImpl extends RemoteServiceServlet implements
		FaceTubeService {

	
	/**
	 * @param accessToken a facebook client access token
	 * @return a list of Users, the user allowing access first and her friends following
	 */
	public List<User> getFBUserNFriends(String accessToken){//TODO TURN THIS INTO A LAZY REQUEST
		
		List<User> results = new ArrayList<User>();
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
		User user = facebookClient.fetchObject("me", User.class);
		results.add(user);//add user first
		Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class);
		results.addAll(myFriends.getData());//add friends to the list
		
		return results;
	}
	
	public List<Page> getMediaPagesFromFB(String accessToken, String userID, MediaType type){//TODO TURN THIS INTO A LAZY REQUEST... TASK
		//MAKE INTO TASK... ADD TIMER FOR 10MIN
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
		JsonObject myMedia = facebookClient.fetchObject(userID+"/"+type.toString(), JsonObject.class);
		JsonArray data = myMedia.getJsonArray("data"); 
		
		//myMedia.getJsonArray("data").getJsonObject(0).getString("name");
		//myMedia.getJsonArray("data").getJsonObject(0).getString("category");
		
		//REQUEST A LIST OF PAGES THE USER LIKES USING FACEBOOK AND RESTFB
		BatchRequest mediaPageRequest;
		String id;
		BatchRequest[] reqs = new BatchRequest[data.length()];
		for (int i = 0; i < data.length(); i++){
			id = data.getJsonObject(i).getString("id");
			mediaPageRequest = new BatchRequestBuilder(id).build();//get facebook pages for all the user likes
			reqs[0]=mediaPageRequest;
		}

		List<BatchResponse> batchResponses = facebookClient.executeBatch(reqs);//execute batch request
		JsonMapper jsonMapper = new DefaultJsonMapper();
		Page p;
		List<Page> ret = new ArrayList<Page>();
		for (BatchResponse br : batchResponses){
			p = jsonMapper.toJavaObject(br.getBody(), Page.class); //get each page from response
			ret.add(p);
		}
		return ret;
	}
	
	public List<StoreLink> getStoreFront(Page media){
		return null;
	}
}
