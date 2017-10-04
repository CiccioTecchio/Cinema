package cinema;

import java.util.ArrayList;
import java.util.Comparator;
/**
 * La classe ereidita i metodi di Cinema aggiungendone altri che servono solo al gestore,
 * 
 */
public class CinemaGestore extends Cinema{

	
	public CinemaGestore(){
		super();
	}
	/**
	 * aggiunge uno spettacolo nell'Arraylist
	 * @param s ,spettacolo da aggiungere
	 */
	public void addSpettacolo(Spettacolo s){
		super.getProgrammazione().add(s);
	}
	
	/**Rimuove uno spettacolo in base alla posizione che esso occupa nell'ArrayList
	 * @param n ,posizione nell array dello spettacolo da rimuovere
	 */
	public void removeSpettacolo(int n){
		super.getProgrammazione().remove(n);
	
	}
	
	/**
	 * Modifica uno spettacolo
	 * @param i indice dello spettacolo da modificare
	 * @param n spettacolo contenente le informazioni che vogliamo nello spettacolo da modificare
	 */
	public void replaceSpettacolo(int i,Spettacolo n){
			Prenotazioni[][] prenotazione=super.getProgrammazione().get(i).getPrenotazioni();
			int giorni=super.getProgrammazione().get(i).getGiorni();
			int pDisponibili[]=super.getProgrammazione().get(i).getpDisponibili();
			boolean fruibili=super.getProgrammazione().get(i).isFruibili();
			super.getProgrammazione().remove(i);
			super.getProgrammazione().add(i, n);
			super.getProgrammazione().get(i).setPrenotazioni(prenotazione);
			super.getProgrammazione().get(i).setGiorni(giorni);
			super.getProgrammazione().get(i).setpDisponibili(pDisponibili);
			super.getProgrammazione().get(i).setFruibili(fruibili);
	}
	
	/**
	 * L'incasso totale del cinema
	 * @return incasso del cinema durante la settimana
	 */
	public double getIncassoTotale(){
		double app=0;
		int i;
		for (i=0;i<super.getProgrammazione().size(); i++){
			app+=getIncassoFilm(i);
		}
		return app;
	}
	
	/**
	 * Restituisce l'incasso di un singolo film
	 * @param i indice dello spettacolo contenente il film del quale voglimo conoscere l'incasso
	 * @return  l'incasso del film
	 */
	public double getIncassoFilm(int i){
		return (super.getProgrammazione().get(i).getFilmAttuale().getIncasso());

	}
	
	/**
	 * Aggiunge una nuova politica di Sconto
	 * @param sco nuovo sconto
	 */
	public void addPolitica(Sconto sco){
		int i=0;
		while(sco.getPercentuale()<super.getPolitiche().get(i).getPercentuale())
			i++;
		super.getPolitiche().add(i,sco);
	}
	
	/**
	 * Rimuove una politica di sconto
	 * @param sco sconto da rimuovere
	 */
	public void removePolitica(Sconto sco){
		int i=0;
		while((i<super.getPolitiche().size())&&(!super.getPolitiche().get(i).equals(sco)))
			i++;
		if(i<super.getPolitiche().size())
			super.getPolitiche().remove(i);
	}
	
	/**
	 * Attiva o disattiva una politica
	 * @param i indice della politica da disattivare o attivare
	 * @param b stato della politica true attiva false disattiva
	 */
	public void politicheSiNo(int i, boolean b){
		super.getPolitiche().get(i).setStato(b);
	}
	
	public CinemaGestore clone(){
		CinemaGestore cloned=(CinemaGestore)super.clone();
		return cloned;
	}
	
	
}

