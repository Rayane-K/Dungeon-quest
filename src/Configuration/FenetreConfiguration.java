package Configuration;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Jeu.Configuration;

import javax.swing.JDialog;

public class FenetreConfiguration extends JDialog {

	private static final long serialVersionUID = 1L;

	private JButton OK;
	private JButton quitter;
	private JLabel largeur;
	private JTextField Tlargeur;
	private JLabel hauteur;
	private JTextField Thauteur;
	private JLabel nbPersonnage;
	private JTextField TnbPersonnage;
	private JLabel densiteObstacle;
	private JTextField TdensiteObstacle;
	private JLabel TEMPS_PAUSE;
	private JTextField TTEMPS_PAUSE;
	private JLabel Poulet;
	private JTextField TPoulet;
	private JCheckBox TpasApas;

	private JPanel pan;
	private JPanel pan3;
	private JPanel pan2;
	private JPanel pan4;
	private JPanel mainPanel;
	private boolean ok=false;

	public FenetreConfiguration(final Configuration config) {
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setTitle("Configurations");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		pan = new JPanel();
		pan2 = new JPanel();
		pan3 = new JPanel();
		pan4 = new JPanel();
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		pan4.setLayout(new BorderLayout());

		OK = new JButton("Ok");
		quitter = new JButton("quitter");

		largeur = new JLabel("Largeur :");
		hauteur = new JLabel("Hauteur :");
		nbPersonnage = new JLabel("Nombre de Personnages :");
		densiteObstacle = new JLabel("Densit�� d'obstacle :");
		TEMPS_PAUSE = new JLabel("Vitesse de simulation :");
		Poulet = new JLabel("Densité de poulet :");

		Tlargeur = new JTextField(String.valueOf(config.getLargeur()));
		Thauteur = new JTextField(String.valueOf(config.getHauteur()));
		TnbPersonnage = new JTextField(String.valueOf(config.getNbPersonnage()));
		TdensiteObstacle = new JTextField(String.valueOf(config.getNbObstacle()));
		TTEMPS_PAUSE = new JTextField(String.valueOf(config.getTEMPS_PAUSE()));
		TpasApas = new JCheckBox("Mode pas �� pas");
		TPoulet=new JTextField(String.valueOf(config.getDensitePoulet()));

		pan.setLayout(new GridLayout(6, 2));
		pan.setBackground(Color.GRAY);
		pan2.setBackground(Color.GRAY);
		pan3.setBackground(Color.GRAY);
		pan4.setBackground(Color.GRAY);

		pan.add(largeur);
		pan.add(Tlargeur);

		pan.add(hauteur);
		pan.add(Thauteur);

		pan.add(nbPersonnage);
		pan.add(TnbPersonnage);

		pan.add(densiteObstacle);
		pan.add(TdensiteObstacle);
		
		pan.add(Poulet);
		pan.add(TPoulet);

		pan.add(TEMPS_PAUSE);
		pan.add(TTEMPS_PAUSE);

		

		OK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				config.setLargeur(Integer.valueOf(Tlargeur.getText()));
				config.setHauteur(Integer.valueOf(Thauteur.getText()));
				config.setNbPersonnage(Integer.valueOf(TnbPersonnage.getText()));
				config.setNbObstacle(Integer.valueOf(TdensiteObstacle.getText()));
				config.setTEMPS_PAUSE(Integer.valueOf(TTEMPS_PAUSE.getText()));
				config.setDensitePoulet(Integer.valueOf(TPoulet.getText()));
				FenetreConfiguration.this.ok=true;
				dispose();
			}
		});

		quitter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		
		TpasApas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(((JCheckBox)e.getSource()).isSelected()){
					config.setPasAPas(true);
				}
				else if(!((JCheckBox)e.getSource()).isSelected()){
					config.setPasAPas(false);
				}
			}
		});
		
		
		pan3.add(TpasApas);
		pan2.add(OK);
		pan2.add(quitter);
		pan4.add(pan3,BorderLayout.CENTER);
		pan4.add(pan2,BorderLayout.SOUTH);
		
		mainPanel.add(pan,BorderLayout.CENTER);
		mainPanel.add(pan4,BorderLayout.SOUTH);
		this.setContentPane(mainPanel);
	}
	
	public boolean estOK(){
		return this.ok;
	}
}