import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Alien extends Sprite {
    
	public static final int SPEED_ALIEN = 1;
	
	 	  
	public Alien(int x, int y) {
		super(x,y);
		
		loadImage("images/alien_EASY.png");
		visible = true;	
	
	}
	
	public void movementAlien(){
		
		if(this.y < 0){
			this.y = Game.getHeight();
		}
		else {
			this.y += SPEED_ALIEN;
		}
		
    	if((y + width >= Game.getHeight())){
    		visible = false;  
    	}
	}
	
	public List<Alien> aliens = new ArrayList<Alien>();

	
	public List<Alien> getAliens() {
		return aliens;
	}
		
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
