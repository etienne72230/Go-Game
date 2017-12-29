package IHM;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;

import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class infoController {
	@FXML
	private Button ok_btn;
	@FXML
	private Hyperlink regle_hl;
	@FXML
	private ImageView profil_img;
	
	@FXML
	public void initialize() {
		profil_img.setImage(new Image(getClass().getResource("/profil.jpg").toString()));
	}
	// Event Listener on Button[#ok_btn].onAction
	@FXML
	public void Quitter(ActionEvent event) {
		Stage stage = (Stage) ok_btn.getScene().getWindow();
		stage.close();
	}
	// Event Listener on Hyperlink[#regle_hl].onAction
	@FXML
	public void linkCliqued(ActionEvent event) throws IOException, URISyntaxException {
		Desktop desktop = Desktop.getDesktop();
        desktop.browse(new URI("https://fr.wikipedia.org/wiki/R%C3%A8gles_du_go#R%C3%A8gle_chinoise"));

	}
}
