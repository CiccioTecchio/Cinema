package cinema;

import java.io.Serializable;
import java.util.Date;

/**
 * Interfaccia per gestire i vari tipi di sconti
 * 
 *
 */
public interface Scontabili extends Serializable{
	/**
	 * 
	 * @param d data dello spettacolo in cui applicare lo sconto
	 * @param b true se può applicato il prezzo ridotto false altrimenti 
	 * @return true se può essere applicato uno sconto false altrimeti
	 */
	public boolean idoneo(Date d,boolean b);
}
