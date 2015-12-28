package com.dnb.webmash.facetube.client.ui;

import com.dnb.webmash.facetube.client.Model;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AbstractDataProvider;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionModel.AbstractSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

/**
 * @author nic
 *	Using a treemodel, displays a tree of Users who may like Pages which may be found in Stores
 */
public class TreeView extends Composite {

	private static TreeViewUiBinder uiBinder = GWT.create(TreeViewUiBinder.class);
	
	@UiField(provided=true) CellBrowser cellBrowser = new CellBrowser(
			new TreeViewModel() {
				final AbstractDataProvider<String> dataProvider = new ListDataProvider<String>();
				final AbstractSelectionModel<String> selectionModel = new NoSelectionModel<String>();
				@Override
				public <T> NodeInfo<?> getNodeInfo(T value) {
					return new DefaultNodeInfo<String>(dataProvider, new TextCell(), selectionModel, null);
				}
				@Override
				public boolean isLeaf(Object value) {
					return true;
				}
			}, null);
		

	interface TreeViewUiBinder extends UiBinder<Widget, TreeView> {
	}

	public TreeView() {
		// Create a model for the browser.
	    TreeViewModel model = new Model();

	    /*
	     * Create the browser using the model. We use <code>null</code> as the
	     * default value of the root node. The default value will be passed to
	     * CustomTreeModel#getNodeInfo();
	     */
	    CellBrowser browser = new CellBrowser(model, null);
	    browser.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

	    // Add the browser to the root layout panel.
	    RootLayoutPanel.get().add(browser);
		initWidget(uiBinder.createAndBindUi(this));
	}
	
}
