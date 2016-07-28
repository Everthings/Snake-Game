import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;


public class Snake extends Thread{
	
	ArrayList<Square> list = new ArrayList<Square>();
	
	Timer t;
	TimerTask task;
	
	Semaphore sema = new Semaphore(1);
	
	boolean invincible = false;
	
	int invisiForMillis = 3000;
	
	String s;
	
	int initialTime;
	
	int boostForMillis = 3000;
	int boostFrameRate = 5;
	
	int startInvisiTime;
	
	boolean ran = false;
	
	Board b;
	
	boolean dead = false;
	
	int startingX, startingY;
	
	Direction d = Direction.Up;
	
	Snake(int x, int y, String s, Board b) {
		t = new Timer();
		this.startingX = x;
		this.startingY = y;
		this.b = b;
		this.s = s;
		this.start();
	}
	
	@Override
	public void run() {
		task = new updateSnake();
		t.scheduleAtFixedRate(task, 10, 1000 / b.frameRate);
		startInvisiTime = (int)System.currentTimeMillis();
	}
	
	public void changeFrameRate(int rate){
		
	}
	
	public void resetFrameRate(){
		
	}

	public void moveSnake(){
	
		if(list.size() > 0){
			setNewHead();
		}
	}
	
	public void createNewPiece(){
		
		if(list.isEmpty()){
			new SnakePiece(startingX, startingY, Color.GREEN, list, s);
		}else{
		
		
			switch(d){
			 case Up:
			 	new SnakePiece(list.get(list.size() - 1).p.x, list.get(list.size() - 1).p.y + 1, Color.GREEN, list, s);
			 	break;
			 case Down:
				 new SnakePiece(list.get(list.size() - 1).p.x, list.get(list.size() - 1).p.y - 1, Color.GREEN, list, s);
				 break;
			 case Left:
				 new SnakePiece(list.get(list.size() - 1).p.x + 1, list.get(list.size() - 1).p.y, Color.GREEN, list, s);
				 break;
			 case Right:
				 new SnakePiece(list.get(list.size() - 1).p.x - 1, list.get(list.size() - 1).p.y, Color.GREEN, list, s);
				 break;
			 }
			//new SnakePiece(x, y, Color.GREEN, list);
		}
	}
	
	public void setNewHead(){
		if(!dead){
			Square s = list.get(list.size() - 1);
			
			Square s2 = list.get(0);
			
			list.remove(list.size() - 1);
			list.add(0, s);
			
			switch(d){
			case Up:
				//s.setX(s.p.y - 1);
				s.p.y = s2.p.y - 1;
				s.p.x = s2.p.x;
				break;
			case Down:
				//s.setX(s.p.y + 1);
				s.p.y = s2.p.y + 1;
				s.p.x = s2.p.x;
				break;
			case Left:
				//s.setX(s.p.x - 1);
				s.p.x = s2.p.x - 1;
				s.p.y = s2.p.y;
				break;
			case Right:
				//s.setX(s.p.x + 1);
				s.p.x = s2.p.x + 1;
				s.p.y = s2.p.y;
				break;
			}
			
			if((int)System.currentTimeMillis() - startInvisiTime > invisiForMillis && !invincible){
				for(Square square: list){
					square.setColor(Color.GREEN);
				}
				hitObstacle();
				checkBounds(s.p.x);
				checkBounds(s.p.y);
				//checkIntersection(s);
			}else{
				for(Square square: list){
					square.setColor(Color.MAGENTA);
				}
			}
			
			if(b.f.checkEaten(this) != null){
				 b.f.removeFruit(b.f.checkEaten(this));
				 b.f.createFruit();
				 
				 this.createNewPiece();
				 this.createNewPiece();
				 this.createNewPiece();
			}
		}
		
		b.GameBoard.repaint();
	}
	
	public void hitObstacle(){
		if(b.o.checkHit(this) != null){
			labelDeadSquare(list.get(0));
			initialTime = (int) System.currentTimeMillis();
			dead = true;
		}
	}
	
	public void checkBounds(int i){
		if(i < 0 || i > Board.NUM_BLOCKS - 1){
			initialTime = (int) System.currentTimeMillis();
			dead = true;
		}
	}
	
	public void checkIntersection(Square s){
		for(int i = 1; i < list.size(); i++){
			if(list.get(i).p.x == s.p.x && list.get(i).p.y == s.p.y){
				initialTime = (int) System.currentTimeMillis();
				dead = true;
				labelDeadSquare(list.get(0));
			}
		}
	}
	
	public void intersectOtherSnake(Snake s){
		for(int i = 0; i < s.list.size(); i++){
			if(list.get(0).p.x == s.list.get(i).p.x && list.get(0).p.y == s.list.get(i).p.y){
				initialTime = (int) System.currentTimeMillis();
				dead = true;
				labelDeadSquare(list.get(0));
			}
		}
	}
	
	public void giveInvincibility(){
		invincible = true;
	}
	
	public void removeInvincibility(){
		invincible = false;
	}
	
	public void labelDeadSquare(Square s){
		s.c = Color.PINK;
	}
	
	public void removeAllSnakes(){
		while(list.size() > 0){
			list.remove(0);
		}
	}
	
	
	public void resetSnake(){
		startInvisiTime = (int)System.currentTimeMillis();
		dead = false;
		invincible = false;
	}
	
	public class updateSnake extends TimerTask{
		
		Square s;
		
		@Override
		public void run(){
			moveSnake();
		}
	}	
}
