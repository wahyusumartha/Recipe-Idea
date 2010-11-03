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
		jsonObject = services.getData(this.services.getParameter());
		log.debug(jsonObject.toString());
	}

	public RecipeImpl(JSONObject jsonObject) {
		// this.services = services;
		this.jsonObject = jsonObject;
	}

	public String getIngredients() {
		try {
			return jsonObject.getJSONObject("results").getString("ingredients");
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public String getLink() {
		try {
			return jsonObject.getJSONObject("results").getString("href");
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public JSONArray getRecipes() {
		try {
			JSONArray array = services.getData(services.getParameter())
					.getJSONArray("results");

			for (int i = 0; i < array.length(); i++) {
				// JSONObject recipeObject = array.getJSONObject(i);
				// recipe[i] = new RecipeImpl(services, recipeObject);
				log.debug(array.getJSONObject(i).toString());
			}
			return array;
		} catch (JSONException e) {
			log.error(getClass().getName() + " : " + e.getMessage());
		} catch (ServiceClientException e) {
			log.error(getClass().getName() + " : " + e.getMessage());
		}

		return null;

	}

	public String getThumbnail() {
		try {
			return jsonObject.getJSONObject("results").getString("thumbnail");
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public String getTitle() {
		try {
			return jsonObject.getJSONObject("results").getString("title");
		} catch (JSONException e) {
			log.error(e.getMessage());
		}
		return null;
	}

}
