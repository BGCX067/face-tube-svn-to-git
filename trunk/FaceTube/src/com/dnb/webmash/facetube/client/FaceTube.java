package com.dnb.webmash.facetube.client;

import com.dnb.webmash.facetube.client.requests.FaceTubeService;
import com.dnb.webmash.facetube.client.requests.FaceTubeServiceAsync;
import com.dnb.webmash.facetube.client.ui.*;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.dnb.webmash.facetube.shared.FBUser;/**

 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FaceTube implements EntryPoint {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 * TODO move this to the uibinder widgets as necessary
	 */
	private final FaceTubeServiceAsync faceTubeService = GWT
			.create(FaceTubeService.class);
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Keep track of logged in status
	 */
	private FBUser user;
	/**
	 * This is the entry point that helps keep track of the current user and arranges the main widgets.
	 */
	public void onModuleLoad() {
		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<FBUser>() {
			public void onFailure(Throwable error) {
			}	      
			public void onSuccess(FBUser result) {
				if (result == null)
					new RequestLogin().show();//SHOW POPUP TO PROMPT LOGIN
				else {
					user = result;
					if (!user.isLoggedIn())
						new RequestLogin().show();//SHOW POPUP... how could we have a user object but they not be logged in??
				}
			}
		});
		
		VerticalPanel main = new VerticalPanel();
		main.setSize("100%", "100%");
		RootPanel.get().add(main);
		/*
		FContentProvider facebookCUI = new FContentProvider(user);
		main.add(facebookCUI);
		facebookCUI.setSize("100%", "100");
		
		MediaLibraryManager mediaManUI = new MediaLibraryManager(user);
		main.add(mediaManUI);
		mediaManUI.setSize("100%", "100%");
		
		StoresManager storesManUI = new StoresManager(user);
		main.add(storesManUI);
		storesManUI.setSize("100%", "100%");
		*/
		TubePlayer tubePlayerUI = new TubePlayer(user);
		main.add(tubePlayerUI);
		tubePlayerUI.setSize("100%", "100%");
		//TODO SEARCH TEST HERE
		//tubePlayerUI.searchNPlayFirst("Mickey Mouse");
		//tubePlayerUI.loadTube("Mickey Mouse");
		tubePlayerUI.createSearchPanel("Mickey Mouse");
	}
}
