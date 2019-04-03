package Mobile;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

import AffichageDePlateau.PlateauPiece;
import Configuration.ConnexionSQL;
import Jeu.Bonus;
import Jeu.Direction;
import Jeu.Donjon;
import Jeu.Enregistrement_Jeu;
import Jeu.Pierre;

public class Mobile implements PlateauPiece {
	protected int x;
	protected int y;
	protected Image image;
	protected Donjon monDonjon;
	protected Direction direction;
	protected String cheminImage;

	protected boolean vivant;

	public Mobile(String image, Donjon donjon, boolean vivant) {
		try {
			this.image = ImageIO.read(new File("images/" + image));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.cheminImage = "images/"+image;
		this.monDonjon = donjon;
		this.vivant = vivant;
	}

	public int prochainX() {
		switch (this.direction) {
		case EST:
			return this.x + 1;
		case OUEST:
			return this.x - 1;
		default:
			return this.x;
		}
	}

	public int prochainY() {
		switch (this.direction) {
		case SUD:
			return this.y + 1;
		case NORD:
			return this.y - 1;
		default:
			return this.y;
		}
	}

	public void seDeplacer(int nombreTour, Connection conn) {
		/*String requete = "INSERT INTO Jeu (NumTour,x,y,image) VALUES ('"
				+ nombreTour + "', '" + this.x + "', '" + this.y
				+ "',\"images/" + this.cheminImage + "\");";

		Stockage stock = new Stockage(requete, conn);
		stock.start();*/

		
		Enregistrement_Jeu e = new Enregistrement_Jeu(this.x, this.y,
				nombreTour,this.cheminImage);
		monDonjon.addEnregistrement(e);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Image getImage() {
		return this.image;
	}

	public int getPositionHorizontale() {
		return this.x;
	}

	public int getPositionVerticale() {
		return this.y;
	}

	public void setImage(String image) {
		if (this.cheminImage != image) {
			try {
				this.image = ImageIO.read(new File("images/" + image));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.cheminImage = "images/"+image;
		}

	}

	public boolean estVivant() {
		return this.vivant;
	}

	public boolean caseDisponible(int x, int y) {
		if (monDonjon.getXY(x, y).estVide()
				&& ((monDonjon.getElementMobile(x, y) == null)
						|| !monDonjon.getElementMobile(x, y).estVivant() || monDonjon
							.getElementMobile(x, y) instanceof Bonus)
				&& !(monDonjon.getElementMobile(x, y) instanceof Pierre))
			return true;
		else
			return false;
	}

	public Mobile(int x, int y, String image) {
		this.x = x;
		this.y = y;
		try {
			this.image = ImageIO.read(new File(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Mobile() {
	}
}