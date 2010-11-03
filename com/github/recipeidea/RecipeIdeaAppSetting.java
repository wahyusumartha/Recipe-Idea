package com.github.recipeidea;

public class RecipeIdeaAppSetting {

	private String url = "http://www.recipepuppy.com/api/";

	public RecipeIdeaAppSetting(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
