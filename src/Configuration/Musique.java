package Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import Jeu.Sound;

public class Musique extends Thread {

	Sound player;
	InputStream stream;

	public Musique() {

	}

	public void run() {
		while (true) {
			this.player = new Sound("epic.wav");
			this.stream = new ByteArrayInputStream(player.getSamples());
			player.play(stream);
		}
	}
}