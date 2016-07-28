import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;


public class FruitCollection {
	boolean eaten = false;
	
	Random r = new Random();
	
	String s;
	
	ArrayList<Square> list = new ArrayList<Square>();
	
	FruitCollection(String s){
		this.s = s;
	}
	
	public void removeAllFruits(){
		while(list.size() > 0){
			list.remove(0);
		}
	}
	
	public void removeFruit(Square s) {
		list.remove(s);
	}
	
	public void createFruit(){
		new Fruit(r.nextInt(Board.NUM_BLOCKS), r.nextInt(Board.NUM_BLOCKS), Color.RED, list, s);
	}
	

	public Square checkEaten(Snake snake){
		
		for(Square s: list){
			if(isEaten(s, snake)){
				return s;
			}
		}
		
		return null;
	}
	
	public boolean isEaten(Square s, Snake snake){
		
		if(snake.list.get(0).p.x == s.p.x && snake.list.get(0).p.y == s.p.y){
			return true;
		}
		
		return false;
		
	}
}
