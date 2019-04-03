package Jeu;

import java.util.Random;

public enum Direction {
	NORD, SUD, OUEST, EST;

	public Direction demiTour(){
		if(this==NORD)
			return SUD;
		else if(this==SUD)
			return NORD;
		else if(this==EST)
			return OUEST;
		else if(this==OUEST)
			return EST;
		else
			return null;
	}
	
	public Direction tournerADroite(){
		if(this==NORD)
			return EST;
		else if(this==SUD)
			return OUEST;
		else if(this==EST)
			return SUD;
		else if(this==OUEST)
			return NORD;
		else
			return null;
	}
	
	


	public Direction nouvelleDirection(Direction d) {
		Random r = new Random();
		int n=0;
		switch (d){
		case NORD:
			n=r.nextInt(3);
			if(n==0)
				return NORD;
			else if(n==1)
				return EST;
			else
				return OUEST;
		case SUD:
			n=r.nextInt(3);
			if(n==0)
				return SUD;
			else if(n==1)
				return EST;
			else
				return OUEST;
		case EST:
			n=r.nextInt(3);
			if(n==0)
				return NORD;
			else if(n==1)
				return EST;
			else
				return SUD;
		default:
			n=r.nextInt(3);
			if(n==0)
				return NORD;
			else if(n==1)
				return SUD;
			else
				return OUEST;
		}
	}

}
