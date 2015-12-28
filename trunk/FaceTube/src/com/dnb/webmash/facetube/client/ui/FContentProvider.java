package com.dnb.webmash.facetube.client.ui;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.dnb.webmash.facetube.client.requests.DirectRequests;
import com.dnb.webmash.facetube.client.requests.LazyRequests;
import com.dnb.webmash.facetube.shared.FBUser;
import com.dnb.webmash.facetube.shared.MediaItem;
import com.dnb.webmash.facetube.shared.MediaType;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * @author nic
 * Displays a cellList of facebook users.
 */
public class FContentProvider extends Composite{
	private static FContentProviderUiBinder uiBinder = GWT.create(FContentProviderUiBinder.class);
	@UiField(provided=true) CellList<FBUser> cellList = new CellList<FBUser>(new AbstractCell<FBUser>(){
		@Override
		public void render(Context context, FBUser value, SafeHtmlBuilder sb) {
		}
	});
	
	@UiField ListBox mediaType_combo;
	private FBUser myUser;
	protected FBUser mySelectedFBUMediaAdvisor;
	
	interface FContentProviderUiBinder extends
			UiBinder<Widget, FContentProvider> {
	}
	
	public FContentProvider(FBUser user) {
		for (MediaType mt : MediaType.values())
			this.mediaType_combo.addItem(mt.name());
		if ((user!=null)&&(user.isLoggedIn()))
			this.setUser(user);
		else	this.error("Not logged in.");
		
		//CONFIGURE CELLLIST
		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	    //SELECTION MODEL
	    final SingleSelectionModel<FBUser> selectionModel = new SingleSelectionModel<FBUser>();
	    cellList.setSelectionModel(selectionModel);
	    //CELLLIST SELECTION HANDLER
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	      public void onSelectionChange(SelectionChangeEvent event) {
	    	  FBUser selected = selectionModel.getSelectedObject();
	        
	    	  if (selected != null) {
	        	FContentProvider.this.mySelectedFBUMediaAdvisor = selected;
	        }
	      }
	    });

	    //INITIAL DATA LOAD 
	    LazyRequests.getFBUserNFriends(this.myUser.getSession().getToken(), "requestID");
	    
	    //TODO USE THIS IN SOME SORT OF LOOP (check how to restore control to user)... post processing helper guide from gwt
	    List<FBUser> myFBUMediaProviders = DirectRequests.getCachedFBUList(myUser, "requestID");
	    // Push the data into the widget.
	    cellList.setRowData(0, myFBUMediaProviders);
	    cellList.setRowCount(myFBUMediaProviders.size(), true);
	    
	    initWidget(uiBinder.createAndBindUi(this));
	}
	
	
	private void setUser(FBUser user) {
		this.myUser = user;
	}
	
	//Returns the selected User's media list 
	public List<MediaItem> getMedias() {
		// TODO Auto-generated method stub
		return null;
	}
	private void error(String string) {
		// TODO Auto-generated method stub
		
	}
	
	//********************* HANDLERS
}

