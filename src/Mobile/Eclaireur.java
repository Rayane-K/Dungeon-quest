package Mobile;

import Jeu.Donjon;

public class Eclaireur extends Personnage {

	public Eclaireur(Donjon donjon) {
		super(donjon,"eclaireurNord.png");
	}

	public void lEclaireurSignale(){
		monDonjon.signalerOgre();
	}
	
	
	public void changerImage(){
		switch(direction){
		case NORD:
			this.setImage("eclaireurNord.png");
			break;
		case SUD:
			this.setImage("eclaireurSud.png");
			break;
		case EST:
			this.setImage("eclaireurEst.png");
			break;
		case OUEST:
			this.setImage("eclaireurOuest.png");
			break;
		}
	}
}
