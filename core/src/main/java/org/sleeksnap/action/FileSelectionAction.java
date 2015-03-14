package org.sleeksnap.action;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;

import org.sleeksnap.upload.Upload;
import org.sleeksnap.upload.types.FileUpload;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Nikki
 */
public class FileSelectionAction implements Action<Upload> {

	public static void main(String[] args) {
		new JFXPanel();
		new FileSelectionAction().execute(null);
	}

	@Override
	public void execute(Consumer<Upload> callback) {
		Platform.runLater(() -> openChooser(callback));
	}

	private void openChooser(Consumer<Upload> callback) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Select a file to upload");
		Optional<File> chosen = Optional.ofNullable(chooser.showOpenDialog(null));

		if (chosen.isPresent()) {
			callback.accept(new FileUpload(chosen.get()));
		}
	}

}
