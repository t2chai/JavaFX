package snake;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import snake.Snake.Map;

@FunctionalInterface
interface newShape{
	public Shape create(int x, int y);
}

public class Gsnake extends Snake{
	private double scale=1;
	private Pane pane = new Pane();
	private boolean moveOnly = true;
	private int millis = 200;

	private LinkedList<Shape> gsnake = new LinkedList<>();
	private ArrayList<Shape> gfoods = new ArrayList<>();
	
	private LinkedList<Shape> gsnakeLast = new LinkedList<>();
	private ArrayList<Shape> gfoodsLast = new ArrayList<>();
	
	private static Color wallColor = Color.BLACK;
	private static Color foodColor = Color.INDIANRED;
	private static Color headColor = Color.DODGERBLUE;
	private static Color bodyColor = Color.DARKSEAGREEN;
	private static Color spaceColor = Color.WHITE;

	public newShape newWall = (x, y) ->{
		x--; y--;
		Shape s = new Rectangle(x*scale, y*scale, scale, scale);
		s.setFill(wallColor);
		return s;
	};
	
	public newShape newHead = (x, y) ->{
		x--; y--;
		Shape s = new Rectangle(x*scale, y*scale, scale, scale);
		s.setFill(headColor);
		return s;
	};
	
	public newShape newBody = (x, y) ->{
		x--; y--;
		Shape s = new Rectangle(x*scale, y*scale, scale, scale);
		s.setFill(bodyColor);
		return s;
	};
	
	public newShape newFood = (x, y) ->{
		x--; y--;
		Shape s = new Polygon(x*scale, (y+1)*scale,
				(x+.5)*scale, y*scale,
				(x+1)*scale, (y+1)*scale);
		s.setFill(foodColor);
		return s;
	};

	public Gsnake(int mapw, int maph, double scale, int numFood) {
		super(mapw, maph, numFood);
		this.scale = scale;
	}

	public Node getPane() {
		return this.pane;
	}


	public void setUser(char c) {
		this.user = c;
	}
	
	@Override
	public void startGame() {
		this.init();
		this.start();
	}

	@Override
	public void initSnake() {
		super.initSnake();
		gsnake.add(newHead.create(snake.getFirst().x,snake.getFirst().y));
		
		for(int i=1; i<snake.size(); i++) {
			gsnake.add(newBody.create(snake.get(i).x,snake.get(i).y));
		}
	}

	@Override
	public void addFood(int numFood) {
		super.addFood(numFood);
		
		for(int i=foods.size()-numFood; i<foods.size(); i++) {
			gfoods.add(newFood.create(foods.get(i).x, foods.get(i).y));
		}
	}
	
	@Override
	public void checkNext() {

		Map.Node next = map.getNode(nextx, nexty);
		
		if(next == null) {
			snake.addFirst(map.addHead(nextx, nexty));
			map.ChangeNode(snake.get(1), "body");
			map.removeNode(snake.getLast());
			snake.removeLast();
			
			moveOnly = true;
			
		} else if(next.type=="food") {
			moveOnly = false;
			//1 head-body, 2food-head
			
			//1 head>body
			map.ChangeNode(snake.getFirst(), "body");
			gsnake.removeFirst();
			gsnake.addFirst(newBody.create(snake.getFirst().x,snake.getFirst().y));
			
			//2 food>head
			map.ChangeNode(next, "head");
			snake.addFirst(next);
			gsnake.addFirst(newHead.create(nextx, nexty));
			
			//3 foods, gfoods
			int i = foods.indexOf(next);
			foods.remove(next);
			gfoods.remove(i);
			
			//4 add food
			addFood(1);
			
		} else if(next.type=="wall" || next.type=="body") {
			System.out.println("game over");
			gameover = true;
		}
	}
	
	public void reflesh() {
		if(pane.getChildren().size()==0) {//1初始化
			//wall
			for(int x=1; x<=mapw; x++) {
				pane.getChildren().add(newWall.create(x, 1));
				pane.getChildren().add(newWall.create(x, maph));
			}
			for(int y=2; y<=maph; y++) {
				pane.getChildren().add(newWall.create(1, y));
				pane.getChildren().add(newWall.create(mapw, y));
			}
			//snake
			gsnake.forEach(s->{
				pane.getChildren().add(s);
				gsnakeLast.add(s);
			});
			
			//food
			gfoods.forEach(s->{
				pane.getChildren().add(s);
				gfoodsLast.add(s);
			});
		} else {//2 已经开始游戏
			if(moveOnly) {
				for(int i=gsnake.size()-1; i>0; i--) {
					Rectangle s2 = (Rectangle) gsnake.get(i);
					Rectangle s1 = (Rectangle) gsnake.get(i-1);
					
					s2.setX(s1.getX());
					s2.setY(s1.getY());
				}
				
				((Rectangle) gsnake.getFirst()).setX((nextx-1)*scale);
				((Rectangle) gsnake.getFirst()).setY((nexty-1)*scale);
				
			}else {
				final Set<Shape> rmSet = new HashSet<>();

				final Set<Shape> addSet = new HashSet<>();
				
				gsnake.iterator().forEachRemaining(e->{
					if(!gsnakeLast.contains(e)) addSet.add(e);
				});
				
				gsnakeLast.iterator().forEachRemaining(e->{
					if(!gsnake.contains(e)) rmSet.add(e);
				});
				
				addSet.forEach(e->{
					pane.getChildren().add(e);
					gsnakeLast.add(e);
				});
				
				rmSet.forEach(e->{
					pane.getChildren().remove(e);
					gsnakeLast.remove(e);
				});
				
				final Set<Shape> rmSet2 = new HashSet<>();
				final Set<Shape> addSet2 = new HashSet<>();
				
				gfoods.iterator().forEachRemaining(e->{
					if(!gfoodsLast.contains(e)) addSet2.add(e);
				});
				
				gfoodsLast.iterator().forEachRemaining(e->{
					if(!gfoods.contains(e)) rmSet2.add(e);
				});
				
				addSet2.forEach(e->{
					pane.getChildren().add(e);
					gfoodsLast.add(e);
				});
				
				rmSet2.forEach(e->{
					pane.getChildren().remove(e);
					gfoodsLast.remove(e);
				});
			}
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
			//map.print();
			Platform.runLater(()-> reflesh());
			
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
