package com.dnb.webmash.facetube.client.ui;

import java.util.List;

import com.dnb.webmash.facetube.client.requests.DirectRequests;
import com.dnb.webmash.facetube.client.requests.LazyRequests;
import com.dnb.webmash.facetube.shared.FBUser;
import com.dnb.webmash.facetube.shared.MediaItem;
import com.dnb.webmash.facetube.shared.MediaType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * @author nic
 *	Displays a cellList of Media items 
 */
public class MediaLibraryManager extends Composite {

	private static MediaLibraryManagerUiBinder uiBinder = GWT
			.create(MediaLibraryManagerUiBinder.class);
	@UiField(provided=true) CellList<MediaItem> cellList = new CellList<MediaItem>(new AbstractCell<MediaItem>(){
		@Override
		public void render(Context context, MediaItem value, SafeHtmlBuilder sb) {
		}
	});

	interface MediaLibraryManagerUiBinder extends
			UiBinder<Widget, MediaLibraryManager> {
	}
	
	private FBUser myUser;
	protected MediaItem mySelectedMedia; 
	
	public MediaLibraryManager(FBUser user) {

		if ((user!=null)&&(user.isLoggedIn()))
			this.setUser(user);
		else	this.error("Not logged in.");
		
		//CONFIGURE CELLLIST
		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	    //SELECTION MODEL
	    final SingleSelectionModel<MediaItem> selectionModel = new SingleSelectionModel<MediaItem>();
	    cellList.setSelectionModel(selectionModel);
	    //CELLLIST SELECTION HANDLER
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	      public void onSelectionChange(SelectionChangeEvent event) {
	    	  MediaItem selected = selectionModel.getSelectedObject();
	        
	    	  if (selected != null) {
	        	MediaLibraryManager.this.mySelectedMedia = selected;
	        }
	      }
	    });

	    //INITIAL DATA LOAD 
	    LazyRequests.getMedias(this.myUser.getSession().getToken(), myUser, MediaType.MUSIC, "requestID");
	    
	    //TODO USE THIS IN SOME SORT OF LOOP (check how to restore control to user)... post processing helper guide from gwt
	    List<MediaItem> myMedias = DirectRequests.getCachedMedias(myUser, "requestID");
	    // Push the data into the widget.
	    cellList.setRowData(0, myMedias);
	    cellList.setRowCount(myMedias.size(), true);
	    
	    initWidget(uiBinder.createAndBindUi(this));

	}

	private void error(String string) {
		// TODO Auto-generated method stub
		
	}

	private void setUser(FBUser user) {
		this.myUser = user;
	}

	public MediaItem getSelectedMedia() {
		return mySelectedMedia;
	}
	
	//********************* HANDLERS
}
