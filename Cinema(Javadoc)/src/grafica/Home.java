package grafica;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import javax.swing.*;

import cinema.Cinema;
import cinema.Cliente;
import cinema.Gestore;
import cinema.Spettacolo;

/**
 * Frame che contiente l'interfaccia grafica che userà il cliente 
 *
 */
public class Home extends JFrame{
	
	private JButton fine;
	private JPanel pnll;
	private MouseListener ml;
	private Cliente cliente;
	private int azione;
	private JPanel pnlr;
	private TextArea txt;
	private JScrollPane jspl;
	private JMenuBar menu;
	private JMenu file;
	private JMenu visualizza;
	private JMenu gestisci;
	private JMenuItem exit;
	private JMenu settimanale;
	private JMenuItem tutto;
	private JMenuItem totale;
	private JMenu sala;
	private JMenuItem uno;
	private JMenuItem due;
	private JMenuItem tre;
	private JMenuItem quattro;
	private JMenuItem cinque;
	private JMenu ordina;
	private JMenuItem cronologico;
	private JMenuItem salac;
	private JMenuItem alfabetico;
	private JMenuItem scelta;
	private JTextArea[] lblv;
	public  Home (Cliente c) {
		
		azione=0;
		cliente = c;
		setTitle("Cinema | Utente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
		setSize(dim.width,dim.height);

		pnlr = new JPanel();
		pnlr.setLayout(new BoxLayout(pnlr, BoxLayout.Y_AXIS));
		fine = new JButton("");
		
		txt = new TextArea("",10,10,TextArea.SCROLLBARS_VERTICAL_ONLY);

		
		/**
		 * 
		 * Listener utilizzato per la descrizione dello spettacolo
		 *
		 */
		class Desc implements MouseListener{
			@Override
			/**
			 * Stampa la descrizione dello spettacolo scelto(azione 0)
			 * Apre il frame di gestione dei posti (azione 1)
			 * @param arg0 spettacolo selezionato
			 * 
			 */
			public void mouseClicked(MouseEvent arg0) {
				int i=(Integer.parseInt(""+((JTextArea)arg0.getSource()).getText().charAt(0)))-1;
				
				if(azione==0){
				txt.setText(c.getCinema().getProgrammazione().get(i).getFilmAttuale().getDesc());
				Date d = c.getCinema().getProgrammazione().get(i).getDataInizio();
				txt.setText(txt.getText()+"\nIn Programmazione da:"+d.getDate()+"/"+(d.getMonth()+1)+"/"+(d.getYear()+1900));
				d=c.getCinema().getProgrammazione().get(i).getDataFine();
				txt.setText(txt.getText()+" a:"+d.getDate()+"/"+(d.getMonth()+1)+"/"+(d.getYear()+1900));
				}
				if(azione==1){
					MyPostiCliente mypc = new MyPostiCliente(c, i,Home.this);
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub	
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub	
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub	
			}
		}

		ml = new Desc();
		pnll = lista(c.getCinema().getProgrammazione());
		jspl = new JScrollPane(pnll);
		jspl.getVerticalScrollBar().setUnitIncrement(20);

		txt.setBackground(Color.yellow);
		txt.setFont(new Font("ARIAL",Font.ITALIC,30));
		txt.setEditable(false);
		pnlr.add(txt);
		
		/**
		 * 
		 * Setta azione=0 e nasconde il bottone
		 *
		 */
		class FineListener implements ActionListener{
			/**
			 * nasconde il bottone e setta l'azione a 0
			 */
			public void actionPerformed(ActionEvent e) {
				fine.setVisible(false);
				azione=0;
			}
		}
		
		FineListener fl = new FineListener();
		fine.addActionListener(fl);
		fine.setVisible(false);
		pnlr.add(fine);
		
		JPanel pnl = new JPanel();
		pnl.setLayout(new GridLayout(1,2));
		pnl.add(jspl);
		pnl.add(pnlr);

		add(pnl);		
		setJMenuBar(myMenuBar());

		setVisible(true);

	}

	/**
	 * Crea una barra dei menù
	 * @return una barra dei munu con tre opzioni
	 */
	public JMenuBar myMenuBar() {

		 menu= new JMenuBar();
		 file = new JMenu("File");
		 visualizza = new JMenu("Visualizza");
		 gestisci = new JMenu("Gestisci");

		 /**
		  * 
		  * Listener per far teminare l'esecuzione del programma
		  *
		  */
		class ExitListener implements ActionListener {
			/**
			 * Termina il programma
			 */
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		}

		exit = new JMenuItem("Exit");
		ExitListener el = new ExitListener();
		exit.addActionListener(el);
		file.add(exit);

		settimanale = new JMenu("Programma Settimanale");

		 /**
		  * 
		  * Listener per far stampare la programmazione degli spettacoli completa
		  *
		  */
		class TotListener implements ActionListener {
			/**
			 * aggiorna la schermata con la programmazione completa
			 */
			public void actionPerformed(ActionEvent arg0) {
				refresh(cliente.getCinema().getProgrammazione());
			}
		}
		TotListener tl = new TotListener();
		tutto = new JMenuItem("Programmazione Completa");
		tutto.addActionListener(tl);

		/**
		 * 
		 *Listener per far stamare la programmazione settimanale
		 *
		 */
		class SettTotListener implements ActionListener {
			/**
			 * aggiorna la schermata con la programmazione settimanale
			 */
			public void actionPerformed(ActionEvent arg0) {
				refresh(cliente.getCinema().getProgrammaSettimanale());
			}
		}
		SettTotListener stl = new  SettTotListener();
		totale = new JMenuItem("Tutte le Sale");
		totale.addActionListener(stl);

		sala = new JMenu("Sala");

		/**
		  * 
		  * Listener per visualizzare la programmazione in una determinata sala
		  *
		  */
		class SalaListener implements ActionListener {
			/**
			 * aggiorna la schermata con la programmazione di una determinata sala
			 */
			public void actionPerformed(ActionEvent arg0) {
				int i= Integer.parseInt(((JMenuItem)arg0.getSource()).getText());
				refresh(cliente.getCinema().getProgrammaSala(i));
			}
		}
		SalaListener sal= new SalaListener();

		uno = new JMenuItem("1");
		uno.addActionListener(sal);
		due = new JMenuItem("2");
		due.addActionListener(sal);
		tre = new JMenuItem("3");
		tre.addActionListener(sal);
		quattro = new JMenuItem("4");
		quattro.addActionListener(sal);
		cinque = new JMenuItem("5");
		cinque.addActionListener(sal);

		sala.add(uno);
		sala.add(due);
		sala.add(tre);
		sala.add(quattro);
		sala.add(cinque);

		settimanale.add(totale);
		settimanale.add(sala);

		 ordina = new JMenu("Programmazione Ordinata Per");

		 /**
		  * Listener per l'ordinameno
		  * 
		  *
		  */
		class OrdinaListener implements ActionListener {
			/**
			 * Oridina gli spettacoli in base al criterio scelto
			 */
			public void actionPerformed(ActionEvent arg0) {
				Comparator<Spettacolo> criterio=null;
				String st = ((JMenuItem)arg0.getSource()).getText();

				switch (st){
				case "Ordine Cronologico" : {criterio=(a,b)->{

					if((a.isFruibili())^(b.isFruibili())){
						if(a.isFruibili()) return -1;
						else return 1;
					}
					else{
						Date today=new Date();
						Date ai = a.getDataInizio(); 
						Date bi = b.getDataInizio();
						
						if((today.before(ai))||(today.before(bi))){
						if(ai.before(bi)){
							return -1;
						}
						else if(ai.after(bi)){
							return 1;
						}
						else return 0;
						}
						else {
							int minutoa = a.getOraInizio()*60+a.getMinutoInizio();
							int minutob = b.getOraInizio()*60+b.getMinutoInizio();
							if (minutoa<minutob) return -1;
							else if(minutoa==minutob) return 0;
							else return 1;
						}
					}
				}; 
				break;
				}
				case "Numero Sala Crescente" : {criterio=(a,b)->{
					if((a.isFruibili())^(b.isFruibili())){
						if(a.isFruibili()) return -1;
						else return 1;
					}
					else{
						if(a.getNsala()<b.getNsala()) return -1;
						else if(a.getNsala()==b.getNsala()) return 0;
						else return 1;
					}
				}; 
				break;
				}

				case "Titolo in Ordine Alfabetico" : {criterio=(a,b)->{
					if((a.isFruibili())^(b.isFruibili())){
						if(a.isFruibili()) return -1;
						else return 1;
					}
					else
						return(a.getFilmAttuale().getTitolo().toLowerCase().compareTo(b.getFilmAttuale().getTitolo().toLowerCase()));
				};
				break;
				}
				}
					
				cliente.getCinema().setProgrammazione(cliente.getCinema().Ordinatore(criterio));
				save();
				
				int i=0;
				ArrayList<Spettacolo> app = new ArrayList<Spettacolo>();
				while(i<cliente.getCinema().getProgrammazione().size()){
					if(cliente.getCinema().getProgrammazione().get(i).isFruibili()) 
						app.add(cliente.getCinema().getProgrammazione().get(i));
					i++;
				}
				refresh(app);
			}
		}
		OrdinaListener ol = new OrdinaListener();

		 cronologico = new  JMenuItem("Ordine Cronologico");
		cronologico.addActionListener(ol);
		 salac = new JMenuItem("Numero Sala Crescente");
		salac.addActionListener(ol);
		 alfabetico = new JMenuItem("Titolo in Ordine Alfabetico");
		alfabetico.addActionListener(ol);
		
		/**
		 * 
		 * Listener per la gestione dei posti
		 *
		 */
		class GestisciListener implements ActionListener {
			/**
			 * Apre il frame della gestione dei posti
			 * e fa comparire il bottone per la fine della gestione dei posti
			 */
			public void actionPerformed(ActionEvent arg0) {
				azione=1;
				fine.setText("Fine Gestione");
				fine.setVisible(true);
			}
		}
		scelta = new JMenuItem("Prenotazioni/Acquisti");
		GestisciListener gl = new GestisciListener();
		scelta.addActionListener(gl);
		gestisci.add(scelta);
		
		ordina.add(cronologico);
		ordina.add(salac);
		ordina.add(alfabetico);

		visualizza.add(tutto);
		visualizza.add(settimanale);
		visualizza.add(ordina);

		menu.add(file);
		menu.add(visualizza);
		menu.add(gestisci);
		return menu;
	}

	/**
	 * Pannello su cui viene stampata la programmazione completa
	 * @param al arraylist contente gli spettacoli
	 * @return restituisce un pannelo con tutte le informazioni sui film inseriti
	 */
	public JPanel lista (ArrayList<Spettacolo> al){
		int LUNG=al.size();
		lblv= new JTextArea[LUNG];
		JPanel pnll = new JPanel();
		pnll.setLayout(new BoxLayout(pnll,BoxLayout.Y_AXIS));
		int i;

		for (i=0;i<lblv.length;i++){
			if(al.get(i)!=null){
				lblv[i]= new JTextArea();

				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				lblv[i].setText(i+1+"\n"+cliente.getCinema().getInfo(cliente.getCinema().getProgrammazione().get(i)));
				lblv[i].setBackground(Color.yellow);
				lblv[i].setBorder(BorderFactory.createMatteBorder(20,20,20,20,Color.orange));
				lblv[i].setFont(new Font("ARIAL",Font.ITALIC,15));
				lblv[i].setMaximumSize(new Dimension((int)(dim.height/1.3), 170));
				lblv[i].setMinimumSize(new Dimension((int)(dim.height/1.3), 170));
				lblv[i].setEditable(false);
				lblv[i].addMouseListener(ml);
				pnll.add(lblv[i]);
			}
		}

		pnll.setBackground(Color.orange);
		return pnll;
	}
	/**
	 * Aggiorna il pannello con la lista degli spettacoli contenuta un al
	 * @param al lista degli spettacoli
	 */
	public void refresh(ArrayList<Spettacolo> al){
		pnll.removeAll();
		pnll.add(lista(al));
		pnll.setVisible(false);
		pnll.setVisible(true);
		pnll.setBackground(Color.ORANGE);
	}
	/**
	 * Salvataggio degli oggetti all'interno del file spettacoli.wa
	 */
	public void save(){
		try{
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("spettacoli.wa")));
			oos.writeObject(cliente.getCinema().getProgrammazione());
			oos.writeObject(cliente.getCinema().getPolitiche());
			oos.close();
			}
			catch(IOException e1){}
	}
}
