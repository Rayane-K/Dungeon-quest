package Jeu;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

import AffichageDePlateau.Plateau;
import Configuration.ConnexionSQL;
import Configuration.FenetreReplay;
import Mobile.Eclaireur;
import Mobile.Freezer;
import Mobile.Kamikaze;
import Mobile.Mobile;
import Mobile.Monstre;
import Mobile.Personnage;
import Mobile.Simple;

public class Donjon {
	private Configuration configurations;
	private Case[][] cases;
	private ArrayList<Mobile> mobiles;
	private Plateau monPlateau;
	private Random hasard;
	private int nombreTour;
	private ArrayList<Enregistrement_Jeu> unTour;
	private ArrayList<ArrayList<Enregistrement_Jeu>> enregistrements;
	private Connection conn;
	private FenetreReplay choixReplay;
	
	public Donjon(final Configuration configuration, FenetreReplay choixReplay) {
		/*try {
			conn = ConnexionSQL.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		this.choixReplay=choixReplay;
		Stockage_Background monStock = new Stockage_Background(choixReplay);
		this.configurations = configuration;
		this.cases = new Case[this.configurations.getLargeur()][this.configurations
				.getHauteur()];
		this.mobiles = new ArrayList<>();
		this.enregistrements = new ArrayList<>();
		this.hasard = new Random();
		/* creer la table sql background */
		//creerTables(conn);

		Case uneCase = null;
		for (int y = 0; y < this.configurations.getHauteur(); y++) {
			for (int x = 0; x < this.configurations.getLargeur(); x++) {
				if ((x == 0) || (x == (this.configurations.getLargeur() - 1)) || (y == 0) || (y == (this.configurations.getHauteur() - 1))) {
					uneCase=placerBordure(x,y);
				} else if (this.hasard.nextInt(configuration.getNbObstacle()) == 0) {
					uneCase = new Mur();
				} else {
					uneCase = new Vide();
				}
				setXY(x, y, uneCase);

				// mettre � jour la base de donn�e
				monStock.add(x, y, cases[x][y].getCheminImage());
			}
		}
		//monStock.start();
		this.monPlateau = new Plateau(this.configurations.getLargeur(),
				this.configurations.getHauteur(), this.cases, this, nombreTour);
		placerPierres();
		placerPersonnages();
		placerMonstre();
		placerViande();
		placerPortail();
		placerPortail();
		rafraichir();
	}

	private void placerPierres() {
		int nbCase = configurations.getHauteur() * configurations.getLargeur();
		for (int i = 0; i < nbCase; i++) {
			if (hasard.nextInt(configurations.getNbObstacle()) == 0) {
				final Pierre pierre = new Pierre(this);
				mobiles.add(pierre);
				placerMobile(pierre);
				this.monPlateau.placerPiece(pierre);
			}
		}
	}

	public void rafraichir() {
		this.monPlateau.rafraichir(this.nombreTour);
	}

	public void setXY(final int x, final int y, final Case c) {
		this.cases[x][y] = c;
	}

	public Case getXY(final int x, final int y) {
		return this.cases[x][y];
	}

	private void placerPersonnages() {
		Personnage p;
		for (int i = 0; i < this.configurations.getNbPersonnage(); i++) {
			if (hasard.nextInt(5) > 2) {// Simple
				p = new Simple(this);
			} else if (hasard.nextInt(3) == 0) {// Eclaireur
				p = new Eclaireur(this);
			} else if (hasard.nextInt(3) == 0){// Kamikaze
				p = new Kamikaze(this);
			} else {// Freezer
				p = new Freezer(this);
			}
			
			mobiles.add(p);
			placerMobile(p);
			this.monPlateau.placerPiece(p);
		}
	}

	public void placerMonstre() {
		final Monstre monstre = new Monstre(this);
		mobiles.add(monstre);
		placerMobile(monstre);
		this.monPlateau.placerPiece(monstre);
	}

	private void placerMobile(final Mobile e) {
		int x = this.hasard.nextInt(this.configurations.getLargeur());
		int y = this.hasard.nextInt(this.configurations.getHauteur());
		while (!(((this.getXY(x, y).estVide())) && (this.getElementMobile(x, y) == null))) {
			x = this.hasard.nextInt(this.configurations.getLargeur());
			y = this.hasard.nextInt(this.configurations.getHauteur());
		}
		e.setX(x);
		e.setY(y);
	}

	public Mobile getElementMobile(final int x, final int y) {
		for (final Mobile e : this.mobiles) {
			if (e != null) {
				if ((e.getPositionHorizontale() == x)
						&& (e.getPositionVerticale() == y)) {
					return e;
				}
			}
		}
		return null;
	}

	public void jouerUnTour(int nombreTour) {
		unTour=new ArrayList<>();
		
		
		this.nombreTour = nombreTour;
		for (Mobile m : this.mobiles) {// Les eclaireurs jouent en premier
			if (m instanceof Eclaireur)
				m.seDeplacer(nombreTour, conn);
		}
		for (Mobile m : this.mobiles) {// Les kamikazes ensuite
			if (m instanceof Kamikaze)
				m.seDeplacer(nombreTour, conn);
		}
		for (Mobile m : this.mobiles) {// Les freezers ensuite
			if (m instanceof Freezer)
				m.seDeplacer(nombreTour, conn);
		}
		for (Mobile m : this.mobiles) {// Le reste apres
			if (m instanceof Simple)
				m.seDeplacer(nombreTour, conn);
		}
		for (Mobile m : this.mobiles) {// Le monstre en dernier
			if (m instanceof Monstre)
				m.seDeplacer(nombreTour, conn);
		}
		for (Mobile m : this.mobiles) {//les pierres
			if (m instanceof Pierre)
				((Pierre) m).seDeplacer(m.getPositionHorizontale(),
						m.getPositionVerticale(), nombreTour, "Donjon", conn);
		}
		for (Mobile m : this.mobiles) {//les bonus
			if (m instanceof Bonus)
				((Bonus) m).seDeplacer(conn,nombreTour);
		}
		
		enregistrements.add(unTour);
		rafraichir();
		

	}
	
	public void finaliser(int nombreTour){
		this.nombreTour=nombreTour;
		for(Mobile m : this.mobiles){
			m.seDeplacer(nombreTour, conn);
		}
		enregistrements.add(unTour);
	}

	public Monstre recupMonstre() {
		for (Mobile m : mobiles) {
			if (m instanceof Monstre)
				return (Monstre) m;
		}
		return null;
	}

	public void signalerOgre() {
		for (Mobile m : mobiles) {
			if (m instanceof Personnage && !(m instanceof Eclaireur) && m.estVivant()) {
				Personnage p = ((Personnage) m);
				p.fuirVers(p.fuite());
			}
		}
	}

	public boolean fini() {
		if (!recupMonstre().estVivant() || enfantsMorts())
			return true;
		else
			return false;
	}

	public boolean enfantsMorts() {
		for (Mobile m : mobiles) {
			if (m.estVivant() && m instanceof Personnage)
				return false;
		}
		return true;
	}

	public int getNbPersoRestant() {
		int compteur = 0;
		for (Mobile m : mobiles) {
			if (m instanceof Personnage && m.estVivant())
				compteur++;
		}
		return compteur;
	}

	public void creerTables(Connection conn) {

		Statement stat = null;
		try {
			stat = conn.createStatement();
			stat.execute("DROP TABLE Background");
			stat.execute("DROP TABLE Jeu");
			stat.execute("CREATE  TABLE `test_jdbc`.`Background` (`image` TEXT(30) ,`x` INT,`y` INT);");
			stat.execute("CREATE  TABLE `test_jdbc`.`Jeu` (`idMobiles` INT NOT NULL AUTO_INCREMENT,`NumTour` INT NOT NULL ,`x` INT ,`y` INT ,`image` TEXT NULL ,PRIMARY KEY (`idMobiles`, `NumTour`) );");
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void fermerConnexion() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void placerPortail() {
		final Portail portail = new Portail(this);
		mobiles.add(portail);
		placerMobile(portail);
		this.monPlateau.placerPiece(portail);
	}

	public void placerViande() {
		int nbCase = configurations.getHauteur() * configurations.getLargeur();
		for (int i = 0; i < nbCase; i++) {
			if (hasard.nextInt(configurations.getDensitePoulet()) == 0) {
				final Viande viande = new Viande(this);
				mobiles.add(viande);
				placerMobile(viande);
				this.monPlateau.placerPiece(viande);
			}
		}
	}

	public Bonus getBonus(int x, int y) {
		for (final Mobile e : this.mobiles) {
			if ((e.getPositionHorizontale() == x)
					&& (e.getPositionVerticale() == y)) {
				if (e instanceof Bonus) {
					return (Bonus) e;
				}
			}
		}
		return null;
	}
	

	public Viande estViande(int x,int y){
		for (final Mobile e : this.mobiles) {
			if (e instanceof Viande) {
				if ((e.getPositionHorizontale() == x) && (e.getPositionVerticale() == y)) {
					return (Viande) e;
				}
			}
		}
		return null;
	}

	public int getAutrePortailY(Portail monPortail) {
		for (Mobile m : mobiles) {
			if (m instanceof Portail && m != monPortail) {
				return m.getPositionVerticale();
			}
		}
		return 0;
	}

	public int getAutrePortailX(Portail monPortail) {
		for (Mobile m : mobiles) {
			if (m instanceof Portail && m != monPortail) {
				return m.getPositionHorizontale();
			}
		}
		return 0;
	}

	public void enfantMalade() {
		for (Mobile m : mobiles) {
			if (m instanceof Personnage && m.estVivant()) {
				((Personnage) m).Malade();
				return;
			}
		}
	}

	/*
	public void ajouterALaBaseDeDonnee() {
		for (Enregistrement_Jeu e : enregistrements) {
			int x = e.getX();
			int y = e.getY();
			int numTour = e.getNumTour();
			String image = e.getImage();
			String requete = "INSERT INTO Jeu (NumTour,x,y,image) VALUES ('"
					+ numTour + "', '" + x + "', '" + y + "',\"" + image
					+ "\");";
			Stockage monStock = new Stockage(requete, this.conn);
			//monStock.start();
			System.out.println("taille du tableau : "+enregistrements.size());
		}
		System.out.println("Les données ont bien été ajoutées sur MySQL.");
	}
	*/
	
	public void addEnregistrement(Enregistrement_Jeu e) {
		this.unTour.add(e);
	}
	public Case placerBordure(int x,int y){
		Case uneCase=null;
				if(x==0 && y==0)
					uneCase=new Bordure("bordure1.jpg");
				else if(x==0 && y==configurations.getHauteur()-1)
					uneCase=new Bordure("bordure3.jpg");
				else if(x==configurations.getLargeur()-1 && y==0)
					uneCase=new Bordure("bordure2.jpg");
				else if(x==configurations.getLargeur()-1 && y==configurations.getHauteur()-1)
					uneCase=new Bordure("bordure4.jpg");
				else if (x==0)
					uneCase=new Bordure("bordureGauche.jpg");
				else if(y==0)
					uneCase=new Bordure("bordureHaut.png");
				else if(x==configurations.getLargeur()-1)
					uneCase=new Bordure("bordureDroite.jpg");
				else if(y==configurations.getHauteur()-1)
					uneCase=new Bordure("bordureBas.jpg");
				return uneCase;
			}
	public ArrayList<ArrayList<Enregistrement_Jeu>> getEnregistrements(){
		return this.enregistrements;
	}
	public Case[][] getBackground(){
		return this.cases;
	}
}