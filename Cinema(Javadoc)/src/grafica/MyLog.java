package grafica;
import java.awt.*;

import javax.swing.*;

/**
 * 
 * Interfaccia grafica per il log in del gestore o dell utente
 * Utilizzata anche per la registrazione di un Cliente
 *
 */
public class MyLog extends JFrame{

	private JButton accedi;
	private JButton registra;
	private JTextField user;
	private JPasswordField password;
	private JLabel lbluser;
	private JLabel lblpwd;
	private JPanel panelhome;
	
	public MyLog(){
		setSize(600, 80);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Log in");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width/2)-(getWidth()/2),(dim.height/2)-(getHeight()));
		setResizable(false);
		panelhome = new JPanel();
		panelhome.setLayout(new BorderLayout());
		panelhome.add(creaLog(),BorderLayout.CENTER);
		add(panelhome);
		setVisible(true);
	}
	/**
	 * Costruisce l'interfaccia che permette il log in
	 * @return pannello contenente l'interfaccia del log in
	 */
	private JPanel creaLog(){
		JPanel panel = new JPanel();
		lbluser = new JLabel("User");
		user = new JTextField(10);
		lblpwd = new JLabel("Password");
		password = new JPasswordField(10);
		accedi= new JButton("Accedi");
		registra= new JButton ("Registrati");
		panel.add(lbluser);
		panel.add(user);
		panel.add(lblpwd);
		panel.add(password);
		panel.add(accedi);
		panel.add(registra);
		return panel;
	}

	/**
	 * Restituisce il bottone accedi
	 * @return bottone accedi
	 */
	public JButton getAccedi() {
		return accedi;
	}
	/**
	 * Restituisce il bottone registra
	 * @return bottone registrati
	 */
	public JButton getRegistra() {
		return registra;
	}
	/**
	 * Restituisce la textfield dove viene scritto lo username
	 * @return bottone accedi
	 */
	public JTextField getUser() {
		return user;
	}

	/**
	 * Restituisce la jpasswordfield dove viene scritto la password
	 * @return jpasswordfield in cui si scrive la password
	 */
	public JPasswordField getPassword() {
		return password;
	}


}
