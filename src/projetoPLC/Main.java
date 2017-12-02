package projetoPLC;

import processing.core.PApplet;

public class Main extends PApplet {
	
	Player snake;
	RunnableKeyListener _keyListener;
	int size = 600;
	int background = 100;
	
	public static void main(String[] args) {
		PApplet.main("projetoPLC.Main");
	}
	
	public void settings () {
		size(size,size);
	}
	
	public void setup () {
		snake = new Player(this, size);
		frameRate(10);
		keyRepeatEnabled = false;
		_keyListener = new RunnableKeyListener(this, snake);
		_keyListener.start();
	}
	
	public void draw () {
		translate(300, 300);
		background(background);
		snake.display();
		snake.move();
		drawFrameRate();
	}
	
	public void mousePressed() {
		snake.length++;
	}
	
	public void drawFrameRate() {
		text(String.format("FPS: %.3g%n", frameRate), 220, 290);
	}
	
}
