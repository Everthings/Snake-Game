import java.awt.Color;
import java.util.ArrayList;


public class SnakePiece extends Square{

	SnakePiece(int x, int y, Color c, ArrayList<Square> list, String s) {
		super(x, y, c, list, s, 1, 1);
		list.add(this);
	}
}
