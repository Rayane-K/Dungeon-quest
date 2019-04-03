package Configuration;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class FenetreQuitter extends JDialog{
	private static final long serialVersionUID = 1L;
	private JButton quitter;
	private JLabel label;
	
	public FenetreQuitter(){
		this.setTitle("       Partie terminee");
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setSize(300, 100);
		this.setLocationRelativeTo(null);
		this.quitter = new JButton("Quitter le jeu");
		this.label=new JLabel("La partie est terminee !");
		label.setFont(new Font("Arial", Font.BOLD, 20));
		this.setLayout(new BorderLayout());
		this.getContentPane().add(quitter,BorderLayout.SOUTH);
		this.getContentPane().add(label,BorderLayout.NORTH);
		
		
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}
