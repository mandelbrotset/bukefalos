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
		myPosition = getCenterOfBallAt((lowerRightCorner.x - upperLeftCorner.x) / 2, (lowerRightCorner.y - upperLeftCorner.y) / 2);
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
			System.out.println("nu är vi i loopen");
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
		Point horizontal = new Point(x, x);
		System.out.println("Får in X: " + x + " Y: " + y);
		
		while(isABallHere(horizontal.x, y)) {
			horizontal.x--;
		}
		
		while(isABallHere(horizontal.y, y)) {
			horizontal.y++;
		}
		
		Point vertical = new Point(y, y);
		
		while(isABallHere(x, vertical.x)) {
			vertical.x--;
		}
		
		while(isABallHere(x, vertical.y)) {
			vertical.y++;
		}
		
		
		
		Point center = new Point(horizontal.x + (horizontal.y-horizontal.x)/2, vertical.x + (vertical.y-vertical.x)/2);
		
		System.out.println("Beräknat X: " + center.x + " Y: " + center.y);
		
		return center;
	}
	
	Point gamePosToRealPos(int x, int y) {
		return new Point(x + upperLeftCorner.x, y + upperLeftCorner.y); 
	}
	
	private boolean isABallHere(int x, int y) { //need improvements!! What if it is an image with something white in?!
		int realX = gamePosToRealPos(x, y).x;
		int realY = gamePosToRealPos(x, y).y;
		Color pixelColor = r.getPixelColor(realX, realY);
		System.out.println("pixel: " + x + ", " + y + ", Color: " + pixelColor.toString());
		return (pixelColor.equals(Color.white)); 
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
