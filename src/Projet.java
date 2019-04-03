import java.util.ArrayList;

import Configuration.FenetreCheat;
import Configuration.FenetreConfiguration;
import Configuration.FenetreQuitter;
import Configuration.FenetreReplay;
import Configuration.Musique;
import Jeu.Case;
import Jeu.Configuration;
import Jeu.Donjon;
import Jeu.Enregistrement_Jeu;
import Jeu.Replay;
import Launcher.FenetreLauncher;

public class Projet {
	public static void main(final String[] args) {
		int nombreTour=1;
		Donjon monDonjon;
		//Musique maMusique=new Musique();
		//maMusique.start();
		
		ArrayList<ArrayList<Enregistrement_Jeu>> enregistrements = new ArrayList<>();
		Case[][] background=null;
		
		final FenetreLauncher launch = new FenetreLauncher();
		launch.setVisible(true);

	   if (launch.estOK() == true) {

			final Configuration configuration = new Configuration();
			FenetreReplay choixReplay = new FenetreReplay(configuration);


			try {
				final FenetreConfiguration fenetreConfig = new FenetreConfiguration(
						configuration);
				fenetreConfig.setVisible(true);
				FenetreCheat cheats = new FenetreCheat(configuration);
				if (fenetreConfig.estOK() == true) {
					monDonjon = new Donjon(configuration,choixReplay);
					for (;;) {
						if (configuration.estPasAPas()) {
							nombreTour=pas(cheats, monDonjon,nombreTour);
						} else {
							monDonjon.jouerUnTour(nombreTour);
							nombreTour++;
							try {
								Thread.sleep(configuration.getTEMPS_PAUSE());
							} catch (final InterruptedException e) {
								e.printStackTrace();
							}
						}
						if(monDonjon.fini()){
							//monDonjon.ajouterALaBaseDeDonnee();
							monDonjon.finaliser(nombreTour);
							enregistrements=monDonjon.getEnregistrements();
							background=monDonjon.getBackground();
							//monDonjon.fermerConnexion();
							break;
						}
					}
				}
				cheats.fermer();
				choixReplay.setVisible(true);
				Replay monreplay = new Replay(configuration,nombreTour,background,enregistrements);
				while(true){
					if(monreplay.estFini()){
						FenetreQuitter quit = new FenetreQuitter();
						quit.setVisible(true);
						break;
					}
				}
				//monDonjon=null;

			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static int pas(FenetreCheat cheats, Donjon monDonjon,int nombreTour) {

		while (true) {

			if (cheats.estGo()) {
				monDonjon.jouerUnTour(nombreTour);
				nombreTour++;
				cheats.stop();
				return nombreTour;
			}
			// Cette ligne est l� pour �pargner au programme de tourner en
			// boucle trop rapidement en attendant l'event "Jouer" du bouton
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
		return nombreTour;
	}
}