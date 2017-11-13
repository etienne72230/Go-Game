package IHM;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.ensim.Go.CouleurPierre;
import fr.ensim.Go.Intersection;
import fr.ensim.Go.Joueur;
import fr.ensim.Go.Pierre;
import fr.ensim.Go.Plateau;

public class GamePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static int plateauX = 50;
	final static int plateauY = 50;
	
	private Plateau plateau;
	private List<Joueur> joueurs = new ArrayList<Joueur>();
	private int actualJoueur;
	
	private JLabel partie_lbl;
	
	private JButton j1Passe_btn;
	private JButton j2Passe_btn;
	
	private Image goban_img;
	private Image pierreNoire_img;
	private Image pierreBlanche_img;
	
	private int mouseX;
	private int mouseY;
	
	public GamePanel(int taille, double komi, String pseudo1, String pseudo2) {
		joueurs.add(new Joueur(pseudo1, new Pierre(CouleurPierre.Noire), 0));
		actualJoueur = 0;
		joueurs.add(new Joueur(pseudo2, new Pierre(CouleurPierre.Blanc), komi));
		plateau = new Plateau(taille);
		
		//initialisation des images
		try {
			goban_img = ImageIO.read(getClass().getResource("/goban_"+plateau.getTaille()+".png"));
			pierreNoire_img = ImageIO.read(getClass().getResource("/pierre_noire.png"));
			pierreBlanche_img = ImageIO.read(getClass().getResource("/pierre_blanche.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		partie_lbl = new JLabel(pseudo1 +"\tVS\t" + pseudo2);
		
		j1Passe_btn = new JButton("Passer");
		j2Passe_btn = new JButton("Passer");
		j2Passe_btn.setEnabled(false);
		
		this.add(j1Passe_btn);
		this.add(partie_lbl);
		this.add(j2Passe_btn);
		//Initialisation de écouteur
		this.addMouseListener(mousePressed);
		this.addMouseMotionListener(mouseMoved);
		
		j1Passe_btn.addActionListener(Passe);
		j2Passe_btn.addActionListener(Passe);
		
	}
	
	public void paintComponent(Graphics g){//Affichage de la table et tout les éléments dessus
		Graphics2D g2d = (Graphics2D)g;
		drawPlateau(g);
		if(plateau.getPierre(getGridX(mouseX), getGridY(mouseY)) == null){
			
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
			if(actualJoueur == 0){
				g2d.drawImage(pierreNoire_img, getDispX(getGridX(mouseX)), getDispY(getGridY(mouseY)), this);
			}else{
				g2d.drawImage(pierreBlanche_img, getDispX(getGridX(mouseX)), getDispY(getGridY(mouseY)), this);
			}
		}
	}
	
	private void drawPlateau(Graphics g){
		g.drawImage(goban_img, plateauX, plateauY, this);
		for(Intersection i: plateau.getIntersections()){
			if(i.getPierre()!=null){
				if(i.getPierre().getCouleur() == CouleurPierre.Blanc){
					g.drawImage(pierreBlanche_img, getDispX(i.getX()), getDispY(i.getY()), this);
				}else{
					g.drawImage(pierreNoire_img, getDispX(i.getX()), getDispY(i.getY()), this);
				}
			}
		}
	}
	
	MouseListener mousePressed = new MouseListener(){

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(jouerPierre(getGridX(e.getX()), getGridY(e.getY()))){
				j1Passe_btn.setEnabled(!j1Passe_btn.isEnabled());
				j2Passe_btn.setEnabled(!j2Passe_btn.isEnabled());
			}
			repaint();
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	MouseMotionListener mouseMoved = new MouseMotionListener(){

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent mouse) {
			// TODO Auto-generated method stub
			mouseX = mouse.getX();
			mouseY = mouse.getY();
			repaint();
		}
		
	};
	
	ActionListener Passe = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			jouerPierre(-1,-1);
			j1Passe_btn.setEnabled(!j1Passe_btn.isEnabled());
			j2Passe_btn.setEnabled(!j2Passe_btn.isEnabled());
			if(joueurs.get(0).getFin() == true && joueurs.get(1).getFin() == true){
				System.out.println("\t---Suppression pierre morte---");
			}
		}
		
	};
	
	//Ajoute une pierre au plateau et passe au tour de l'adversaire
	private boolean jouerPierre(int x, int y){
		if(x == -1 && y == -1){
			joueurs.get(actualJoueur).setFin(true);
			System.out.println(joueurs.get(actualJoueur).getPseudo()+" passe son tour");
			actualJoueur=(actualJoueur+1)%2;
			return true;
		}else{
			joueurs.get(actualJoueur).setFin(false);
			int prisonniers = plateau.addPierre(x, y, joueurs.get(actualJoueur).getPierre());
			if(prisonniers != -1){
				joueurs.get(actualJoueur).addPrisonniers(prisonniers);
				actualJoueur=(actualJoueur+1)%2;
				return true;
			}else{
				return false;
			}
		}
			
	}
	
	//Supression des pierres mortes en accord entre les deux joueurs
	private boolean suppressionPierreMorte(int x, int y){
		if(x!=-1 && y!=-1){
			plateau.removePierre(x, y);
			return true;
		}
		return false;
	}

	private int getDispX(int x){
		return 6+plateauX+x*44;
	}
	private int getDispY(int y){
		return 6+plateauY+y*44;
	}
	
	
	private int getGridX(int x){
		int index = (x-6-plateauX)/44;
		if(index<0) index=0;
		if(index>plateau.getTaille()-1) index=plateau.getTaille()-1;
		return index;
	}
	private int getGridY(int y){
		int index = (y-6-plateauY)/44;
		if(index<0) index=0;
		if(index>plateau.getTaille()-1) index=plateau.getTaille()-1;
		return index;
	}
}
