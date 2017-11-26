package fr.ensim.Go;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Hello world!
 *
 */
public class App 
{
	static Go go;
    public static void main( String[] args )
    {
 
    	
    	//Version console
        go = new Go(19, 6.5, "Etienne", "Lee Sedol");
        go.jouer();
    }
    
	
	public static void sauvegarde() {
		try {
			System.out.println("Sauvegarde en cours");
			FileOutputStream fos = new FileOutputStream("Go.serial");
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			try {
				oos.writeObject(go); 
				oos.flush();
				System.out.println("Sauvegarde terminé");
			} finally {
				try {
					oos.close();
				} finally {
					fos.close();
				}
			}
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void chargement() {
		System.out.println("Chargement en cours");
		try {
			// ouverture d'un flux d'entrée depuis le fichier "personne.serial"
			FileInputStream fis = new FileInputStream("Go.serial");
			// création d'un "flux objet" avec le flux fichier
			ObjectInputStream ois= new ObjectInputStream(fis);
			try {	
				// désérialisation : lecture de l'objet depuis le flux d'entrée
				go = (Go) ois.readObject(); 
			} finally {
				// on ferme les flux
				try {
					ois.close();
				} finally {
					fis.close();
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		System.out.println("Chargement terminé");
		System.out.println(go);
		go.jouer();
	}
}
