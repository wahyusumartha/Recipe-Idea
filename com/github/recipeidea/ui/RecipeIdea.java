package com.github.recipeidea.ui;

import net.rim.device.api.ui.UiApplication;

public class RecipeIdea extends UiApplication implements ActionListener {

	private WelcomeScreen welcomeScreen;

	public RecipeIdea() throws Exception {
		WelcomeScreen welcomeScreen = new WelcomeScreen();
		pushScreen(welcomeScreen);
		// Test test = new Test();
		// pushScreen(test);
	}

	public static void main(String[] args) throws Exception {
		RecipeIdea main = new RecipeIdea();
		main.enterEventDispatcher();
	}

	public void onAction(Action event) {
		// TODO Auto-generated method stub

	}

}
