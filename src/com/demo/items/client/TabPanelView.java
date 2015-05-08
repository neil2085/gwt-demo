package com.demo.items.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TabPanelView extends Composite {

	private static TabPanelViewUiBinder uiBinder = GWT
			.create(TabPanelViewUiBinder.class);

	interface TabPanelViewUiBinder extends UiBinder<Widget, TabPanelView> {
	}

	public TabPanelView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
