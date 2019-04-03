package Jeu;

import Mobile.Personnage;

public class Voisinage_Monstre extends Voisinage{

	public Voisinage_Monstre(Donjon monDonjon) {
		super(monDonjon);
	}

	public Personnage trouverPersoDansVoisinage(int x,int y,Direction direction){
		//vérifie sur la case du monstre
		if(monDonjon.getElementMobile(x, y) !=null)
			if(monDonjon.getElementMobile(x, y) instanceof Personnage)
				return (Personnage) monDonjon.getElementMobile(x, y);
		
		//vérifie dans le voisinage
		Personnage p=null;
		switch(direction){
		case NORD:
			if(personnage(x+1, y))
				p=(Personnage) monDonjon.getElementMobile(x+1, y);
			else if(personnage(x-1, y))
				p=(Personnage) monDonjon.getElementMobile(x-1, y);
			else if(personnage(x, y-1))
				p=(Personnage) monDonjon.getElementMobile(x, y-1);
			break;
		case SUD:
			if(personnage(x+1, y))
				p=(Personnage) monDonjon.getElementMobile(x+1, y);
			else if(personnage(x-1, y))
				p=(Personnage) monDonjon.getElementMobile(x-1, y);
			else if(personnage(x, y+1))
				p=(Personnage) monDonjon.getElementMobile(x, y+1);
			break;
		case EST:
			if(personnage(x+1, y))
				p=(Personnage) monDonjon.getElementMobile(x+1, y);
			else if(personnage(x, y+1))
				p=(Personnage) monDonjon.getElementMobile(x, y+1);
			else if(personnage(x, y-1))
				p=(Personnage) monDonjon.getElementMobile(x, y-1);
			break;
		case OUEST:
			if(personnage(x-1, y))
				p=(Personnage) monDonjon.getElementMobile(x-1, y);
			else if(personnage(x, y+1))
				p=(Personnage) monDonjon.getElementMobile(x, y+1);
			else if(personnage(x, y-1))
				p=(Personnage) monDonjon.getElementMobile(x, y-1);
			break;
		}
		return p;
	}
	
	public boolean personnage(int x,int y){
		if(monDonjon.getElementMobile(x, y)!=null)
			if(monDonjon.getElementMobile(x, y) instanceof Personnage)
				return true;
		return false;
	}
}
