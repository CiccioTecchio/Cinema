package grafica;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import cinema.Cliente;
import cinema.Gestore;
import cinema.PostoIndisponibileException;
import cinema.Spettacolo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
/**
 * 
 * Interfaccia grafica utilizzata per l'acquisto(anche di un biglietto ridotto),la prenotazione o
 * per disdire una prenotazione da parte del cliente
 *
 */
public class MyPostiCliente extends JFrame{
	
	private Spettacolo s;
	private JButton[]posti;
	private JPanel global;
	private JPanel center;
	private JPanel pnlnord;
	private JPanel pnlest;
	private ArrayList<String> date;
	private JComboBox listadate;
	private int y;
	private int giorni;
	private ActionListener sp;
	private JRadioButton compra;
	private JRadioButton prenota;
	private JRadioButton disdici;
	private ButtonGroup gruppo;
	private JButton salva;
	private JCheckBox ridotto;
	private Cliente c;
	private Home h;
	
	public MyPostiCliente(Cliente c,int np,Home h){
		this.c=c;
		center = new JPanel();
		center.setLayout(new BorderLayout());
		compra= new JRadioButton("Acquista Biglietto");
		prenota = new JRadioButton("Prenota Biglietto");
		disdici = new JRadioButton("Disdici Prenotazione");
		this.c=c;
		this.h=h;
		s= c.getCinema().getProgrammazione().get(np);
		y=s.getGiorni();
		global = new JPanel();
		global.setLayout(new BorderLayout());
		posti = new JButton[s.getpTotali()];
		date = new ArrayList<String>();
		giorni=0;
		
		Date today = new Date();
		Date d1=s.getDataInizio();
		String s1;
		Calendar c1= Calendar.getInstance();
		boolean b;
		int i;
		for(i=0;i<y;i++){
			b=(d1.getYear()==today.getYear())&&(d1.getMonth()==today.getMonth())&&(d1.getDate()==today.getDate());
			s1=(d1.getYear()+1900)+"/";
			if((""+(d1.getMonth()+1)).length()==1) s1=s1+"0"+(d1.getMonth()+1)+"/";
			else s1=s1+(d1.getMonth()+1)+"/";
			if((""+d1.getDate()).length()==1) s1=s1+"0"+d1.getDate();
			else s1=s1+d1.getDate();
			if((today.before(d1))||(b)){
			date.add(s1);
			}
			c1.setTime(d1);
			c1.add(Calendar.DATE, 1);
			d1 = c1.getTime();	
		}
		
		/**
		 * 
		 * Listener della combobox che permette di scegliere
		 * la data di proiezione dello spettacolo selezionato in precedenza
		 *
		 */
		class DateListener implements ActionListener{
			@Override
			/**
			 * Fa visualizzare la griglia dei posti dello spettacolo scelto nella data selezionata,
			 * prelevando le informazioni da una riga della matrice prenotaizoni identificata dalla distanza
			 * in giorni tra la data di inizio dello spettacolo e quella selezionata
			 */
			public void actionPerformed(ActionEvent e) {
				Date inizio = s.getDataInizio();
				String st = ((String)((JComboBox)e.getSource()).getSelectedItem());
				pnlnord.setBorder(new TitledBorder(new EtchedBorder(),(String)listadate.getSelectedItem()));
				int day = Integer.parseInt(st.substring(8,10));
				int month = Integer.parseInt(st.substring(5,7));
				int year = Integer.parseInt(st.substring(0,4));
				Date scelta = new Date (year-1900,month-1,day);		
				Calendar c1 = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				c1.setTime(inizio);
				c2.setTime(scelta);
				long g=(c2.getTimeInMillis()-c1.getTimeInMillis())/(24*3600*1000);
				giorni = (int)g;
				center.removeAll();
				center.add(getGriglia(),BorderLayout.CENTER);
				center.setVisible(false);
				center.setVisible(true);
			}
		}
		DateListener dl = new DateListener();
		
		pnlnord = new JPanel();
		String[] adate = new String[date.size()];
		for(i=0;i<date.size();i++){
			adate[i]=date.get(i);
		}
		
		listadate= new JComboBox(adate);
		listadate.addActionListener(dl);
		listadate.setSelectedIndex(0);
		pnlnord.add(listadate);
		pnlnord.setBorder(new TitledBorder(new EtchedBorder(),(String)listadate.getSelectedItem()));
		global.add(pnlnord,BorderLayout.NORTH);
		
		
		/**
		 * 
		 * Cambia il colore della poltrona in base all' opzione scelta
		 *
		 */
		class SetPostiListener implements ActionListener{
			@Override
			/**
			 * Viene visualizzata la griglia e in base all'opzione scelta(acquista,prenota ,disdici solo per la prenotazione)
			 *
			 */
			public void actionPerformed(ActionEvent e) {
				int i=(((JButton)e.getSource()).getText().charAt(0))-65;
				i=(8*i)+(Integer.parseInt(""+((JButton)e.getSource()).getText().charAt(1))-1);
				
				try{
				
				if(compra.isSelected()){
					if((posti[i].getBackground()==Color.GREEN)||(posti[i].getBackground()==Color.YELLOW)){
						posti[i].setBackground(Color.RED);
						if(ridotto.isSelected()) 
							posti[i].setForeground(Color.WHITE);
					}
					else
						if((posti[i].getBackground()==Color.RED)&&(s.getStato(i,giorni)!=0)){
							if(s.getStato(i,giorni)==1) posti[i].setBackground(Color.GREEN);
							else 
								if(s.getId(i,giorni)==c.getId()) posti[i].setBackground(Color.YELLOW);
								else throw (new PostoIndisponibileException("Posto Non Acquistabile"));
							posti[i].setForeground(Color.BLACK);
						}
						else throw (new PostoIndisponibileException("Posto Non Acquistabile"));
				}
				
				if(prenota.isSelected()){
					if(posti[i].getBackground()==Color.GREEN){
						posti[i].setBackground(Color.YELLOW);
					}
					else
						if((posti[i].getBackground()==Color.YELLOW)&&(s.getStato(i,giorni)!=2)){
							posti[i].setBackground(Color.GREEN);	
						}
						else throw (new PostoIndisponibileException("Posto non prenotabile"));
				}
				
				if(disdici.isSelected()){
					if(posti[i].getBackground()==Color.YELLOW){
						posti[i].setBackground(Color.GREEN);
					}
					else throw new PostoIndisponibileException("Non hai prenotato questo posto");
				}
	
			}
				catch(PostoIndisponibileException e1){FrameError fe = new FrameError(e1.getMessage());}
		}
		}
		
		sp = new SetPostiListener();
		center.add(getGriglia(),BorderLayout.CENTER);
		global.add(center,BorderLayout.CENTER);
		
		/**
		 * 
		 * Listener per salvare lo stato della sala
		 *
		 */
		class SalvaListener implements ActionListener{
			@Override
			/**
			 * Aggiorna le informazioni della matrice prenotazioni
			 * Controllando se il colore della poltrona(bottone) è uguale
			 * al corrispondente stato nella matrice
			 * 
			 */
			public void actionPerformed(ActionEvent e) {
				int i;
				for(i=0;i<80;i++){
					if((posti[i].getBackground()==Color.GREEN)&&(s.getStato(i,giorni)!=1)){
						c.disdici(s,i,giorni);
					}
					else{
						if((posti[i].getBackground()==Color.YELLOW)&&(s.getStato(i,giorni)!=2))
							c.prenota(s,i,giorni);
						else 
							if((posti[i].getBackground()==Color.RED)&&(s.getStato(i,giorni)!=0)){
								String st=(String)listadate.getSelectedItem();
								int day = Integer.parseInt(st.substring(8,10));
								int month = Integer.parseInt(st.substring(5,7));
								int year = Integer.parseInt(st.substring(0,4));
								Date d = new Date (year-1900,month-1,day);
								d.setHours(s.getOraInizio());
								d.setMinutes(s.getMinutoInizio());
								if(posti[i].getForeground()==Color.WHITE)
									c.acquista(s,d,i,giorni,true);
								else
									c.acquista(s,d,i,giorni,false);
							}
					}
				}
				
				if(s.getpDisponibili(giorni)<=0) s.setFruibili(false);
				else s.setFruibili(true);
				save();
				h.refresh(c.getCinema().getProgrammazione());
			}
		}
		
		salva = new JButton("Salva");
		SalvaListener sl = new SalvaListener();
		salva.addActionListener(sl);

		global.add(salva,BorderLayout.SOUTH);
		
		/**
		 * 
		 * Listener dei radiobutton (Acquista, Prenota, Disdici)
		 * e della checkbox del biglietto ridotto
		 */
		class GruppoListener implements ActionListener{
			/**
			 * Se il bottone compra viene selezionato allora si attiva la
			 * checkbox del biglietto ridotto altrimenti è disabilitato
			 */
			public void actionPerformed(ActionEvent e) {
				if(compra.isSelected())
					ridotto.setEnabled(true);
				else
					ridotto.setEnabled(false);
			}
		}
		
		GruppoListener grl = new GruppoListener();
		
		compra.setSelected(true);
		compra.addActionListener(grl);
		
		prenota.addActionListener(grl);
		
		disdici.addActionListener(grl);
		gruppo= new ButtonGroup();
		gruppo.add(compra);
		gruppo.add(prenota);
		gruppo.add(disdici);
		ridotto= new JCheckBox("Ridotto (inferiore a 12 anni)");
		ridotto.setSelected(false);
		ridotto.setEnabled(compra.isSelected());
		
		pnlest=new JPanel();
		pnlest.setLayout(new GridLayout(3,2));
		pnlest.add(compra);
		pnlest.add(ridotto);
		pnlest.add(prenota);
		pnlest.add(new JPanel());
		pnlest.add(disdici);
		
		
		global.add(pnlest,BorderLayout.EAST);
		
			add(global);
			setVisible(true);
			setTitle("Gestisci Posti");
			setResizable(false);
			setSize(800,600);
		
	}
	/**
	 * Crea la griglia dei posti
	 * (inizialmente i posti sono tutti liberi)
	 * @return pannello con la griglia dei posti
	 */
	private JPanel getGriglia (){
		JPanel pnl= new JPanel();
		pnl.setLayout(new GridLayout(10,8));
		int i;
		int j=1;
		char ch='A';
		
		boolean b;
		Date today = new Date();
		String st = ( (String)(listadate.getSelectedItem()) );
		int day = Integer.parseInt(st.substring(8,10));
		int month = Integer.parseInt(st.substring(5,7));
		int year = Integer.parseInt(st.substring(0,4));
		Date d1 = new Date (year-1900,month-1,day);	
		b=(d1.getYear()==today.getYear())&&(d1.getMonth()==today.getMonth())&&(d1.getDate()==today.getDate());
		
		if((b)&&((s.getOraInizio()-12)<=today.getHours()))
			prenota.setEnabled(false);
		else
			prenota.setEnabled(true);
		
		for(i=0;i<80;i++){
			posti[i]=new JButton(ch+""+j);
			j++;
			if(j>8){ch++;
			         j=1;}
			
			
			
			switch (s.getStato(i,giorni)){
			case 0: {posti[i].setBackground(Color.RED);	break;}
			case 1: {posti[i].setBackground(Color.GREEN);break;}
			case 2: {

				if((b)&&((s.getOraInizio()-12)<=today.getHours())){
					posti[i].setBackground(Color.GREEN);
					s.setStato( i,giorni, 1);
					s.setId(i ,giorni, -1);
					save();
				}
				else {
					if(s.getId(i,giorni)==c.getId())
						posti[i].setBackground(Color.YELLOW);
					else
						posti[i].setBackground(Color.RED);	
				}
				break;
			}
			
			case 3: {posti[i].setBackground(Color.DARK_GRAY);break;}
			}
			posti[i].addActionListener(sp);
			pnl.add(posti[i]);
		}
		
		return pnl;
	}
	/**
	 * Salva nel file "spettacoli.wa" le modifiche fatte agli spettacoli
	 */
	private void save(){
		try{
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("spettacoli.wa")));
			oos.writeObject(c.getCinema().getProgrammazione());
			oos.writeObject(c.getCinema().getPolitiche());
			oos.close();
			}
			catch(IOException e1){}
	}
}