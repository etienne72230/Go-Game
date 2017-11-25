package IHM;


import javafx.application.Application;
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
	
	private Main main;
	
	public void initialize() {
		taille_cb.getItems().addAll("9x9", "13x13", "19x19");
		commencer_btn.setOnMouseClicked(this::commencer);
    }
	
	public void setApp(Main main) {
		this.main = main;
	}
	
	private void commencer(MouseEvent event) {
		main.initGo(taille_cb.getValue(), Double.valueOf(komi_tf.getText()), joueur1_tf.getText(), joueur2_tf.getText());
	}

}
