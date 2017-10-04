package grafica;
	import java.awt.*;

import javax.swing.*;

/**
 * 
 * Frame per la comunicazione di messaggi
 *
 */
	public class FrameError extends JFrame{
		private JLabel label;
		
		public FrameError(String err){
			setSize(500,80);
			setResizable(false);
			setAlwaysOnTop(true);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation((dim.width/2)-(getWidth()/2),(dim.height/2)-(getHeight()));
			setTitle("Errore");
			label = new JLabel(err);
			add(label);
			setVisible(true);
		}
 
	}

