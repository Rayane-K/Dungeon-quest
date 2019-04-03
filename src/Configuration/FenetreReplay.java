package Configuration;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Jeu.Configuration;

public class FenetreReplay extends JDialog{
	private static final long serialVersionUID = 1L;
	private boolean ok;
	private JButton replay;
	private JButton quitter;
	private JLabel label;
	
	private JPanel pan;
	private JPanel pan2;
	private JPanel pan3;
	
	public FenetreReplay(final Configuration configs){
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setSize(350, 100);
		this.setLocationRelativeTo(null);
		this.ok=false;
		quitter=new JButton("Quitter");
		replay = new JButton("Voir le replay");
		replay.setEnabled(false);
		label = new JLabel("La partie est finie !",SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 20));
		pan=new JPanel();
		pan2=new JPanel();
		pan3=new JPanel();
		pan.add(quitter);
		pan.add(replay);
		pan2.add(pan);
		pan3.setLayout(new BorderLayout());
		pan3.add(pan2,BorderLayout.SOUTH);
		pan3.add(label,BorderLayout.CENTER);
		
		this.setContentPane(pan3);
		replay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ok=true;
				System.out.println(ok);
			}
		});
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(0);
			}
		});
		replay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	
	public boolean estGo(){
		return this.ok;
	}
	
	public void stop(){
		this.ok=false;
	}
	public void go(){
		this.ok=true;
	}
	public void fermer(){
		dispose();
	}
	
	public void activer(){
		this.replay.setEnabled(true);
	}
}