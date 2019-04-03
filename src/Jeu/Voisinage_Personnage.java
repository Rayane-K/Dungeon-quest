package Jeu;

import Mobile.Monstre;

public class Voisinage_Personnage extends Voisinage {

	public Voisinage_Personnage(Donjon monDonjon) {
		super(monDonjon);
	}

	public boolean ogreDansVoisinage(int x, int y, Direction direction) {
		switch (direction) {
		case NORD:
			if (ogre(x + 1, y + 1) || ogre(x - 1, y - 1) || ogre(x - 1, y + 1)
					|| ogre(x + 1, y - 1) || ogre(x - 1, y) || ogre(x - 2, y)
					|| ogre(x + 1, y) || ogre(x + 2, y) || ogre(x, y + 2)
					|| ogre(x, y + 1))
				return true;
		case SUD:
			if (ogre(x - 1, y - 1) || ogre(x + 1, y + 1) || ogre(x + 1, y - 1)
					|| ogre(x - 1, y + 1) || ogre(x + 1, y) || ogre(x + 2, y)
					|| ogre(x - 1, y) || ogre(x - 2, y) || ogre(x, y - 2)
					|| ogre(x, y - 1))
				return true;
		case EST:
			if (ogre(x + 1, y) || ogre(x, y - 1) || ogre(x, y + 1)
					|| ogre(x - 1, y - 1) || ogre(x, y - 2)
					|| ogre(x + 1, y - 1) || ogre(x - 1, y + 1)
					|| ogre(x, y + 2) || ogre(x + 1, y + 1) || ogre(x + 2, y))
				return true;
		case OUEST:
			if (ogre(x - 1, y) || ogre(x, y + 1) || ogre(x, y - 1)
					|| ogre(x + 1, y + 1) || ogre(x, y + 2)
					|| ogre(x - 1, y + 1) || ogre(x + 1, y - 1)
					|| ogre(x, y - 2) || ogre(x - 1, y - 1) || ogre(x - 2, y))
				return true;
		default:
			return false;
		}
	}

	public boolean ogre(int x, int y) {
		if (monDonjon.getElementMobile(x, y) instanceof Monstre)
			return true;
		else
			return false;
	}

}
