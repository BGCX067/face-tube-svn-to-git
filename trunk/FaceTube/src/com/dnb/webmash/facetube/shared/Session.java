package com.dnb.webmash.facetube.shared;

import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Session implements IsSerializable{
	@Persistent
	private Date createDate;
	@Persistent
	private String token;
	@Persistent
	private int expiry;
	@Persistent
	private Boolean loggedIn = false;
	@Persistent
	private Boolean admin;
	@Persistent
	private FBUser selectedFBUMediaAdvisor;
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String primaryKey;

	public Session(){
		this.createDate = new Date();
	}

	public Session(String accessToken, int expires, Date date) {
		token = accessToken;
		expiry = expires;
		createDate = date;
	}

	public Boolean isValid() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getCreateDate() {
		return createDate;
	}
	public String getToken() {
		return token;
	}
	public int getExpiry() {
		return expiry;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}

	public Session setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
		return this;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isAdmin() {
		return admin;
	}

}
