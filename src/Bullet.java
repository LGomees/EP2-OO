import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Bullet  {
    
	public static final int SPEED_BULLET = 3;
	
	 	private int x;
	    private int y;
	    private int width;
	    private int height;
	    private boolean visible;
	    private Image image;
	

	public Bullet(int x, int y) {
		this.x=x;
		this.y=y;
		
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
	
	private void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
        getImageDimensions();
    }
    
    private void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }    

    public Image getImage() {
        return image;
    }
	
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean enable) {
        visible = enable;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
	
    
   
	
	
	
	
	
	
	
}
