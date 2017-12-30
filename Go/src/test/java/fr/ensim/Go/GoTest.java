package fr.ensim.Go;

import static org.junit.Assert.*;

import org.junit.Test;

public class GoTest {

	@Test
	public void testGo() {
		 Go go = new Go(19, 0, "Etienne", "Lee Sedol");
		assertEquals("Joueur qui commence",CouleurPierre.Noire, go.getActualJoueur().getPierre().getCouleur());
		go.jouerPierre(0, 0);
		assertEquals("Le joueur a bien changé",CouleurPierre.Blanche, go.getActualJoueur().getPierre().getCouleur());
		assertEquals("Pierre 0, 0 bien posé",go.getJoueurs().get(0).getPierre(), go.getPlateau().getPierre(0, 0));
		//blanc passe son tour
		go.jouerPierre(-1, -1);
		assertEquals("Partie pas fini", false, go.partieFini());
		//Noire passe son tour
		go.jouerPierre(-1, -1);
		assertEquals("Partie fini", true, go.partieFini());
		go.suppressionPierreMorte(0, 0);
		assertEquals("Pierre 0, 0 bien supprimé",null, go.getPlateau().getPierre(0, 0));
		go.annulerFin();
		assertEquals("Partie pas fini", false, go.partieFini());
		assertEquals("Pierre 0, 0 bien revenue",go.getJoueurs().get(0).getPierre(), go.getPlateau().getPierre(0, 0));
		//Blanc
		go.jouerPierre(1, 0);
		//Noire
		go.jouerPierre(1, 1);
		//Blanc coup impossible
		assertEquals("Toujours au joueur blanc de jouer",CouleurPierre.Blanche, go.getActualJoueur().getPierre().getCouleur());
		go.jouerPierre(0, 0);
		
		//Blanc rejoue
		go.jouerPierre(1, 2);
		
		//les deux joueurs passe
		go.jouerPierre(-1, -1);
		go.jouerPierre(-1, -1);
		
		//suppression deuxième pierre joueur blanc
		go.suppressionPierreMorte(1, 2);
		//On remet la pierre
		go.suppressionPierreMorte(1, 2);
		assertEquals("Pierre 1, 2 bien revenue",go.getJoueurs().get(1).getPierre(), go.getPlateau().getPierre(1, 2));
		
		go.suppressionPierreMorte(1, 2);
		//validation de la suppression des deux joueurs
		go.jouerPierre(-1, -1);
		go.jouerPierre(-1, -1);
		
		go.calculerScore();
		assertEquals("Joueur 1 score de 2", 2, go.getJoueurs().get(0).getScore(),0.1);
		assertEquals("Joueur 1 gagne", go.getJoueurs().get(0), go.getGagnant());
		
	}

}
