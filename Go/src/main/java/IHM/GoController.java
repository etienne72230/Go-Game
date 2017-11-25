package IHM;

import java.io.File;

import javax.imageio.ImageIO;

import fr.ensim.Go.Go;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.MenuItem;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Menu;

public class GoController {
	@FXML
	private MenuItem sauvegarder_btn;
	@FXML
	private MenuItem charger_btn;
	@FXML
	private MenuItem nouvellePartie_btn;
	@FXML
	private Menu info_btn;
	@FXML
	private Button j1Passe_btn;
	@FXML
	private Label score_lbl;
	@FXML
	private Button j2Passe_btn;
	@FXML
	private ImageView plateau_img;
	@FXML
	private ImageView bolNoire_img;
	@FXML
	private ImageView bolBlanc_img;
	@FXML
	private GridPane grid_pane;
	
	public  Go go;
	private int plateauX;
	private int plateauY;
	
	public void LauchGame(Go go) {
		this.go = go;
		plateau_img.setImage(new Image(getClass().getResource("/goban_"+go.getPlateau().getTaille()+".png").toString()));
		score_lbl.setText(go.getJoueurs().get(0).getPseudo()+"     Vs     "+go.getJoueurs().get(1).getPseudo());
		bolBlanc_img.setVisible(false);
		j2Passe_btn.setDisable(true);
		grid_pane.setOnMouseMoved(this::mouseMoved);
		plateauX = (int)(plateau_img.getLayoutX()-(plateau_img.getImage().getWidth()/2));
		plateauY = (int)plateau_img.getLayoutY();
	}
	
	private void mouseMoved(MouseEvent event) {
		System.out.println("X : "+getGridX((int)event.getX())+" Y : "+getGridY((int)event.getY()));
	}
	
	public void redimension() {
		plateauX = (int)(plateau_img.getX()-(plateau_img.getImage().getWidth()/2));
		plateauY = (int)plateau_img.getLayoutY();
	}
	
	private int getDispX(int x){
		return 6+plateauX+x*44;
	}
	private int getDispY(int y){
		return 6+plateauY+y*44;
	}
	
	private int getGridX(int x){
		int index = (x-6-plateauX)/44;
		if(index<0) index=0;
		if(index>go.getPlateau().getTaille()-1) index=go.getPlateau().getTaille()-1;
		return index;
	}
	private int getGridY(int y){
		int index = (y-6-plateauY)/44;
		if(index<0) index=0;
		if(index>go.getPlateau().getTaille()-1) index=go.getPlateau().getTaille()-1;
		return index;
	}
}
