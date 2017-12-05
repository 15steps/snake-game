package projetoPLC;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Main extends PApplet {
	
	Player snake;
	RunnableKeyListener _keyListener;
	FoodGenerator foodGenerator;
	int size = 600;
	int background = 100;
	PVector foodLocation;
	int score = 0;
	BlockingQueue<PVector> foodQ = new ArrayBlockingQueue<>(1);
	PImage gameOver;
	int scale = 30;
	boolean toggleGrid = false;
	
	
	public static void main(String[] args) {
		PApplet.main("projetoPLC.Main");
	}
	
	public void settings () {
		size(size,size);
	}
	
	public void setup () {
		gameOver = loadImage("game_over.jpg");
		colorMode(HSB);
		snake = new Player(this, size, scale, foodQ);
		frameRate(5);
		keyRepeatEnabled = false;
		_keyListener = new RunnableKeyListener(this, snake);
		_keyListener.start();
		foodGenerator = new FoodGenerator(foodQ, this, size, scale);
		foodGenerator.start();
		takeFood();
	}
	
	public void draw () {
		translate(size/2, size/2);
		if(!snake.gameOver()) {
			noStroke();
			snake.move();
			background(background);
			if(foodQ.peek() != null && snake.eatFood()) {
				takeFood();
				score++;
			}
			snake.display();
			drawFood();
			drawFrameRate();
		} else {
			background(0);
			image(gameOver, -190, -150, 400, 200);
			text("Press R to restart", -50, 50);
			if(key == 'r' && keyPressed)
				reset();
		}
		if(toggleGrid)
			drawGrid();
	}
	
	public void reset() {
		snake = new Player(this, size, scale, foodQ);
		_keyListener = new RunnableKeyListener(this, snake);
		_keyListener.start();
		score = 0;
	}
	
	public void drawFood() {
		fill(frameCount % 256, 255, 255);
		foodQ.forEach(location -> rect(location.x, location.y, scale, scale));
	}
	
	public void takeFood() {
		try {
			synchronized(foodGenerator) {
				foodLocation = foodQ.take();
				foodGenerator.notify();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void drawFrameRate() {
		textSize(20);
		fill(100, 255, 255);
		text(String.format("FPS: %.3g%n", frameRate), 210, 270);
		text(String.format("Score: " + score, frameRate), 210, 290);
	}
	
	public void drawGrid() {
		translate(-size/2,-size/2);
		for (int i = 0; i < size/scale; ++i) {
			for (int j = 0; j < size/scale; ++j) {
				stroke(200);
				line(i, j*scale, i+size, j*scale);
				line(i*scale, j, i*scale, j+size);
			}
		}
		noStroke();
	}
	
	public void keyPressed() {
		if(key == 'g')
			toggleGrid = !toggleGrid;
	}
	
}
