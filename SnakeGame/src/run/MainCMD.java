package run;

import snake.Snake;

public class MainCMD {

	public static void main(String[] args) {
		Snake snake = new Snake(30, 10, 10);
		snake.startGame();
	}

}
