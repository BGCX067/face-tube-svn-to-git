package com.dnb.webmash.facetube.client.ui;

import com.dnb.webmash.facetube.shared.Facebook;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Login extends Composite{
	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	@UiField Anchor loginLink;
	
	interface LoginUiBinder extends
			UiBinder<Widget, Login> {
	}

	public Login() {
		initWidget(uiBinder.createAndBindUi(this));
		 loginLink.setHref(Facebook.getLoginRedirectURL());
	}
}
