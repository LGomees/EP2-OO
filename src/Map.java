
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Map extends JPanel implements ActionListener {

	private final int SPACESHIP_X = 220;
	private final int SPACESHIP_Y = 430;
	private final Timer timer_map;

	private final Image background;
	private final Spaceship spaceship;

	private boolean isAlive;

	private List<Alien> aliens;

	public Map() {

		addKeyListener(new KeyListerner());

		setFocusable(true);
		setDoubleBuffered(true);
		ImageIcon image = new ImageIcon("images/space.jpg");

		this.background = image.getImage();

		spaceship = new Spaceship(SPACESHIP_X, SPACESHIP_Y);
		
		startAliens();

		timer_map = new Timer(Game.getDelay(), this);
		timer_map.start();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(this.background, 0, 0, null);

		draw(g);

		Toolkit.getDefaultToolkit().sync();
	}

	private void draw(Graphics g) {

		// Draw spaceship
		g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);
		
		// Draw Bullet
		List<Bullet> firedBullet = spaceship.getFiredBullet();

		for (int i = 0; i < firedBullet.size(); i++) {

			Bullet b = (Bullet) firedBullet.get(i);
			g.drawImage(b.getImage(), b.getX(), b.getY(), this);
		}
		
		// Draw Alien
		for (int i = 0; i < aliens.size(); i++) {

			Alien a = aliens.get(i);
			g.drawImage(a.getImage(), a.getX(), a.getY(), this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		updateSpaceship();
		updateBullet();
		updateAlien();
		repaint();
	}

	private void drawMissionAccomplished(Graphics g) {

		String message = "MISSION ACCOMPLISHED";
		Font font = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metric = getFontMetrics(font);

		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
	}

	private void drawGameOver(Graphics g) {

		String message = "Game Over";
		Font font = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metric = getFontMetrics(font);

		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
	}

	public void startAliens() {

		aliens = new ArrayList<Alien>();


		for (int i = 0; i < aliens.size(); i++) {
			
			int n = (int)(Math.random());
			aliens.add(new Alien(n,0));
		}
	}

	private void updateSpaceship() {
		spaceship.move();
	}

	private void updateBullet() {

		List<Bullet> firedBullet = spaceship.getFiredBullet();

		for (int i = 0; i < firedBullet.size(); i++) {

			Bullet b = (Bullet) firedBullet.get(i);
			if (b.isVisible()) {
				b.movementBullet();
			} else {
				firedBullet.remove(i);
			}
		}
	}

	private void updateAlien() {


		for (int i = 0; i < aliens.size(); i++) {

			Alien a = (Alien) aliens.get(i);

			if (a.isVisible()) {
				a.movementAlien();
			} else {
				aliens.remove(i);
			}
		}

	}

	private class KeyListerner extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			spaceship.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			spaceship.keyReleased(e);
		}

	}
}