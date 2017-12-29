package IHM;


import javafx.fxml.FXML;


import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ComboBox;
/**
 * Controleur de la fenetre d'option
 * 
 * @author Etienne Cayon
 *
 */
public class optionController {
	@FXML
	private TextField joueur1_tf;
	@FXML
	private TextField joueur2_tf;
	@FXML
	private TextField komi_tf;
	@FXML
	private ComboBox<String> taille_cb;
	
	
	private Main main;
	
	public void initialize() {
		taille_cb.getItems().addAll("9x9", "13x13", "19x19");
    }
	
	/**
	 * Lien vers le main
	 */
	public void setApp(Main main) {
		this.main = main;
	}
	/**
	 * Commencer la partie
	 */
	@FXML
	private void commencer(MouseEvent event) {
		if(taille_cb.getValue()==null) taille_cb.setValue("9x9");
		if(joueur1_tf.getText().equals("")) joueur1_tf.setText("Joueur1");
		if(joueur2_tf.getText().equals("")) joueur2_tf.setText("Joueur2");
		main.initGo(taille_cb.getValue(), Double.valueOf(komi_tf.getText()), joueur1_tf.getText(), joueur2_tf.getText());
	}

}
