package IHM;

import java.io.File;

import javax.imageio.ImageIO;

import fr.ensim.Go.CouleurPierre;
import fr.ensim.Go.Go;
import fr.ensim.Go.Intersection;
import fr.ensim.Go.Plateau;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.MenuItem;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
	@FXML 
	private Pane pierre_pane;
	@FXML
	private Label message_lbl;
	
	public  Go go;
	private int plateauX;
	private int plateauY;
	private boolean partieFini;
	private ImageView pierreFantome_img;
	private ImageView croix_img;
	
	public void LauchGame(Go go) {
		partieFini = false;
		this.go = go;
		
		pierreFantome_img = new ImageView(new Image(getClass().getResource("/pierre_noire.png").toString()));
		pierreFantome_img.setOpacity(0.75);
		pierre_pane.getChildren().add(pierreFantome_img);
		
		croix_img = new ImageView(new Image(getClass().getResource("/croix.png").toString()));
		croix_img.setVisible(false);
		
		plateau_img.setImage(new Image(getClass().getResource("/goban_"+go.getPlateau().getTaille()+".png").toString()));
		score_lbl.setText(go.getJoueurs().get(0).getPseudo()+"     Vs     "+go.getJoueurs().get(1).getPseudo());
		bolBlanc_img.setVisible(false);
		j2Passe_btn.setDisable(true);
		grid_pane.setOnMouseMoved(this::mouseMoved);
		grid_pane.setOnMouseClicked(this::mouseClicked);
		j1Passe_btn.setOnAction(this::passeClicked);
		j2Passe_btn.setOnAction(this::passeClicked);
		
		plateauX = (int)(plateau_img.getLayoutX()-(plateau_img.getImage().getWidth()/2));
		plateauY = (int)plateau_img.getLayoutY();
		
	}
	
	private void mouseMoved(MouseEvent event) {
		//System.out.println("X : "+getGridX((int)event.getX())+" Y : "+getGridY((int)event.getY()));

		if(partieFini == false) {
			pierreFantome_img.setLayoutX(getDispX(getGridX((int)event.getX())));
			pierreFantome_img.setLayoutY(getDispY(getGridY((int)event.getY())));
			if(go.getActualJoueur().getPierre().getCouleur() == CouleurPierre.Noire) {
				pierreFantome_img.setImage(new Image(getClass().getResource("/pierre_noire.png").toString()));
			}else {
				pierreFantome_img.setImage(new Image(getClass().getResource("/pierre_blanche.png").toString()));
			}
		}else {
			croix_img.setLayoutX(getDispX(getGridX((int)event.getX())));
			croix_img.setLayoutY(getDispY(getGridY((int)event.getY())));
		}
	}
	
	private void mouseClicked(MouseEvent event) {
		if(partieFini == false) {
			if(go.jouerPierre(getGridX((int)event.getX()), getGridY((int)event.getY()))) {
				message_lbl.setText("");
			}else {
				message_lbl.setText("Coup Impossible");
			}
		}else {
			go.suppressionPierreMorte(getGridX((int)event.getX()), getGridY((int)event.getY()));
			j1Passe_btn.setDisable(false);
			j2Passe_btn.setDisable(false);
		}
		displayUpdate();
	}
	
	//Le joueur passe son tour
	private void passeClicked(ActionEvent event) {
		go.jouerPierre(-1, -1);
		displayUpdate();
	}
	
	private void validerClicked(ActionEvent event) {
		if(event.getSource().equals(j1Passe_btn)) {
			j1Passe_btn.setDisable(true);
		}else {
			j2Passe_btn.setDisable(true);
		}
		
		//Fin de la phase de suppresion des pierres mortes
		if(j1Passe_btn.isDisable() && j2Passe_btn.isDisable()) {
			go.calculerScore();
			displayUpdate();
			
			score_lbl.setText(go.displayScore());
			message_lbl.setText("Victoire de "+go.getGagnant().getPseudo());
			j1Passe_btn.setVisible(false);
			j2Passe_btn.setVisible(false);
			grid_pane.setOnMouseClicked(null);
			grid_pane.setOnTouchMoved(null);
			croix_img.setVisible(false);
		}
	}
	
	/*Redimension désactivé
	public void redimension() {
		plateauX = (int)(plateau_img.getX()-(plateau_img.getImage().getWidth()/2));
		plateauY = (int)plateau_img.getLayoutY();
	}*/
	
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
	
	private void displayUpdate() {
		if(go.getActualJoueur().getPierre().getCouleur() == CouleurPierre.Noire) {
			j1Passe_btn.setDisable(false);
			j2Passe_btn.setDisable(true);
			bolNoire_img.setVisible(true);
			bolBlanc_img.setVisible(false);
		}else {
			j1Passe_btn.setDisable(true);
			j2Passe_btn.setDisable(false);
			bolNoire_img.setVisible(false);
			bolBlanc_img.setVisible(true);
		}
		drawPierre();
		
		//Fin de partie, suppression des pierres mortes
		if(go.partieFini()) {
			partieFini = true;
			message_lbl.setText("Partie Terminé !\nSupprimez les pierres mortes");
			pierreFantome_img.setVisible(false);
			croix_img.setVisible(true);
			j1Passe_btn.setDisable(false);
			j2Passe_btn.setDisable(false);
			j1Passe_btn.setText("Valider");
			j2Passe_btn.setText("Valider");
			j1Passe_btn.setOnAction(this::validerClicked);
			j2Passe_btn.setOnAction(this::validerClicked);
			bolBlanc_img.setVisible(false);
			bolNoire_img.setVisible(false);
		}
	}
	
	private void drawPierre() {
		Plateau plateau = go.getPlateau();
		pierre_pane.getChildren().clear();
		pierre_pane.getChildren().add(pierreFantome_img);	
		for(Intersection i: plateau.getIntersections()){
			if(i.getPierre()!=null){
				ImageView pierre_img;
				if(i.getPierre().getCouleur() == CouleurPierre.Blanc){
					pierre_img = new ImageView(new Image(getClass().getResource("/pierre_blanche.png").toString()));
					pierre_img.setLayoutX(getDispX(i.getX()));
					pierre_img.setLayoutY(getDispY(i.getY()));
					pierre_pane.getChildren().add(pierre_img);
				}else{
					pierre_img = new ImageView(new Image(getClass().getResource("/pierre_noire.png").toString()));
					pierre_img.setLayoutX(getDispX(i.getX()));
					pierre_img.setLayoutY(getDispY(i.getY()));
					pierre_pane.getChildren().add(pierre_img);
				}
			}
		}
		pierre_pane.getChildren().add(croix_img);
	}
}
