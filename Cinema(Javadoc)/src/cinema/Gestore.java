package cinema;


import java.util.ArrayList;

/**
 * Astrazione di un gestore di un Cinema esso ha uno username e una password che di default sono 
 * "admin" "admin"
 * 
 *
 */

public class Gestore{
	
	private String user;
	private String pswd;
	private CinemaGestore c;
	
	public Gestore(String user, String pswd){
		this.user=user;
		this.pswd=pswd;
		c= new CinemaGestore();
	}
	/**
	 * Restituisce CinemaGestrore
	 * @return un oggetto Cinemagestore contente l'array delgli spettacoli e delle politiche di sconto
	 */
	public CinemaGestore getCinemaGestore(){
		return c;
	}
	
	/**
	 * Restituisce lo username del gestore
	 * @return username del gestore
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * Modifica lo username del gestore
	 * @param user nuovo username del gestore
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * Restituisce la password del gestore
	 * @return password del gestore
	 */
	public String getPassword() {
		return pswd;
	}
	
	/**
	 * Modifica la password del gestore
	 * @param pswd nuova password del gestore
	 */
	public void setPassword(String pswd) {
		this.pswd = pswd;
	}
	
	/**
	 * Rende indisponibili posti di uno Spettacolo
	 * @param s spettacolo scelto
	 * @param n poltrona selezionata
	 * @param y identifica a quale proiezione si fa riferimento
	 */
	public void indisponibile(Spettacolo s,int n, int y){
		s.setStato(n,y,3);
		s.setpDisponibili(y,s.getpDisponibili(y)-1);
		s.setId(n,y,-1);
}
	/**
	 * Rende disponibili posti di uno Spettacolo
	 * @param s spettacolo scelto
	 * @param n poltrona selezionata
	 * @param y identifica a quale proiezione si fa riferimento
	 */
	public void disponibile(Spettacolo s,int n, int y){
		s.setStato(n,y,1);
		s.setpDisponibili(y,s.getpDisponibili(y)+1);
		s.setId(n,y,-1);
}
	
	@Override
	public String toString(){
		return getClass().getName()+"[user="+user+"][pswd"+pswd+"][Cinema"+c.toString()+"]";
	}
	@Override
	public boolean equals(Object other){
		if(other == null) return false;
		if(getClass() != other.getClass()) return false;
		Gestore obj = (Gestore) other;
		return (obj.pswd == pswd) && (obj.user == user) && (obj.c.equals(c));
	}
	@Override
	public Gestore clone(){
		try{
			Gestore cloned = (Gestore) super.clone();
			return cloned;
		}catch(CloneNotSupportedException e){return null;}
	}
	

}
