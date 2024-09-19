package run;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import snake.Gsnake;


public class MainGUI extends javafx.application.Application{

		final static int MAPW = 30;
		final static int MAPH = MAPW;
		int numFood = 150;
		double scale = 30;

		public static void main(String[] args) {
			Application.launch(args);
		}

		public void start(Stage stage1) throws Exception {

			VBox vbox = new VBox();

			Scene scene = new Scene(vbox, MAPW*scale, MAPH*scale);
			stage1.setScene(scene);
			stage1.setResizable(false);
			
			Gsnake game = new Gsnake(MAPW, MAPH, scale, numFood);
			vbox.getChildren().add(game.getPane());
			game.startGame();
			
			stage1.show();
			
			scene.setOnKeyPressed(e->{
				switch(e.getCode()) {
				case W:
				case UP:
				case KP_UP: game.setUser('w');break;
				case S:
				case DOWN:
				case KP_DOWN: game.setUser('s');break;
				case A:
				case LEFT:
				case KP_LEFT: game.setUser('a');break;
				case D:
				case RIGHT:
				case KP_RIGHT: game.setUser('d');break;
				}
			});
			
			stage1.setOnCloseRequest(e->System.exit(0));
			
		}

	}



























