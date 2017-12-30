package fr.ensim.Go;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import IHM.Main;



/**
 * Classe permetant la gestion des pierres sur le plateau
 * @author Etienne Cayon
 */
public class Plateau  implements Serializable{
	
	private static final long serialVersionUID = 1L;
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
	
	public List<Intersection> getIntersections(){
		return intersections;
	}
	
	/**
	 * Ajout d'une pierre sur une intersection
	 * @param x
	 * 		coordonnée X de la pierre à ajouter
	 * @param y
	 *		coordonnée Y de la pierre à ajouter
	 * @param p
	 * 		pierre à ajouter
	 * @return	nombre de prisonnier réalisé
	 */
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
						if(p.getCouleur()==CouleurPierre.Blanche) {
							groupePrisonnier(x, y, new Pierre(CouleurPierre.Noire));
						}else { 
							if(p.getCouleur()==CouleurPierre.Noire) {
								groupePrisonnier(x, y, new Pierre(CouleurPierre.Blanche));
							}
						}
						if(isPrisonniers()){
							removePierre(x, y);
							Main.logger.warn("Coup suicidaire");
							return -1;
						}
					}
				return prisonniers;
				}
			}
		}
		return -1;
	}
	
	/**
	 * Suppression d'une pierre
	 * @param x
	 * 		coordonnée X de la pierre à supprimer
	 * @param y
	 * 		coordonnée Y de la pierre à supprimer
	 * @return	pierre supprimé
	 */
	public Pierre removePierre(int x, int y){
		for(Intersection inter : intersections){
			if( inter.getX() == x && inter.getY() == y && inter.getPierre()!= null){
				Pierre p = inter.getPierre();
				inter.setPierre(null);
				return p;
			}
		}
		return null;		
	}
	
	/**
	 * Donne la pierre qui se trouve aux coordonnées indiqué
	 * @param x
	 * 		coordonnée X de l'intersection à controler
	 * @param y
	 * 		coordonnée Y de l'intersection à controler
	 * @return	pierre qui se trouve sur l'intersection indiqué
	 */
	public Pierre getPierre(int x, int y){
		
		//return intersections.get(calculIndex(x, y, taille)).getPierre();
		for(Intersection inter : intersections){
			if( inter.getX() == x && inter.getY() == y){
				return inter.getPierre();
			}
		}
		return null;
	}

	/**
	 * Mise à jour du plateau suite à la pause d'une nouvelle pierre
	 * @param x
	 * 		coordonnée X de la pierre ajouté
	 * @param y
	 * 		coordonnée Y de la pierre ajouté
	 * @param p
	 * 		pierre ajouté
	 * @return	(-1) si le coup n'est pas possible, sinon le nombre de prisonnier éffectué
	 */
	private int updatePlateau(int x, int y, Pierre p){
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
	/**
	 * Création des groupes de pierres affecté par la pose d'une nouvelle pierre. Il peut y avoir un groupe de chaque coté de la pierre posé.
	 * @param x
	 * 		coordonnée X de la nouvelle pierre
	 * @param y
	 * 		coordonnée Y de la nouvelle pierre
	 * @param p
	 * 		pierre ajouté
	 */
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
	
	/**
	 * Verifie que le groupe est fait prisonnier
	 * @return	si le groupe est prisonnié
	 */
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
	
	/**
	 * Supprime les pierres qui sont prisonnières
	 * @return	le nombre de prisonnier effectué
	 */
	private int removePrisonniers(){
		int nbPrisonnier= indexPrisonniers.size();
		for(int i : indexPrisonniers){
			intersections.get(i).setPierre(null);
		}
		return nbPrisonnier;
	}
	
	//Taille du plateau
	public int getTaille(){
		return taille;
	}
	
	/**
	 * Calcul l'index de l'ArrayList par rapport aux coordonnées et à la taille du plateau
	 * @param x
	 * 		coordonnée X
	 * @param y
	 * 		coordonnée Y
	 * @param taille
	 * 		taille du plateau
	 * @return	index correspondant aux coordonnées données
	 */
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
	/**
	 * Calculer le score des deux joueurs
	 * @param joueur1
	 * @param joueur2
	 */
	public void calculScore(Joueur joueur1, Joueur joueur2) {
		List<List<Integer>> listeGroupe = new ArrayList<List<Integer>>();
		for(int i=0; i<intersections.size(); i++){
			
			//calcul des territoire
			if(intersections.get(i).getPierre()==null){
				Boolean dejaTraiter = false;
				for(List<Integer> liste : listeGroupe){
					//Nouveau groupe
					if(liste.indexOf(i)!=-1){
						dejaTraiter = true;
						break;
					}
				}
				if(dejaTraiter == false){
					List<Integer> tmp = new ArrayList<Integer>();
					formationGroupe(intersections.get(i).getX(), intersections.get(i).getY(), tmp);
					listeGroupe.add(tmp);
				}
			}else{
				//1 point par pierre sur le plateau
				if(intersections.get(i).getPierre().getCouleur()== CouleurPierre.Noire) joueur1.addPoint(1);
				if(intersections.get(i).getPierre().getCouleur()== CouleurPierre.Blanche) joueur2.addPoint(1);
			}
		}
		//Regarder si les territoires sont controler par un joueur
		for(List<Integer> liste : listeGroupe){
			Pierre p = isTerritoire(liste);
			//Territoire a personne
			if(p != null){
				//Territoire au joueur 1
				if(p == joueur1.getPierre()){
					joueur1.addPoint(liste.size());
					for(int i : liste){intersections.get(i).setPierre(joueur1.getPierre());}
					//Territoire au joueur 2
				}else{
					joueur2.addPoint(liste.size());
					for(int i : liste){intersections.get(i).setPierre(joueur2.getPierre());}
				}
			}
		}
		
	}
	
	/**
	 * Création des groupe de territoire vide
	 * @param x
	 * 		coordonnée X de l'intersection à vérifier
	 * @param y
	 * 		coordonnée Y de l'intersection à vérifier
	 * @param liste
	 * 		liste des intersections déjà traité
	 */
	private void formationGroupe(int x, int y, List<Integer> liste){
		
		for(int i=0; i<intersections.size(); i++){
			
			//Elle même
			if(intersections.get(i).getX() == x && intersections.get(i).getY() == y){
				if(liste.indexOf(i)==-1){
					liste.add(i);
				}
			}
			
			//Pierre du haut
			if(intersections.get(i).getX() == x && intersections.get(i).getY() == y-1){
				if(intersections.get(i).getPierre()== null){
					if(liste.indexOf(i)==-1){
						liste.add(i);
						formationGroupe(x, y-1, liste);
					}
				}
			}
			
			//Pierre de droite
			if(intersections.get(i).getX() == x+1 && intersections.get(i).getY() == y){
				if(intersections.get(i).getPierre()== null){
					if(liste.indexOf(i)==-1){
						liste.add(i);
						formationGroupe(x+1, y, liste);
					}
				}
			}
			
			//Pierre du bas
			if(intersections.get(i).getX() == x && intersections.get(i).getY() == y+1){
				if(intersections.get(i).getPierre()== null){
					if(liste.indexOf(i)==-1){
						liste.add(i);
						formationGroupe(x, y+1, liste);
					}
				}
			}
			
			//Pierre de gauche
			if(intersections.get(i).getX() == x-1 && intersections.get(i).getY() == y){
				if(intersections.get(i).getPierre() == null){
					if(liste.indexOf(i)==-1){
						liste.add(i);
						formationGroupe(x-1, y, liste);
					}
				}
			}
			
		}
	}
	

	/**
	 * Regarde si le groupe appartient à un joueur
	 * @param liste
	 * 		groupe à vérifier
	 * @return	pierre qui possède le groupe, null si le groupe est à personne
	 */
	private Pierre isTerritoire(List<Integer> liste){
		Pierre p = null;
		for(int i : liste){
			//haut
			if(intersections.get(i).getY()-1>=0){
				if(intersections.get(calculIndex( intersections.get(i).getX() ,intersections.get(i).getY()-1, taille)).getPierre()!=null ){
					if(p == null) p = intersections.get(calculIndex( intersections.get(i).getX() ,intersections.get(i).getY()-1, taille)).getPierre();
					if(intersections.get(calculIndex( intersections.get(i).getX() ,intersections.get(i).getY()-1, taille)).getPierre()!=p){
						return null;
					}
				}
			}
			//Droite
			if(intersections.get(i).getX()+1<=taille-1){
				if(intersections.get(calculIndex( intersections.get(i).getX()+1 ,intersections.get(i).getY(), taille)).getPierre()!=null){
					if(p == null) p = intersections.get(calculIndex( intersections.get(i).getX()+1 ,intersections.get(i).getY(), taille)).getPierre();
					if(intersections.get(calculIndex( intersections.get(i).getX()+1 ,intersections.get(i).getY(), taille)).getPierre()!=p){
						return null;
					}					
				}
			}
			//Bas
			if(intersections.get(i).getY()+1<=taille-1){
				if(intersections.get(calculIndex( intersections.get(i).getX() ,intersections.get(i).getY()+1, taille)).getPierre()!=null){
					if(p == null) p = intersections.get(calculIndex( intersections.get(i).getX() ,intersections.get(i).getY()+1, taille)).getPierre();
					if(intersections.get(calculIndex( intersections.get(i).getX() ,intersections.get(i).getY()+1, taille)).getPierre()!=p){
						return null;
					}					
				}
			}
			//Gauche
			if(intersections.get(i).getX()-1>=0){
				if(intersections.get(calculIndex( intersections.get(i).getX()-1 ,intersections.get(i).getY(), taille)).getPierre()!=null){
					if(p == null) p = intersections.get(calculIndex( intersections.get(i).getX()-1 ,intersections.get(i).getY(), taille)).getPierre();
					if(intersections.get(calculIndex( intersections.get(i).getX()-1 ,intersections.get(i).getY(), taille)).getPierre()!=p){
						return null;
					}					
				}
			}
		}	
		return p;
	}
	
}
