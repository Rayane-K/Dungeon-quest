package Launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;

public class FenetreLauncher extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton start;
	private JButton quitter;
	private JPanel pan;
	private JPanel frame;
	public static Boolean condition = false;
	private boolean ok = false;

	
	public FenetreLauncher() {
		
		
		this.setTitle("Projet Java");
		this.setSize(800, 500);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		pan = new JPanel();
		frame = new JPanel();
		start = new JButton("Start");
		quitter = new JButton("quitter");

		this.setContentPane(frame);

		frame.setLayout(new BorderLayout());
		frame.add(pan, BorderLayout.SOUTH);

		start.setPreferredSize(new Dimension(200, 60));
		quitter.setPreferredSize(new Dimension(200, 60));
		pan.setBackground(Color.BLACK);

		pan.add(start);
		pan.add(quitter);

		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				FenetreLauncher.this.ok = true;
				FenetreLauncher.this.setVisible(false);
			}
		});
		quitter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);

			}
		});
		
		JPanel imagePanel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				try {
					BufferedImage image = ImageIO.read(new File("images/FondLaunch.jpg"));
					g.drawImage(image, 0, 0, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		this.getContentPane().add(imagePanel);
		
	}

	public boolean estOK(){
		return ok;
	}
	
	
	
}