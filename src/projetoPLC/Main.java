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
	
	
	public static void main(String[] args) {
		PApplet.main("projetoPLC.Main");
	}
	
	public void settings () {
		size(size,size);
	}
	
	public void setup () {
		gameOver = loadImage("game_over.jpg");
		colorMode(HSB);
		snake = new Player(this, size);
		frameRate(7);
		keyRepeatEnabled = false;
		_keyListener = new RunnableKeyListener(this, snake);
		_keyListener.start();
		foodGenerator = new FoodGenerator(foodQ, this, size);
		foodGenerator.start();
		takeFood();
	}
	
	public void draw () {
		translate(300, 300);
		if(!snake.gameOver()) {
			background(background);
			if(snake.eatFood(foodLocation)) {
				takeFood();
				score++;
			} 	
			snake.move();
			snake.display();
			drawFood();
			drawFrameRate();
		} else {
			background(0);
			image(gameOver, -190, -150, 400, 200);
		}
	}
	
	public void drawFood() {
		fill(5, 255, 255);
		rect(foodLocation.x, foodLocation.y, 20,20);
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
	
	public void mousePressed() {
		snake.length++;
	}
	
	public void drawFrameRate() {
		text(String.format("FPS: %.3g%n", frameRate), 220, 270);
		text(String.format("Score: " + score, frameRate), 220, 290);
	}
	
}
