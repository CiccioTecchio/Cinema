package grafica;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import cinema.Film;
import cinema.Gestore;
import cinema.Spettacolo;

/**
 * Frame che contiente l'interfaccia grafica che userà il gestore 
 *
 */
public class HomeAdmin extends JFrame{
	
	private int azione=0;
	private JButton fine;
	private JPanel pnll;
	private MouseListener ml;
	private JPanel pnlr;
	private TextArea txt;
	private JScrollPane jspl;
	private JMenuBar menu;
	private JMenu file;
	private JMenu visualizza;
	private JMenu modifica;
	private JMenu sconti;
	private JMenu posti;
	private JMenuItem exit;
	private JMenuItem salva;
	private JMenu settimanale;
	private JMenuItem npl;
	private JMenu incasso;
	private JMenuItem tot;
	private JMenuItem film;
	private JMenuItem aggiungi;
	private JMenuItem elimina;
	private JMenuItem sostituisci;
	private JMenuItem gestiscisconti;
	private JTextArea[] lblv;
	private JMenuItem gposti;
	private Gestore g;
	
	public  HomeAdmin (Gestore g) {

		this.g=g;
		setTitle("Cinema | Gestore");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
		setSize(dim.width,dim.height);
		pnlr = new JPanel();
		pnlr.setLayout(new BoxLayout(pnlr, BoxLayout.Y_AXIS));
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
			 * Apre il frame 
			 * Apre il frame per la sostituzione di uno spettacolo (azione 2)
			 * Apre il frame visualizza l'incasso per spettacolo (azione 3)
			 * Apre il frame di gestione dei posti (azione 4)
			 * @param arg0 spettacolo selezionato
			 * 
			 */
			public void mouseClicked(MouseEvent arg0) {
				int i=(Integer.parseInt(""+((JTextArea)arg0.getSource()).getText().charAt(0)))-1;
				if(azione==0){
				
				txt.setText(g.getCinemaGestore().getProgrammazione().get(i).getFilmAttuale().getDesc());
				Date d = g.getCinemaGestore().getProgrammazione().get(i).getDataInizio();
				txt.setText(txt.getText()+"\nIn Programmazione da:"+d.getDate()+"/"+(d.getMonth()+1)+"/"+(d.getYear()+1900));
				d=g.getCinemaGestore().getProgrammazione().get(i).getDataFine();
				txt.setText(txt.getText()+" a:"+d.getDate()+"/"+(d.getMonth()+1)+"/"+(d.getYear()+1900));

				}
				
				if(azione==1){
					g.getCinemaGestore().removeSpettacolo(i);
					save();
					refresh();
					}
				if(azione==2){
					MyReplaceSpettacolo mrs = new MyReplaceSpettacolo(g, i, HomeAdmin.this);
				}
				
				if(azione==3){
					Film f = g.getCinemaGestore().getProgrammazione().get(i).getFilmAttuale();
					FrameError fe = new FrameError(""+g.getCinemaGestore().getIncassoFilm(i));
					fe.setTitle("Incasso Settimana "+f.getTitolo());
				}
				
				if(azione==4){
					MyPosti mp = new MyPosti(g,i,HomeAdmin.this);
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
		pnll = lista();
		
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
		class FineListener implements ActionListener {

			@Override
			/**
			 * setta azione a 0 nasconde il bottone salva i dati e aggiorna la pagina
			 */
			public void actionPerformed(ActionEvent e) {
				azione=0;
				fine.setVisible(false);
				save();
				refresh();
				
				}
		}
			
			
		FineListener fl = new FineListener();
		fine = new JButton();
		fine.addActionListener(fl);
		pnlr.add(fine);
		fine.setVisible(false);
		
		JPanel pnl = new JPanel();
		pnl.setLayout(new GridLayout(1,2));
		pnl.add(jspl);
		pnl.add(pnlr);

		add(pnl);

		setJMenuBar(myMenuBar());
		setVisible(true);

	}

	//fine costruttore
	/**
	 * 
	 * Restituisce una barra dei men� con le opzione che pu� svolgere il gestore
	 * @return la barra dei menu del gestore
	 */
	public JMenuBar myMenuBar() {

		menu= new JMenuBar();
		file = new JMenu("File");
		visualizza = new JMenu("Visualizza");
		modifica = new JMenu("Modifica");
		sconti = new JMenu("Sconti");
		posti = new JMenu("Posti");


		 /**
		  *Listener er terminare l'esecuzione del programma 
		  */
		class ExitListener implements ActionListener {
			/**
			 * termina l'esecuzione del programma
			 */
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		}

		exit = new JMenuItem("Exit");
		ExitListener el = new ExitListener();
		exit.addActionListener(el);
		file.add(exit);
		
		/**
		 * 
		 * Listener per il salvataggio delle modifiche fatte dal gestore
		 *
		 */
		class SaveListener implements ActionListener {
			/**
			 * Salva le modifiche fatte dal gestore
			 */
			public void actionPerformed(ActionEvent arg0) {
				save();
		}
		}

		salva = new JMenuItem("Salva");
		SaveListener sl = new SaveListener();
		salva.addActionListener(sl);
		file.add(salva);

		settimanale = new JMenu("Programma per");
		npl = new JMenuItem("Numero Posti Liberi");
		incasso = new JMenu("Incasso");
		tot = new JMenuItem("Totale");
		film = new JMenuItem("Film");
		
		 /**
		  * Listener per l'ordinamento
		  *
		  */
		class OrdinaListener implements ActionListener {
			
			/**
			 * Utilizza il metodo ordinatore della classe Cinema
			 */
			public void actionPerformed(ActionEvent arg0) {

				g.getCinemaGestore().setProgrammazione(g.getCinemaGestore().Ordinatore(
						(a,b)->{
							int giornia;
							int giornib;
							long differenza;
							Date inizioa = a.getDataInizio();
							Date iniziob = b.getDataInizio();
							Date today = new Date();
							Calendar c1 = Calendar.getInstance();
							Calendar c2 = Calendar.getInstance();
							if(today.before(inizioa)) giornia = 0;
							else{
								c1.setTime(inizioa);
								c2.setTime(today);
								differenza = (c2.getTimeInMillis() - c1.getTimeInMillis())/ (24*3600*1000);
								giornia = (int) differenza;
							}
							if(today.before(iniziob)) giornib = 0;
							else{
								c1.setTime(iniziob);
								c2.setTime(today);
								differenza = (c2.getTimeInMillis() - c1.getTimeInMillis())/ (24*3600*1000);
								giornib = (int) differenza;
							}
							
							if (a.getpDisponibili(giornia) > b.getpDisponibili(giornib)) return 1;
							else  if(a.getpDisponibili(giornia) == b.getpDisponibili(giornib)) return 0;
							else return -1;}
						)
						);
				save();
				refresh();
			}
		}
		
		OrdinaListener ol = new OrdinaListener();
		npl.addActionListener(ol);
		settimanale.add(npl);
		
		
		/**
		 * 
		 * Listener per l'incasso totale
		 *
		 */
		class IncassoListener implements ActionListener {
			/**
			 * Apre un frame che mostra quanto ha incassato il cinema in una settimana
			 */
			public void actionPerformed(ActionEvent arg0) {
				/*x = Math.floor(x*100);
				  x = x/100;
				  return x;*/
				String s=""+g.getCinemaGestore().getIncassoTotale();
				double x=g.getCinemaGestore().getIncassoTotale();
				x=Math.floor(x*100);
				x=x/100;
				FrameError fe = new FrameError(""+x);
				fe.setTitle("Incasso Totale Settimana");
			}
		}
		
		
		/**
		 * Listener per far visualizzare l'incasso di un singolo film
		 *
		 */
		class IncassoFilmListener implements ActionListener {
			/**
			 * Setta ad azione 3, quando viene selezionato un film
			 * viene mostrato l'incasso del film
			 */
			public void actionPerformed(ActionEvent arg0) {
				azione=3;
				fine.setText("Fine Visualizza Incasso");
				fine.setVisible(true);
			}
		}
		
		IncassoListener incl = new IncassoListener();
		tot.addActionListener(incl);
		IncassoFilmListener incfl = new IncassoFilmListener();
		film.addActionListener(incfl);
		incasso.add(tot);
		incasso.add(film);
		visualizza.add(settimanale);
		visualizza.add(incasso);

		/**
		 * 
		 * Listener per l'aggiunta di uno spettacolo
		 *
		 */
		class AddListener implements ActionListener {
			/**
			 * Apre il frame per l'aggiunta dello spettacolo
			 */
			public void actionPerformed(ActionEvent arg0) {
				azione=0;
				MyAddSpettacolo maf= new MyAddSpettacolo(g,HomeAdmin.this);
				fine.setVisible(false);
			}
		}


		/**
		 * 
		 * Listener per l'eliminazione di un film
		 *
		 */
		class RemoveListener implements ActionListener {
			/**
			 * Apre il frame per la rimozione dello spettacolo
			 */
			public void actionPerformed(ActionEvent arg0) {
				azione=1;
				fine.setText("Fine Rimozione");
				fine.setVisible(true);
				
			}
		}

		/**
		 * 
		 * Listener per la modifica del film
		 *
		 */
		class ModListener implements ActionListener {
			/**
			 * Setta l'azione a due e una volta cliccato lo spettacolo da modificare lancia 
			 * il frame di modifica
			 */

			public void actionPerformed(ActionEvent arg0) {
				azione=2;
				fine.setText("Fine Modifica");
				fine.setVisible(true);
			}
		}

		aggiungi = new JMenuItem("Aggiungi Nuovo Spettacolo");
		elimina = new JMenuItem("Elimina Spettacolo");
		sostituisci = new JMenuItem("Modifica Spettacoli");
		AddListener addl = new AddListener();
		RemoveListener rmvl= new RemoveListener();
		ModListener modl = new ModListener();
		aggiungi.addActionListener(addl);
		elimina.addActionListener(rmvl);
		sostituisci.addActionListener(modl);
		modifica.add(aggiungi);
		modifica.add(elimina);
		modifica.add(sostituisci);
		
		/**
		 * 
		 * Listener per gli sconti
		 *
		 */
		class ScontiListener implements ActionListener {
			/**
			 * Apre il frame per gestire le politiche di sconto
			 */
			public void actionPerformed(ActionEvent arg0) {
				MySconti sconti = new MySconti(g);
			}
		}
		
		ScontiListener scl = new ScontiListener();
		gestiscisconti = new JMenuItem("Gestisci politiche di sconto");
		gestiscisconti.addActionListener(scl);
		sconti.add(gestiscisconti);
		
		
		/**
		 * 
		 * Listener per la gestione dei posti in sala
		 *
		 */
		class PostiListener implements ActionListener {
			/**
			 * Setta l'azione a 4 aprendo il frame per a gestione dei posti
			 * in cui il gestore può rendere inbabilitati o abilitare posti
			 */
			public void actionPerformed(ActionEvent arg0) {
				azione=4;
				fine.setVisible(true);
				fine.setText("Fine Gestione Posti");
			}
		}
		
		PostiListener pl = new PostiListener();
		gposti = new JMenuItem("Gestisci Posti");
		gposti.addActionListener(pl);
		posti.add(gposti);
		
		menu.add(file);
		menu.add(visualizza);
		menu.add(modifica);
		menu.add(sconti);
		menu.add(posti);
		return menu;
	}
	/**
	 * Salvataggio degli oggetti all'interno del file spettacoli.wa
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
	/**
	 * Aggiorna l'interfaccia grafica dell homeadmin
	 */
	public void refresh(){
		pnll.removeAll();
		pnll.add(lista());
		pnll.setVisible(false);
		pnll.setVisible(true);
		pnll.setBackground(Color.ORANGE);
	}
	/**
	 * Pannello contenente le informazioni sugli spettacoli
	 * @return pannello con informazioni sugli spettacoli
	 */
	public JPanel lista (){
		int LUNG=g.getCinemaGestore().getProgrammazione().size();
		lblv= new JTextArea[LUNG];
		JPanel pnll = new JPanel();
		pnll.setLayout(new BoxLayout(pnll,BoxLayout.Y_AXIS));
		int i;
		
		for (i=0;i<lblv.length;i++){
			lblv[i]= new JTextArea();
			
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			lblv[i].setText(i+1+"\n"+g.getCinemaGestore().getInfo(g.getCinemaGestore().getProgrammazione().get(i)));
			lblv[i].setBackground(Color.yellow);
			lblv[i].setBorder(BorderFactory.createMatteBorder(20,20,20,20,Color.orange));
			lblv[i].setFont(new Font("ARIAL",Font.ITALIC,15));
			lblv[i].setMaximumSize(new Dimension((int)(dim.height/1.3), 170));
			lblv[i].setMinimumSize(new Dimension((int)(dim.height/1.3), 170));
			lblv[i].setEditable(false);
			lblv[i].addMouseListener(ml);
			pnll.add(lblv[i]);
		}
		pnll.setBackground(Color.orange);
		return pnll;
	}
	
}



