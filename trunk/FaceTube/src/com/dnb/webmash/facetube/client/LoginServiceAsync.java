package com.dnb.webmash.facetube.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.dnb.webmash.facetube.shared.FBUser;
import com.dnb.webmash.facetube.shared.Session;

public interface LoginServiceAsync {
  public void login(String requestUri, AsyncCallback<FBUser> async);
}
