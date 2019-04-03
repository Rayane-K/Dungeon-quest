package Jeu;

public class Viande extends Bonus {

	public Viande(Donjon donjon){
		super(donjon, "viande.png");
	}
	public void disparaitre(){
		this.vivant=false;
		this.setImage("Neant.png");
	}
}