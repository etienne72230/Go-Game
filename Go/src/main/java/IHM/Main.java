package IHM;

import java.io.IOException;

import fr.ensim.Go.Go;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Classe principal pour lancer le jeu en version graphique
 * @author Etienne Cayon
 *
 */
public class Main extends Application {
	
	
	private  Go go;
	private GoController controllerGo;
	private optionController controllerOption;
	private Stage stageOption;
	private Stage stageGo;
	@Override
	public void start(Stage stage) {
		stageGo = stage;
		FXMLLoader loaderGo = new FXMLLoader(getClass().getResource("Go.fxml"));
		FXMLLoader loaderOption = new FXMLLoader(getClass().getResource("option.fxml"));
		
		try {
			Scene sceneGo = new Scene((Parent) loaderGo.load());
	        Scene sceneOption = new Scene((Parent) loaderOption.load());
			controllerGo = loaderGo.<GoController>getController();
			controllerOption = loaderOption.<optionController>getController();
			controllerOption.setApp(this);
			
	        stageGo.setScene(sceneGo);
	        stageOption = new Stage();
	        stageOption.setScene(sceneOption);
	        stageOption.setResizable(false);
	        stageOption.setTitle("Nouvelle partie");
	        stageGo.setResizable(false);
	        stageGo.setTitle("Jeu de Go");
	        stageOption.initModality(Modality.APPLICATION_MODAL);
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
	/**
	 * Création d'une nouvelle partie
	 */
	public void nouvellePartie() {
		FXMLLoader loaderOption = new FXMLLoader(getClass().getResource("option.fxml"));
		Scene sceneOption;
		try {
			sceneOption = new Scene((Parent) loaderOption.load());
			controllerOption = loaderOption.<optionController>getController();
			controllerOption.setApp(this);
			stageOption = new Stage();
	        stageOption.setScene(sceneOption);
	        stageOption.setResizable(false);
	        stageOption.setTitle("Nouvelle partie");
	        stageOption.initModality(Modality.APPLICATION_MODAL);
			stageOption.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialisation du jeu
	 * @param taille_str
	 * 		taille du plateau
	 * @param komi
	 * 		nombre de point de départ du joueur blanc pour compenser le fait qu'il ne commence pas
	 * @param j1
	 * 		pseudo joueur 1
	 * @param j2
	 * 		pseudo joueur 2
	 */
	public void initGo(String taille_str, Double komi, String j1, String j2) {
		stageOption.close();
		int taille;
		switch (taille_str){
			case "9x9": taille = 9;stageGo.setWidth(790); stageGo.setHeight(580); break;
			case "13x13": taille = 13;stageGo.setWidth(950); stageGo.setHeight(760); break;
			case "19x19": taille =  19;stageGo.setWidth(1220); stageGo.setHeight(1000); break;
			default : taille = 9;stageGo.setWidth(790); stageGo.setHeight(550); break;
		}
		stageGo.centerOnScreen();
		go = new Go(taille, komi, j1, j2);
		controllerGo.LauchGame(go, this);
	}

}
