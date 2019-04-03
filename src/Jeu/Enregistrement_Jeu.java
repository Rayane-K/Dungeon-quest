package Jeu;

public class Enregistrement_Jeu {
	private int x;
	private int y;
	private String image;
	private int numTour;
	
	public Enregistrement_Jeu(int x,int y,int numTour,String image){
		this.x=x;
		this.y=y;
		this.numTour=numTour;
		this.image=image;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String getImage() {
		return image;
	}
	public int getNumTour() {
		return numTour;
	}
}