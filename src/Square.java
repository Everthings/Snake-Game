import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;


public abstract class Square{
	Color c;
	
	Pair p;
	
	String s;
	
	int squareHeight;
	int squareWidth;
	
	ArrayList<Square> list;
	
	int length = Board.WIDTH / Board.NUM_BLOCKS;
	
	Square(int x, int y, Color c, ArrayList<Square> list, String s, int squareHeight, int squareWidth){
		this.squareHeight = squareHeight;
		this.squareWidth = squareWidth;
		this.s = s;
		p = new Pair(x, y);
		this.c = c;
		this.list = list;
	}
	
	public void setColor(Color c){
		this.c = c;
	}

	public void drawAll(Graphics g){
		for(int i = list.size() - 1; i >= 0; i--){
			list.get(i).drawSquare(g);
		}
		
		drawString(g);
	}
	
	public void drawString(Graphics g){
		g.setColor(Color.CYAN);
		g.drawString(s, p.x * length - length, p.y * length - length);
	}
	
	public void drawSquare(Graphics g){
		g.setColor(c);
		g.fillRect(p.x * length, p.y * length, length * squareHeight, length * squareHeight);	
	}
	
	public void setX(int x){
		p.x = x;
	}
	
	public void setY(int y){
		p.y = y;
	}
}
