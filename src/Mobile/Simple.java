package Mobile;

import Jeu.Direction;
import Jeu.Donjon;

public class Simple extends Personnage{
	
	Direction temp;

	public Simple(Donjon donjon) {
		super(donjon, "simpleNord.png");
	}
	
	
	public void changerImage(){
		switch(direction){
		case NORD:
			this.setImage("simpleNord.png");
			break;
		case SUD:
			this.setImage("simpleSud.png");
			break;
		case EST:
			this.setImage("simpleEst.png");
			break;
		case OUEST:
			this.setImage("simpleOuest.png");
			break;
		}
	}
	
	public boolean simplet(Direction d,Direction fuite){
		Direction d2=d.tournerADroite();
		if(fuite==d.demiTour()){//derrière
			this.direction=d.tournerADroite();
			changerImage();
			this.demiTour=true;
			return true;		
			}
		else if(fuite==d2){//droite
			this.direction=d2;
			changerImage();
			return true;
		}
		else if(fuite==d2.demiTour()){//gauche
			this.direction=d2.demiTour();
			changerImage();
			return true;
		}
		else{
			return false;
		}
	}
}