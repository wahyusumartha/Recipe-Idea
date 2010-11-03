package com.github.recipeidea;

import java.util.Vector;

import com.github.recipeidea.json.JSONArray;

public interface Recipe {

	/*
	 * Get Title Recipe
	 */
	public String getTitle();

	/*
	 * Get Link Detail Recipe
	 */
	public String getLink();

	/*
	 * Get Ingredients Recipe
	 */
	public String getIngredients();

	/*
	 * Get Thumbnail Recipe
	 */
	public String getThumbnail();

	/*
	 * Recipe List
	 */
	public JSONArray getRecipes();
}
