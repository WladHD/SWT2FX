package de.swt;

import de.swt.mysql.MySQL;
import de.swt.produktverwaltung.ProduktverwaltungDataInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	boolean debug = false;

	public static void main(String[] args) {
		launch(args);
	}

	private static BorderPane root;

	@Override
	public void start(Stage primaryStage) {
		if (debug)
			ProduktverwaltungDataInterface.getProduktverwaltung().generateTestData();

		try {
			root = (BorderPane) FXMLLoader.load(getClass().getResource("/de/swt/scenes/Main.fxml"));
			Scene scene = new Scene(root, 1200, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			MySQL.getInstance().getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BorderPane getRoot() {
		return root;
	}
}
