package org.sleeksnap.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Nikki
 */
public class SleeksnapUIApplication extends Application {

	private Scene scene;

	@Override
	public void start(Stage stage) throws Exception {
		// create the scene
		stage.setTitle("Sleeksnap Settings");
		stage.getIcons().add(new Image("icon.png"));
		scene = new Scene(new UIRegion(),750,500, Color.web("#666970"));
		stage.setScene(scene);
		scene.getStylesheets().add("application.css");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
