package grafica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cinema.Film;
import cinema.Gestore;
import cinema.Spettacolo;

public class MyReplaceSpettacolo extends JFrame{

	
	private JLabel lbltitolo;
	private JLabel lbldurata;
	private JLabel lblinizio;
	private JLabel lblfine;
	private JLabel lblprezzo;
	private JLabel lblns;
	private JLabel lblorainizio;
	private JLabel lbldesc;
	private JTextField titolo;
	private JTextField durata;
	private JComboBox annoinizio;
	private JComboBox meseinizio;
	private JComboBox giornoinizio;
	private JPanel datainizio;
	private JComboBox annofine;
	private JComboBox mesefine;
	private JComboBox giornofine;
	private JPanel datafine;
	private JTextField prezzo;
	private JComboBox ns;
	private JPanel pnlns;
	private JComboBox ora;
	private JComboBox minuto;
	private JPanel orainizio;
	private TextArea desc;
	private JButton inserisci;
	private JPanel su;
	private JPanel giu;
	private JPanel global;
	private Gestore g;
	
public MyReplaceSpettacolo(Gestore g, int indice, HomeAdmin hm){
	
	this.g=g;
	setSize(600, 600);
	setTitle("Sostituisci Film");
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	setResizable(false);
	setAlwaysOnTop(true);
		
		 lbltitolo= new JLabel("Titolo");
		 lbldurata = new JLabel("Durata");
		 lblinizio = new JLabel("Data inizio proiezione (yyyy-mm-dd)");
		 lblfine = new JLabel("Data fine proiezione");
		 lblprezzo = new JLabel("Prezzo Biglietto (Standard)");
		 lblns = new JLabel("Numero Sala");
		 lblorainizio = new JLabel("Ora Inizio (HH:MM)");
		 lbldesc = new JLabel("Descrizione");
		 
		 titolo = new JTextField(10);
		 durata= new JTextField(10);
		 
		 Date d = new Date();
		 
		 annoinizio = new JComboBox();
		 annoinizio.addItem(d.getYear()+1900);
		 annoinizio.addItem(d.getYear()+1901);
		 annofine = new JComboBox();
		 annofine.addItem(d.getYear()+1900);
		 annofine.addItem(d.getYear()+1901);
		 
		 Integer[] mesi = {1,2,3,4,5,6,7,8,9,10,11,12};
		 meseinizio = new JComboBox(mesi);
		 mesefine = new JComboBox(mesi);
		 
		 Integer[] giorni = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
		 giornoinizio = new JComboBox(giorni);
		 giornofine = new JComboBox(giorni);
		 
		 
		 class MeseListener implements ActionListener{
				public void actionPerformed(ActionEvent arg0) {
					
					Calendar c= Calendar.getInstance();
					c.set(Calendar.YEAR,((int)annoinizio.getSelectedItem()));
					c.set(Calendar.MONTH,((int)(meseinizio.getSelectedItem()))-1);
					int giornimax = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					giornoinizio.removeAllItems();
					int i;
					for(i=0; i<giornimax; i++){
						giornoinizio.addItem(giorni[i]);
					}		
					
					int x = (int)annoinizio.getSelectedItem();
					annofine.removeAllItems();
					annofine.addItem(x);
					annofine.addItem(x+1);
					
				
				} 
			 }
		 
		 
		MeseListener mol = new MeseListener();
		annoinizio.addActionListener(mol);
		 meseinizio.addActionListener(mol);
		 
		 class Mese2Listener implements ActionListener{
				public void actionPerformed(ActionEvent arg0) {
					Calendar c= Calendar.getInstance();
					if(annofine.getSelectedItem()!=null){
					c.set(Calendar.YEAR,((int) (annofine.getSelectedItem())));
					c.set(Calendar.MONTH,((int)(mesefine.getSelectedItem()))-1);
					int giornimax = c.getActualMaximum(Calendar.DAY_OF_MONTH);	
					int i;
					giornofine.removeAllItems();
					for(i=0; i<giornimax; i++){
						giornofine.addItem(giorni[i]);
					}
					}
				} 
			 }
		 
		 
		 Mese2Listener mtl = new Mese2Listener();
		 annofine.addActionListener(mtl);
		 mesefine.addActionListener(mtl);
		 
		 datainizio = new JPanel();
		 datainizio.add(annoinizio);
		 datainizio.add(meseinizio);
		 datainizio.add(giornoinizio);
		 
		 datafine  = new JPanel();
		 datafine.add(annofine);
		 datafine.add(mesefine);
		 datafine.add(giornofine);
		 
		 prezzo= new JTextField(10);
		 
		 pnlns = new JPanel();
		 ns = new JComboBox();
		 ns.addItem(1);
		 ns.addItem(2);
		 ns.addItem(3);
		 ns.addItem(4);
		 ns.addItem(5);
		 pnlns.add(ns);
		 ns.setMaximumRowCount(3);
		 
		 ora = new JComboBox();
		 int i;
		 for(i=0;i<24;i++){
			 String st = ""+i;
			 if (st.length()==1) st="0"+st;
			 ora.addItem(st);
		 }
		 ora.setMaximumRowCount(2);
		 
		 minuto = new JComboBox();
		 for(i=0;i<60;i++){
			 String st = ""+i;
			 if (st.length()==1) st="0"+st;
			 minuto.addItem(st);
		 }
		 minuto.setMaximumRowCount(2);
		
		 
		 orainizio = new JPanel();
		 orainizio.add(ora);
		 orainizio.add(minuto);
		 
		 desc = new TextArea();
		 inserisci = new JButton("Salva");
		 su = new JPanel();
		 su.setLayout(new GridLayout(8,2));
		 su.add(lbltitolo);
		 su.add(titolo);
		 su.add(lbldurata);
		 su.add(durata);
		 su.add(lblinizio);
		 su.add(datainizio);
		 su.add(lblfine);
		 su.add(datafine);
		 su.add(lblprezzo);
		 su.add(prezzo);
		 su.add(lblns);
		 su.add(pnlns);
		 su.add(lblorainizio);
		 su.add(orainizio);
		 su.add(lbldesc);
		 su.add(new JPanel());
		 
		 class InserisciListener implements ActionListener{
				public void actionPerformed(ActionEvent arg0) {
					if((!titolo.getText().isEmpty())&&(!durata.getText().isEmpty())&&(!prezzo.getText().isEmpty())){
						Film f=null;
						Spettacolo spe=null;
					try{	
						Date today = new Date();
						Date in = new Date ((int)annoinizio.getSelectedItem()-1900,(int)meseinizio.getSelectedItem()-1,(int)giornoinizio.getSelectedItem());
						Date fi = new Date ((int)annofine.getSelectedItem()-1900,(int)mesefine.getSelectedItem()-1,(int)giornofine.getSelectedItem());
						if(fi.before(in)) throw new IllegalArgumentException("Data fine precedente a data inizo");
						double p = Double.parseDouble(prezzo.getText());
						int d = Integer.parseInt(durata.getText());
						String trama = desc.getText();
						f = new Film (titolo.getText(),trama,p,d);
						int h = Integer.parseInt((String)ora.getSelectedItem());
						int m = Integer.parseInt((String)minuto.getSelectedItem());
						spe = new Spettacolo(ns.getSelectedIndex()+1,f,in,fi,h,m);
						
						int i=0;
						boolean sameS=false;
						Date daI=null;
						Date daF=null;
						int minutaggioI=0;
						int minutaggioF=0;
						int minutaggioNew=(h*60)+m;
						while(i<g.getCinemaGestore().getProgrammazione().size()){
							sameS=(int)ns.getSelectedItem()==g.getCinemaGestore().getProgrammazione().get(i).getNsala();
							daI=g.getCinemaGestore().getProgrammazione().get(i).getDataInizio();
							daF=g.getCinemaGestore().getProgrammazione().get(i).getDataFine();
							minutaggioI=g.getCinemaGestore().getProgrammazione().get(i).getOraInizio()*60;
							minutaggioI+=g.getCinemaGestore().getProgrammazione().get(i).getMinutoInizio();
							minutaggioF+=minutaggioI+g.getCinemaGestore().getProgrammazione().get(i).getFilmAttuale().getDurata();

							if(sameS
								&&(((daI.before(in))&&(daF.after(in)))||((daI.before(fi))&&(daF.after(fi))))
								&&(((minutaggioNew>=minutaggioI)&&(minutaggioNew<=minutaggioF))||(((minutaggioNew+d)>=minutaggioI)&&(minutaggioNew<minutaggioI))))	
							{throw new IllegalArgumentException("Sala occupata");}

							i++;
						}
						
						g.getCinemaGestore().replaceSpettacolo(indice, spe);
						hm.refresh();
						save();
						dispose();
					}
					catch(IllegalArgumentException e){FrameError fe = new FrameError(e.getMessage());}
					
					}
					else {FrameError fe = new FrameError("Riempire tutti i campi");}
			 }
		 }		
		 
		 
		InserisciListener il = new InserisciListener();
		 inserisci.addActionListener(il);
		 giu = new JPanel();
		 giu.setLayout(new BorderLayout());
		 giu.add(desc,BorderLayout.CENTER);
		 giu.add(inserisci,BorderLayout.SOUTH);
		 global= new JPanel();
		 global.setLayout(new BorderLayout());
		 global.add(su,BorderLayout.NORTH);
		 global.add(giu,BorderLayout.CENTER);
		 add(global);
		 SpettacoloInfo(g.getCinemaGestore().getProgrammazione().get(indice));
		 
		 setVisible(true);
}

	private void SpettacoloInfo(Spettacolo s){
		Date in =  s.getDataInizio();
		Date fi =  s.getDataFine();
		titolo.setText(s.getFilmAttuale().getTitolo());
		durata.setText(""+s.getFilmAttuale().getDurata());
		annoinizio.setSelectedItem(in.getYear()+1900);
		meseinizio.setSelectedIndex(in.getMonth());
		giornoinizio.setSelectedIndex(in.getDate()-1);
		annofine.setSelectedItem(fi.getYear()+1900);
		mesefine.setSelectedIndex(fi.getMonth());
		giornofine.setSelectedIndex(fi.getDate()-1);
		prezzo.setText(""+s.getFilmAttuale().getPrezzo());
		ns.setSelectedIndex(s.getNsala()-1);
		ora.setSelectedIndex(s.getOraInizio());
		minuto.setSelectedIndex(s.getMinutoInizio());
		desc.setText(s.getFilmAttuale().getDesc());
	}
	
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
