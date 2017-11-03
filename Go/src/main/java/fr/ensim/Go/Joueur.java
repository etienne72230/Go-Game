package fr.ensim.Go;

public class Joueur {

		private String pseudo;
		private Pierre pierre;
		
		public Joueur(String pseudo, Pierre pierre){
			this.pseudo = pseudo;
			this.pierre = pierre;
		}
		
		public String getPseudo(){
			return pseudo;
		}
		
		public Pierre getPierre(){
			return pierre;
		}
}
