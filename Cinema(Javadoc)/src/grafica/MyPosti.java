package grafica;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import cinema.Gestore;
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
 * Interfaccia grafica per l'abilitazione e la disabilitazione dei posti in sala
 * da parte del gestore
 *
 */
public class MyPosti extends JFrame{
	
	private Gestore g;
	private Spettacolo s;
	private JButton[]posti;
	private JPanel global;
	private JPanel center;
	private JPanel pnlnord;
	private ArrayList<String> date;
	private JComboBox listadate;
	private JButton salva;
	private int y;
	private int giorni;
	private ActionListener sp;
	private HomeAdmin hm;
	
	public MyPosti(Gestore g,int np,HomeAdmin hm){
		
		this.hm=hm;
		center = new JPanel();
		center.setLayout(new BorderLayout());
		
		this.g=g;
		s= g.getCinemaGestore().getProgrammazione().get(np);
		y=s.getGiorni();
		global = new JPanel();
		global.setLayout(new BorderLayout());
		posti = new JButton[s.getpTotali()];
		date= new ArrayList<String>();
		giorni=0;
		
		Date today = new Date();
		Date d1=s.getDataInizio();
		String s1;
		Calendar c1= Calendar.getInstance();
		int i;
		boolean b;
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
		 * Listener della combo box che permette di scegliere
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
		 * Listener per rendere inabilitati o abilitati i posti
		 *
		 */
		class SetPostiListener implements ActionListener{
			/**
			 * Controlla se il colore del bottone premuto è verde(abilitato) allora lo fa diventare grigio(inabilitato)
			 * e viceversa
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				int i=(((JButton)e.getSource()).getText().charAt(0))-65;
				i=(8*i)+(Integer.parseInt(""+((JButton)e.getSource()).getText().charAt(1))-1);
				
				if(posti[i].getBackground()==Color.GREEN) posti[i].setBackground(Color.DARK_GRAY);
				else if(posti[i].getBackground()==Color.DARK_GRAY) posti[i].setBackground(Color.GREEN);
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
			/**
			 * Aggiorna le informazioni della matrice prenotazioni
			 * Controllando se il colore della poltrona(bottone) è uguale
			 * al corrispondente stato nella matrice
			 * 
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				int i;
				for(i=0;i<80;i++){
					if((posti[i].getBackground()==Color.GREEN)&&((s.getStato(i,giorni))!=1)){g.disponibile(s,i,giorni);}
					else if((posti[i].getBackground()==Color.DARK_GRAY)&&((s.getStato(i,giorni))!=3)){g.indisponibile(s,i,giorni);}
				}
				if(s.getpDisponibili(giorni)<=0) s.setFruibili(false);
				else s.setFruibili(true);
				save();
				hm.refresh();
			}
		}
		
		salva = new JButton("Salva");
		SalvaListener sl = new SalvaListener();
		salva.addActionListener(sl);

		global.add(salva,BorderLayout.SOUTH);
		
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
		char c='A';
		
		boolean b;
		Date today = new Date();
		String st = ( (String)(listadate.getSelectedItem()) );
		int day = Integer.parseInt(st.substring(8,10));
		int month = Integer.parseInt(st.substring(5,7));
		int year = Integer.parseInt(st.substring(0,4));
		Date d1 = new Date (year-1900,month-1,day);	
		b=(d1.getYear()==today.getYear())&&(d1.getMonth()==today.getMonth())&&(d1.getDate()==today.getDate());
		
		for(i=0;i<80;i++){
			posti[i]=new JButton(c+""+j);
			j++;
			if(j>8){c++;
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
					posti[i].setBackground(Color.YELLOW);
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
			oos.writeObject(g.getCinemaGestore().getProgrammazione());
			oos.writeObject(g.getCinemaGestore().getPolitiche());
			oos.close();
			}
			catch(IOException e1){}
	}
}