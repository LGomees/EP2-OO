
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Map extends JPanel implements ActionListener {

	private final int SPACESHIP_X = 220;
	private final int SPACESHIP_Y = 430;
	private final Timer timer_map;

	private final Image background;
	private Spaceship spaceship;

	private boolean isAlive;
	private boolean isWinner;

	private List<Alien> aliens;
	private Timer timer_aliens;
	private List<Bonus> bonus;
	private Timer timer_bonus;

	private Level level = Level.EASY;

	public Map() {

		addKeyListener(new KeyListerner());

		setFocusable(true);
		setDoubleBuffered(true);
		ImageIcon image = new ImageIcon("images/space.jpg");

		this.background = image.getImage();

		spaceship = new Spaceship(SPACESHIP_X, SPACESHIP_Y);

		isAlive = true;
		isWinner = false;

		startAliens();
		startBonus();

		timer_map = new Timer(Game.getDelay(), this);
		timer_map.start();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(this.background, 0, 0, null);

		if (isWinner) {
			drawMissionAccomplished(g);
		} else if (isAlive) {
			draw(g);
		} else {
			drawGameOver(g);
		}

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

		// Draw Bonus
		for (int i = 0; i < bonus.size(); i++) {
			Bonus b = bonus.get(i);
			g.drawImage(b.getImage(), b.getX(), b.getY(), this);
		}

		g.setColor(Color.WHITE);
		g.drawString("LIFE: " + spaceship.getLife(), 5, 15);
		g.drawString("SCORE: " + spaceship.getScore(), 5, 490);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		updateSpaceship();
		updateBullet();
		updateAlien();
		updateBonus();
		checkCollision();
		Levelfinished();

		repaint();
	}

	private void drawMissionAccomplished(Graphics g) {

		String message = "MISSION ACCOMPLISHED";
		String message2 = "YOU DID IT! ";
		String message3 = "Press ENTER to play again";
		Font font = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metric = getFontMetrics(font);
		
		updateAlien();
		updateBullet();
		updateBonus();

		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(message, ((Game.getWidth() - metric.stringWidth(message))) / 2, Game.getHeight() / 2);
		g.drawString(message2, ((Game.getWidth() - metric.stringWidth(message2)) / 2), (Game.getHeight() / 2) + 40);
		g.drawString(message3, ((Game.getWidth() - metric.stringWidth(message3)) /2), Game.getHeight() - 60); 
	}

	private void drawGameOver(Graphics g) {

		String message = "Game Over";
		String message2 = "SCORE: ";
		String message3 = "Press ENTER to try again";
		Font font = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metric = getFontMetrics(font);
		
		updateAlien();
		updateBullet();
		updateBonus();

		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
		g.drawString(message2 + spaceship.getScore(), (((Game.getWidth() - metric.stringWidth(message2)) / 2) - 20), (Game.getHeight() / 2) + 40);
		g.drawString(message3, ((Game.getWidth() - metric.stringWidth(message3)) /2), Game.getHeight() - 60);
	}

	public void newSpaceship() {
		this.spaceship = new Spaceship(SPACESHIP_X, SPACESHIP_Y);
	}

	private void startBonus() {
		bonus = new ArrayList<>();
		timer_bonus = new Timer(level.getBonusTime(), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateBonus();
			}
		});
		timer_bonus.start();
	}

	private void generateBonus() {
		Random rnd = new Random();
		for (int i = 0; i < level.getBonusCount(); i++) {
			int nx = Math.abs(rnd.nextInt(Game.getWidth()));
			int ny = -500 + Math.abs(rnd.nextInt(Game.getHeight()));
			bonus.add(new Bonus(nx, ny, "images/bonus.png"));
		}
	}

	public void restartScore() {
		spaceship.setScore(0);
		level = Level.EASY;
	}

	public void restartLife() {
		spaceship.setLife(3);
	}

	public void startAliens() {
		aliens = new ArrayList<Alien>();
		timer_aliens = new Timer(level.getAlienTime(), new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				generateAliens();
			}
		});
		timer_aliens.start();
	}

	public void Levelfinished() {
		if (level == Level.EASY && spaceship.getScore() >= 5000 && spaceship.getScore() < 15000) {
			nextLevel();
		}
		if (level == Level.MEDIUM && spaceship.getScore() >= 15000 && spaceship.getScore() < 40000) {
			nextLevel();
		}
		if (level == Level.HARD && spaceship.getScore() >= 40000) {
			this.isWinner = true;
		}
	}

	public void nextLevel() {
		Optional<Level> next = level.next();
		level = next.get();
		timer_aliens.setDelay(level.getAlienTime());
		timer_bonus.setDelay(level.getBonusTime());
	}

	public void checkCollision() {

		Rectangle recSpaceship = spaceship.getBounds();
		Rectangle recBullet;
		Rectangle recAlien;
		Rectangle recBonus;

		for (int i = 0; i < aliens.size(); i++) {
			Alien tempAlien = aliens.get(i);
			recAlien = tempAlien.getBounds();

			if (recSpaceship.intersects(recAlien)) {

				if (spaceship.getLife() == 1) {
					isAlive = false;
				} else {
					spaceship.setLife(spaceship.getLife() - 1);
					tempAlien.setVisible(false);
				}
			}
		}

		List<Bullet> firedBullet = spaceship.getFiredBullet();

		for (int i = 0; i < firedBullet.size(); i++) {

			Bullet tempBullet = firedBullet.get(i);
			recBullet = tempBullet.getBounds();

			for (int j = 0; j < aliens.size(); j++) {

				Alien tempAlien = aliens.get(j);
				recAlien = tempAlien.getBounds();

				if (recBullet.intersects(recAlien)) {

					tempAlien.explosion();
					spaceship.setScore(spaceship.getScore() + 100);
					tempAlien.setVisible(false);
					tempBullet.setVisible(false);

				}
			}
		}

		for (int i = 0; i < firedBullet.size(); i++) {

			Bullet tempBullet = firedBullet.get(i);
			recBullet = tempBullet.getBounds();

			for (int j = 0; j < bonus.size(); j++) {

				Bonus tempBonus = bonus.get(j);
				recBonus = tempBonus.getBounds();

				if (recBullet.intersects(recBonus)) {

					tempBonus.explosion();
					// tempBonus.setVisible(false);
					tempBullet.setVisible(false);

				}
			}
		}

		for (int i = 0; i < bonus.size(); i++) {
			Bonus tempBonus = bonus.get(i);
			recBonus = tempBonus.getBounds();

			if (recSpaceship.intersects(recBonus)) {

				spaceship.setScore(spaceship.getScore() + 2000);
				tempBonus.setVisible(false);
			}
		}
	}

	public void generateAliens() {
		Random rnd = new Random();

		for (int i = 0; i < level.getAlienCount(); i++) {

			int nx = Math.abs(rnd.nextInt(Game.getWidth()));

			int ny = -500 + Math.abs(rnd.nextInt(Game.getHeight()));
			aliens.add(new Alien(nx, ny, level.getAlienImage()));
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
			if (a.isExplosion) {
				a.setVisible(false);
			}
		}

	}

	private void updateBonus() {
		for (int i = 0; i < bonus.size(); i++) {

			Bonus a = bonus.get(i);

			if (a.isVisible()) {
				a.movementBonus();
			} else {
				bonus.remove(i);
			}
			if (a.isExplosion) {
				a.setVisible(false);
			}
		}
	}

	private class KeyListerner extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			if ((!isAlive && e.getKeyCode() == KeyEvent.VK_ENTER) || (isWinner && e.getKeyCode() == KeyEvent.VK_ENTER)) {
				isAlive = true;
				isWinner = false;
				newSpaceship();
				startAliens();
				startBonus();
				restartScore();
				restartLife();

			}
			
			if (isAlive)
				spaceship.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (isAlive)
				spaceship.keyReleased(e);
		}

	}
}