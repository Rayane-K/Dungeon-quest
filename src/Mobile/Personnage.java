package Mobile;

import java.sql.Connection;
import java.util.Random;

import Jeu.Direction;
import Jeu.Donjon;
import Jeu.Pierre;
import Jeu.Portail;
import Jeu.Voisinage_Personnage;

public class Personnage extends Mobile {
	private Voisinage_Personnage voisinage;
	protected boolean malade = false;
	protected boolean demiTour;
	private int decompte = 0;

	public Personnage(Donjon donjon,String image) {
		super(image, donjon, true);
		this.voisinage = new Voisinage_Personnage(donjon);
		this.direction=Direction.NORD;
	}

	public boolean simplet(Direction d,Direction fuite){
		if(!(this instanceof Simple))
			return false;
		else
			return true;
		}
	
	
	public void seDeplacer(int nombreTour,Connection conn) {
		//mettre la base de donnée à jour
		super.seDeplacer(nombreTour,conn);
		
		
		
		if (!this.estVivant())
			return;
		if (this.direction == null)
			changerDirection();
		if(this.demiTour){
			this.direction=direction.tournerADroite();
			changerImage();
			this.demiTour=false;
			return;
		}

		if (voisinage.ogreDansVoisinage(this.x,this.y,this.direction)) {
			Direction d=this.direction;
			this.direction = fuite();
			if(simplet(d,fuite()))
				return;
			changerImage();
		}

		int x = prochainX();
		int y = prochainY();

		if (caseDisponible(x, y)) {
			this.x = x;
			this.y = y;
		} else if ((monDonjon.getElementMobile(x, y) instanceof Pierre)) {
			deplacerPierre(x, y,nombreTour,conn);
			changerDirection();
		} else
			changerDirection();
		
		if (monDonjon.getBonus(this.x, this.y) != null
				&& monDonjon.getBonus(x, y) instanceof Portail) {
			this.x = monDonjon.getAutrePortailX((Portail) monDonjon.getBonus(x,
					y));
			this.y = monDonjon.getAutrePortailY((Portail) monDonjon.getBonus(x,
					y));
		}
		if(malade){
			System.out.println(malade);
			this.setImage("Personnage.jpg");
			decompte--;
			System.out.println(decompte);
			if (decompte==0){
				this.mourir();
			}
		}
	}

	public void changerDirection() {
		if(new Random().nextInt(4)<3)//pas de demiTour
			this.direction = this.direction.nouvelleDirection(this.direction);
		else{//demiTour
			this.direction=direction.tournerADroite();
			this.demiTour=true;
		}
		changerImage();
	}
	public void mourir() {
		this.vivant = false;
		this.setImage("Dead.png");
	}

	public void deplacerPierre(int x, int y, int nombreTour,Connection conn) {
		switch (direction) {
		case NORD:
			if (caseDisponible(x, y - 1)) {
				((Pierre)monDonjon.getElementMobile(x, y)).seDeplacer(x,y-1,nombreTour,"Personnage",conn);
			}
			break;
		case SUD:
			if (caseDisponible(x, y + 1)) {
				((Pierre)monDonjon.getElementMobile(x, y)).seDeplacer(x,y+1,nombreTour,"Personnage",conn);
			}
			break;
		case OUEST:
			if (caseDisponible(x - 1, y)) {
				((Pierre)monDonjon.getElementMobile(x, y)).seDeplacer(x-1,y,nombreTour,"Personnage",conn);
			}
			break;
		case EST:
			if (caseDisponible(x + 1, y)) {
				((Pierre)monDonjon.getElementMobile(x, y)).seDeplacer(x+1,y,nombreTour,"Personnage",conn);
			}
			break;
		}
		monDonjon.rafraichir();
	}

	public Direction fuite(){
		//Fonction qui va fonctionner seulement si le personnage est un eclaireur
		lEclaireurSignale();
		int mobX=monDonjon.recupMonstre().getPositionHorizontale();
		int mobY=monDonjon.recupMonstre().getPositionVerticale();
		int diffX=mobX-x;
		int diffY=mobY-y;
		
		if(Math.abs(diffX)>Math.abs(diffY))//La direction �� prendre est verticale
			return prendreDirection(x,y,diffX, diffY, false);
		else if(Math.abs(diffX)<Math.abs(diffY))//La direction �� prendre est horizontale
			return prendreDirection(x,y,diffX,diffY,true);
		
		else{//On peut aller verticalement ou horizontalement
			if(new Random().nextInt(2)==0)
				return prendreDirection(x, y, diffX, diffY, true);//horizontal
			else
				return prendreDirection(x, y, diffX, diffY, false);//vertical
		}
	}

	public Direction prendreDirection(int x, int y, int diffX, int diffY,
			boolean horizontal) {
		if (horizontal) {
			if (diffX > 0) {// Le montre est �� l'est
				if (caseDisponible(x - 1, y))
					return Direction.OUEST;
				else if (diffY >= 0) {
					if (caseDisponible(x, y - 1))
						return Direction.NORD;
					else
						return Direction.EST;
				}
			} else if (diffX < 0) {// le monstre est �� l'ouest
				if (caseDisponible(x + 1, y))
					return Direction.EST;
				else if (diffY >= 0) {
					if (caseDisponible(x, y - 1))
						return Direction.NORD;
					else
						return Direction.OUEST;
				}
			} else {// monstre sur la meme colonne
				if (diffY<0){//monstre au nord
					if(caseDisponible(x, y+1))
						return Direction.SUD;
					else if(caseDisponible(x+1, y))
						return Direction.EST;
					else
						return Direction.OUEST;
				}
				else{//monstre au sud
					if(caseDisponible(x, y-1))
						return Direction.NORD;
					else if(caseDisponible(x+1, y))
						return Direction.EST;
					else
						return Direction.OUEST;
				}
			}
		} else {
			if (diffY > 0) {// Le montre est au sud
				if (caseDisponible(x, y - 1))
					return Direction.NORD;
				else if (diffX >= 0) {// monstre �� l'est
					if (caseDisponible(x - 1, y))
						return Direction.OUEST;
					else
						return Direction.EST;
				}
			} else if (diffY < 0) {// le monstre est au nord
				if (caseDisponible(x, y + 1))
					return Direction.SUD;
				else if (diffX >= 0) {
					if (caseDisponible(x + 1, y))
						return Direction.OUEST;
					else
						return Direction.EST;
				}
			} else {// monstre sur la meme ligne
				if (diffX>0){//monstre a l'est
					if(caseDisponible(x-1, y))
						return Direction.OUEST;
					else if(caseDisponible(x, y+1))
						return Direction.SUD;
					else
						return Direction.NORD;
				}
				else{//monstre �� l'ouest
					if(caseDisponible(x+1, y))
						return Direction.EST;
					else if(caseDisponible(x, y+1))
						return Direction.SUD;
					else
						return Direction.NORD;
				}
			}
		}
		return Direction.EST;
	}
	
	public void fuirVers(Direction d){
		Direction D = this.direction;
		this.direction=d;

		if(simplet(D,d))
			return;
		changerImage();
	}
	public void changerImage(){
	}
	public void lEclaireurSignale(){
	}
	public void Malade(){
		
		this.malade=true;
	}
}