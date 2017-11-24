package fr.ensim.Go;

import java.io.Serializable;

public class Pierre  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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