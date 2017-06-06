import java.util.Optional;

public enum Level {
	EASY(6000, 10, "images/alien_EASY.png"), 
	MEDIUM(6000, 25, "images/alien_MEDIUM.png"), 
	HARD(6000, 50, "images/alien_HARD.png");
	
	private int time;
	private int qtt;
	private String image;
	
	Level(int time, int qtt, String img) {
		this.time = time;
		this.qtt =qtt;
		this.image = img;
	}
	
	public String getImage() {
		return image;
	}
	
	public int getQtt() {
		return qtt;
	}
	
	public int getTime() {
		return time;
	}
	
	public Optional<Level> next() {
		switch(this){
		case EASY:
			return Optional.of(MEDIUM);
		case MEDIUM:
			return Optional.of(HARD);
			default:
				return Optional.empty();
		}
	}
	
}
