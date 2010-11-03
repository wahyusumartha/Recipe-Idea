package com.github.recipeidea;

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
	public Recipe[] getRecipe();
}
