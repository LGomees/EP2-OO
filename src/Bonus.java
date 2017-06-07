public class Bonus extends Sprite {

    public static final int SPEED_BONUS = 1;

    public boolean isExplosion;

    public Bonus(int x, int y, String image) {
        super(x, y);
        loadImage(image);
        visible = true;
    }

    public synchronized void explosion() {
        loadImage("images/explosion.png");
        isExplosion = true;
    }

    public void movementBonus() {
        this.y += SPEED_BONUS;
        if ((y + width >= Game.getHeight())) {
            visible = false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

