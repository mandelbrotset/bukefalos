package bukefalos.eyes;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.sun.org.apache.bcel.internal.generic.GOTO;

import static bukefalos.Body.upperLeftCorner;
import static bukefalos.Body.lowerRightCorner;

public class Vision {
	public static final int MIN_SIZE = 46; //uppskattning
	private Robot r;
	private HashMap<Point, Integer> balls;
	private float zoomLevel;
	private int mySize;
	private Point myPosition;
	private BufferedImage snapchat;
	
	public void init() {
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		takeCapture();
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
	
	private void takeCapture() {
		int x = upperLeftCorner.x;
		int y = upperLeftCorner.y;
		int width = lowerRightCorner.x-upperLeftCorner.x;
		int height = lowerRightCorner.y - upperLeftCorner.y;
		System.out.println("x:" + x);
		System.out.println("y:" + y);
		System.out.println("w:" + width);
		System.out.println("h:" + height);
		snapchat = r.createScreenCapture(new Rectangle(x, y, width, height));
	}
	
	public void takeALook() {
		System.out.println("takeALook");
		
		takeCapture();
		
		balls.clear();
		long tcenter = 0;
		long tsize = 0;
		if (mySize < 10) {
			System.out.println("den är mindre än 10");
			mySize = 60;
		}
		for (int x = upperLeftCorner.x; x <= lowerRightCorner.x; x += mySize) {
			for (int y = upperLeftCorner.y; y <= lowerRightCorner.y; y += mySize) {
				if (!(y > (lowerRightCorner.y - upperLeftCorner.y) - 1|| x > (lowerRightCorner.x - upperLeftCorner.x)-1)) {
					if (isABallHere(x, y)) {
						//System.out.println("Found ball at: " + x + ", " + y);
						long t1 = System.currentTimeMillis();
						Point position = getCenterOfBallAt(x, y);
						long t2 = System.currentTimeMillis();
						tcenter += t2 - t1;
						t1 = System.currentTimeMillis();
						int size = getSizeOfBallAt(position);
						t2 = System.currentTimeMillis();
						tsize += t2 - t1;
						balls.put(position, size);
					}
				}
			}
		}
		System.out.println("tcenter: " + tcenter);
		System.out.println("tsize: " + tsize);
		updateMySize();
	}
	
	public int getMySize() {
		return mySize;
	}
	
	public Point getMyPosition() {
		return myPosition;
	}
	
	private int getSizeOfBallAt(Point center) { //need improvements!! What if the ball is skewed or overlapping?
		//System.out.println("getSizeOfBall");
		int x = center.x;
		while (isABallHere(x, center.y)) {
			x--;
		}
		//System.out.println("getSizeOfBall done, size: " + (center.x - x) * 2);
		return (center.x - x) * 2;
	}
	
	
	
	private Point getCenterOfBallAt(int x, int y) {
		//System.out.println("getCenterOfBallAt");
		//TODO: implement..
		// We will assume ideal conditions, meaning:
		// The whole ball is visible on screen
		Point horizontal = new Point(x, x);
		//System.out.println("Får in X: " + x + " Y: " + y);
		
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
		
		//System.out.println("Beräknat X: " + center.x + " Y: " + center.y);
		
		return center;
	}
	
	Point gamePosToRealPos(int x, int y) {
		return new Point(x + upperLeftCorner.x, y + upperLeftCorner.y); 
	}
	
	private boolean isABallHere(int x, int y) { //need improvements!! What if it is an image with something white in?!
		
		//if ((y > (lowerRightCorner.y - upperLeftCorner.y) - 1|| x > (lowerRightCorner.x - upperLeftCorner.x)-1)) {
		if ((y > 672 || x > 1360)) {
			return false;
		}
		//long t1 = System.nanoTime();
		//Color pixelColor = r.getPixelColor(realX, realY);
		
		//long t2 = System.nanoTime();
		//System.out.println("isaballhere: " + x + ", " + y);
		Color pixelColor = Color.BLACK;
		try {
			pixelColor = new Color(snapchat.getRGB(x, y));
		} catch (Exception e) {
			System.out.println("x: " + x + ", y: " + y);
			e.printStackTrace();
			
		}
		//long t3 = t2-t1;
		//System.out.println("getPixelColor: " + t3);
		//System.out.println("pixel: " + x + ", " + y + ", Color: " + pixelColor.toString());
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
