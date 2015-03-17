package org.sleeksnap.ui;

import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * @author Nikki
 */
public class UIRegion extends Region {

	private static final String[][] TABS = new String[][] {
			{ "Home", "HOME" },
			{ "Uploaders", "CLOUD_UPLOAD" },
			{ "Hotkeys", "KEYBOARD_ALT" },
			{ "History", "HISTORY" }
	};

	private final HBox toolBar;

	public UIRegion() {
		getStyleClass().add("settings");

		Hyperlink[] hpls = new Hyperlink[TABS.length];
		for (int i = 0; i < TABS.length; i++) {
			Hyperlink hpl = hpls[i] = new Hyperlink(TABS[i][0]);
			hpl.setGraphic(GlyphsBuilder.create(FontAwesomeIcon.class).glyph(FontAwesomeIcons.valueOf(TABS[i][1])).size("2em").build());

		}

		toolBar = new HBox();
		toolBar.setAlignment(Pos.CENTER);
		toolBar.getStyleClass().add("settings-toolbar");
		toolBar.getChildren().addAll(hpls);
		toolBar.getChildren().add(createSpacer());

		getChildren().add(toolBar);
	}

	private Node createSpacer() {
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		return spacer;
	}

	@Override
	protected void layoutChildren() {
		double w = getWidth();
		double h = getHeight();
		double tbHeight = toolBar.prefHeight(w);

		layoutInArea(toolBar, 0, h-tbHeight, w, tbHeight, 0, HPos.CENTER, VPos.CENTER);
	}

	@Override
	protected double computePrefWidth(double height) {
		return 750;
	}

	@Override
	protected double computePrefHeight(double width) {
		return 600;
	}
}
