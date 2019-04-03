package AffichageDePlateau;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


class PlateauPanneau extends JPanel {
	private static final long serialVersionUID = -3618605287900763008L;
	private final PlateauCase plateauCases[][];
	private final ArrayList<PlateauPiece> pieces;
	private final int largeur;
	private final int hauteur;

	public PlateauPanneau(final int largeur, final int hauteur, final PlateauCase plateauCases[][]) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.plateauCases = plateauCases;
		this.pieces = new ArrayList<>();
	}

	@Override
	public synchronized void paintComponent(final Graphics g) {
		final int tailleLargeur = this.getWidth() / this.largeur;
		final int tailleHauteur = this.getHeight() / this.hauteur;

		for (int i = 0; i < this.largeur; i++) {
			for (int j = 0; j < this.hauteur; j++) {
				g.drawImage(this.plateauCases[i][j].getImage(), tailleLargeur * i, tailleHauteur
						* j, tailleLargeur, tailleHauteur, this);
			}
		}

		for (final PlateauPiece piece : this.pieces) {
			g.drawImage(piece.getImage(), tailleLargeur * piece.getPositionHorizontale(),
					tailleHauteur * piece.getPositionVerticale(), tailleLargeur, tailleHauteur,
					this);
		}
	}

	public synchronized void placerPiece(final PlateauPiece piece) {
		this.pieces.add(piece);
	}
	public synchronized void enleverPiece(final PlateauPiece piece){
		for(PlateauPiece p : this.pieces){
			if (p==piece){
				this.pieces.remove(p);
				break;
			}
		}
	}

}
