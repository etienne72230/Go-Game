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
		
		assertEquals("Ajout pierre blanche", true,plateau.addPierre(0, 0, pB));
		assertEquals("Ajout pierre noire", true,plateau.addPierre(15, 11, pN));
		
		assertEquals("Pierre blanche présente", pB, plateau.getPierre(0, 0));
		assertEquals("Autre valeur toujours null", null, plateau.getPierre(1, 0));
		assertEquals("Pierre noire présente", pN, plateau.getPierre(15, 11));
		assertEquals("GetPierre hors limite return null", null, plateau.getPierre(-15, 17));
		
		assertEquals("Impossible de mettre une autre pierre sur une case déjà utilisé", false, plateau.addPierre(15, 11, pB));
		assertEquals("Ajout hors limite impossible", false, plateau.addPierre(19, 19, pB));
		
		
		assertEquals("Suppression pierre", true, plateau.removePierre(15, 11));
		assertEquals("Pierre bien supprimé", null, plateau.getPierre(15, 11));
		assertEquals("Suppression hors limite impossible", false, plateau.removePierre(20, 19));
		
		plateau = new Plateau(10);
		assertEquals("Ajout pierre sur un plateau plus petit impossible", false ,plateau.addPierre(15, 11, pN));
		
	}



}
