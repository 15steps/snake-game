package projetoPLC;

import java.util.concurrent.BlockingQueue;

import processing.core.PApplet;
import processing.core.PVector;

public class FoodGenerator extends Thread{
	BlockingQueue<PVector> foodQ;
	PApplet p;
	PVector foodLocation;
	int size;
	
	public FoodGenerator(BlockingQueue<PVector> q, PApplet app, int size) {
		p = app;
		foodQ = q;
		this.size = size;
	}
	
	@Override
	public void run() {
		while(true) {
				if(foodQ.isEmpty())  {
					generateFood();
					foodQ.add(foodLocation);			
				} else {
					try {
						synchronized (this) {
							wait();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
	}
	
	public void generateFood() {
		int cols = size/20;
		float x = PApplet.floor(p.random(cols));
		float y = PApplet.floor(p.random(cols));
		this.foodLocation = new PVector(x,y);
		foodLocation.mult(20);
		foodLocation.x = foodLocation.x % 300;
		foodLocation.y = foodLocation.y % 300;
		System.out.println("generateFood");
	}
	
	public void drawFood() {
		p.fill(5, 255, 255);
		p.rect(foodLocation.x, foodLocation.y, 20,20);
//		System.out.println(foodLocation.x + " " + foodLocation.y);
	}
	
}
