package com.github.recipeidea.ui;

import com.github.recipeidea.ServiceClientException;
import com.github.recipeidea.test.Test;

import net.rim.device.api.ui.UiApplication;

public class RecipeIdea extends UiApplication {
	public RecipeIdea() throws Exception {
		// WelcomeScreen welcomeScreen = new WelcomeScreen();
		// pushScreen(welcomeScreen);
		Test test = new Test();
		pushScreen(test);
	}

	public static void main(String[] args) throws Exception {
		RecipeIdea main = new RecipeIdea();
		main.enterEventDispatcher();
	}
}
