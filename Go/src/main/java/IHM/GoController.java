package IHM;

import fr.ensim.Go.CouleurPierre;
import fr.ensim.Go.Go;
import fr.ensim.Go.Intersection;
import fr.ensim.Go.Plateau;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.MenuItem;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Menu;

/**
 * Classe principal pour la gestion graphique du jeu
 * @author Etienne Cayon
 *
 */
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
	@FXML
	private Button annuler_btn;
	@FXML
	private ImageView fond_img;
	
	public  Go go;
	private int plateauX;
	private int plateauY;
	private boolean partieFini;
	private ImageView pierreFantome_img;
	private ImageView croix_img;
	
	private Image pierreNoire_img;
	private Image pierreBlanche_img;
	
	private Main main;
	/**
	 * Lancer le jeu
	 * @param go
	 * 		classe go du jeu
	 * @param main
	 * 		lien vers la classe main
	 */
	public void LauchGame(Go go, Main main) {
		
		this.go = go;
		this.main = main;
		
		partieFini = false;
		
		pierreNoire_img = new Image(getClass().getResource("/pierre_noire.png").toString());
		pierreBlanche_img = new Image(getClass().getResource("/pierre_blanche.png").toString());
		
		pierreFantome_img = new ImageView(pierreNoire_img);
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
		nouvellePartie_btn.setOnAction(this::nouvellePartie);
		annuler_btn.setOnAction(this::annulerFin);
		
		j1Passe_btn.setOnAction(this::passeClicked);
		j2Passe_btn.setOnAction(this::passeClicked);
		j1Passe_btn.setVisible(true);
		j2Passe_btn.setVisible(true);
		j1Passe_btn.setText("Passer");
		j2Passe_btn.setText("Passer");
		message_lbl.setText("");
		
		fond_img.setPreserveRatio(false);
		
		plateauY = 120;
		switch(go.getPlateau().getTaille()) {
		case 9: plateauX = 191; break;
		case 13: plateauX = 184; break;
		case 19: plateauX = 187; break;
		}
		/*plateauX = (int)(plateau_img.getLayoutX()-(plateau_img.getImage().getWidth()/2));
		plateauY = (int)plateau_img.getLayoutY();*/
		displayUpdate();
	}

	//Création nouvelle partie
	private void nouvellePartie(ActionEvent event) {
		main.nouvellePartie();
	}
	
	//Souris bougé
	private void mouseMoved(MouseEvent event) {
		//System.out.println("X : "+getGridX((int)event.getX())+" Y : "+getGridY((int)event.getY()));
		if(partieFini == false) {
			pierreFantome_img.setLayoutX(getDispX(getGridX((int)event.getX())));
			pierreFantome_img.setLayoutY(getDispY(getGridY((int)event.getY())));
			if(go.getActualJoueur().getPierre().getCouleur() == CouleurPierre.Noire) {
				pierreFantome_img.setImage(pierreNoire_img);
			}else {
				pierreFantome_img.setImage(pierreBlanche_img);
			}
		}else {
			croix_img.setLayoutX(getDispX(getGridX((int)event.getX())));
			croix_img.setLayoutY(getDispY(getGridY((int)event.getY())));
		}
	}
	
	//Clic de la souris
	private void mouseClicked(MouseEvent event) {
		if(event.getTarget()==plateau_img) {
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
	}
	
	//Le joueur passe son tour
	private void passeClicked(ActionEvent event) {
		go.jouerPierre(-1, -1);
		displayUpdate();
	}
	
	//Annulation du mode suppression de pierre, la partie continue
	private void annulerFin(ActionEvent event) {
		partieFini = false;
		go.annulerFin();
		pierreFantome_img.setVisible(true);
		croix_img.setVisible(false);
		j1Passe_btn.setText("Passer");
		j2Passe_btn.setText("Passer");
		j1Passe_btn.setOnAction(this::passeClicked);
		j2Passe_btn.setOnAction(this::passeClicked);
		annuler_btn.setVisible(false);
		displayUpdate();
	}
	
	//Le joueur valide les pierres supprimées
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
			partieFini=false;
			annuler_btn.setVisible(false);
			drawPierre();
		}
	}
	
	/*Redimension (désactivé)
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
	
	//Mise à jour de l'affichage
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
		
		//Fin de partie, suppression des pierres mortes
		if(go.partieFini()) {
			partieFini = true;
			message_lbl.setText(" ");
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
			annuler_btn.setVisible(true);
		}
		drawPierre();
	}
	
	//Affichage des pierres du plateau
	private void drawPierre() {
		Plateau plateau = go.getPlateau();
		pierre_pane.getChildren().clear();
		pierre_pane.getChildren().add(pierreFantome_img);	
		for(Intersection i: plateau.getIntersections()){
			if(i.getPierre()!=null){
				ImageView pierre_img;
				if(i.getPierre().getCouleur() == CouleurPierre.Blanc){
					pierre_img = new ImageView(pierreBlanche_img);
					pierre_img.setLayoutX(getDispX(i.getX()));
					pierre_img.setLayoutY(getDispY(i.getY()));
					pierre_pane.getChildren().add(pierre_img);
				}else{
					pierre_img = new ImageView(pierreNoire_img);
					pierre_img.setLayoutX(getDispX(i.getX()));
					pierre_img.setLayoutY(getDispY(i.getY()));
					pierre_pane.getChildren().add(pierre_img);
				}
			}
		}
		if(partieFini) {
			for(Intersection i : go.getPierreSupprime()) {
				ImageView pierreSupprime_img;
				if(i.getPierre().getCouleur() == CouleurPierre.Blanc){
					pierreSupprime_img = new ImageView(pierreBlanche_img);
					pierreSupprime_img.setLayoutX(getDispX(i.getX()));
					pierreSupprime_img.setLayoutY(getDispY(i.getY()));
					pierreSupprime_img.setOpacity(0.75);
					pierre_pane.getChildren().add(pierreSupprime_img);
				}else{
					pierreSupprime_img = new ImageView(pierreNoire_img);
					pierreSupprime_img.setLayoutX(getDispX(i.getX()));
					pierreSupprime_img.setLayoutY(getDispY(i.getY()));
					pierreSupprime_img.setOpacity(0.75);
					pierre_pane.getChildren().add(pierreSupprime_img);
				}
			}
			pierre_pane.getChildren().add(croix_img);
		}
		
	}
}
