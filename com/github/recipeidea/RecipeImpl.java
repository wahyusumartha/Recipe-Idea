package com.github.recipeidea;

import java.util.Vector;

import com.github.recipeidea.json.JSONArray;
import com.github.recipeidea.json.JSONException;
import com.github.recipeidea.json.JSONObject;
import com.github.recipeidea.log.Logger;

public class RecipeImpl implements Recipe {

	private ServiceClient services = null;
	private JSONObject jsonObject = null;
	protected Logger log = Logger.getLogger(getClass());

	public RecipeImpl(ServiceClient services) throws ServiceClientException {
		this.services = services;
		// jsonObject = services.getData(this.services.getParameter());
		jsonObject = new JSONObject();
		log.debug(jsonObject.toString());
	}

	public RecipeImpl(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public String getIngredients() {
		try {
			return jsonObject.getString("ingredients");
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public String getLink() {
		try {
			// return jsonObject.getJSONObject("results").getString("href");
			return jsonObject.getString("href");
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public String getThumbnail() {
		try {
			// return
			// jsonObject.getJSONObject("results").getString("thumbnail");
			return jsonObject.getString("thumbnail");
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public String getTitle() {
		try {
			// return jsonObject.getJSONObject("results").getString("title");
			return jsonObject.getString("title");
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public Recipe[] getRecipe() {
		Recipe[] recipe = null;

		try {
			JSONArray array = services.getData(services.getParameter())
					.getJSONArray("results");
			recipe = new Recipe[array.length()];
			for (int i = 0; i < array.length(); i++) {
				JSONObject recipeObject = array.getJSONObject(i);
				log.debug(recipeObject.toString());
				recipe[i] = new RecipeImpl(recipeObject);
			}
		} catch (JSONException e) {
			log.error(e.getMessage());
		} catch (ServiceClientException e) {
			log.error(e.getMessage());
		}
		return recipe;

	}

}
