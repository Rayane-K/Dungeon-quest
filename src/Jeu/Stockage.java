package Jeu;

import java.sql.Connection;
import java.sql.Statement;



public class Stockage extends Thread{
	private String requete;
	private Connection conn;
	
	
	public Stockage(String requete,Connection conn){
		this.requete=requete;
		this.conn=conn;
	}
	
	
	public void run(){
		Statement stat = null;
		try {
			stat = conn.createStatement();
			stat.execute(requete);
			stat.close();
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	
	}

	public void setRequete(String requete){
		this.requete = requete;
	}
}