package cinema;

import grafica.FrameError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
/**
La classe Cinema è l'astrazione di un cinema esso contiene un insieme di spettacoli
un inseme di politiche di sconto (ordinati in ordinate in ordine decrescente in base
alla percentuale di sconto),
*/
public class Cinema {
		private ArrayList<Sconto> politiche;
		private ArrayList<Spettacolo>  programmazione;
		private boolean b;
		
		public Cinema(){
			b=false;
			File f1 = new File("spettacoli.wa");
		
			
			try{
				if (!f1.exists()){
					PrintWriter f= new PrintWriter("spettacoli.wa");
					f.close();
					programmazione = new ArrayList<Spettacolo>();
					politiche = new ArrayList<Sconto>();
					Sconto s0 = new Sconto("Ridotto",50,(a,b)->{if(b) return true; else return false;},true);
					Sconto s1 = new Sconto("Giorno",40,(a,b)->{if(a.getDay()==3)return true; else return false;},true);
					Sconto s2 = new Sconto("Pomeridiana",30,((a,b)->{if((a.getHours()>14)&&(a.getHours()<19)) return true; else return false; }),true);
					Sconto s3 = new Sconto("NonSconto",0,(a,b)->true,true);
					politiche.add(s0);
					politiche.add(s1);
					politiche.add(s2);
					politiche.add(s3);
					save();
					
				}
				
				else
				{
					programmazione = new ArrayList<Spettacolo>();
					politiche = new ArrayList<Sconto>();
					boolean b=false;
					try{
						
					FileInputStream fis = new FileInputStream(f1);	
					ObjectInputStream ois = new ObjectInputStream(fis);
					programmazione = (ArrayList<Spettacolo>)ois.readObject();
					politiche = (ArrayList<Sconto>)ois.readObject();
					b=true;
					ois.close();
					}
					catch(ClassNotFoundException e){}
					catch(IOException e){if(!b){FrameError err = new FrameError(e.getMessage()+"\nNessuno Spettacolo Inserito"); System.out.println(e.getMessage());}}
					
					
				}	
				
			}
			catch(IOException e){}
			
			autoRemove();
			resetIncasso();
			save();
		}
		//chiudi costruttore
		
		/**
		 * Fra le varie politiche istanziate sceglie lo sconto migliore da applicare
		 * @param d data dello spettacolo in cui applicare lo sconto
		 * @param b b true se può applicato il prezzo ridotto false altrimenti
		 * @return miglior sconto applicabile fra gli attivi
		 */
		
		public int getMiglioreSconto(Date d,boolean b){
			int i=0;
			while(true){
				if(politiche.get(i).isStato()){
					if(politiche.get(i).getCriterio().idoneo(d, b)) {
						return politiche.get(i).getPercentuale();
					}
				}
				i++;
			}
		}
		/**
		 * Restituisce l'insieme delle poliche
		 * @return l'arrayList delle politiche
		 */
		public ArrayList<Sconto> getPolitiche() {
			return politiche;
		}
		
		/**
		 * Restituisce l'inseme degli spettacoli
		 * @return l'arrayList di spettacoli
		 */
		public ArrayList<Spettacolo> getProgrammazione() {
			return programmazione;
		}
		
		

		/**
		 * Controlla dalla data attuale fino alla settimana successiva
		 * tutti gli spettacoli restituendo solo gli spettacoli che iniziano
		 * o già iniziati in settimana
		 * @return un ArrayList contenete gli spettacoli dalla data attuale fino alla settimana successiva
		 */
		public ArrayList<Spettacolo> getProgrammaSettimanale(){
			ArrayList<Spettacolo> help = new ArrayList<Spettacolo>();
			Date today = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(today);
			calendar.add(Calendar.DATE, 7);
			Date pw = calendar.getTime();
			int i;
			for(i=0;i<programmazione.size();i++){
				if (!(programmazione.get(i).getDataInizio().after(pw)))
					help.add(programmazione.get(i));
				else
					help.add(null);
			}
			
			return help;
		}
		/**
		 * Controlla in basa alla sala scelta dall' utente quali spettacoli vengono riprodotti nella data sala
		 * @param ns numero della sala 
		 * @return un ArrayList contente tutti gli spettacoli che vengono riprodotti nella sala ns
		 */
		public ArrayList<Spettacolo> getProgrammaSala(int ns) {
			ArrayList<Spettacolo> help = new ArrayList<Spettacolo>();
			Date today = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(today);
			calendar.add(Calendar.DATE, 7);
			Date pw = calendar.getTime();
			int i;
			for(i=0;i<programmazione.size();i++){
				if (!(programmazione.get(i).getDataInizio().after(pw))&&(programmazione.get(i).getNsala()==ns))
					help.add(programmazione.get(i));
				else
					help.add(null);
			}
			
			return help;
		}
		
		/**
		 * In basa a un criterio scelto dall' utente esso ordina l'ArrayList
		 * @param criterio ,scelta dell'utente in basa a cosa vuole ordiare
		 * @return vettoreapp , vettore copia di programmazione, esso
		 * verrà ordinato e restituito
		 */
		
		public ArrayList<Spettacolo> Ordinatore (Comparator<Spettacolo> criterio){
			boolean b=false;
			ArrayList<Spettacolo> vettapp= programmazione;
			int i=vettapp.size();
			int j;
			Spettacolo app;
			while(!b){
				b=true;
			for (j=0;j<i-1;j++){
				if(criterio.compare(vettapp.get(j), vettapp.get(j+1))>0){
					b=false;
					app=vettapp.get(j);
					vettapp.set(j, vettapp.get(j+1));
					vettapp.set(j+1, app);
				}	
			}
			i--;
		  }
			return vettapp;
		}
		
		/**
		 * Informazioni sullo spettacolo
		 * @param s spettacolo scelto dall'utente
		 * @return desc , Stringa contenete tutte le informazioni dello spettacolo s
		 */
		
		public String getInfo(Spettacolo s){
			String desc="";
			
			desc=s.getFilmAttuale().getTitolo()+"\n";
			desc=desc+"Sala:"+s.getNsala()+"\n";
			String min=""+s.getMinutoInizio();
			if(min.length()==1) min="0"+min;
			desc=desc+"Ora Inizio:"+s.getOraInizio()+":"+min+"\n";
			desc=desc+"Durata:"+s.getFilmAttuale().getDurata()+"\n";
			desc=desc+"Prezzo:"+s.getFilmAttuale().getPrezzo()+"\n";
			Date today = new Date();
			Date d=s.getDataInizio();
			if(today.before(d))
				desc=desc+"Posti prima serata:"+s.getpDisponibili(0);
			else{
				Calendar c1 = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				c1.setTime(d);
				c2.setTime(today);
				long g= (c2.getTimeInMillis()-c1.getTimeInMillis())/(3600*24*1000);
				desc=desc+"Posti Oggi:"+s.getpDisponibili((int)g);
			}
			
			return desc;
		}

		public boolean equals(Object other){
			if(other==null)return false;
			if(getClass()!=other.getClass())return false;
			Cinema obj=(Cinema)other;
			return (obj.programmazione==programmazione)&&
					(obj.politiche==politiche);
		}
		
		public Cinema clone(){
			try{
				Cinema cloned=(Cinema)super.clone();
				return cloned;
			}catch(CloneNotSupportedException e){return null;}
		}
		
		
		/**
		 * Scrive sul file(spettacolo.wa) tutti gli spettacoli inseriti e le politiche di sconto
		 */
		public void save(){
			try{
				ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("spettacoli.wa")));
				oos.writeObject(programmazione);
				oos.writeObject(politiche);
				oos.close();
				}
				catch(IOException e1){}
		}
		
		/**
		 * Con il trascorrere del tempo alcuni spettacoli finiscono
		 * questa funzione si occupa di autorimuovere gli spettacoli finiti
		 */
		private void autoRemove(){
			int i=0;
			Date today = new Date();
			while(i<programmazione.size()){
				if(programmazione.get(i).getDataFine().before(today))
					programmazione.remove(i);
				else
					i++;
			}
		}
		
		/**
		 * Il gestore vuole conoscere l'incasso settimanale di tutti i film trascorsa
		 * una settimana l'incasso va azzerato. il metodo ogni lunedì resetta l'incasso
		 */
		private void resetIncasso(){
			int i=0;
			Date today = new Date();
			
			if((today.getDay()==1)&&(!b)){	
				while(i<programmazione.size()){
					programmazione.get(i).getFilmAttuale().setIncasso(0);
					i++;
				}
				b=true;
			}
			if(today.getDay()==2){	
				b=false;
			}
		}

		/**
		 * cambia l'array di programmazione con un nuovo array
		 * @param programmazione nuovo array
		 */
		public void setProgrammazione(ArrayList<Spettacolo> programmazione) {
			this.programmazione = programmazione;
		}

		
}





