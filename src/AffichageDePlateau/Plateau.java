package AffichageDePlateau;

import Jeu.Donjon;



public class Plateau {
	private final PlateauFenetre plateauFenetre;

	public Plateau(final int largeur, final int hauteur, final PlateauCase plateauCases[][], Donjon monDonjon, int nombreTour) {
		this.plateauFenetre = new PlateauFenetre(largeur, hauteur, plateauCases, monDonjon);
	}
	public Plateau(final int largeur, final int hauteur, final PlateauCase plateauCases[][]) {
		this.plateauFenetre = new PlateauFenetre(largeur, hauteur, plateauCases);
	}
	
	public void placerPiece(final PlateauPiece piece) {
		this.plateauFenetre.placerPiece(piece);
	}
	public synchronized void enleverPiece(final PlateauPiece piece) {
		this.plateauFenetre.enleverPiece(piece);
	}

	public void rafraichir(int nbTour) {
		this.plateauFenetre.rafraichir(nbTour);
	}
	public void rafraichir() {
		this.plateauFenetre.rafraichir();
		
	}
}