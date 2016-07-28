import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Obstacle extends Square{

	Obstacle(int x, int y, Color c, ArrayList<Square> list, String s, int sizeX, int sizeY) {
		super(x, y, c, list, s, sizeX, sizeY);
		list.add(this);
	}
}
