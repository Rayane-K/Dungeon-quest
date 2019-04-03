package Jeu;

import java.sql.Connection;

import Mobile.Mobile;


public class Pierre extends Mobile{
	
	boolean aEteDeplace;

	public Pierre(Donjon donjon){
		super("Pierre.png",donjon,false);
	}
	
	public void seDeplacer(int x,int y,int nombreTour,String appelant,Connection conn){
		if(appelant=="Personnage"){
			super.seDeplacer(nombreTour,conn);

			this.setX(x);
			this.setY(y);
			if(monDonjon.estViande(x, y) instanceof Viande){
				System.out.println("je vais le supprimer");
				monDonjon.estViande(x, y).disparaitre();
			}
			
		aEteDeplace=true;
		return;
		}
		else if(appelant=="Donjon"){
			if(aEteDeplace){
				aEteDeplace=false;
				return;}
			super.seDeplacer(nombreTour,conn);
		}
	}
}