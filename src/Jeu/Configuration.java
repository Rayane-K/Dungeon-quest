package Jeu;

import Configuration.Configurable;


public class Configuration implements Configurable{
	private int largeur;
	private int hauteur;
	private int nbPersonnage;
	private int densiteObstacle;
	private int TEMPS_PAUSE;
	private int densitePoulet;
	private boolean pasAPas;
	
	
	public Configuration(){
		this.largeur = 20;
		this.hauteur = 20;
		this.nbPersonnage = 10;
		this.densiteObstacle = 10;
		this.densiteObstacle = 10;
		this.TEMPS_PAUSE = 300;
		this.pasAPas=false;
		this.densitePoulet=70;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public int getNbPersonnage() {
		return nbPersonnage;
	}

	public void setNbPersonnage(int nbPersonnage) {
		this.nbPersonnage = nbPersonnage;
	}

	public int getNbObstacle() {
		return densiteObstacle;
	}

	public void setNbObstacle(int nbObstacle) {
		this.densiteObstacle = nbObstacle;
	}

	public int getTEMPS_PAUSE() {
		return TEMPS_PAUSE;
	}

	public void setTEMPS_PAUSE(int tEMPS_PAUSE) {
		TEMPS_PAUSE = tEMPS_PAUSE;
	}

	public boolean estPasAPas() {
		return pasAPas;
	}

	public void setPasAPas(boolean p) {
		this.pasAPas = p;
	}

	@Override
	public void setDensitePoulet(int densitePoulet) {
		this.densitePoulet=densitePoulet;
	}

	@Override
	public int getDensitePoulet() {
		return this.densitePoulet;
	}
	
}