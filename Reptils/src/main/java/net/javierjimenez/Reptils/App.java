package net.javierjimenez.Reptils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 
 * @author Surrui
 *
 */
public class App extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/reptils.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/reptils.css").toExternalForm());
			primaryStage.setTitle("Rèptils i amfibis de Catalunya");
			primaryStage.setScene(scene);
			primaryStage.show();
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}