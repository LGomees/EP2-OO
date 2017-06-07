
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Application extends JFrame {

	public Application() {
		
		
		add(new Map());

		setSize(Game.getWidth(), Game.getHeight());

		setTitle("Space Combat Game");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Application app = new Application();
				app.setVisible(true);
			}
		});
	}
}