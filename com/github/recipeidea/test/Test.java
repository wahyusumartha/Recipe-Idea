package com.github.recipeidea.test;

import com.github.recipeidea.Recipe;
import com.github.recipeidea.RecipeIdeaAppSetting;
import com.github.recipeidea.ServiceClient;
import com.github.recipeidea.ServiceClientException;
import com.github.recipeidea.json.JSONArray;
import com.github.recipeidea.json.JSONException;
import com.github.recipeidea.log.Logger;
import com.github.recipeidea.network.HttpClient;
import com.github.recipeidea.network.HttpConnectionFactory;
import com.github.recipeidea.ui.RecipeIdeaScreen;

import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;

public class Test extends RecipeIdeaScreen {
	private LabelField label;

	// private HttpConnectionFactory factory;
	// private ServiceClient services;
	// private RecipeIdeaAppSetting setting;
	// private HttpClient httpClient;

	public static final String ACTION_ENTER = "testScreen";
	public static final String ACTION_SUCCESS = "success";
	public static final String ACTION_ERROR = "error";

	private Recipe[] recipe;
	protected Logger log = Logger.getLogger(getClass());

	public Test(ServiceClient serviceClient) throws Exception {
		super(serviceClient);
		// factory = new HttpConnectionFactory();
		// setting = new
		// RecipeIdeaAppSetting("http://www.recipepuppy.com/api/");
		// services = new ServiceClient(setting, factory);
		label = new LabelField();

		// services.setParameter("tomato");
		serviceClient.setParameter("ginger");

		StringBuffer buffer = new StringBuffer();
		recipe = serviceClient.getRecipeAccess().getRecipe();
		for (int i = 0; i < recipe.length; i++) {
			buffer.append(recipe[i].getTitle()).append("\n");
		}
		label.setText(buffer.toString());

		add(label);
	}
}
