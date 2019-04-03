package Mobile;

import java.util.Random;

import Jeu.Direction;
import Jeu.Donjon;

public class Freezer extends Personnage{

	public Freezer(Donjon donjon) {
		super(donjon, "FreezerNord.png");
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
		this.monDonjon.recupMonstre().freeze();
	}
	
	public void changerImage(){
		switch(direction){
		case NORD:
			this.setImage("FreezerNord.png");
			break;
		case SUD:
			this.setImage("FreezerSud.png");
			break;
		case EST:
			this.setImage("FreezerEst.png");
			break;
		case OUEST:
			this.setImage("FreezerOuest.png");
			break;
		}
	}
}
