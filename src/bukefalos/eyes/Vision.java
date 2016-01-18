package bukefalos.eyes;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;
import java.util.HashMap;
import static bukefalos.Body.upperLeftCorner;
import static bukefalos.Body.lowerRightCorner;

public class Vision {
	public static final int MIN_SIZE = 46; //uppskattning
	private Robot r;
	private HashMap<Point, Integer> balls;
	private float zoomLevel;
	private int mySize;
	private Point myPosition;
	
	public void init() {
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		balls = new HashMap<Point, Integer>();
		myPosition = new Point((lowerRightCorner.x - upperLeftCorner.x) / 2, (lowerRightCorner.y - upperLeftCorner.y) / 2);
		System.out.println("myPosition: " + myPosition);
		updateMySize();
	}
	
	public HashMap<Point, Integer> getBalls() {
		return balls;
	}
	
	private void updateMySize() {
		mySize = getSizeOfBallAt(myPosition);
		System.out.println("MySize: " + mySize);
	}
	
	public void takeALook() {
		System.out.println("takeALook");
		balls.clear();
		for (int x = upperLeftCorner.x; x <= lowerRightCorner.x; x += MIN_SIZE) {
			for (int y = upperLeftCorner.y; y <= lowerRightCorner.y; y += MIN_SIZE) {
				if (isABallHere(x, y)) {
					System.out.println("Found ball at: " + x + ", " + y);
					Point position = getCenterOfBallAt(x, y);
					int size = getSizeOfBallAt(position);
					balls.put(position, size);
				}
			}
		}
		updateMySize();
	}
	
	public int getMySize() {
		return mySize;
	}
	
	public Point getMyPosition() {
		return myPosition;
	}
	
	private int getSizeOfBallAt(Point center) { //need improvements!! What if the ball is skewed or overlapping?
		System.out.println("getSizeOfBall");
		int x = center.x;
		while (isABallHere(x, center.y)) {
			x--;
		}
		System.out.println("getSizeOfBall done");
		return (center.x - x) * 2;
	}
	
	
	
	private Point getCenterOfBallAt(int x, int y) {
		System.out.println("getCenterOfBallAt");
		//TODO: implement..
		// We will assume ideal conditions, meaning:
		// The whole ball is visible on screen
		int east = x;
		int west = x;
		
		while(isABallHere(west, y)) {
			west--;
		}
		
		while(isABallHere(east, y)) {
			east++;
		}
		
		int vertical = y;
		
		if(!isABallHere(west, vertical+5)) {
			while(isABallHere(west, vertical)) {
				vertical--;
			}
		} else {
			while(isABallHere(west, vertical)) {
				vertical++;
			}
		}
		
		Point center = new Point((east-west)/2, Math.abs((vertical-y)/2));
		
		return center;
	}
	
	private boolean isABallHere(int x, int y) { //need improvements!! What if it is an image with something white in?!
		Color pixelColor = r.getPixelColor(x, y);
		return (pixelColor == Color.white); 
	}
	
	private boolean isGray(Color color) {
		int margin = 20;
		if (color.getBlue() > color.getGreen() - margin && color.getBlue() < color.getGreen() + margin &&
				color.getBlue() > color.getRed() - margin && color.getBlue() < color.getRed() + margin) {
			return true;
		}
		return false;
	}
}
