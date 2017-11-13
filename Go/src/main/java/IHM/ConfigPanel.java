package IHM;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.ensim.Go.Go;

public class ConfigPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GameFrame gf;
	
	private JLabel joueur1_lbl;
	private JTextField joueur1_tf;
	
	private JLabel joueur2_lbl;
	private JTextField joueur2_tf;
	
	private JLabel taille_lbl;
	private JComboBox taille_cb;
	
	private JLabel komi_lbl;
	private JTextField komi_tf;	
	
	private JButton jouer_btn;
	
	public ConfigPanel(GameFrame gf){
		this.gf = gf;
		//Initialisation de la grille 
		GridBagLayout gb = new GridBagLayout();
		this.setLayout(gb);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
		//Initilaisation des différent éléments
		joueur1_lbl = new JLabel("Pseudo joueur 1 : ");
		joueur1_tf = new JTextField();
		joueur2_lbl = new JLabel("Pseudo joueur 2 : ");
		joueur2_tf = new JTextField();
		
		taille_lbl = new JLabel("Taille du plateau : ");
		String[] listeTaille = new String[]{"9x9", "13x13", "19x19"};
		taille_cb = new JComboBox(listeTaille);
		
		komi_lbl = new JLabel("Komi : ");
		komi_tf = new JTextField("0.0");
		
		jouer_btn = new JButton("Commencer");
		
		//Positionnement sur la grille
		gbc.gridx=0;
		gb.setConstraints(joueur1_lbl, gbc);
		gb.setConstraints(joueur2_lbl, gbc);
		gb.setConstraints(taille_lbl, gbc);
		gb.setConstraints(komi_lbl, gbc);
		gbc.gridwidth = 2;
		gb.setConstraints(jouer_btn, gbc);
		gbc.gridwidth = 1;
		
		gbc.gridx=1;
		gb.setConstraints(joueur1_tf, gbc);
		gb.setConstraints(joueur2_tf, gbc);	
		gb.setConstraints(taille_cb, gbc);	
		gb.setConstraints(komi_tf, gbc);
		
		//Ajout des éléments sur le panel
		this.add(joueur1_lbl);
		this.add(joueur1_tf);
		this.add(joueur2_lbl);
		this.add(joueur2_tf);
		this.add(taille_lbl);
		this.add(taille_cb);
		this.add(komi_lbl);
		this.add(komi_tf);
		this.add(jouer_btn);
		
		
		//Ajout de l'écouteur
		jouer_btn.addActionListener(jouer);
	}
	
	
	ActionListener jouer = new ActionListener(){//Supression d'un robot à la liste
		   public void actionPerformed(ActionEvent e) {
			   gf.joueur1 = joueur1_tf.getText();
			   gf.joueur2 = joueur2_tf.getText();
			   gf.komi = Double.valueOf(komi_tf.getText());
			   switch (taille_cb.getSelectedItem().toString()){
				case "9x9": gf.taille = 9; break;
				case "13x13": gf.taille = 13; break;
				case "19x19": gf.taille =  19; break;
				}
			   gf.jouer();
		   }
	};
	
}
