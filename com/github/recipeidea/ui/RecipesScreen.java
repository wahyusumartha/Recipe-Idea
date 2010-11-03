package com.github.recipeidea.ui;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.github.recipeidea.Recipe;
import com.github.recipeidea.ServiceClient;
import com.github.recipeidea.ServiceClientException;
import com.github.recipeidea.log.Logger;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;

final class RecipesScreen extends RecipeIdeaScreen {

	static final String ACTION_ENTER = "recipesScreen";
	static final String ACTION_SUCCESS = "success";
	static final String ACTION_ERROR = "error";

	private static final String LABEL_TITLE = "The Recipes";

	private ListField listField;
	private StreamListCallBack streamListCallBack;

	protected Logger log = Logger.getLogger(getClass());

	public RecipesScreen(ServiceClient serviceClient) {
		super(serviceClient);
		setTitle(new LabelField(LABEL_TITLE, LabelField.ELLIPSIS
				| LabelField.USE_ALL_WIDTH));

		listField = new ListField();
		listField.setCallback(streamListCallBack);
		add(listField);
	}

	public void loadList() {
		while (listField.getSize() > 0) {
			listField.delete(0);
		}

		try {
			Recipe[] recipes = serviceClient.getRecipeAccess().getRecipe();
			streamListCallBack.clear();

			for (int i = 0; i < recipes.length; i++) {
				listField.insert(listField.getSize());
				streamListCallBack.add(recipes[i]);
			}
			// Load Thumbnail
			streamListCallBack.loadBitmaps();
		} catch (ServiceClientException e) {
			log.error(e.getMessage());
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
				int height = listField.getHeight();
				Recipe recipe = (Recipe) recipes.elementAt(index);
				Bitmap bitmap = getBitmap(recipe.getTitle());

				if (bitmap != null) {
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
				font = fontFamily.getFont(Font.PLAIN, 6, Ui.UNITS_pt);

				graphics.setFont(font);
				// draw title
				graphics.drawText(recipe.getTitle(), 52, y, 0, width - 52);
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
