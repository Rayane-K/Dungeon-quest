package Configuration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Jeu.Configuration;

public class FenetreCheat extends JFrame{
	private static final long serialVersionUID = 1L;
	private boolean go;
	private JButton modeNormal;
	private JButton modePas;
	private JButton next;
	private JPanel pan;
	
	public FenetreCheat(final Configuration configs){
		this.go=false;
		this.setSize(300, 100);
		this.setLocation(80, 60);
		this.setResizable(false);
		modePas=new JButton("Mode pas a pas");
		modeNormal = new JButton("Mode automatique");
		next = new JButton("Jouer");
		pan=new JPanel();
		pan.add(modePas);
		pan.add(modeNormal);
		pan.add(next);
		this.setContentPane(pan);
		this.setVisible(true);

		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				go();
			}
		});
		modeNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configs.setPasAPas(false);
			}
		});
		modePas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configs.setPasAPas(true);
			}
		});
	}

	public boolean estGo(){
		return this.go;
	}
	
	public void stop(){
		this.go=false;
	}
	public void go(){
		this.go=true;
	}
	public void fermer(){
		dispose();
	}
}