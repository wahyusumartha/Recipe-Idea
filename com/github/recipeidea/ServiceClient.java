package com.github.recipeidea;

import com.github.recipeidea.json.JSONObject;
import com.github.recipeidea.json.JSONTokener;
import com.github.recipeidea.network.HttpClient;
import com.github.recipeidea.network.HttpConnectionFactory;

public class ServiceClient {

	private String parameter;
	private HttpConnectionFactory factory;
	private HttpClient httpClient;

	private RecipeIdeaAppSetting appSetting;

	private Recipe recipeAccess = null;

	public ServiceClient(RecipeIdeaAppSetting setting,
			HttpConnectionFactory factory) {
		this.appSetting = setting;
		this.factory = factory;
		httpClient = new HttpClient(this.factory);
	}

	public JSONObject getData(String parameter) throws ServiceClientException {
		StringBuffer buffer = new StringBuffer();
		try {
			buffer = httpClient.doGet(appSetting.getUrl(), "?i=" + parameter,
					factory);
			if (buffer.length() == 0) {
				new ServiceClientException("No Responds");
			}
			return new JSONObject(new JSONTokener(buffer.toString()));
		} catch (ServiceClientException e) {
			throw e;
		} catch (Exception e) {
			new ServiceClientException(e.getMessage());
		}
		return null;

	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Recipe getRecipeAccess() throws ServiceClientException {
		if (recipeAccess == null) {
			recipeAccess = new RecipeImpl(this);
		}
		return recipeAccess;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

}
