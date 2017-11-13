package IHM;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.ensim.Go.Go;
import fr.ensim.Go.Joueur;
import fr.ensim.Go.Plateau;

public class GameFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ConfigPanel configPanel;
	GamePanel gamePanel;
	public int taille;
	public String joueur1;
	public String joueur2;
	public double komi;
	
	public GameFrame(){
		super("Go");
		this.setSize(200, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		configPanel = new ConfigPanel(this);
		this.add(configPanel);
		this.setVisible(true);
	}

	
	public void jouer(){
		this.remove(configPanel);
		this.setSize(500, 500);
		gamePanel = new GamePanel(taille, komi,  joueur1, joueur2);
		this.add(gamePanel);
		this.repaint();
	}
	
}
