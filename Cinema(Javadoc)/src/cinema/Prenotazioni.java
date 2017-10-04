package cinema;

import java.io.Serializable;

/**
 * Gestisce la prenotazione per un singolo posto
 */
public class Prenotazioni implements Serializable{
	private int id;
	private int stato;
	
	public Prenotazioni(int id, int stato){
		this.id=id;
		this.stato=stato;
	}
	/**
	 * Restituisce l'id del cliente che ha prenotato il posto -1 altrimenti
	 * @return 'id del cliente che ha prenotato il posto -1 altrimenti
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sostituisce l'id del posto con l'id passato
	 * @param id id da assegnare al posto
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Restituisce lo stato del posto
	 * @return lo stato del posto 0 acquistato,1 libero ,2 prenotato,3 indisponibile
	 */
	public int getStato() {
		return stato;
	}
	/**
	 * Modifica lo stato del posto
	 * @param stato cambia lo stato del posto 0 acquistato,1 libero ,2 prenotato,3 indisponibile
	 */
	public void setStato(int stato) {
		this.stato = stato;
	}
	
	public String toString(){
		return getClass().getName()+" "+"[id: "+id+
									"stato: "+stato+"]";
	}	
	
	public boolean equals(Object sc){
		if (sc==null) return false;
		if (getClass()!=sc.getClass()) return false;
		Prenotazioni obj=(Prenotazioni)sc;
		return (stato==obj.stato)&&(id==obj.id); 
	}
	
	public Prenotazioni clone (){
		try{
			Prenotazioni cloned=(Prenotazioni)super.clone();
			return cloned;
		}
		catch(CloneNotSupportedException e){return null;}
	}
	
	
}
