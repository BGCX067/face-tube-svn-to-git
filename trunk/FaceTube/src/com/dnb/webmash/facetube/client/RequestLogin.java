package com.dnb.webmash.facetube.client;

import com.dnb.webmash.facetube.client.ui.Login;
import com.google.gwt.user.client.ui.PopupPanel;

public class RequestLogin extends PopupPanel {
	
	public RequestLogin() {
		super(false);//CANT HIDE THIS WITHOUT PERMISSION
		setWidget(new Login());
	}

}
