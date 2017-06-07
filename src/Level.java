import java.util.Optional;

public enum Level {

	EASY(6000, 10, 5000, 1, "images/alien_EASY.png"), 
	MEDIUM(6000, 25, 5000, 2, "images/alien_MEDIUM.png"), 
	HARD(6000, 50, 5000, 3, "images/alien_HARD.png");

	private final int alienTime;
	private final int alienCount;
	private final int bonusTime;
	private final int bonusCount;
	private final String alienImage;

	Level(int alienTime, int alienCount, int bonusTime, int bonusCount, String alienImage) {

		this.alienTime = alienTime;

		this.alienCount = alienCount;

		this.bonusTime = bonusTime;

		this.bonusCount = bonusCount;

		this.alienImage = alienImage;

	}

	public int getAlienCount() {
		
		return alienCount;

	}

	public int getAlienTime() {

		return alienTime;

	}

	public String getAlienImage() {

		return alienImage;

	}

	public int getBonusCount() {

		return bonusCount;

	}

	public int getBonusTime() {

		return bonusTime;

	}

	public Optional<Level> next() {

		switch (this) {

		case EASY:

			return Optional.of(MEDIUM);

		case MEDIUM:

			return Optional.of(HARD);

		default:

			return Optional.empty();

		}

	}
}
