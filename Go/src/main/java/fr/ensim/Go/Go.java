package fr.ensim.Go;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import IHM.Main;

public class Go implements Serializable{

	/**
	 * Classe principal permetant les différentes actions utilent au jeu
	 * @author Etienne Cayon
	 */
	private static final long serialVersionUID = 1L;
	private Plateau plateau;
	private List<Joueur> joueurs = new ArrayList<Joueur>();
	private int actualJoueur;
	
	private ArrayList<Intersection> pierreSupprime = new ArrayList<Intersection>();
	/**
	 * Constructeur avec choix de la taille du plateau
	 * 	@param taille
	 * 		taille du plateau
	 * @param komi
	 * 		nombre de point de départ du joueur blanc pour compenser le fait qu'il ne commence pas
	 * @param pseudo1
	 * 		pseudo du joueur 1
	 * @param pseudo2
	 * 		pseudo du joueur 2
	 */
	public Go(int taille, double komi, String pseudo1, String pseudo2){

		joueurs.add(new Joueur(pseudo1, new Pierre(CouleurPierre.Noire), 0));
		actualJoueur = 0;
		joueurs.add(new Joueur(pseudo2, new Pierre(CouleurPierre.Blanc), komi));
		plateau = new Plateau(taille);
	}
	/**
	 * Constructeur avec plateau classique de 19 par 19
	 * @param komi
	 * 		nombre de point de départ du joueur blanc pour compenser le fait qu'il ne commence pas
	 * @param pseudo1
	 * 		pseudo du joueur 1
	 * @param pseudo2
	 * 		pseudo du joueur 2
	 */
	public Go(String pseudo1, String pseudo2, double komi){
		
		joueurs.add(new Joueur(pseudo1, new Pierre(CouleurPierre.Noire), 0));
		actualJoueur = 0;
		joueurs.add(new Joueur(pseudo2, new Pierre(CouleurPierre.Blanc), komi));
		plateau = new Plateau(19);
	}
	
	/**
	 * Méthode principal pour jouer en mode console
	 */
	public void jouer(){
		while(partieFini() == false){		
			System.out.println(this);
			jouerPierreConsole();
		}
		System.out.println("\t---Suppression pierre morte---");
		while(suppressionPierreMorteConsole()){System.out.println(this);}
		
		calculerScore();
		
		System.out.println("\t---Fin de partie---");
		System.out.println(displayScore());
		
		if(getGagnant()!=null) {
			System.out.println("Victoire de "+getGagnant().getPseudo());
		}else{
			System.out.println("Match nul");
		}
		System.out.println(plateau);
	}
	
	/**
	 * Calcul le score des deux joueurs
	 */
	public void calculerScore() {
		plateau.calculScore(joueurs.get(0), joueurs.get(1));
	}
	
	/**
	 * Affichage des scores
	 * @return un String de l'affichage des scores
	 */
	public String displayScore() {
		return joueurs.get(0).getPseudo()+"\t["+joueurs.get(0).getScore()+"-"+joueurs.get(1).getScore()+"]\t"+joueurs.get(1).getPseudo();
	}
	
	/**
	 * Donne le gagnant
	 * @return le joueur gagnant
	 */
	public Joueur getGagnant() {
		if(joueurs.get(0).getScore()> joueurs.get(1).getScore()) return joueurs.get(0);
		if(joueurs.get(0).getScore()< joueurs.get(1).getScore()) return joueurs.get(1);
		return null;
	}
	
	/**
	 * Ajoute une pierre au plateau et passe au tour de l'adversaire
	 * @param x
	 * 		coordonnée X de la pierre joué
	 * @param y
	 * 		coordonnée Y de la pierre joué
	 * @return si la pierre a bien été joué
	 */
	public boolean jouerPierre(int x, int y){
		if(x == -1 && y == -1){
			joueurs.get(actualJoueur).setFin(true);
			System.out.println(joueurs.get(actualJoueur).getPseudo()+" passe son tour");
			actualJoueur=(actualJoueur+1)%2;
			return true;
		}else{
			joueurs.get(actualJoueur).setFin(false);
			int prisonniers = plateau.addPierre(x, y, joueurs.get(actualJoueur).getPierre());
			if(prisonniers != -1){
				joueurs.get(actualJoueur).addPrisonniers(prisonniers);
				Main.logger.info(joueurs.get(actualJoueur).getPseudo()+" a joué en ["+x+", "+y+"] et a fait "+prisonniers+" prisonnier(s)");
				actualJoueur=(actualJoueur+1)%2;
				return true;
			}else{
				Main.logger.warn("Coup non joué");
				return false;
			}
		}
			
	}
	
	/**
	 * Jouer une pierre depuis la console
	 */
	private void jouerPierreConsole(){
		String saisie ="";
		String[] coords;
		do{
			saisie=lireString();
			//On sauvegarde la partie
			if(saisie.equals("S")) {
				App.sauvegarde();
			}
			//On charge la partie
			if(saisie.equals("L")) {
				App.chargement();
			}
			coords = saisie.split(" ");
		}while(coords.length!=2 || tryParseInt(coords[0]) == false || tryParseInt(coords[1]) == false || jouerPierre(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])) == false);
	}
	
	/**
	 * Supression des pierres mortes en accord entre les deux joueurs
	 * @param x
	 * 		coordonnée X de la pierre à supprimer
	 * @param y
	 * 		coordonnée Y de la pierre à supprimer
	 * @return si la pierre a bien été supprimé
	 */
	public boolean suppressionPierreMorte(int x, int y){
		Pierre p = plateau.removePierre(x, y);
		if(p==null) {
			//Recherche dans les pierres déjà supprimé
			for(Intersection inter : pierreSupprime) {
				if(inter.getX()==x && inter.getY()==y){
					//On remet la pierre sur le plateau
					pierreSupprime.remove(inter);
					plateau.addPierre(x, y, inter.getPierre());
					return false;
				}
			}
			return false;
		}else {
			//Suppresion de la pierre
			pierreSupprime.add(new Intersection(x, y, p));
			return true;
		}
	}
	
	public ArrayList<Intersection> getPierreSupprime(){
		return pierreSupprime;
	}
	
	/**
	 * Supression des pierres mortes en accord entre les deux joueurs depuis la console
	 * @return si la pierre a bien été supprimé
	 */
	private boolean suppressionPierreMorteConsole(){
		System.out.println("Saisir coordonnées pierre morte(-1 -1 pour finir) : ");
		String saisie ="";
		String[] coords;
		do{
			saisie=lireString();
			coords = saisie.split(" ");
		}while(coords.length!=2 || tryParseInt(coords[0]) == false || tryParseInt(coords[1]) == false);
		if(Integer.parseInt(coords[0])==-1 && Integer.parseInt(coords[1]) == -1) {
			return false;
		}else {
			suppressionPierreMorte(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
			return true;
		}
	}
	
	/**
	 * Annuler la suppression des pierres morte, la partie continue
	 */
	public void annulerFin() {
		for(Joueur j : joueurs) {
			j.setFin(false);
		}
		for(Intersection inter : pierreSupprime) {
			plateau.addPierre(inter.getX(), inter.getY(), inter.getPierre());
		}
		pierreSupprime.clear();
	}
	
	/**
	 * Récuperer le joueur don c'est le tour de jouer
	 * @return le joueur à qui c'est le tour
	 */
	public Joueur getActualJoueur(){
		return joueurs.get(actualJoueur);
	}
	
	/**
	 * Lire un string saisie par l'utilisateur dans la console
	 * @return le String saisie par l'utilisateur
	 */
	private static String lireString ()   
    {
        String ligne_lue = null ;
        try
        {
            InputStreamReader lecteur = new InputStreamReader (System.in) ;
            BufferedReader entree = new BufferedReader (lecteur) ;
            ligne_lue = entree.readLine() ;
        }
        catch (IOException err)
        {
            System.exit(0) ;
        }
        return ligne_lue ;
    }
	
	/**
	 * Test si le string est bien castable en entier
	 * @param String a tester
	 * @return si le String est bien un entier
	 */
	boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
	
	/**
	 * Affichage des informations de jeu pour la console
	 */
	public String toString(){
		String print = "(";
		print+=joueurs.get(0).getPierre()+")"+joueurs.get(0).getPseudo()+" : "+joueurs.get(0).getNbPrisonniers()+"\tVs\t"+joueurs.get(1).getNbPrisonniers()+" : "+joueurs.get(1).getPseudo()+"("+joueurs.get(1).getPierre()+")\n";
		print+=plateau;
		return print;
	}
	
	/**
	 * Permet de savoir si la partie est finie
	 * @return si la partie est finie
	 */
	public boolean partieFini() {
		return joueurs.get(0).getFin() == true && joueurs.get(1).getFin() == true;
	}

	public Plateau getPlateau() {
		return plateau;
	}
	
	public List<Joueur> getJoueurs(){
		return joueurs;
	}
	

}
