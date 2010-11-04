package com.github.recipeidea.ui;

import com.github.recipeidea.ServiceClient;
import com.github.recipeidea.log.Logger;
import com.github.recipeidea.test.Test;
import com.github.recipeidea.ui.component.BackgroundManager;
import com.github.recipeidea.ui.component.HeaderField;
import com.github.recipeidea.ui.component.RoundRectField;
import com.github.recipeidea.ui.component.WEditField;
import com.github.recipeidea.ui.component.WLabelField;

import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class WelcomeScreen extends RecipeIdeaScreen {

	private static final String SEARCH_LABEL = "Find Recipes Idea By Ingredient : ";
	private static final String EXAMPLE_LABEL = "Ex : Tomato, Egg, Noodle";
	private static final String SEARCH_BUTTON = "Find!";

	private WLabelField searchLabel;
	private BackgroundManager backgroundScreen;
	private WEditField keywordEditField;
	private WLabelField exampleLabel;
	private RoundRectField searchButton;

	protected Logger log = Logger.getLogger(getClass());

	public WelcomeScreen(ServiceClient serviceClient) {
		super(serviceClient);
		VerticalFieldManager vertical = new VerticalFieldManager(
				VerticalFieldManager.NO_HORIZONTAL_SCROLL
						| VerticalFieldManager.NO_VERTICAL_SCROLL);
		// HorizontalFieldManager hfm = new HorizontalFieldManager();
		backgroundScreen = new BackgroundManager();
		HeaderField banner = new HeaderField("Recipe Idea's");
		searchLabel = new WLabelField(SEARCH_LABEL);
		exampleLabel = new WLabelField(EXAMPLE_LABEL);
		keywordEditField = new WEditField();
		searchButton = new RoundRectField(SEARCH_BUTTON) {
			protected void doAction() {
				// fireAction(Test.ACTION_ENTER);
				WelcomeScreen.this.serviceClient.setParameter(keywordEditField
						.getText());
				fireAction(RecipesScreen.ACTION_ENTER);
				keywordEditField.setText("");
			}

		};
		vertical.add(banner);
		vertical.add(new SeparatorField());
		vertical.add(searchLabel);
		vertical.add(new SeparatorField());
		vertical.add(keywordEditField);
		vertical.add(new SeparatorField());
		vertical.add(exampleLabel);
		vertical.add(new SeparatorField());
		vertical.add(searchButton);
		backgroundScreen.add(vertical);
		// hfm.add(backgroundScreen);
		add(backgroundScreen);
	}

	public boolean onClose() {
		if (Dialog
				.ask("Are You Sure to Exit?", new String[] { "Yes", "No" }, 0) == 0) {
			log.info(getClass().getName() + ": User Exiting Application");
			((RecipeIdea) getApplication()).exit();
		}
		return true;
	}
}
