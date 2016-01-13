package bukefalos;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;

import bukefalos.brain.Brain;
import bukefalos.eyes.Vision;

public class Body extends Thread {
	public static final Point lowerLeftCorner = new Point(0,0);
	public static final Point upperRightCorner = new Point(0,0);
	
	private boolean isRunning = true;
	private boolean isPaused = true;
	private Brain brain;
	private Vision vision;
	private Robot r;
	
	public Body() {
		vision = new Vision();
		brain = new Brain(vision);
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		if (!isInterrupted() && isRunning) {
			if (!isPaused) {
				
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Point nextMove = brain.DecideAMove();
				r.mouseMove(nextMove.x, nextMove.y);
			}
		}
	}
}
