package fr.ensim.Go;

import java.util.ArrayList;
import java.util.List;

public class Plateau {

	private List<Intersection> intersections = new ArrayList<Intersection>();
	
	public Plateau(){
		for(int y = 0; y < 19 ; y++){
			for(int x = 0; x < 19 ; x++){
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
	
	public String toString(){
		String print = "###########################################################\n";
		for(int y = 0; y < 19 ; y++){
			print+="#";
			for(int x = 0; x < 19 ; x++){
				if(intersections.get(x+19*y).getPierre()!=null){
					print+=" "+intersections.get(x+19*y).getPierre()+" ";
				}else{
					print+=" + ";
				}
			}
			print+="#\n";
		}
		print += "###########################################################";
		return print;
	}
	
}
