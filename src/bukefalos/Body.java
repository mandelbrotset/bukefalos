package bukefalos;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.util.Random;

import bukefalos.brain.Brain;
import bukefalos.eyes.Vision;

public class Body extends Thread {
	public static final Point upperLeftCorner = new Point(1,92);
	public static final Point lowerRightCorner = new Point(1363,766);
	
	private boolean isRunning = true;
	private boolean isPaused = false;
	private Brain brain;
	private Vision vision;
	private Robot r;
	private Random rune = new Random();
	
	public Body() {
		vision = new Vision();
		brain = new Brain(vision);
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		vision.init();
	}
	
	public void togglePause() {
		isPaused = !isPaused;
	}
	
	@Override
	public void run() {
		while (!isInterrupted() && isRunning) {
			try {
				sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!isPaused) {
				System.out.println("den är int pausad");
				Point nextMove = brain.DecideAMove();
				System.out.println("move: " + nextMove.toString());
				r.mouseMove((int)(rune.nextFloat() * 100) - 100 + nextMove.x + upperLeftCorner.x, nextMove.y + upperLeftCorner.y + (int)(rune.nextFloat() * 100) - 100);
			} else {
				System.out.println("den är pausad");
			}
		}
		System.out.println("här ska den ju inte vara");
	}
}
