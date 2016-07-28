import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;


public class Board extends JLabel{
	
	final static int WIDTH = 600;
	final static int HEIGHT = 600;
	final static int NUM_BLOCKS = 60;
	
	final int SNAKE_ONE = 1;
	final int SNAKE_TWO = 2;
	
	int numPieces = 4;
	
	Random r = new Random();
	
	int frameRate = 20;
	
	JFrame GameBoard;
	
	Snake s1 = new Snake(NUM_BLOCKS / 3, NUM_BLOCKS * 2 / 3, "1", this);
	Snake s2 = new Snake(NUM_BLOCKS * 2 / 3, NUM_BLOCKS * 2 / 3, "2", this);
	FruitCollection f = new FruitCollection("");;
	ObstacleCollection o = new ObstacleCollection("");
	
	ArrayList<ArrayList<Square>> compiledList = new ArrayList<ArrayList<Square>>();
	
	public Board(){
		GameBoard = new JFrame("Snake");
		GameBoard.setLocationRelativeTo(null);
		GameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameBoard.pack();
        GameBoard.setResizable(false);
        GameBoard.setLocationByPlatform(true);
        GameBoard.setSize(WIDTH, HEIGHT + 22);
        GameBoard.setVisible(true);  
        GameBoard.add(this);
        
        compiledList.add(f.list);
		compiledList.add(o.list);
		compiledList.add(s1.list);
		compiledList.add(s2.list);
        
        this.requestFocus();
        
        setupKeyBindings();
        
		setupBoard();
	}
	
	public void setupBoard(){
		
		s1.d = Direction.Up;
		s2.d = Direction.Up;
		
		s1.resetSnake();
		s2.resetSnake();
		
		for(int i = 0; i < numPieces; i++){
			s1.createNewPiece();
			s2.createNewPiece();
		}
        
	//	for(int i = 0; i < r.nextInt(5) + 1; i++){
	//		f.createFruit();
	//	}
		
		f.createFruit();
		f.createFruit();
		f.createFruit();
		f.createFruit();
		
        
		for(int i = 0; i < r.nextInt(7) + 3; i++){
			 o.createObstacle();
		}
	}
	
	public void setupKeyBindings(){
		
		InputMap inMap = this.getInputMap(JComponent.WHEN_FOCUSED);
		
		KeyStroke wKey = KeyStroke.getKeyStroke('w');
		KeyStroke aKey = KeyStroke.getKeyStroke('a');
		KeyStroke sKey = KeyStroke.getKeyStroke('s');
		KeyStroke dKey = KeyStroke.getKeyStroke('d');
		
		KeyStroke zKey = KeyStroke.getKeyStroke('z');
		
		KeyStroke slashKey = KeyStroke.getKeyStroke('/');
		
		KeyStroke upKey = KeyStroke.getKeyStroke("UP");
		KeyStroke downKey = KeyStroke.getKeyStroke("DOWN");
		KeyStroke leftKey = KeyStroke.getKeyStroke("LEFT");
		KeyStroke rightKey = KeyStroke.getKeyStroke("RIGHT");
			
		inMap.put(zKey, "changeSOneSpeed");
		inMap.put(slashKey, "changeSTwoSpeed");
		
		inMap.put(wKey, "moveSOneUp");
		inMap.put(aKey, "moveSOneLeft");
		inMap.put(sKey, "moveSOneDown");
		inMap.put(dKey, "moveSOneRight");
		
		inMap.put(upKey, "moveSTwoUp");
		inMap.put(leftKey, "moveSTwoLeft");
		inMap.put(downKey, "moveSTwoDown");
		inMap.put(rightKey, "moveSTwoRight");
		
		ActionMap actMap = this.getActionMap();
		
		actMap.put("changeSOneSpeed", new changeSpeed(s1));
		actMap.put("changeSTwoSpeed", new changeSpeed(s2));
		
		actMap.put("moveSOneUp", new setDirection(Direction.Up, 1));
		actMap.put("moveSOneDown", new setDirection(Direction.Down, 1));
		actMap.put("moveSOneLeft", new setDirection(Direction.Left, 1));
		actMap.put("moveSOneRight", new setDirection(Direction.Right, 1));
		
		actMap.put("moveSTwoUp", new setDirection(Direction.Up, 2));
		actMap.put("moveSTwoDown", new setDirection(Direction.Down, 2));
		actMap.put("moveSTwoLeft", new setDirection(Direction.Left, 2));
		actMap.put("moveSTwoRight", new setDirection(Direction.Right, 2));
	}
	
	public void resetAllCollections(){
		s1.removeAllSnakes();
		s2.removeAllSnakes();
		f.removeAllFruits();
		o.removeAllObstacles();
	}
	
	public void paintComponent(Graphics g){
		
		 g.setColor(Color.BLACK);
		 g.fillRect(0, 0, WIDTH, HEIGHT);
		 
		 if(s1.dead == true){
			 if((int)System.currentTimeMillis() - s1.initialTime > 3000){	
				 resetAllCollections();
				 setupBoard();
			 }
		 }
		 
		 if(s2.dead == true){
			 if((int)System.currentTimeMillis() - s2.initialTime > 3000){	
				 resetAllCollections();
				 setupBoard(); 
			 }
		 }
		
		if(s1.dead == true && s2.dead == false){
			s2.giveInvincibility();
		}else if(s1.dead == false && s2.dead == true){
			s1.giveInvincibility();
		}
		
		if(s1.invincible == true){	
			if(s2.invincible == false){
				s2.intersectOtherSnake(s1);
			}
		}else if(s2.invincible == true){
			if(s1.invincible == false){
				s1.intersectOtherSnake(s2);
			}
		}else if((int)System.currentTimeMillis() - s1.startInvisiTime > s1.invisiForMillis){
			if(s1.dead == false && s2.dead == false){
				s2.intersectOtherSnake(s1);
				s1.intersectOtherSnake(s2);
			}
		}else if((int)System.currentTimeMillis() - s2.startInvisiTime > s2.invisiForMillis){
			if(s1.dead == false && s2.dead == false){
				s2.intersectOtherSnake(s1);
				s1.intersectOtherSnake(s2);
			}
		}
		
		for(ArrayList<Square> element: compiledList){
			if(element.size() > 0){
				element.get(0).drawAll(g);
			}
		}
	}
	
	public class changeSpeed extends AbstractAction{

		Snake s;
		
		changeSpeed(Snake s){
			this.s = s;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
		
			
		}
		
	}
	
	public class setDirection extends AbstractAction{
		
		Direction d;
		
		int snake;
		
		public setDirection(Direction d, int snake){
			this.d = d;
			this.snake = snake;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(snake == 1){ //player one's snake
				switch(d){
				case Up:
					if(s1.d != Direction.Down)
						s1.d = Direction.Up;
					break;
				case Down:
					if(s1.d != Direction.Up)
						s1.d = Direction.Down;
					break;
				case Left:
					if(s1.d != Direction.Right)
						s1.d = Direction.Left;
					break;
				case Right:
					if(s1.d != Direction.Left)
						s1.d = Direction.Right;
					break;
				}
			}else{
				switch(d){ //player two's snake
				case Up:
					if(s2.d != Direction.Down)
						s2.d = Direction.Up;
					break;
				case Down:
					if(s2.d != Direction.Up)
						s2.d = Direction.Down;
					break;
				case Left:
					if(s2.d != Direction.Right)
						s2.d = Direction.Left;
					break;
				case Right:
					if(s2.d != Direction.Left)
						s2.d = Direction.Right;
					break;
				}
			}
		}
	}
}
