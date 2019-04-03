package Mobile;

import java.sql.Connection;
import java.util.Random;

import Jeu.Direction;
import Jeu.Donjon;
import Jeu.Viande;
import Jeu.Voisinage_Monstre;


public class Monstre extends Mobile {
	private int pointDeVie;
	private int nombreTourDroite;
	private int nombreTourAttendre;
	private int nextX;
	private int nextY;
	private boolean digestion;
	private boolean freeze;
	private int nbTourFreeze;
	private Voisinage_Monstre voisinage;
	private Random hasard;

	public Monstre(Donjon donjon) {
		super("Monstre.png", donjon, true);
		this.pointDeVie = 100;
		this.nombreTourDroite = 0;
		this.nbTourFreeze = 0;
		this.digestion = false;
		this.voisinage = new Voisinage_Monstre(donjon);
	}

	public void seDeplacer(int nombreTour,Connection conn) {
		super.seDeplacer(nombreTour, conn);
		if (this.direction == null)
			this.direction = Direction.NORD;
		if (!this.estVivant())
			return;
		cheat();
		if (this.freeze)
		{
			if(nbTourFreeze<8){
				this.nbTourFreeze++;
				this.pointDeVie--;
				return;
			}
			else{
				this.freeze=false;
				this.nbTourFreeze=0;
				if(this.pointDeVie>10)
					this.setImage("Monstre.png");
				else
					this.setImage("MonstreLow.png");

			}
		}
		if (boufferVoisinage())
			return;

		int x = prochainX();
		int y = prochainY();

		if (digestion) {
			this.x = this.nextX;
			this.y = this.nextY;
			if (nombreTourAttendre > 0){
				nombreTourAttendre--;
				this.pointDeVie--;
			}
			else
				digestion = false;
			return;
		}

		if (caseDisponible(x, y)) {
			this.x = x;
			this.y = y;
			this.pointDeVie--;
			if (pointDeVie <= 0)
				this.mourir();
		} else {
			autreDirection();
			this.pointDeVie--;
			if (pointDeVie <= 0)
				this.mourir();
		}
		
		if (monDonjon.getBonus(this.x, this.y)!=null 
				&& monDonjon.getBonus(this.x, this.y).estVivant() 
				&& monDonjon.getBonus(x, y) instanceof Viande){
			this.pointDeVie+=30;
			monDonjon.getBonus(this.x, this.y).disparaitre(conn,nombreTour);
		}
		
		if (boufferVoisinage())
			return;
	}

	public void autreDirection() {
		if (this.nombreTourDroite < 3) {
			direction = direction.tournerADroite();
			nombreTourDroite++;
		} else {
			this.nombreTourDroite = 0;
			direction = direction.demiTour();
		}
	}
	public boolean boufferVoisinage() {
		if (this.digestion)
			return false;
		Personnage p = voisinage.trouverPersoDansVoisinage(this.x, this.y,
				this.direction);
		if (p != null && ((Personnage) p).estVivant()) {
			p.mourir();
			this.nextX = p.getPositionHorizontale();
			this.nextY = p.getPositionVerticale();
			this.digestion = true;
			this.pointDeVie += 20;
			this.nombreTourAttendre = 3;
			return true;
		}
		return false;
	}

	public void mourir() {
		this.vivant = false;
		this.setImage("MonstreMort.png");
	}

	public int getPointDeVie() {
		return this.pointDeVie;
	}
	public void setPointDeVie(int p) {
		this.pointDeVie=p;
	}
	public void cheat(){
		this.hasard = new Random();
		
		if(this.pointDeVie == 10){
			this.setImage("MonstreLow.png");
			if(this.hasard.nextInt(3) == 0)
				monDonjon.enfantMalade();
			
		}
	}

	public void freeze() {
		this.freeze=true;
		this.setImage("MonstreFreeze.png");
	}
}