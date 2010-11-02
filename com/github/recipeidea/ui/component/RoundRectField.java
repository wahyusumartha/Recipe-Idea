package com.github.recipeidea.ui.component;

import net.rim.device.api.system.Characters;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Ui;

public class RoundRectField extends Field {

	/*
	 * Font for this Button Round Rect
	 */
	private FontFamily prefFontFamily;
	private Font prefFont;
	private String label;

	/*
	 * Layout Color
	 */
	private static final int CURVE_X = 12; // X-axis inset of curve
	private static final int CURVE_Y = 12; // Y-axis inset of curve
	private static final int MARGIN = 2; // Space within component boundary

	/*
	 * Static Color
	 */
	private static final int TEXT_COLOR = 0xFFFFFF; // White
	private static final int BORDER_COLOR = 0x424546;
	private static final int BACKGROUND_COLOR = 0xFFFFFF; // White

	/*
	 * Point types array for rounded rectangle. Each point type corresponds to
	 * one of the colors in the colors array. The space marks the division
	 * between points on the top half of the rectangle and those on the bottom.
	 */
	private static final byte[] PATH_POINT_TYPES = {
			Graphics.CURVEDPATH_END_POINT,
			Graphics.CURVEDPATH_QUADRATIC_BEZIER_CONTROL_POINT,
			Graphics.CURVEDPATH_END_POINT, Graphics.CURVEDPATH_END_POINT,
			Graphics.CURVEDPATH_QUADRATIC_BEZIER_CONTROL_POINT,
			Graphics.CURVEDPATH_END_POINT,

			Graphics.CURVEDPATH_END_POINT,
			Graphics.CURVEDPATH_QUADRATIC_BEZIER_CONTROL_POINT,
			Graphics.CURVEDPATH_END_POINT, Graphics.CURVEDPATH_END_POINT,
			Graphics.CURVEDPATH_QUADRATIC_BEZIER_CONTROL_POINT,
			Graphics.CURVEDPATH_END_POINT, };

	/*
	 * Colors array for rounded rectangle gradient. Each color corresponds to
	 * one of the points in the point types array. Top light, bottom black.
	 * Default private static final int[] PATH_GRADIENT = { 0xAAAAAA, 0xAAAAAA,
	 * 0xAAAAAA, 0xAAAAAA, 0xAAAAAA, 0xAAAAAA,
	 * 
	 * 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000 };
	 */

	private static final int[] PATH_GRADIENT = { 0xa0acc2, 0x7788a4, 0x7788a4,
			0xa0acc2, 0xa0acc2, 0x7788a4,

			0x70819d, 0x606e88, 0x606e88, 0x70819d, 0x70819d, 0x606e88, };

	/*
	 * Center our field in the space we're given.
	 */
	public RoundRectField(String label) {
		super(FIELD_HCENTER | FIELD_VCENTER);
		this.label = label;

		try {
			prefFontFamily = FontFamily.forName("System");
			prefFont = prefFontFamily.getFont(Font.PLAIN, 7, Ui.UNITS_pt);
			setFont(prefFont);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			prefFont = Font.getDefault();
		}

	}

	/*
	 * This field in this demo has a fixed height.
	 */
	public int getPreferredHeight() {
		return prefFont.getHeight() + (MARGIN * 5);
	}

	/*
	 * This field in this demo has a fixed width.(non-Javadoc)
	 */
	public int getPreferredWidth() {
		return Math.max(prefFont.getAdvance(label) + (MARGIN * 8), 75);
	}

	/*
	 * When layout is requested, return our height and width.(non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Field#layout(int, int)
	 */
	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(), getPreferredHeight());
	}

	/*
	 * When painting is requested, do it ourselves.(non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Field#paint(net.rim.device.api.ui.Graphics)
	 */
	protected void paint(Graphics g) {
		// Clear this area to white background, fully opaque.
		// g.clear();
		// g.setGlobalAlpha(255);
		// g.setBackgroundColor(BACKGROUND_COLOR);

		// Drawing within our margin.
		int width = getPreferredWidth() - (MARGIN * 2);
		int height = getPreferredHeight() - (MARGIN * 2);

		// Compute paths for the rounded rectangle. The 1st point (0) is on the
		// left
		// side, right where the curve in the top left corner starts. So the top
		// left
		// corner is point 1. These points correspond to our static arrays.
		int[] xPts = { 0, 0, CURVE_X, width - CURVE_X, width, width, width,
				width, width - CURVE_X, CURVE_X, 0, 0 };
		int[] yPts = { CURVE_Y, 0, 0, 0, 0, CURVE_Y, height - CURVE_Y, height,
				height, height, height, height - CURVE_Y };

		// Draw the gradient fill.
		g.drawShadedFilledPath(xPts, yPts, PATH_POINT_TYPES, PATH_GRADIENT,
				null);

		// Draw a rounded rectangle for the outline.
		// I think that drawRoundRect looks better than drawPathOutline.
		g.setColor(BORDER_COLOR);
		g.drawRoundRect(0, 0, width, height, CURVE_X * 2, CURVE_Y * 2);
		g.setColor(0xAAAAAA);
		g.drawRoundRect(0, 0, width, height, (CURVE_X * 2) - 2,
				(CURVE_Y * 2) - 2);

		int textWidth = prefFont.getAdvance(label);
		int textHeight = prefFont.getHeight();
		g.setColor(TEXT_COLOR);
		g.drawText(label, (width / 2) - (textWidth / 2) - MARGIN, (height / 2)
				- (textHeight / 2) - MARGIN);
	}

	protected void drawFocus(Graphics graphics, boolean on) {

	}

	public boolean isFocusable() {
		return true;
	}

	protected void onFocus(int arg0) {
		prefFont = prefFontFamily.getFont(Font.BOLD, 7, Ui.UNITS_pt);
		setFont(prefFont);
		invalidate();
	}

	protected void onUnfocus() {
		prefFont = prefFontFamily.getFont(Font.PLAIN, 7, Ui.UNITS_pt);
		setFont(prefFont);
		invalidate();
	}

	protected void doAction() {

	}

	protected boolean keyChar(char character, int status, int time) {
		if (character == Characters.ENTER) {
			doAction();
			return true;
		}
		return false;
	}

	protected boolean navigationClick(int status, int time) {
		doAction();
		return super.navigationClick(status, time);
	}

}