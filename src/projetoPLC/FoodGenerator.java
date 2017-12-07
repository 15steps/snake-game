package projetoPLC;

import java.util.concurrent.BlockingQueue;

import processing.core.PApplet;
import processing.core.PVector;

public class FoodGenerator extends Thread{
	BlockingQueue<PVector> foodQ;
	PApplet p;
	PVector foodLocation;
	int size;
	int scale;
	
	public FoodGenerator(BlockingQueue<PVector> q, PApplet app, int size, int scale) {
		p = app;
		foodQ = q;
		this.size = size;
		this.scale = scale;
	}
	
	@Override
	public void run() {
		while(true) {
				generateFood();
				try {
					foodQ.put(foodLocation);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}							
			}
	}
	
	public void generateFood() {
		int cols = size/scale;
		float x = PApplet.floor(p.random(cols));
		float y = PApplet.floor(p.random(cols));
		this.foodLocation = new PVector(x,y);
		foodLocation.mult(scale);
		foodLocation.x = foodLocation.x % (size/2);
		foodLocation.y = foodLocation.y % (size/2);
	}
	
	public void drawFood() {
		p.fill(5, 255, 255);
		p.rect(foodLocation.x, foodLocation.y, scale,scale);
//		System.out.println(foodLocation.x + " " + foodLocation.y);
	}
	
}
