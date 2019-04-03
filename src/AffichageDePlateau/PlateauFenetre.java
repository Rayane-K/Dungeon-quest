package AffichageDePlateau;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Jeu.Donjon;

class PlateauFenetre extends JFrame {
	private static final long serialVersionUID = -6563585351564617603L;
	private final PlateauPanneau plateauPanneau;
	private JPanel pan;
	private JPanel panInfo;
	private JLabel vie;
	private JLabel nbPersoRestant;


	private Donjon monDonjon;
	private int vieMonstre;
	private JButton quitter;

	public PlateauFenetre(final int largeur, final int hauteur,
			final PlateauCase plateauCases[][], Donjon monDonjon) {
		this.monDonjon = monDonjon;
		this.setTitle("Plateau de jeu");
		this.setResizable(false);
		this.setSize(700, 700);
		this.setLocation(500, 60);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.plateauPanneau = new PlateauPanneau(largeur, hauteur, plateauCases);
		Font font = new Font("arial", Font.BOLD, 16);
		pan = new JPanel();
		panInfo = new JPanel();
		quitter = new JButton("Quitter");
		vie = new JLabel();
		nbPersoRestant = new JLabel();
		
		vie.setFont(font);;
		vie.setForeground(Color.RED);
		
		nbPersoRestant.setFont(font);
		nbPersoRestant.setForeground(Color.RED);
		pan.setLayout(new BorderLayout());
		this.setContentPane(this.pan);
		pan.add(plateauPanneau, BorderLayout.CENTER);
		pan.add(panInfo, BorderLayout.SOUTH);
		panInfo.setLayout(new BorderLayout(10,0));
		panInfo.add(vie,BorderLayout.WEST);
		panInfo.add(nbPersoRestant);
		panInfo.add(quitter, BorderLayout.EAST);
		this.setVisible(true);

		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
	}
	
	
	
	public PlateauFenetre(final int largeur, final int hauteur,
			final PlateauCase plateauCases[][]) {
		this.setTitle("Plateau de jeu");
		this.setResizable(false);
		this.setSize(700, 700);
		this.setLocation(500, 60);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.plateauPanneau = new PlateauPanneau(largeur, hauteur, plateauCases);
		this.setContentPane(plateauPanneau);
		this.setVisible(true);
	}

	public void placerPiece(final PlateauPiece piece) {
		this.plateauPanneau.placerPiece(piece);
	}

	public synchronized void enleverPiece(final PlateauPiece piece) {
		this.plateauPanneau.enleverPiece(piece);
	}

	public void rafraichir(int nbTour) {
		this.plateauPanneau.repaint();
		vieMonstre = monDonjon.recupMonstre().getPointDeVie();
		vie.setText(String.valueOf(" Vie du monstre : " + vieMonstre));
		nbPersoRestant.setText("Nombre de personnage restant : " + String.valueOf(monDonjon.getNbPersoRestant()) + "  Nombre de tour : " + nbTour);
	}
	public void rafraichir(){
		this.plateauPanneau.repaint();
	}
}
