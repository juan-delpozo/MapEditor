import java.awt.*;

public class Rect {
	int x;
	int y;
	
	int w;
	int h;
	
	public Rect(int x, int y, int w, int h) 
	{
		setLocation(x, y);
		
		setSize(w, h);
	}
	
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	} 
	
	public void setSize(int w, int h) 
	{
		this.w = w;
		this.h = h;
	}
	
	public void draw(Graphics g) 
	{
	g.drawRect(x, y, w, h);	
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return w;
	}

	public void setWidth(int w) {
		this.w = w;
	}

	public int getHeight() {
		return h;
	}

	public void setHeight(int h) {
		this.h = h;
	}
}
