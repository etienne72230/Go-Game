package fr.ensim.Go;

public class Joueur {

		private String pseudo;
		private Pierre pierre;
		private double score;
		private boolean fin;
		
		public Joueur(String pseudo, Pierre pierre){
			this.pseudo = pseudo;
			this.pierre = pierre;
			fin = false;
			if(pierre.getCouleur()==CouleurPierre.Blanc){
				score=6.5;
			}else{
				score=0;
			}
		}
		
		public String getPseudo(){
			return pseudo;
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
		
		public boolean getFin(){
			return fin;
		}
}
