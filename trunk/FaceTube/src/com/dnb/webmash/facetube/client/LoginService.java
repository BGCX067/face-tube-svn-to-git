package com.dnb.webmash.facetube.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.dnb.webmash.facetube.shared.FBUser;
import com.dnb.webmash.facetube.shared.Session;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
  public FBUser login(String requestUri);
}
