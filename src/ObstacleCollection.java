import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;


public class ObstacleCollection {
	
	boolean hit = false;
	
	Random r = new Random();
	
	String s;
	
	
	int sizeX = 4;
	int sizeY = 4;

	ArrayList<Square> list = new ArrayList<Square>();
	
	ObstacleCollection(String s){
		this.s = s;
	}
	
	public void removeAllObstacles(){
		while(list.size() > 0){
			list.remove(0);
		}
	}
	
	public void createObstacle(){
		
		int x = r.nextInt(Board.NUM_BLOCKS);
		int y = r.nextInt(Board.NUM_BLOCKS);
		
		new Obstacle(x, y, Color.GRAY, list, s, sizeX, sizeY);
	}
	
	public void removeObstacle(Square s){
		list.remove(s);
		list.remove(s);
	}
	
	public Square checkHit(Snake snake){
		for(Square s: list){
			if(isHit(s, snake)){
				return s;
			}
		}
		
		return null;
	}
	
	public boolean isHit(Square s, Snake snake){
		
		for(int a = 0; a < sizeX; a++){
			for(int b = 0; b < sizeY; b++){
				if(snake.list.get(0).p.x == s.p.x + a && snake.list.get(0).p.y == s.p.y + b){
					return true;
				}
			}
		}
		
		return false;
	}
}
