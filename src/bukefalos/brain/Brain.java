package bukefalos.brain;

import java.awt.Point;
import java.util.HashMap;

import bukefalos.eyes.Vision;

public class Brain {
	public static final int STEERING_DISTANCE = 200;
	private Vision vision;
	private Point myPosition;
	private int mySize;
	HashMap<Point, Integer> balls;
	
	public Brain(Vision vision) {
		this.vision = vision;
	}
	
	public Point DecideAMove() {
		System.out.println("Deciding a move..");
		long t1 = System.currentTimeMillis();
		vision.takeALook();
		long t2 = System.currentTimeMillis();
		System.out.println("Det tog " + Long.toString(t2-t1) + " ms att titta");
		balls = vision.getBalls();
		myPosition = vision.getMyPosition();
		Point moveTo = new Point(0,0);
		
		Point enemy = getNearestDanger();
		//if (getDistance(myPosition, enemy) < 500) {
			moveTo = escapeFrom(enemy);
		//}
		
		return getMovePosition(moveTo);
	}
	
	private Point escapeFrom(Point position) {
		System.out.println("escaping! :O");
		Point direction = subtract(position, myPosition);
		return new Point(direction.x * -1, direction.y * -1);
	}
	
	private Point getMovePosition(Point position) {
		//Point towards = subtract(position, myPosition);
		//position = getUnitVector(position);
		//position = multiply(position, STEERING_DISTANCE);
		return add(myPosition, position);
	}
	
	private Point add(Point p1, Point p2) {
		return new Point(p1.x + p2.x, p1.y + p2.y);
	}
	
	private Point multiply(Point p, int m) {
		return new Point(p.x * m, p.y*m);
	}
	
	private Point subtract(Point p1, Point p2) {
		return new Point(p1.x-p2.x, p1.y-p2.y);
	}
	
	private Point getUnitVector(Point vector) {
		int length = getDistance(vector, new Point(0,0));
		return new Point(vector.x / length, vector.y / length);
	}
	
	private Point getNearestDanger() {
		boolean hej = false;
		int nearestDistance = Integer.MAX_VALUE;
		Point nearest = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		for (Point enemy : balls.keySet()) {
			if (balls.get(enemy) > mySize) {
				int distance = getDistance(myPosition, enemy); 
				if (distance < nearestDistance) {
					nearestDistance = distance;
					nearest = enemy;
					hej = true;
				}
			}
		}
		if (!hej) System.out.println("hej = false");
		return nearest;
	}
	
	private int getDistance(Point p1, Point p2) {
		int dx = Math.abs(p1.x - p2.x);
		int dy = Math.abs(p1.y - p2.y);
		return (int)Math.sqrt(dx*dx + dy*dy);
	}

	
}
