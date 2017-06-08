
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
				Menu menu = new Menu();
				menu.showMenu();
			}
		});
	}
}
