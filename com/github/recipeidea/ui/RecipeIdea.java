package com.github.recipeidea.ui;

import com.github.recipeidea.RecipeIdeaAppSetting;
import com.github.recipeidea.ServiceClient;
import com.github.recipeidea.log.AppenderFactory;
import com.github.recipeidea.log.Logger;
import com.github.recipeidea.network.HttpConnectionFactory;
import com.github.recipeidea.test.Test;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

public class RecipeIdea extends UiApplication implements ActionListener {

	private static final String API_URL = "http://www.recipepuppy.com/api/";

	private HttpConnectionFactory factory;
	private ServiceClient serviceClient;

	private WelcomeScreen welcomeScreen;
	private RecipesScreen recipesScreen;
	private Test testScreen;

	protected Logger log = Logger.getLogger(getClass());

	public RecipeIdea() throws Exception {
		// WelcomeScreen welcomeScreen = new WelcomeScreen();
		// pushScreen(welcomeScreen);
		// Test test = new Test();
		// pushScreen(test);
		init();
		if (serviceClient != null) {
			welcomeScreen = new WelcomeScreen(serviceClient);
			welcomeScreen.addActionListener(this);
			pushScreen(welcomeScreen);
		}
	}

	public static void main(String[] args) throws Exception {
		RecipeIdea main = new RecipeIdea();
		main.enterEventDispatcher();
	}

	public void onAction(Action event) {
		/*
		 * Handle Every Event in Welcome Screen
		 */
		if (event.getSource() == welcomeScreen) {
			if (event.getAction().equals(Test.ACTION_ENTER)) {
				if (testScreen == null) {
					try {
						testScreen = new Test(serviceClient);
						testScreen.addActionListener(this);
					} catch (Exception e) {
						log
								.error(getClass().getName() + " : "
										+ e.getMessage());
					}
					pushScreen(testScreen);
				}
			} else if (event.getAction().equals(RecipesScreen.ACTION_ENTER)) {
				if (recipesScreen == null) {
					recipesScreen = new RecipesScreen(serviceClient);
					recipesScreen.addActionListener(this);
				}
				recipesScreen.loadList();
				pushScreen(recipesScreen);
			}
		} else if (event.getSource() == recipesScreen) {
			if (event.getAction().equals(RecipesScreen.ACTION_ERROR)) {
				Dialog.alert("Error : " + event.getData());
			}
		}
	}

	private void init() {
		factory = new HttpConnectionFactory();

		RecipeIdeaAppSetting setting = new RecipeIdeaAppSetting(API_URL);
		serviceClient = new ServiceClient(setting, factory);
	}

	public void exit() {
		AppenderFactory.close();
		System.exit(0);
	}
}
