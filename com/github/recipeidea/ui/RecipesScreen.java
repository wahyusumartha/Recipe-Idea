package com.github.recipeidea.ui;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.github.recipeidea.Recipe;
import com.github.recipeidea.ServiceClient;
import com.github.recipeidea.log.Logger;
import com.github.recipeidea.ui.component.BackgroundManager;
import com.github.recipeidea.ui.component.HeaderField;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;
import net.rim.device.api.ui.component.SeparatorField;

final class RecipesScreen extends RecipeIdeaScreen {

	static final String ACTION_ENTER = "recipesScreen";
	static final String ACTION_SUCCESS = "success";
	static final String ACTION_ERROR = "error";

	private static final String BANNER_TITLE = "The Recipes For You";

	private ListField listField;
	private StreamListCallBack streamListCallBack = new StreamListCallBack();

	private HeaderField banner;
	private BackgroundManager backgroundManager;

	protected Logger log = Logger.getLogger(getClass());

	public RecipesScreen(ServiceClient serviceClient) {
		super(serviceClient);
		banner = new HeaderField(BANNER_TITLE);
		// backgroundManager = new BackgroundManager();
		listField = new ListField();
		listField.setRowHeight(50);
		listField.setCallback(streamListCallBack);
		add(banner);
		add(new SeparatorField());
		add(listField);
		// backgroundManager.add(banner);
		// backgroundManager.add(listField);
		// add(backgroundManager);
	}

	public void loadList() {
		while (listField.getSize() > 0) {
			listField.delete(0);
		}

		try {
			// serviceClient.setParameter("Tomato");
			Recipe[] recipes = serviceClient.getRecipeAccess().getRecipe();
			log.debug(recipes[0].getTitle());
			streamListCallBack.clear();

			for (int i = 0; i < recipes.length; i++) {
				listField.insert(listField.getSize());
				streamListCallBack.add(recipes[i]);
			}
			// Load Thumbnail
			streamListCallBack.loadBitmaps();
		} catch (Exception e) {
			log.error(e.getMessage());
			fireAction(ACTION_ERROR, e.getMessage());
		}

	}

	private class StreamListCallBack implements ListFieldCallback {

		private Vector recipes = new Vector();
		private Hashtable thumbnailRecipe = new Hashtable();

		public StreamListCallBack() {
		}

		public void clear() {
			recipes.removeAllElements();
		}

		public void add(Recipe recipe) {
			recipes.addElement(recipe);
		}

		public void insert(Recipe recipe, int index) {
			recipes.insertElementAt(recipe, index);
		}

		public void drawListRow(ListField listField, Graphics graphics,
				int index, int y, int width) {
			if (index < recipes.size()) {
				int height = listField.getRowHeight();
				// log.info("List Height : " + height);

				Recipe recipe = (Recipe) recipes.elementAt(index);
				Bitmap bitmap = getBitmap(recipe.getTitle());
				/*
				 * Draw List Cell every Row
				 */
				int[] upperX_PTS = new int[] { 0, 0, width, width };
				int[] upperY_PTS = new int[] { 0, y + (height / 2),
						y + (height / 2), 0 };

				int[] lowerX_PTS = new int[] { 0, 0, width, width };
				int[] lowerY_PTS = new int[] { y + (height / 2), y + height,
						y + height, y + (height / 2) };

				int[] upperDrawColors = new int[] { 0xa0acc2, 0x7788a4,
						0x7788a4, 0xa0acc2 };
				int[] lowerDrawColors = new int[] { 0x70819d, 0x606e88,
						0x606e88, 0x70819d };
				graphics.drawShadedFilledPath(upperX_PTS, upperY_PTS, null,
						upperDrawColors, null);
				graphics.drawShadedFilledPath(lowerX_PTS, lowerY_PTS, null,
						lowerDrawColors, null);
				/*
				 * End of Draw List Cell
				 */
				if (bitmap != null) {
					// log.info("Bitmap Retrieved : "
					// + getBitmap(recipe.getTitle()) + " Height : "
					// + getBitmap(recipe.getTitle()).getHeight());

					graphics.drawBitmap(0,
							y
									+ ((height - Math.min(bitmap.getHeight(),
											height)) / 2), 50, height, bitmap,
							0, 0);
				}

				FontFamily fontFamily = null;
				Font font = null;
				try {
					fontFamily = FontFamily.forName("System");
				} catch (ClassNotFoundException e) {
					font = Font.getDefault();
				}
				font = fontFamily.getFont(Font.BOLD, 6, Ui.UNITS_pt);
				graphics.setFont(font);
				// draw title
				graphics.drawText(recipe.getTitle(), 52, y, DrawStyle.ELLIPSIS,
						width - 52);
				font = fontFamily.getFont(Font.PLAIN, 5, Ui.UNITS_pt);
				graphics.setFont(font);
				// draw ingredients
				graphics.drawText("Ingredients : " + recipe.getIngredients(),
						52, y + (height / 2), DrawStyle.ELLIPSIS, width - 52);

				graphics.drawLine(0, y + height - 1, width, y + height - 1);
			}
		}

		public Object get(ListField listField, int index) {
			if (index < recipes.size()) {
				return recipes.elementAt(index);
			}
			return null;
		}

		public int getPreferredWidth(ListField listField) {
			return Display.getWidth();
		}

		public int indexOfList(ListField listField, String prefix, int start) {
			// TODO Auto-generated method stub
			for (int i = start; i < recipes.size(); i++) {
				Recipe recipe = (Recipe) recipes.elementAt(i);

				if (recipe.getTitle().indexOf(prefix) > -1) {
					return i;
				}
			}
			return -1;
		}

		public Bitmap getBitmap(String thumbnail) {
			return (Bitmap) thumbnailRecipe.get(thumbnail);
		}

		public void loadBitmaps() {
			new BitmapThread().start();
		}

		private class BitmapThread extends Thread {

			public void run() {
				Enumeration recipesEnum = recipes.elements();

				while (recipesEnum.hasMoreElements()) {
					Recipe recipe = (Recipe) recipesEnum.nextElement();

					try {
						StringBuffer response = serviceClient.getHttpClient()
								.doGet(recipe.getThumbnail());
						byte[] data = response.toString().getBytes();

						if (data.length > 0) {
							Bitmap bitmap = Bitmap.createBitmapFromBytes(data,
									0, data.length, 1);
							thumbnailRecipe.put(recipe.getTitle(), bitmap);
							listField.invalidate();
						}

					} catch (Exception e) {
						log.error(e.getMessage());
					}

				}
			}
		}
	}

}
