package Mobile;

import java.util.Random;

import Jeu.Direction;
import Jeu.Donjon;

public class Kamikaze extends Personnage {

	public Kamikaze(Donjon donjon) {
		super(donjon,"KamikazeNord.png");
	}


	@Override
	public Direction fuite(){
		int mobX=monDonjon.recupMonstre().getPositionHorizontale();
		int mobY=monDonjon.recupMonstre().getPositionVerticale();
		int diffX=mobX-x;
		int diffY=mobY-y;
		
		if(Math.abs(diffX)>Math.abs(diffY))
			return prendreDirection(x,y,diffX, diffY, false).demiTour();//On inverse la direction
		else if(Math.abs(diffX)<Math.abs(diffY))
			return prendreDirection(x,y,diffX,diffY,true).demiTour();//On inverse la direction
		
		else{
			if(new Random().nextInt(2)==0)
				return prendreDirection(x, y, diffX, diffY, true).demiTour();//On inverse la direction
			else
				return prendreDirection(x, y, diffX, diffY, false).demiTour();//On inverse la direction
		}
	}	
	
	public void mourir(){
		super.mourir();
		monDonjon.signalerOgre();
		monDonjon.recupMonstre().setPointDeVie(monDonjon.recupMonstre().getPointDeVie()-20);
	}
	
	
	public void changerImage(){
		switch(direction){
		case NORD:
			this.setImage("KamikazeNord.png");
			break;
		case SUD:
			this.setImage("KamikazeSud.png");
			break;
		case EST:
			this.setImage("KamikazeEst.png");
			break;
		case OUEST:
			this.setImage("KamikazeOuest.png");
			break;
		}
	}
}