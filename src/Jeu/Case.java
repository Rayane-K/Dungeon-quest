package Jeu;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import AffichageDePlateau.PlateauCase;

public class Case implements PlateauCase{
	protected Image image;
	protected boolean lourd;
	protected boolean vide;
	protected String cheminImage;
	
	public Case(String image,boolean lourd,boolean vide){
		this.lourd=lourd;
		this.vide=vide;
		try {
			this.image=ImageIO.read(new File("images/" + image));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.cheminImage=image;
	}
	
	@Override
	public Image getImage() {
		return this.image;
	}
	
	public boolean estVide(){
		return this.vide;
	}
	
	public boolean estLourd(){
		return this.lourd;
	}
	
	public String getCheminImage(){
		return this.cheminImage;
	}
	//mode replay
	 public Case(String image){
			try {
				this.image=ImageIO.read(new File(image));
			} catch (IOException e) {
				e.printStackTrace();
			}
	  this.cheminImage=image;
	 }
}