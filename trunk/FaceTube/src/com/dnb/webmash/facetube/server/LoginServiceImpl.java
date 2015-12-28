package com.dnb.webmash.facetube.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.dnb.webmash.facetube.client.LoginService;
import com.dnb.webmash.facetube.shared.Session;
import com.dnb.webmash.facetube.shared.FBUser;@SuppressWarnings("serial")

public class LoginServiceImpl extends RemoteServiceServlet implements
    LoginService {

  public FBUser login(String requestUri) {
    //this.getThreadLocalRequest().getCookies();//TODO USE THE COOKIES TO VERIFY AGAINST THE SESSION INFO...
    FBUser user = null;
    try {
		user = (FBUser) this.getThreadLocalRequest().getSession().getAttribute("user");
	} catch (ClassCastException e) {
		throw new RuntimeException("Session Data Corrupted!");
	}
    if (user != null) {
    	user.updateSession(user.getSession().setLoggedIn(true)); //WILL THIS BE STORED PROPERLY IN FBUSER?
    }
    return user;
  }

}
