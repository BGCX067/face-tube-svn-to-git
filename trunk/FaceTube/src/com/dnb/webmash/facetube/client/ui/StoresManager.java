package com.dnb.webmash.facetube.client.ui;

import java.util.List;

import com.dnb.webmash.facetube.client.requests.DirectRequests;
import com.dnb.webmash.facetube.client.requests.LazyRequests;
import com.dnb.webmash.facetube.shared.FBUser;
import com.dnb.webmash.facetube.shared.MediaItem;
import com.dnb.webmash.facetube.shared.MediaType;
import com.dnb.webmash.facetube.shared.StoreLink;
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
 * Displays a cellList of storeItems.
 */
public class StoresManager extends Composite {

	private static StoresManagerUiBinder uiBinder = GWT
			.create(StoresManagerUiBinder.class);
	@UiField(provided=true) CellList<StoreLink> cellList = new CellList<StoreLink>(new AbstractCell<StoreLink>(){
		@Override
		public void render(Context context, StoreLink value, SafeHtmlBuilder sb) {
			// TODO
		}
	});

	interface StoresManagerUiBinder extends UiBinder<Widget, StoresManager> {
	}

	private FBUser myUser;
	
	public StoresManager(FBUser user) {
		initWidget(uiBinder.createAndBindUi(this));
		if ((user!=null)&&(user.isLoggedIn()))
			this.setUser(user);
		else	this.error("Not logged in.");
		
		//CONFIGURE CELLLIST
		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	    //SELECTION MODEL
	    final SingleSelectionModel<StoreLink> selectionModel = new SingleSelectionModel<StoreLink>();
	    cellList.setSelectionModel(selectionModel);
	    //CELLLIST SELECTION HANDLER
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	      public void onSelectionChange(SelectionChangeEvent event) {
	    	  StoreLink selected = selectionModel.getSelectedObject();
	        
	    	  if (selected != null) {
	    		  //TODO SEND TO STORE HERE
	        }
	      }
	    });

	    //INITIAL DATA LOAD 
	    
	    //TODO USE THIS IN SOME SORT OF LOOP (check how to restore control to user)... post processing helper guide from gwt
	    List<StoreLink> myStoreLinks = DirectRequests.getCachedStoreItems(this.myUser, "requestID");
	    // Push the data into the widget.
	    cellList.setRowData(0, myStoreLinks);
	    cellList.setRowCount(myStoreLinks.size(), true);

	}
	private void setUser(FBUser user) {
		this.myUser = user;
	}
	public void error(String message){
		//TODO IMPLEMENT
	}

}
