package grafica;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;

/**
 * 
 * Frame per la registrazione del Cliente
 *
 */
public class MyRegistrazione extends JFrame{
	private JLabel lblnome;
	private JLabel lblcognome;
	private JLabel lbleta;
	private JLabel lbluser;
	private JLabel lblpassword;
	private JTextField nome;
	private JTextField cognome;
	private JTextField eta;
	private JTextField user;
	private JTextField password;
	private JButton iscriviti;
	private JPanel panelhome;

	public MyRegistrazione()  {
	
		setSize(400, 200);
		setTitle("Registrazione");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width/2)-(getWidth()/2),(dim.height/2)-(getHeight()));
		setResizable(false);
		setAlwaysOnTop(true);
		panelhome = new JPanel();
		panelhome.setLayout(new GridLayout(6,2));

		lblnome= new JLabel("Nome");
		lblcognome = new JLabel("Cognome");
		lbleta = new JLabel("Eta");
		lbluser = new JLabel("User");
		lblpassword = new JLabel("Password");
		nome = new JTextField(10);
		cognome= new JTextField(10);
		eta= new JTextField(10);
		user= new JTextField(10);
		password= new JPasswordField(10);
		iscriviti= new JButton("Registrati!");


		panelhome.add(lblnome);
		panelhome.add(nome);
		panelhome.add(lblcognome);
		panelhome.add(cognome);
		panelhome.add(lbleta);
		panelhome.add(eta);
		panelhome.add(lbluser);
		panelhome.add(user);
		panelhome.add(lblpassword);
		panelhome.add(password);
		panelhome.add(iscriviti);
		add(panelhome);
		setVisible(true);

	

		/**
		 * 
		 * Listener per la registrazione di un cliente
		 *
		 */
	class RegistratiListener implements ActionListener {
		/**
		 * Una volta riempiti tutti i campi correttamente l'accunt viene registrato su file
		 */
		public void actionPerformed(ActionEvent arg0) {

			File f1;
			try{
				int i = Integer.parseInt(eta.getText());

				try{
					f1 = new File("log.wa");
					if (!f1.exists()){
						PrintWriter f= new PrintWriter("log.wa");
						f.close();
					}
					PrintWriter fw = new PrintWriter(new FileOutputStream(f1, true));
					if(!(nome.getText().isEmpty())&&!(cognome.getText().isEmpty())&&!(user.getText().isEmpty())&&!(eta.getText().isEmpty())&&!(password.getText().isEmpty())){

						int x=contaRighe(f1);
						fw.println(false);
						fw.println(((int)x/7)+1);
						fw.println(user.getText());
						fw.println(password.getText());
						fw.println(nome.getText());
						fw.println(cognome.getText());
						fw.println(eta.getText());
					}
					else{
						FrameError fe = new FrameError("Riempire tutti i campi");
					}
					fw.close();


				}
				catch(IOException e){}
				if(!(nome.getText().isEmpty())&&!(cognome.getText().isEmpty())&&!(user.getText().isEmpty())&&!(eta.getText().isEmpty())&&!(password.getText().isEmpty()))
					dispose();
			}
			catch(NumberFormatException e){FrameError err = new FrameError("L'Eta deve essere indicata solo da numeri");}
		}
	}


	RegistratiListener rl = new RegistratiListener();
	iscriviti.addActionListener(rl);
}
	/**
	 * Restituisce il numero di righe all'interno del file
	 * grazie al quale si può  risalire al numero di utenti registrati
	 */
	private int contaRighe(File f1){
		int i=0;
		String s;
		try{
			FileReader lettore=new FileReader(f1);
			Scanner sc = new Scanner(lettore);
			while(sc.hasNext()){s=sc.nextLine();
								i++; }
			sc.close();
			lettore.close();
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
		return i;
	}

	
}







