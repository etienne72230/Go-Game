package fr.ensim.Go;

public class Pierre {
	private CouleurPierre couleur;
	
	public Pierre(CouleurPierre c){
		couleur = c;
	}
	
	public CouleurPierre getCouleur(){
		return couleur;
	}
	
	public String toString(){
		String print ="";
		print+= couleur.toString().charAt(0);
		return print;
	}
}