package com.dnb.webmash.facetube.client.requests;

import com.dnb.webmash.facetube.shared.FBUser;
import com.dnb.webmash.facetube.shared.MediaItem;
import com.dnb.webmash.facetube.shared.MediaType;
import com.dnb.webmash.facetube.shared.StoreLink;

/**
 * @author nic
 *	Contains all FaceTubeService requests that do not expect an immediate response (status returned via boolean or int)
 *	These should be paired with TASKS on the serverside
 */
public class LazyRequests {
	public static Boolean getFBUserNFriends(String accessToken, String requestID){
		return false;
	}
	public static Boolean getMedias(String accessToken, FBUser user, MediaType type, String requestID){
		return false;
	}
	public static Boolean getStoreFront(StoreLink mySelectedMedia, String requestID){
		return false;
		
	}
	
}
