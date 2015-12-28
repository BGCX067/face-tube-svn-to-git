package com.dnb.webmash.facetube.shared;

import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class MediaItem implements IsSerializable{
	@Persistent
	private MediaType type;
	@Persistent
	private Text fbPage;
	@PrimaryKey
    @Persistent
    private String primaryKey;
	@Persistent
	private Date createDate;
	
	public MediaItem(){
		this.createDate = new Date();
	}
	public MediaItem(String pg, String id, MediaType type){
		this();
		this.fbPage = new Text(pg);
		this.type = type;
		this.primaryKey = id;
	}
	public MediaType getType() {
		return type;
	}
	public Text getFbPage() {
		return fbPage;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public Date getCreateDate() {
		return createDate;
	}
}
