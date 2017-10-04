package cinema;

/**
 * 
 * Eccezione da lanciare nel caso in cui non ci siano più posti disponibili in sala
 *
 */

public class PostoIndisponibileException extends RuntimeException{

	/**
	 * Lancia un messagio di errore
	 */
		public PostoIndisponibileException(){
			super();		
	}
		/**
		 * Lancia un messaggio di errore scelto dall'utente
		 * @param s messaggio scelto dall utente
		 */
		public PostoIndisponibileException(String s){
			super(s);		
	}
	
}