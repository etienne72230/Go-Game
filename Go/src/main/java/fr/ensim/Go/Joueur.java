package fr.ensim.Go;

import java.io.Serializable;

/**
 * Classe qui représente les joueurs
 * @author Etienne Cayon
 *
 */
public class Joueur  implements Serializable, Comparable<Joueur>{


	private static final long serialVersionUID = 1L;
		private String pseudo;
		private Pierre pierre;
		private double score;
		private boolean fin;
		private int nbPrisonniers;
		
		public Joueur(String pseudo, Pierre pierre, double score){
			this.pseudo = pseudo;
			this.pierre = pierre;
			fin = false;
			nbPrisonniers = 0;
			this.score = score;
		}
		
		public String getPseudo(){
			return pseudo;
		}
		
		public int getNbPrisonniers() {
			return nbPrisonniers;
		}

		/**
		 * Augmenter le nombre de prisonnier réalisé
		 * @param nb
		 * 		nombre de prisonnier à rajouter
		 */
		public void addPrisonniers(int nb) {
			nbPrisonniers+=nb;
		}

		public Pierre getPierre(){
			return pierre;
		}
		
		public double getScore(){
			return score;
		}
		
		public void addPoint(int p){
			score+=p;
		}
		
		public void setFin(boolean b){
			fin = b;
		}
		/**
		 * @return si le joueur veux terminer la partie
		 */
		public boolean getFin(){
			return fin;
		}

		@Override
		public int compareTo(Joueur j) {
			return (int)(j.getScore()-this.getScore());
		}
}
