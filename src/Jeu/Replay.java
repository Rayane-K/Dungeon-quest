package Jeu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import AffichageDePlateau.Plateau;
import Configuration.ConnexionSQL;
import Mobile.Mobile;

public class Replay {
	private Configuration configurations;
	private Case cases[][];
	private Plateau monPlateau;
	private int nombreDeTour;
	private Case[][] maRecup;
	private Recuperation[] donneesMobiles;
	private int nbLigne;
	private ArrayList<Mobile> mobiles;
	private ArrayList<ArrayList<Enregistrement_Jeu>> enregistrements;
	private boolean fini;

	public Replay(Configuration configurations, int nombreTour,Case[][] background,ArrayList<ArrayList<Enregistrement_Jeu>> enregistrements) {
		this.fini=false;
		this.enregistrements=enregistrements;
		this.nombreDeTour = nombreTour;
		this.mobiles=new ArrayList<>();
		this.configurations = configurations;
		//this.cases = background;
		this.cases = new Case[configurations.getHauteur()][configurations.getLargeur()];
		this.maRecup = new Case[configurations.getHauteur()][configurations
				.getLargeur()];
		Connection conn = null;
		Statement stat = null;
		

		try {
			conn = ConnexionSQL.getConnection();
			stat = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.nbLigne=enregistrements.get(0).size();

		this.donneesMobiles = new Recuperation[this.nbLigne];
		for(int i=0;i<this.nbLigne;i++){
			donneesMobiles[i]=new Recuperation();
		}
		for (int x = 0; x < configurations.getLargeur(); x++) {
			for (int y = 0; y < configurations.getHauteur(); y++) {
				// recup�rer le nom de l'image aux coordonn�es x y
				String nomImage = "";
				try {
					ResultSet resultat = stat
							.executeQuery("SELECT * FROM Background WHERE x="
									+ x + " and y=" + y + ";");
					System.out.println("iii");

					while (resultat.next()) {
						nomImage = resultat.getString("image");
						System.out.println("recuperation : " + nomImage);
						maRecup[x][y] = new Case(nomImage);
					}
					resultat.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		try {
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		remplirCases(cases);
		
		monPlateau = new Plateau(configurations.getHauteur(),
				configurations.getLargeur(), cases);
		monPlateau.rafraichir();
		jouer(conn);
		this.fini=true;
	}

	public void remplirCases(Case[][] cases) {
		for (int x = 0; x < configurations.getLargeur(); x++) {
			for (int y = 0; y < configurations.getHauteur(); y++) {
				System.out.println(this.maRecup[x][y].getCheminImage());
				cases[x][y] = new Case(this.maRecup[x][y].getCheminImage());
			}
		}
	}

	public void jouer(Connection conn) {
		for (int i = 0; i < enregistrements.size(); i++) {
			jouerUnTour(i, conn);
			//try {Thread.sleep(configurations.getTEMPS_PAUSE());} catch (InterruptedException e) {e.printStackTrace();}
			monPlateau.rafraichir();
		}
	}

	public void jouerUnTour(int i, Connection conn) {
		/*
		try {
			Statement stat = conn.createStatement();
			ResultSet resultat = stat.executeQuery("SELECT * FROM Jeu WHERE NumTour="+ nombreDeTour + ";");
			int compteur = 0;

			while (resultat.next()) {

				System.out.println("compteur= "+compteur);
				this.donneesMobiles[compteur].setX(resultat.getInt("x"));
				this.donneesMobiles[compteur].setY(resultat.getInt("y"));
				this.donneesMobiles[compteur].setImage(resultat.getString("image"));
				compteur++;
			}
			resultat.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}*/
		
		
		for(int j=0;j<this.nbLigne;j++){
			this.donneesMobiles[j].setX(enregistrements.get(i).get(j).getX());
			this.donneesMobiles[j].setY(enregistrements.get(i).get(j).getY());
			this.donneesMobiles[j].setImage(enregistrements.get(i).get(j).getImage());

		}
		
		supprimerMobiles();
		afficherMobiles();
	}
	
	
	public void afficherMobiles(){
		for(Recuperation r : donneesMobiles){
			Mobile unMobile = new Mobile(r.getX(),r.getY(),r.getImage());
			mobiles.add(unMobile);
			monPlateau.placerPiece(unMobile);
		}
	}
	
	public void supprimerMobiles(){
		for(Mobile r : mobiles){
			monPlateau.enleverPiece(r);
		}
	}
	
	public int trouverNbLigne(Connection conn){
		int compteur = 0;
		try {
			Statement stat = conn.createStatement();
			ResultSet resultat = stat.executeQuery("SELECT * FROM Jeu WHERE NumTour=1;");
			while (resultat.next()) {
				compteur++;
			}
			resultat.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return compteur;
	}
	
	
	public void afficherTableau(){
		for(int i=0;i<enregistrements.size();i++){
			for( int j=0;j<nbLigne;j++){
				System.out.println("tour numéro "+i);
				System.out.println("mobile numéro "+j);
				System.out.println("x = "+enregistrements.get(i).get(j).getX());
				System.out.println("y = "+enregistrements.get(i).get(j).getY());
				System.out.println(enregistrements.get(i).get(j).getImage());
				System.out.println();
			}
		}
	}
	
	public boolean estFini(){
		return this.fini;
	}
}