import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Bullet extends Sprite {
    
	public static final int SPEED_BULLET = 3;
	    

	public Bullet(int x, int y) {
		super(x,y);
		
		loadImage("images/bullet.png");
		visible = true;	
	}
	
	public void movementBullet(){
		
		this.y -= SPEED_BULLET;
    	if((y + width >= Game.getHeight())){
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
