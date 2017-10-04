package cinema;

import java.util.Date;

/**
 * 
 * Astrazione di un Cliente che può, acquistare
 * o prenotare un biglietto per uno spettacolo 
 * o disdire una prenotarione
 *
 */
public class Cliente implements Cloneable{
		private String nome;
		private String cognome;
		private String user;
		private String password;
		private int eta;
		private int id;
		private Cinema c;
	
		public Cliente (String n, String co, String u, String pass,int eta,int i){
			nome = n;
			cognome =  co;
			user = u;
			password = pass;
			this.eta=eta;
			id=i;
			c = new Cinema();
		}
		
		/**
		 * restituisce un oggetto di tipo Cinema
		 * @return oggetto di tipo Cinema
		 */
		public Cinema getCinema() {
			return c;
		}

		/**
		 * Restituisce l'età del cliente
		 * @return restiuisce l'età del cliente
		 */
		
		public int getEta() {
			return eta;
		}
		/**
		 * Modifica l'età del Cliente
		 * @param eta modifica l'età del cliente in base al valore dato 
		 */
		public void setEta(int eta) {
			this.eta = eta;
		}

		/**
		 * Restituisce il nome del cliente
		 * @return restiuisce il nome del cliente
		 */
		public String getNome() {
			return nome;
		}
		/**
		 * Modifica il nome del Cliente
		 * @param nome modifica il nome del cliente in base alla stringa data
		 */
		public void setNome(String nome) {
			this.nome = nome;
		}
		
		/**
		 * Restituisce l'id del cliente che identifica chi ha prenotato o comprato
		 * @return  l'id del cliente
		 */
		public int getId() {
			return id;
		}
		/**
		 * Modifica l'id del Cliente
		 * @param id modifica l'id del cliente in base alla Stringa data
		 */
		public void setId(int id) {
			this.id = id;
		}

		/**
		 * Restituisce lo username del cliente
		 * @return restiuisce lo username del cliente
		 */
		public String getUser() {
			return user;
		}
		
		/**
		 * Modifica lo username del Cliente
		 * @param user modifica lo username del cliente in base alla Stringa data
		 */
		public void setUser(String user) {
			this.user = user;
		}

		/**
		 * Restituisce la password del cliente
		 * @return restiuisce la password del cliente
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * Modifica la password del cliente
		 * @param password modifica la password del cliente in base alla stringa data
		 */
		public void setPassword(String password) {
			this.password = password;
		}

		/**
		 * Restituisce il cognome dell'utente
		 * @return  restituisce il cognome dell'utente
		 */
		public String getCognome() {
			return cognome;
		}
		
		/**
		 * Modifica il cognome dell'utente
		 * @param cognome modifica il cognome dell'utente in base alla stringa data
		 */
		public void setCognome(String cognome) {
			this.cognome = cognome;
		}
		
		/**
		 * Permette di prenotare uno spettacolo
		 * 
		 * @param s spettacoolo da prenotare
		 * @param n numero della poltrona 
		 * @param y identifica a quale proiezione si fa riferimento
		 * n,y indentificano il posto nella matrice prenotazioni
		 */
		public void prenota(Spettacolo s, int n, int y){
			
				s.setStato(n,y,2);
				s.setpDisponibili(y,s.getpDisponibili(y)-1);
				s.setId(n,y,id);
			
		}
		
		/**
		 * Permette di disdire una prenotazione
		 * @param s spettacolo da disdire
		 * @param n numero della poltrona 
		 * @param y identifica a quale proiezione si fa riferimento
		 */
		public void disdici(Spettacolo s,int n,int y){
			s.setStato(n,y,1);
			s.setpDisponibili(y,s.getpDisponibili(y)+1);
			s.setId(n,y,-1);
		}
		
		/**
		 * Permette di acquistare uno spettacolo
		 * @param s spettacolo da disdire
		 * @param n numero della poltrona
		 * @param y identifica a quale proiezione si fa riferimento
		 * @param d data viene usata per vedere se il giorno è o no soggetto a sconto
		 * @param flag utilizzato per saperese lo sconto è attivo o meno
		 */
		public void acquista(Spettacolo s,Date d,int n,int y,boolean flag){
				if(s.getStato(n, y)!=2)
					s.setpDisponibili(y,s.getpDisponibili(y)-1);
				s.setId(n,y,id);
				s.setStato(n,y,0);
				double prezzos =s.getFilmAttuale().getPrezzo()-((s.getFilmAttuale().getPrezzo()*c.getMiglioreSconto(d, flag))/100);
				s.getFilmAttuale().setIncasso(s.getFilmAttuale().getIncasso()+prezzos);
		}

		public String toString(){
			return getClass().getName()+" "+"[nome: "+nome+
										"cognome: "+cognome+
										"password: "+password+
										"user: "+user+
										"id: "+id+
										"Cinema:"+c.toString()+"]";
		}	
		
		public boolean equals(Object other){
			if(other == null) return false;
			if(getClass() != other.getClass()) return false;
			Cliente obj = (Cliente) other;
			return ((obj.getNome() == nome) && (obj.getPassword() == password) && (obj.getCognome() == cognome) && (obj.getUser() == user)&&(obj.c.equals(c)));
		}
		
		public Cliente clone (){
			try{
				Cliente cloned=(Cliente)super.clone();
				return cloned;
			}
			catch(CloneNotSupportedException e){return null;}
		}
		
}
