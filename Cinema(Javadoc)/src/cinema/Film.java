package cinema;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Astrazione di un Film
 * 
 *
 */
public class Film implements Cloneable,Serializable{
	
		private String titolo;
		private int durata;
		private double incasso;
		private String desc;
		private double prezzo;
		
		
		public Film(String t,String tr,double prezzo,int durata){
			titolo=t;
			incasso=0;
			desc=tr;
			this.prezzo=prezzo;
			this.durata=durata;
		}
		/**
		 * Restituisce il prezzo di un film
		 * @return prezzo del film
		 */
		public double getPrezzo() {
			return prezzo;
		}

		/**
		 * Modifica il prezzo del film
		 * @param prezzo nuovo prezzo del film
		 */
		public void setPrezzo(double prezzo) {
			this.prezzo = prezzo;
		}

		/**
		 * Restituisce il titolo del film
		 * @return titolo del film
		 */
		public String getTitolo() {
			return titolo;
		}

		/**
		 * Modifica il titolo del Film
		 * @param titolo nuovo titolo da dare al film
		 */
		public void setTitolo(String titolo) {
			this.titolo = titolo;
		}

		/**
		 * Restituisce la durata del film in minuti
		 * @return durata del film
		 */
		public int getDurata() {
			return durata;
		}

		/**
		 * Modifica la durata del Film
		 * @param durata nuova durata da dare al Film
		 */
		public void setDurata(int durata) {
			this.durata = durata;
		}

		/**
		 * Restituisce l'incasso del film
		 * @return incasso del film
		 */
		public double getIncasso() {
			return incasso;
		}

		/**
		 * Modifica l'incasso del film
		 * @param incasso nuovo incasso da dare al film
		 */
		public void setIncasso(double incasso) {
			this.incasso = incasso;
		}
		
		/**
		 * Restituisce la descrizione del film
		 * @return descrizione del film
		 */
		public String getDesc() {
			return desc;
		}

		/**
		 * Modifica la descrizione del film
		 * @param desc nuova descrizione del film
		 */
		public void setDesc(String desc) {
			this.desc = desc;
		}

		

		public String toString(){
			return getClass().getSimpleName()+" "+"[titolo: "+titolo+
										"durata:"+ durata+
										"incasso: "+incasso+
										"descrizione: "+desc+"]";
		}	
		

		public boolean equals(Object other){
			if (other == null) return false;
			if (getClass()!=other.getClass()) return false;
			Film obj=(Film) other;
			return ((obj.titolo==titolo)&&
					(obj.incasso==incasso)&&
					(obj.desc.equals(desc)));
		}
		
	
		public Film clone (){
			try{
				Film cloned=(Film)super.clone();
				return cloned;
			}
			catch(CloneNotSupportedException e){return null;}
		}
		
}
