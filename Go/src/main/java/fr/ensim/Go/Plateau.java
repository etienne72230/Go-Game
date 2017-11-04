package fr.ensim.Go;

import java.util.ArrayList;
import java.util.List;

public class Plateau {

	private int taille;
	private List<Intersection> intersections = new ArrayList<Intersection>();
	private List<Integer> indexPrisonniers = new ArrayList<Integer>();
	
	public Plateau(int taille){
		this.taille = taille;
		for(int y = 0; y < taille ; y++){
			for(int x = 0; x < taille ; x++){
				intersections.add(new Intersection(x, y));
			}
		}
	}
	
	//Ajout d'une pierre sur une intersection
	public int addPierre(int x, int y, Pierre p){
		int prisonniers=0;
		for(Intersection inter : intersections){
			if( inter.getX() == x && inter.getY() == y){
				if(inter.getPierre()==null){
					inter.setPierre(p);
					prisonniers += updatePlateau(x, y, p);
					//Si le joueur ne fait pas de point il ne faut pas qu'il soit fait prisonnier (coup suicidaire)
					if(prisonniers==0){
						indexPrisonniers.clear();
						if(p.getCouleur()==CouleurPierre.Blanc) groupePrisonnier(x, y, new Pierre(CouleurPierre.Noire));
						if(p.getCouleur()==CouleurPierre.Noire) groupePrisonnier(x, y, new Pierre(CouleurPierre.Blanc));
						if(isPrisonniers()){
							removePierre(x, y);
							System.out.println("Coup suicidaire");
							return -1;
						}
					}
				return prisonniers;
				}
			}
		}
		return -1;
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
	
	//Mise a jour du plateau retourne le nombre de pierre enprisonné
	//Retourne -1 si le coup n'est pas jouable
	//Retourne sinon le nombre de prisonnier effectué
	public int updatePlateau(int x, int y, Pierre p){
		int prisonnier = 0;
		for(Intersection inter : intersections){
			//Pierre du haut
			if(inter.getPierre() !=null && inter.getX() == x && inter.getY() == y-1){
				if(inter.getPierre().getCouleur()!= p.getCouleur()){
					indexPrisonniers.clear();
					groupePrisonnier(x, y-1, p);
					if(isPrisonniers()){
						prisonnier += removePrisonniers();
					}
				}
			}
			//Pierre de droite
			if(inter.getPierre() !=null && inter.getX() == x+1 && inter.getY() == y){
				if(inter.getPierre().getCouleur()!=p.getCouleur()){
					indexPrisonniers.clear();
					groupePrisonnier(x+1, y, p);
					if(isPrisonniers()){
						prisonnier += removePrisonniers();
					}
					
				}				
			}
			//Pierre du bas
			if(inter.getPierre() !=null && inter.getX() == x && inter.getY() == y+1){
				if(inter.getPierre().getCouleur()!=p.getCouleur()){
					indexPrisonniers.clear();
					groupePrisonnier(x, y+1, p);
					if(isPrisonniers()){
						prisonnier += removePrisonniers();
					}
				}					
			}
			//Pierre de gauche
			if(inter.getPierre() !=null && inter.getX() == x-1 && inter.getY() == y){
				if(inter.getPierre().getCouleur()!=p.getCouleur()){
					indexPrisonniers.clear();
					groupePrisonnier(x-1, y, p);
					if(isPrisonniers()){
						prisonnier += removePrisonniers();
					}
				}					
			}
		}
		return prisonnier;
	}
	
	//Créer une liste des pierres de meme couleur qui se touche
	//elle sont potentiellement prisonnière
	private void groupePrisonnier(int x, int y, Pierre p){
		for(int i=0; i<intersections.size(); i++){
			
			//Elle même
			if(intersections.get(i).getX() == x && intersections.get(i).getY() == y){
				if(indexPrisonniers.indexOf(i)==-1){
					indexPrisonniers.add(i);
				}
			}
			
			//Pierre du haut
			if(intersections.get(i).getX() == x && intersections.get(i).getY() == y-1){
				if(intersections.get(i).getPierre()!= null && intersections.get(i).getPierre().getCouleur() != p.getCouleur()){
					if(indexPrisonniers.indexOf(i)==-1){
						indexPrisonniers.add(i);
						groupePrisonnier(x, y-1, p);
					}
				}
			}
			
			//Pierre de droite
			if(intersections.get(i).getX() == x+1 && intersections.get(i).getY() == y){
				if(intersections.get(i).getPierre()!= null && intersections.get(i).getPierre().getCouleur() != p.getCouleur()){
					if(indexPrisonniers.indexOf(i)==-1){
						indexPrisonniers.add(i);
						groupePrisonnier(x+1, y, p);
					}
				}
			}
			
			//Pierre du bas
			if(intersections.get(i).getX() == x && intersections.get(i).getY() == y+1){
				if(intersections.get(i).getPierre()!= null && intersections.get(i).getPierre().getCouleur() != p.getCouleur()){
					if(indexPrisonniers.indexOf(i)==-1){
						indexPrisonniers.add(i);
						groupePrisonnier(x, y+1, p);
					}
				}
			}
			
			//Pierre de gauche
			if(intersections.get(i).getX() == x-1 && intersections.get(i).getY() == y){
				if(intersections.get(i).getPierre()!= null && intersections.get(i).getPierre().getCouleur() != p.getCouleur()){
					if(indexPrisonniers.indexOf(i)==-1){
						indexPrisonniers.add(i);
						groupePrisonnier(x-1, y, p);
					}
				}
			}
			
		}
	}
	
	//Verifie que le groupe est prisonnié
	private boolean isPrisonniers(){
		for(int i : indexPrisonniers){
			//haut
			if(intersections.get(i).getY()-1>=0){
				if(intersections.get(calculIndex( intersections.get(i).getX() ,intersections.get(i).getY()-1, taille)).getPierre()==null) return false;
			}
			//Droite
			if(intersections.get(i).getX()+1<=taille-1){
				if(intersections.get(calculIndex( intersections.get(i).getX()+1 ,intersections.get(i).getY(), taille)).getPierre()==null) return false;
			}
			//Bas
			if(intersections.get(i).getY()+1<=taille-1){
				if(intersections.get(calculIndex( intersections.get(i).getX() ,intersections.get(i).getY()+1, taille)).getPierre()==null) return false;
			}
			//Gauche
			if(intersections.get(i).getX()-1>=0){
				if(intersections.get(calculIndex( intersections.get(i).getX()-1 ,intersections.get(i).getY(), taille)).getPierre()==null) return false;
			}
		}	
		return true;
	}
	
	//Supprime les pierres qui sont prisonnières
	private int removePrisonniers(){
		int nbPrisonnier= indexPrisonniers.size();
		for(int i : indexPrisonniers){
			intersections.get(i).setPierre(null);
		}
		return nbPrisonnier;
	}
	
	//Retourne la taille du plateau
	public int getTaille(){
		return taille;
	}
	
	//Calcul l'index de l'ArrayList par rapport aux coordonnées et à la taille du plateau
	private static int calculIndex(int x, int y, int taille){
		return x+taille*y;
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

	public void calculScore(Joueur joueur1, Joueur joueur2) {
		// TODO Auto-generated method stub
	}
	
}
