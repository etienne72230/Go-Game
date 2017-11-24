package IHM;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage stageGo) {
		FXMLLoader loaderGo = new FXMLLoader(getClass().getResource("Go.fxml"));
		GoController controllerGo = loaderGo.<GoController>getController();
		FXMLLoader loaderOption = new FXMLLoader(getClass().getResource("option.fxml"));
		optionController controllerOption = loaderOption.<optionController>getController();
		try {
			Scene sceneGo = new Scene((Parent) loaderGo.load());
	        Scene sceneOption = new Scene((Parent) loaderOption.load());
	        
	        stageGo.setScene(sceneGo);
	        Stage stageOption = new Stage();
	        stageOption.setScene(sceneOption);
	        stageGo.show();
	        stageOption.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
