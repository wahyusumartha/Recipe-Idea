package com.github.recipeidea.ui;

import net.rim.device.api.ui.UiApplication;

public class RecipeIdea extends UiApplication {
	public RecipeIdea() {
		WelcomeScreen welcomeScreen = new WelcomeScreen();
		pushScreen(welcomeScreen);
	}

	public static void main(String[] args) {
		RecipeIdea main = new RecipeIdea();
		main.enterEventDispatcher();
	}
}
