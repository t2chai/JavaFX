package snake;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Snake extends Thread{
	class Map{

		public static final char MARKWALL = '#';
		public static final char MARKFOOD = '*';
		public static final char MARKHEAD = '@';
		public static final char MARKBODY = 'O';
		
		class Node {
			int x;
			int y;
			private char mark;
			String type;
			
			public Node(int x, int y, String type) {
				super();
				this.x = x;
				this.y = y;
				this.setType(type);
			}

			private void setType(String type) {
				this.type = type;
				if(type=="wall")
					mark = MARKWALL;
				else if(type=="food")
					mark = MARKFOOD;
				else if(type=="head")
					mark = MARKHEAD;
				else if(type=="body")
					mark = MARKBODY;
			}

			@Override
			public String toString() {
				return mark + "";
			}

		}
		
		private Node[][] map;
		private int width;
		private int height;
		
		public Map(int width, int height) {
			this.width = width;
			this.height = height;
			
			map = new Node[height][width];//Node[1][2]鏄痻=2,y=1
			this.initWall();					
		}

		private void initWall() {
			for(int x=1; x<=width; x++) {
				map[0][x-1] = new Node(x, 1, "wall");//涓婅竟澧�
				map[height-1][x-1] = new Node(x, height, "wall");//涓嬭竟澧�
			}
			
			for(int y=2; y<=height-1; y++) {
				map[y-1][0] = new Node(1, y, "wall");//宸﹁竟澧�
				map[y-1][width-1] = new Node(width, y, "wall");//鍙宠竟澧�
			}
		}
		
		public void print(){
			//鍘熷垯锛氱┖鐧藉湴鏂逛笉瑕佸垱寤簄ode
			String grid = "";
			for(int row=0; row<height; row++) {
				for(int col=0; col<width; col++) {
					if(map[row][col]==null)
						grid += " ";
					else
						grid += map[row][col];//toString
				}
				grid += "\n";
			}
			System.out.println(grid);
		}
		
		public Node addFood(int x, int y) {
			map[y-1][x-1] = new Node(x, y, "food");
			return map[y-1][x-1];
		}
		
		public Node addHead(int x, int y) {
			map[y-1][x-1] = new Node(x, y, "head");
			return map[y-1][x-1];
		}
		
		public Node addBody(int x, int y) {
			map[y-1][x-1] = new Node(x, y, "body");
			return map[y-1][x-1];
		}
		
		public void ChangeNode(Node n, String type) {
			n.setType(type);
		}
		
		public void removeNode(Node n) {
			map[n.y-1][n.x-1] = null;
		}
		
		public Node getNode(int x, int y) {
			return map[y-1][x-1];
		}
	}//MAP绫荤粨鏉�
	
	public Map map;
	int mapw;
	int maph;
	int numFood;
	
	LinkedList<Map.Node> snake = new LinkedList<>();
	List<Map.Node> foods = new ArrayList<>();
	
	char user = '\u0000';// user input
	int nextx, nexty;
	protected boolean gameover;
	
	public Snake(int mapw, int maph, int numFood) {
		super();
		this.mapw = mapw;
		this.maph = maph;
		this.numFood = numFood;
	}
	
	public void init() {
		map = new Map(mapw, maph);
		initSnake();
		addFood(numFood);
	}

	public void addFood(int numFood) {
		int count = 0;
		while(count<numFood) {
			int x = 1 + new Random().nextInt(mapw);
			int y = 1 + new Random().nextInt(maph);
			
			if(map.getNode(x, y) == null) {
				foods.add(map.addFood(x, y));
				count++;
			}
		}
		
	}

	public void initSnake() {
		snake.add(map.addHead(mapw/2, maph/2));
		snake.add(map.addBody(mapw/2-1, maph/2));
		snake.add(map.addBody(mapw/2-2, maph/2));
		snake.add(map.addBody(mapw/2-3, maph/2));
	}
	
	private boolean reversed() {
		return nextx == snake.get(1).x && nexty == snake.get(1).y;
	}
	
	public void moveUp() {
		nextx = snake.getFirst().x;
		nexty = snake.getFirst().y-1;
		
		if(reversed())
			moveDown();
		else
			checkNext();
	}
	
	public void moveDown() {
		nextx = snake.getFirst().x;
		nexty = snake.getFirst().y+1;
		
		if(reversed())
			moveUp();
		else
			checkNext();
	}
	
	
	public void moveLeft() {
		nextx = snake.getFirst().x-1;
		nexty = snake.getFirst().y;
		
		if(reversed())
			moveRight();
		else
			checkNext();
	}
	
	public void moveRight() {
		nextx = snake.getFirst().x+1;
		nexty = snake.getFirst().y;
		
		if(reversed())
			moveLeft();
		else
			checkNext();
	}

	public void checkNext() {
		Map.Node next = map.getNode(nextx, nexty);
		
		if(next == null) {//1鏄┖鐧藉尯鍩�
			snake.addFirst(map.addHead(nextx, nexty));
			map.ChangeNode(snake.get(1), "body");
			map.removeNode(snake.getLast());
			snake.removeLast();
			
		} else if(next.type=="food") {
			map.ChangeNode(next, "head");
			map.ChangeNode(snake.getFirst(), "body");
			snake.addFirst(next);
			foods.remove(next);
			addFood(1);
			
		} else if(next.type=="wall" || next.type=="body") {
			System.out.println("娓告垙缁撴潫");
			gameover = true;
		}
	}
	
	@Override
	public void run() {
		while(!gameover) {
			switch(user) {
			case 's': moveDown();break;
			case 'w': moveUp();break;
			case 'a': moveLeft();break;
			case 'd': moveRight();break;
			}
			map.print();
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	public void startGame() {
		this.init();
		this.start();
		
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextLine()) {
			String s = sc.nextLine().trim().toLowerCase();
			if(s.length()>0) {
				if("wasd".indexOf(s.charAt(0)) > -1)
					user = s.charAt(0);
			}
		}
		
	}
	
	
}









