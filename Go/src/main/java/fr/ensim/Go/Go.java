package fr.ensim.Go;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Go {

	private Plateau plateau;
	private List<Joueur> joueurs = new ArrayList<Joueur>();
	private int actualJoueur;
	
	public Go(int taille, String pseudo1, String pseudo2){
		
		joueurs.add(new Joueur(pseudo1, new Pierre(CouleurPierre.Noire)));
		actualJoueur = 0;
		joueurs.add(new Joueur(pseudo2, new Pierre(CouleurPierre.Blanc)));
		plateau = new Plateau(taille);
	}
	
	public Go(String pseudo1, String pseudo2){
		
		joueurs.add(new Joueur(pseudo1, new Pierre(CouleurPierre.Noire)));
		actualJoueur = 0;
		joueurs.add(new Joueur(pseudo2, new Pierre(CouleurPierre.Blanc)));
		plateau = new Plateau(19);
	}
	
	//Lancement du jeu
	public void jouer(){
		while(joueurs.get(0).getFin() == false || joueurs.get(1).getFin() == false){		
			System.out.println(this);
			jouerPierreConsole();
		}
		System.out.println("\t---Fin de partie---");
		System.out.println(joueurs.get(0).getPseudo()+" : "+joueurs.get(0).getScore()+"\tVs\t"+joueurs.get(1).getScore()+" : "+joueurs.get(1).getPseudo());
		if(joueurs.get(0).getScore()> joueurs.get(1).getScore()){
			System.out.println("Victoire de "+joueurs.get(0).getPseudo());
		}else{
			System.out.println("Victoire de "+joueurs.get(1).getPseudo());
		}
	}
	
	
	//Ajoute une pierre au plateau et passe au tour de l'adversaire
	private boolean jouerPierre(int x, int y){
		if(x == -1 && y == -1){
			joueurs.get(actualJoueur).setFin(true);
			System.out.println(joueurs.get(actualJoueur).getPseudo()+" passe son tour");
			actualJoueur=(actualJoueur+1)%2;
			return true;
		}else{
			joueurs.get(actualJoueur).setFin(false);
			int points = plateau.addPierre(x, y, joueurs.get(actualJoueur).getPierre());
			if(points != -1){
				joueurs.get(actualJoueur).addPoint(points);
				actualJoueur=(actualJoueur+1)%2;
				return true;
			}else{
				return false;
			}
		}
			
	}
	
	//Retourne le joueur a qui c'est le tour de jouer
	public Joueur getActualJoueur(){
		return joueurs.get(actualJoueur);
	}
	
	//Jouer une pierre depuis la console
	private void jouerPierreConsole(){
		String saisie ="";
		String[] coords;
		do{
			saisie=lireString();
			coords = saisie.split(" ");
		}while(coords.length!=2 || tryParseInt(coords[0]) == false || tryParseInt(coords[1]) == false || jouerPierre(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])) == false);
	}
	
	//Lire un string saisie par l'utilisateur dans la console
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
	
	//Test si le string est bien castable en entier
	boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
	
	public String toString(){
		String print = "(";
		print+=joueurs.get(0).getPierre()+")"+joueurs.get(0).getPseudo()+" : "+joueurs.get(0).getScore()+"\tVs\t"+joueurs.get(1).getScore()+" : "+joueurs.get(1).getPseudo()+"("+joueurs.get(1).getPierre()+")\n";
		print+=plateau;
		return print;
	}

}
