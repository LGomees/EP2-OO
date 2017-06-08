import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JFrame {

	private JFrame mainFrame;
	private JLabel optionsLabel;
	private JLabel headerLabel;
	private JPanel controlPanel;
	
	public Menu() {
		prepareGUI();	
		
	}

	private void prepareGUI() {

		mainFrame = new JFrame("MENU - Space Combat Game");
		mainFrame.setSize(Game.getWidth(), Game.getHeight());
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.setFocusable(true);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);

		headerLabel = new JLabel("", JLabel.CENTER);
		optionsLabel = new JLabel("", JLabel.CENTER);

		optionsLabel.setSize(Game.getWidth(), Game.getHeight());

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(optionsLabel);
		mainFrame.add(controlPanel);		
		mainFrame.setVisible(true);
		

	}

	public void showMenu() {

		headerLabel.setText("PRESS START");

		JButton newGameButton = new JButton("New Game");
		JButton creditsButton = new JButton("Credits");

		newGameButton.setActionCommand("NewGame");
		creditsButton.setActionCommand("Credits");

		newGameButton.addActionListener(new ButtonClickListener());
		creditsButton.addActionListener(new ButtonClickListener());

		controlPanel.add(newGameButton);
		controlPanel.add(creditsButton);		

		setResizable(false);
		setLocationRelativeTo(null);

		mainFrame.setVisible(true);

	}

	public class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals("NewGame")) {
				Application app = new Application();
				app.setVisible(true);
				mainFrame.setVisible(false);
			} else if (command.equals("Credits")) {
				headerLabel.setText("*Jogo desenvolvido por Lucas Gomes*");
			}
		}

	}

}
