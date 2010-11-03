package com.github.recipeidea.ui;

import com.github.recipeidea.ServiceClient;

public abstract class RecipeIdeaScreen extends ActionScreen {

	protected ServiceClient serviceClient;

	public RecipeIdeaScreen(ServiceClient serviceClient) {
		super();
		this.serviceClient = serviceClient;
	}

}
