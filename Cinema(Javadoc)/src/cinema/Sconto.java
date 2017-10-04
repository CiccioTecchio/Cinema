package cinema;

import java.io.Serializable;

/**
 * La classe crea l'astrazione di un singolo tipo di sconto
 * 
 *
 */
public class Sconto implements Cloneable,Serializable{

	private String nome;
	private int percentuale;
	private boolean stato;
	private Scontabili criterio;
	
	public Sconto(String nome, int num,Scontabili c,boolean b){
		this.nome=nome;
		this.percentuale=num;
		stato=b;
		criterio=c;
	}
	
	/**
	 * Restituisce il criterio in base a cui scontare
	 * @return criterio di sconto
	 */
	public Scontabili getCriterio() {
		return criterio;
	}
	/**
	 * Modifica il vecchio criterio con un nuovo criterio
	 * @param criterio nuovo criterio
	 */
	public void setCriterio(Scontabili criterio) {
		this.criterio = criterio;
	}

	/**
	 * Restituisce il nome della politica di sconto
	 * @return nome della politica
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Modifica il nome dello sconto
	 * @param nome nuovo nome da dare allo sconto
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Restituisce la percentuale di sconto
	 * @return percentuale di sconto
	 */
	public int getPercentuale() {
		return percentuale;
	}
	
	/**
	 * Modifica la percentuale di sconto
	 * @param p nuova percentuale di sconto
	 */
	public void setPercentuale(int p) {
		this.percentuale = p;
	}
	
	/**
	 * Stato dello sconto
	 * @return true se la poolitica di sconto è attiva false altrimente
	 */
	public boolean isStato() {
		return stato;
	}
	
	/**
	 * Modifica lo stato della politica da attiva a disattiva
	 * @param b true attiva la politica false la disattiva
	 */
	public void setStato(boolean b){
		stato=b;
	}

	public String toString(){
		return getClass().getName()+" "+"[nome: "+nome+
									"percentuale: "+percentuale+
									"stato: "+stato+
									"criterio: "+criterio+"]";
	}	
	
	public boolean equals(Object sc){
		if (sc==null) return false;
		if (getClass()!=sc.getClass()) return false;
		Sconto obj=(Sconto)sc;
		return (nome==obj.nome)&&(percentuale==obj.percentuale)&&(stato==obj.stato)&&(criterio==obj.criterio); 
	}
	
	public Sconto clone (){
		try{
			Sconto cloned=(Sconto)super.clone();
			return cloned;
		}
		catch(CloneNotSupportedException e){return null;}
	}
	

}
