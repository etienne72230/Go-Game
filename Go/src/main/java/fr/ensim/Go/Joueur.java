package fr.ensim.Go;

public class Joueur {

		private String pseudo;
		private Pierre pierre;
		double score;
		
		public Joueur(String pseudo, Pierre pierre){
			this.pseudo = pseudo;
			this.pierre = pierre;
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
}
