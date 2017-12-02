package projetoPLC;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Player {
	float x = 0; 
	float y = 0;
	int length = 2;
	float xSpeed = 1;
	float ySpeed = 0;
	int canvas_size;
	int gridScale = 20;
	ArrayList<PVector> tail = new ArrayList<>();
	
	PApplet p;
	
	Player (PApplet parent, int size) {
		p = parent;
		this.canvas_size = size;
	}	
	
	void display () {
		p.fill(255);
		for (PVector v : tail) {
			p.rect(v.x, v.y, gridScale, gridScale);
		}
		p.rect(x, y, gridScale, gridScale);
	}
	
	void move () {
		if(length > 0) {
			if(length == tail.size() && !tail.isEmpty()) {
				tail.remove(0);
			}
			tail.add(new PVector(x, y));
		}
		
		x = x + xSpeed*gridScale;
		y = y + ySpeed*gridScale;
		
//		x = PApplet.constrain(x, 0, (canvas_size)/2-(gridScale * tail.size()));
//		y = PApplet.constrain(y, 0, (canvas_size)/2-(gridScale * tail.size()));
	}
	
	public void changeDir(float x, float y) {
		this.xSpeed = x;
		this.ySpeed = y;
	}
}
