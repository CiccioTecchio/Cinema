package grafica;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import cinema.Cliente;
import cinema.Gestore;

/**
 * 
 * Main del progetto qui vengono creati i file degli utenti
 *
 */

public class Main {

	public static void main(String[] args) {
		MyLog log = new MyLog();
		
		File f1;
		try{
			f1 = new File("log.wa");
			if (!f1.exists()){
				PrintWriter f= new PrintWriter("log.wa");
				f.close();
				PrintWriter fw = new PrintWriter(new FileOutputStream(f1, true));
				fw.println(true);
				fw.println("admin");
				fw.println("admin");

				fw.close();
			}

		}
		catch(IOException e){}
		
		/**
		 * 
		 * Listener per a registrazione
		 *
		 */
		class RegisterListener implements ActionListener{
			/**
			 * Quando viene premuto il bottone registrati si apre il frame MyRegistrazione
			 */
			public void actionPerformed(ActionEvent arg0) {
				MyRegistrazione registra = new MyRegistrazione();		
			}
		}
		
		/**
		 * 
		 * Listener per il LogIn
		 *
		 */
		class LogInListener implements ActionListener{
			/**
			 * Se non esiste alcun file lo crea inserendo per prima cosa admin admin,in seguito verranno inseriti
			 * gli utenti che si registrano,se i campi sono pieni e il contenuto dei campi corrisponde al contenuto
			 * del file allora accede o all HomeAdmin o all'Home altrimenti lancia un FrameError con scritto User o
			 * Password errati
			 * 
			 */
			public void actionPerformed(ActionEvent arg0) {
				File f1;
				Gestore g=null;
				Cliente c=null;
				String nome=null;
				String cognome=null;
				int id=0;
				int eta=0;
				String user=null;
				String pass=null;
				boolean bool;
				try{
					f1 = new File("log.wa");
					if(f1.exists()){
						FileReader lettore=new FileReader(f1);
						Scanner sc = new Scanner(lettore);

						while(sc.hasNext()){
							bool=Boolean.parseBoolean(sc.nextLine());

							if(!bool){
								id=Integer.parseInt(sc.nextLine());
								user=sc.nextLine();
								pass=sc.nextLine();
								nome=sc.nextLine();
								cognome=sc.nextLine();
								eta=Integer.parseInt(sc.nextLine());
								c= new Cliente(nome,cognome,user,pass,eta,id);
							}
							else{
								user=sc.nextLine();
								pass=sc.nextLine();
								g= new Gestore(user,pass);
							}

							if (!bool){
								if ((c.getPassword().equals(log.getPassword().getText()))&&(c.getUser().equals(log.getUser().getText()))){
									break;
								}
								else{
									if ((g.getPassword().equals(log.getPassword().getText()))&&(g.getUser().equals(log.getUser().getText()))){
										break;
									}
								}
							}

						}
						
						
						if(((g!=null)&&(g.getPassword().equals(log.getPassword().getText()))&&(g.getUser().equals(log.getUser().getText())))
								||
								((c!=null)&&(c.getPassword().equals(log.getPassword().getText()))&&(c.getUser().equals(log.getUser().getText())))	){
							if(g.getPassword().equals(log.getPassword().getText())){
								
								HomeAdmin h = new HomeAdmin(g);
								log.dispose();
							}
							else{
								
								Home h = new Home(c);
								log.dispose();
							}
						}	

						else{FrameError fe = new FrameError("User o Pass errati");}
					}
					else{FrameError fe = new FrameError("Nessuna Registrazione");}
				}
				catch(IOException e){}
			}
		}
		LogInListener lil = new LogInListener();
		log.getAccedi().addActionListener(lil);
		RegisterListener rl = new RegisterListener();
		log.getRegistra().addActionListener(rl);
	}

}
