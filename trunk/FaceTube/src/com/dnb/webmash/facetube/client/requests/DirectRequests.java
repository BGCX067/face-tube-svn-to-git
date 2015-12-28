package com.dnb.webmash.facetube.client.requests;

import java.util.List;

import com.dnb.webmash.facetube.shared.StoreLink;
import com.dnb.webmash.facetube.shared.FBUser;
import com.dnb.webmash.facetube.shared.MediaItem;
import com.dnb.webmash.facetube.shared.MediaType;

/**
 * @author nic
 * Contains all static methods that use the FaceTubeService and expect an immediate response
 */
public class DirectRequests {
	public static MediaItem getRandomMediaItem(FBUser user, String accessToken, MediaType type){
		return null;
	}
	
	//LAZY REQUEST HELPERS (CACHE SIDE)
	public static List<FBUser> getCachedFBUList(FBUser user, String requestID){
		return null;
	}
	public static List<MediaItem> getCachedMedias(FBUser user, String requestID){
		return null;
	}
	public static List<StoreLink> getCachedStoreItems(FBUser user, String requestID){
		return null;
	}
}
