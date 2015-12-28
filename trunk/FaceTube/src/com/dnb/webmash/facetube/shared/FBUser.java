package com.dnb.webmash.facetube.shared;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.Date;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class FBUser implements IsSerializable{
	@Persistent
	Session session;
	@Persistent
	Text user;
	@PrimaryKey
    @Persistent
    private String userID;
	@Persistent
	private Date createDate;
	
	public FBUser(){
		this.createDate = new Date();
	}
	public FBUser(String usr, String id, Session sess) {
		this();
		user = new Text(usr);
		session = sess;
		userID = id;
	}
	public Boolean isLoggedIn(){
		return session.isValid();
	}
	public String getUserID() {
		return userID;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Session getSession() {
		return session;
	}
	public void updateSession(Session session) {
		this.session = session;
	}

}
