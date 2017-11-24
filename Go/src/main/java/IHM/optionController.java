package IHM;


import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ComboBox;

public class optionController {
	@FXML
	private TextField joueur1_tf;
	@FXML
	private TextField joueur2_tf;
	@FXML
	private Button commencer_btn;
	@FXML
	private TextField komi_tf;
	@FXML
	private ComboBox<String> taille_cb;
	
	private GoController goController;
	
	public void initialize() {
		taille_cb.getItems().addAll("9X9", "13X13", "19X19");
		commencer_btn.setOnMouseClicked(this::commencer);
    }
	
	private void setController(GoController gc) {
		goController=gc;
	}
	
	private void commencer(MouseEvent event) {
		goController.initGo();
		//TODO à finir
	}

}
