package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		
		launch(args);

	}
//new main
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/PrimaryStage.fxml"));
			primaryStage.setTitle("TAB2XML");
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

