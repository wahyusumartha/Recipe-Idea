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

import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;

public class Test extends MainScreen {
	private LabelField label;

	private HttpConnectionFactory factory;
	private ServiceClient services;
	private RecipeIdeaAppSetting setting;
	// private HttpClient httpClient;

	private Recipe[] recipe;
	protected Logger log = Logger.getLogger(getClass());

	public Test() throws Exception {
		factory = new HttpConnectionFactory();
		setting = new RecipeIdeaAppSetting("http://www.recipepuppy.com/api/");
		services = new ServiceClient(setting, factory);
		label = new LabelField();

		services.setParameter("tomato");
		String cook = null;
		JSONArray array = services.getRecipeAccess().getRecipes();
		// log.debug(recipe[0].toString());
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < array.length(); i++) {
			buffer.append(array.getJSONObject(i).get("title")).append("\n");
		}
		label.setText(buffer.toString());
		// log.debug(response.toString());

		add(label);
	}
}
