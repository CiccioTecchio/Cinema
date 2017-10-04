package grafica;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;

import cinema.Gestore;

/**
 * 
 * Interfaccia grafica per attivare o disattivare politiche di sconto
 *
 */
public class MySconti extends JFrame{

	private JCheckBox giorno;
	private JCheckBox pomeridiana;
	private JCheckBox ridotto;
	private JPanel panelhome;
	private JButton salva;
	private Gestore g;
	
	public MySconti(Gestore g)  {
		this.g=g;
		setSize(250, 150);
		setTitle("Attiva e Disattiva Sconti");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width/2)-(getWidth()/2),(dim.height/2)-(getHeight()));
		giorno=new JCheckBox("Sconto Mercoledì");
		pomeridiana=new JCheckBox("Sconto fascia pomeridiana");
		ridotto=new JCheckBox("Sconto bambini");
		setResizable(false);
		setAlwaysOnTop(true);
		panelhome = new JPanel();
		panelhome.setLayout(new BoxLayout(panelhome, BoxLayout.Y_AXIS));
		panelhome.add(ridotto);
		panelhome.add(giorno);
		panelhome.add(pomeridiana);
		if(g.getCinemaGestore().getPolitiche().get(0).isStato()){ridotto.setSelected(true);}
		if(g.getCinemaGestore().getPolitiche().get(1).isStato()){giorno.setSelected(true);}
		if(g.getCinemaGestore().getPolitiche().get(2).isStato()){pomeridiana.setSelected(true);}
		salva = new JButton("Salva");

	

		/**
		 * 
		 * Listener per attivare o disattivare politiche di sconto
		 *
		 */
			class RegistratiListener implements ActionListener {
				/**
				 * Attiva o disattiva varie politiche di sconto
				 */
		public void actionPerformed(ActionEvent arg0) {
			g.getCinemaGestore().getPolitiche().get(0).setStato(ridotto.isSelected());
			g.getCinemaGestore().getPolitiche().get(1).setStato(giorno.isSelected());
			g.getCinemaGestore().getPolitiche().get(2).setStato(pomeridiana.isSelected());
			dispose();
			save();
		}
	}


	RegistratiListener rl = new RegistratiListener();
	salva.addActionListener(rl);
	panelhome.add(salva);
	add(panelhome);
	setVisible(true);
}
	/**
	 * Salva lo stato degli sconti allinterno del file spettacoli.wa
	 */
	public void save(){
		try{
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("spettacoli.wa")));
			oos.writeObject(g.getCinemaGestore().getProgrammazione());
			oos.writeObject(g.getCinemaGestore().getPolitiche());
			oos.close();
			}
			catch(IOException e1){}
	}
	
	
}