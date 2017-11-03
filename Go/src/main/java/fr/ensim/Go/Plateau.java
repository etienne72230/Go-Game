package fr.ensim.Go;

import java.util.ArrayList;
import java.util.List;

public class Plateau {

	private int taille;
	private List<Intersection> intersections = new ArrayList<Intersection>();
	
	public Plateau(int taille){
		this.taille = taille;
		for(int y = 0; y < taille ; y++){
			for(int x = 0; x < taille ; x++){
				intersections.add(new Intersection(x, y));
			}
		}
	}
	
	//Ajout d'une pierre sur une intersection
	public boolean addPierre(int x, int y, Pierre p){
		
		for(Intersection inter : intersections){
			if( inter.getX() == x && inter.getY() == y){
				if(inter.getPierre()==null){
					inter.setPierre(p);
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	
	//Suppression d'une pierre sur une intersection
	public boolean removePierre(int x, int y){
		for(Intersection inter : intersections){
			if( inter.getX() == x && inter.getY() == y){
				inter.setPierre(null);
				return true;
			}
		}
		return false;		
	}
	
	//Retourne la pierre sur l'intersection voulue s'il y en a une
	public Pierre getPierre(int x, int y){
		
		for(Intersection inter : intersections){
			if( inter.getX() == x && inter.getY() == y){
				return inter.getPierre();
			}
		}
		return null;
	}
	
	//Retourne la taille du plateau
	public int getTaille(){
		return taille;
	}
	
	public String toString(){
		String print = "";
		for(int i=0 ; i<taille*3 ; i++) print+="#";
		print+="##\n";
		for(int y = 0; y < taille ; y++){
			print+="#";
			for(int x = 0; x < taille ; x++){
				if(intersections.get(x+taille*y).getPierre()!=null){
					print+=" "+intersections.get(x+taille*y).getPierre()+" ";
				}else{
					print+=" + ";
				}
			}
			print+="#\n";
		}
		for(int i=0 ; i<taille*3 ; i++) print+="#";
		print+="##";
		return print;
	}
	
}
