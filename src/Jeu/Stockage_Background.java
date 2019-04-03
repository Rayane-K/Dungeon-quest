package Jeu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Configuration.ConnexionSQL;
import Configuration.FenetreReplay;

public class Stockage_Background extends Thread{
	private ArrayList<Integer> x;
	private ArrayList<Integer> y;
	private ArrayList<String> images;
	private FenetreReplay choixReplay;
	
	public Stockage_Background(FenetreReplay choixReplay){
		this.choixReplay=choixReplay;
		this.x=new ArrayList<Integer>();
		this.y=new ArrayList<Integer>();
		this.images=new ArrayList<String>();
	}
	
	
	public void add(int x,int y,String image){
		this.x.add(x);
		this.y.add(y);
		this.images.add(image);
	}
	
	
	public void run(){
		
		

		Statement stat = null;
		Connection conn = null;
		try {
			conn = ConnexionSQL.getConnection();
			stat = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i=0;i<getNbLigne();i++){
			int x=0;
			int y=0;
			String image = "";
			int compteur=0;
			for(int j : this.x){
				if(i==compteur)
					x=j;
				compteur++;
			}
			compteur=0;
			for(int j : this.y){
				if(i==compteur)
					y=j;
				compteur++;
			}
			compteur=0;
			for(String j : this.images){
				if(i==compteur)
					image=j;
				compteur++;
			}
			compteur=0;
			//les variables temporaires ont maintenant les valeurs à entrer dans la base de donnée
			try {
				stat.execute("INSERT INTO Background(image, x, y) VALUES ('images/"+ image +"','"+x+"','"+y+"');");
			} catch (SQLException e) {e.printStackTrace();}
		}
		try {
			stat.close();
			conn.close();
			finalize();
			this.choixReplay.activer();
			System.out.println("le background a été chargé sur mySQL");
		} catch (Throwable e) {e.printStackTrace();}
	}
	
	public int getNbLigne(){
		int compteur=0;
		for(int i : x){
			compteur++;
		}
		return compteur;
	}
}