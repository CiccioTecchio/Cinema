package cinema;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


/**
 * Astrazione di uno spettacolo che appenza istanziato è uno 
 * spettacolo fruibile ed ha tutti i posti liberi
 */
public class Spettacolo implements Cloneable,Serializable{
	
	private boolean fruibili;
	private int nsala;
	private Film filmAttuale;
	private int pDisponibili[];
	final private int PTOTALI=80;
	private int giorni;
	private Date ora;
	private Date dataInizio;
	private Date dataFine;
	private Prenotazioni[][] prenotazioni;
	
	public Spettacolo(int n,Film f,Date di,Date df,int hi,int mi){
		fruibili=true;
		nsala=n;
		filmAttuale=f;
		ora = new Date(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hi,mi);
		dataInizio = di;
		dataFine = df;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(dataInizio);
		c2.setTime(dataFine);
		long differenza = (c2.getTimeInMillis() - c1.getTimeInMillis())/ (24*3600*1000);
		giorni = ((int) differenza)+1;
		
		prenotazioni = new Prenotazioni[PTOTALI][giorni];
		pDisponibili=new int[giorni];
		int i;
		int j;
		for(i=0;i<giorni;i++){
			pDisponibili[i]=PTOTALI;}
		
		for(i=0;i<PTOTALI;i++){
			for(j=0;j<giorni;j++){
				prenotazioni[i][j]=new Prenotazioni(-1,1);
			}
		}

	}
	
	/**
	 * Restituisce per quanti giorni è in proiezione lo spettacolo
	 * @return giorni di proiezione
	 */
	public int getGiorni() {
		return giorni;
	}


	/**
	 * Modifica il numero di giorni di proiezione dello spettacolo
	 * @param giorni nuovo numero di giorni di proiezione del film
	 */
	public void setGiorni(int giorni) {
		this.giorni = giorni;
	}


	/**
	 * Restituisce se lo spettacolo è fruibile o meno per fruibile si intende
	 * che lo spettacolo è ancora in corso  eche ci siano posti disponibili 
	 * @return true se lo spettacolo è fruibili false altrimenti
	 */
	public boolean isFruibili() {
		return fruibili;
	}

	/**
	 * Modifica la fruibilità dello speettacolo
	 * @param fruibili se è true lo spettacolo diventa fruibili se è false lo spettacolo non è più fruibile
	 */
	public void setFruibili(boolean fruibili) {
		this.fruibili = fruibili;
	}
	
	/**
	 * Restituisce data di inizio dello Spettacolo
	 * @return data inizio Spettacolo
	 */
	public Date getDataInizio() {
		return dataInizio;
	}

	/**
	 * Cambia la data d'inizio del Film con una nuova
	 * @param dataInizio nuova data d'inizo
	 */
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	
	/**
	 * Restituisce data di fine dello Spettacolo
	 * @return data fine Spettacolo
	 */
	public Date getDataFine() {
		return dataFine;
	}

	/**
	 * Cambia la data di fine del Film con una nuova
	 * @param dataFine nuova data di fine
	 */
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	/**
	 * Restituisce il numero della sala in cui viene  proiettato lo spettacolo
	 * @return numero dalla sala in cui viene proiettato lo spettacolo
	 */
	public int getNsala() {
		return nsala;
	}
	/**
	 * Modifica la sala di proiezione dello spettacolo
	 * @param nsala nuova sala in cui verrà proiettato lo spettacolo
	 */
	public void setNsala(int nsala) {
		this.nsala = nsala;
	}
	/**
	 * Restituisce un Film preso in esame
	 * @return oggetto Film
	 */
	public Film getFilmAttuale() {
		return filmAttuale;
	}

	/**
	 * Modifica il Film preso in esame
	 * @param filmAttuale nuovo film da sostituire al vecchio
	 */
	public void setFilmAttuale(Film filmAttuale) {
		this.filmAttuale = filmAttuale;
	}
	
	
	/**
	 *Restituisce il vettore dei posti disponibili 
	 * @return posti disponibili
	 */
	public int[] getpDisponibili() {
		return pDisponibili;
	}
	/**
	 * Modifica il vettore dei posti disponibili con un nuovo vettore
	 * @param pDisponibili nuovo vettore
	 */
	public void setpDisponibili(int[] pDisponibili) {
		this.pDisponibili = pDisponibili;
	}

	/**
	 * Restituisce la matrice delle prenotazioni
	 * @return matrice delle prenotazioni
	 */
	public Prenotazioni[][] getPrenotazioni() {
		return prenotazioni;
	}
	
	/**
	 * Modifica la matrice prenotazioni con una nuova matrice
	 * @param prenotazioni nuova matrice
	 */
	public void setPrenotazioni(Prenotazioni[][] prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	/**
	 * Modifica l' id del posto(-1 di default)
	 * @param np poltrona selezionata
	 * @param y proiezione selezionata
	 * @param id stato del posto
	 */
	public void setId(int np,int y,int id){
		prenotazioni[np][y].setId(id);
	}
	
	
	/**
	 * Restituisce l'id del posto (-1 di default)
	 * @param np poltrona selezionata
	 * @param y proiezione selezionata
	 * @return id del posto -1 di default se il posto viene prenotato da qualcuno prende l'id del cliente
	 */
	public int getId(int np,int y){
		return prenotazioni[np][y].getId();
	}
	
	/**
	 * Restituisce i posti disponibili di uno spettacolo
	 * @param i indice di una determinata proiezione dello spettacolo
	 * @return posti disponibili della proiezione scelta
	 */
	public int getpDisponibili(int i) {
		return pDisponibili[i];
	}

	/**
	 * Modifica i posti disponibili di uno spettacolo
	 * @param i indice di una determinata proiezione dello spettacolo
	 * @param pDisponibili numero di posti disponibili da inserire
	 */
	public void setpDisponibili(int i,int pDisponibili) {
		this.pDisponibili[i] = pDisponibili;
	}

	/**
	 * restituisce l'ora d'inzio dello spettacolo
	 * @return ora di inizio settacolo
	 */
	public int getOraInizio() {
		return ora.getHours();
	}
	
	/**
	 * modifica l'ora d'inizio dello spettacolo
	 *  @param oraInizio nuova ora d'inizio dello spettacolo
	 */
	public void setOraInizio(int oraInizio) {
		ora.setHours(oraInizio);
	}
	
	/**
	 * restituisce il minuto d'inzio dello spettacolo
	 * @return minuto di inizio settacolo
	 */
	public int getMinutoInizio() {
		return ora.getMinutes();
	}
	
	/**
	 * modifica il minuto d'inizio dello spettacolo
	 *  @param minutoInizio nuovo minuto d'inizio dello spettacolo
	 */
	public void setMinutoInizio(int minutoInizio) {
		ora.setMinutes(minutoInizio);
	}

	/**
	 * Stato di una poltrona di default è libera
	 * @param np poltrona selezionata
	 * @param y identifica a quale proiezione si fa riferimento
	 * @return stato della poltrona (0 acquistato,1 libero ,2 prenotato,3 indisponibile)
	 */
	
	public int getStato(int np,int y){
		return prenotazioni[np][y].getStato();
	}
	
	/**
	 * Modifica stato della poltrona
	 * @param np poltrona selezionata
	 * @param y identifica a quale proiezione si fa riferimento
	 * @param stato stato da assegnare alla poltrona 0 acquistato,1 libero ,2 prenotato,3 indisponibile
	 */
	public void setStato(int np,int y,int stato){
		prenotazioni[np][y].setStato(stato);
	}

	/**
	 * Restituisce i posti totali all'interno della sala
	 * @return posti totali all'interno della sala
	 */
	public int getpTotali() {
		return PTOTALI;
	}
	
	public String toString(){
		return getClass().getName()+" "+"[nsala: "+nsala+
									"filmAttuale: "+filmAttuale.toString()+
									"fruibile: "+fruibili+
									"pDisponibili: "+pDisponibili+
									"oraInizio: "+ora.getHours()+
									"minutoInizio: "+ora.getMinutes()+
									"prenotazioni: "+prenotazioni.toString()+"]";
	}	
	
	public boolean equals(Object other){
		if (other == null) return false;
		if (getClass()!=other.getClass()) return false;
		Spettacolo obj=(Spettacolo) other;
		return ((obj.nsala==nsala)&&
				(obj.fruibili==fruibili)&&
				(obj.filmAttuale.equals(filmAttuale))&&
				(obj.pDisponibili.equals(pDisponibili))&&
				(obj.giorni==giorni)&&
				(obj.ora.getHours()==ora.getHours())&&
				(obj.ora.getMinutes()==ora.getMinutes())&&
				(obj.prenotazioni.equals(prenotazioni)));
	}
	
	
	public Spettacolo clone (){
		try{
			Spettacolo cloned=(Spettacolo)super.clone();
			return cloned;
		}
		catch(CloneNotSupportedException e){return null;}
	}
	

	
		
	
	
}
