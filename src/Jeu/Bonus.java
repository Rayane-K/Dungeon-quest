package Jeu;

import java.sql.Connection;

import Mobile.Mobile;

public class Bonus extends Mobile {
	
	private boolean disparuACeTour;
	
	public Bonus(Donjon donjon, String image){
		super(image, donjon, true);
	}
	
	public void seDeplacer(Connection conn,int nombreTour){
		if(this.estVivant()){
			super.seDeplacer(nombreTour, conn);
		}
		else if(disparuACeTour){
			disparuACeTour=false;
		}
		else
			super.seDeplacer(nombreTour, conn);
	}
	
	
	public void disparaitre(Connection conn,int nombreTour){
		this.vivant=false;
		this.setImage("Neant.png");
		disparuACeTour=true;
		super.seDeplacer(nombreTour,conn);
	}
}