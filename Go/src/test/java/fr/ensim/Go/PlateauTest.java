package fr.ensim.Go;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlateauTest {



	
	@Test
	public void testPlateau() {
		Plateau plateau = new Plateau(19);
		Pierre pB = new Pierre(CouleurPierre.Blanc);
		Pierre pN = new Pierre(CouleurPierre.Noire);
		
		assertEquals("Intersection plateau null", null, plateau.getPierre(0, 0));
		assertEquals("Intersection plateau null", null, plateau.getPierre(19, 19));
		
		assertEquals("Ajout pierre blanche", 0,plateau.addPierre(0, 0, pB));
		assertEquals("Ajout pierre noire", 0,plateau.addPierre(15, 11, pN));
		
		assertEquals("Pierre blanche présente", pB, plateau.getPierre(0, 0));
		assertEquals("Autre valeur toujours null", null, plateau.getPierre(1, 0));
		assertEquals("Pierre noire présente", pN, plateau.getPierre(15, 11));
		assertEquals("GetPierre hors limite return null", null, plateau.getPierre(-15, 17));
		
		assertEquals("Impossible de mettre une autre pierre sur une case déjà utilisé", -1, plateau.addPierre(15, 11, pB));
		assertEquals("Ajout hors limite impossible", -1, plateau.addPierre(19, 19, pB));
		
		
		assertEquals("Suppression pierre", true, plateau.removePierre(15, 11));
		assertEquals("Pierre bien supprimé", null, plateau.getPierre(15, 11));
		assertEquals("Suppression hors limite impossible", false, plateau.removePierre(20, 19));
		assertEquals("Suppression pierre qui n'existe pas impossible", false, plateau.removePierre(15, 11));
		
		plateau = new Plateau(10);
		assertEquals("Ajout pierre sur un plateau plus petit impossible", -1 ,plateau.addPierre(15, 11, pN));
		
		
		//Prisonnier dans l'angle
		plateau = new Plateau(9);
		plateau.addPierre(0, 1, pN);
		plateau.addPierre(0, 0, pB);
		assertEquals("Pierre 0 0 prisonnière", 1, plateau.addPierre(1, 0, pN));
		assertEquals("Pierre 0 0 supprimer", null, plateau.getPierre(0, 0));
		
		//Coup suicide impossible
		assertEquals("Pierre Blanche 0 0 suicide impossible", -1, plateau.addPierre(0, 0, pB));
		
		
		//Prisonnier dans chaque direction
		//++N++
		//+NBN+
		//NB*BN
		//+NBN+
		//++N++
		
		plateau = new Plateau(9);
		plateau.addPierre(0, 2, pN);
		plateau.addPierre(1, 1, pN);
		plateau.addPierre(1, 3, pN);
		plateau.addPierre(2, 0, pN);
		plateau.addPierre(2, 4, pN);
		plateau.addPierre(3, 1, pN);
		plateau.addPierre(3, 3, pN);
		plateau.addPierre(4, 2, pN);
		
		plateau.addPierre(2, 1, pB);
		plateau.addPierre(1, 2, pB);
		plateau.addPierre(3, 2, pB);
		plateau.addPierre(2, 3, pB);
		//System.out.println(plateau);
		assertEquals("Pierre 2 2 fait 4 prisonnier", 4, plateau.addPierre(2, 2, pN));
		assertEquals("Pierre 2 1 supprimer", null, plateau.getPierre(2, 1));
		assertEquals("Pierre 1 2 supprimer", null, plateau.getPierre(1, 2));
		assertEquals("Pierre 3 2 supprimer", null, plateau.getPierre(3, 2));
		assertEquals("Pierre 2 3 supprimer", null, plateau.getPierre(2, 3));
		//System.out.println(plateau);
		
		//Groupe de prisonnier
		//NBBN
		//+NBN
		//NBB*
		//+NBN
		//++N+
		plateau = new Plateau(9);
		plateau.addPierre(1, 0, pN);
		plateau.addPierre(4, 0, pN);
		plateau.addPierre(1, 1, pN);
		plateau.addPierre(3, 1, pN);
		plateau.addPierre(0, 2, pN);
		plateau.addPierre(1, 3, pN);
		plateau.addPierre(3, 3, pN);
		plateau.addPierre(2, 4, pN);
		
		plateau.addPierre(2, 0, pB);
		plateau.addPierre(3, 0, pB);
		plateau.addPierre(2, 1, pB);
		plateau.addPierre(1, 2, pB);
		plateau.addPierre(2, 2, pB);
		plateau.addPierre(2, 3, pB);
		//System.out.println(plateau);
		assertEquals("Pierre 2 2 fait 6 prisonnier", 6, plateau.addPierre(3, 2, pN));
		//System.out.println(plateau);
		
		//Calcul du score final
		//+N+N++
		//+++N+B
		//NNN+N+
		//BN++N+
		//++B+++
		plateau = new Plateau(6);
		plateau.addPierre(1, 0, pN);
		plateau.addPierre(3, 0, pN);
		plateau.addPierre(5, 0, pN);
		plateau.addPierre(3, 1, pN);
		plateau.addPierre(5, 1, pB);
		plateau.addPierre(0, 2, pN);
		plateau.addPierre(1, 2, pN);
		plateau.addPierre(2, 2, pN);
		plateau.addPierre(4, 2, pN);
		plateau.addPierre(0, 3, pB);
		plateau.addPierre(1, 3, pN);
		plateau.addPierre(4, 3, pN);
		plateau.addPierre(1, 4, pB);
		plateau.addPierre(2, 4, pN);
		plateau.addPierre(3, 4, pN);
		plateau.addPierre(2, 5, pB);
		Joueur j1= new Joueur("j1", pN, 0);
		Joueur j2= new Joueur("j2", pB, 0);
		//System.out.println(plateau);
		plateau.calculScore(j1, j2);
		assertEquals("Calcul score joueurs 1 ", 20, j1.getScore(),0);
		assertEquals("Calcul score joueurs 2 ", 7, j2.getScore(),0);
		//System.out.println(plateau);
	}



}
