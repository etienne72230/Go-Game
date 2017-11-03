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
	
	public Go(String pseudo1, String pseudo2){
		joueurs.add(new Joueur(pseudo1, new Pierre(CouleurPierre.Noire)));
		actualJoueur = 0;
		joueurs.add(new Joueur(pseudo2, new Pierre(CouleurPierre.Blanc)));
		
		plateau = new Plateau();
	}
	
	public void jouer(){
		String saisie ="";
		String[] coords;
		while(true){
			do{
				saisie=lireString();
				coords = saisie.split(" ");
			}while(coords.length!=2 || tryParseInt(coords[0]) == false || tryParseInt(coords[1]) == false || jouerPierre(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])) == false);
			
			System.out.println(this);
		}
	}
	
	
	private boolean jouerPierre(int x, int y){
		if(plateau.addPierre(x, y, joueurs.get(actualJoueur).getPierre())){
			actualJoueur=(actualJoueur+1)%2;
			return true;
		}else{
			return false;
		}
	}
	
	public Joueur getActualJoueur(){
		return joueurs.get(actualJoueur);
	}
	
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
	
	boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
	
	public String toString(){
		String print = "\t";
		print+=joueurs.get(0).getPseudo()+" ("+joueurs.get(0).getPierre()+")\t Vs\t "+joueurs.get(1).getPseudo()+" ("+joueurs.get(1).getPierre()+")\n";
		print+=plateau;
		return print;
	}

}
