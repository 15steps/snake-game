package projetoPLC;

import processing.core.PApplet;

public class RunnableKeyListener extends Thread {
	PApplet p;
	Player snake;
	public RunnableKeyListener(PApplet app, Player snake) {
		p = app;
		this.snake = snake;
	}
	
	@Override
	public void run() {
		while(true) {
			switch(p.keyCode) {
			case PApplet.UP:
				snake.changeDir(0, -1);
				break;
			case PApplet.DOWN:
				snake.changeDir(0, 1);
				break;
			case PApplet.RIGHT:
				snake.changeDir(1, 0);
				break;
			case PApplet.LEFT:
				snake.changeDir(-1, 0);
			}
		}
	}

}
