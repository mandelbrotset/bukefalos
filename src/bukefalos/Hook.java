package bukefalos;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Hook implements NativeKeyListener {
	private Body body;
	
	public Hook(Body body) {
		super();
		this.body = body;
	}
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		System.out.println(arg0.getKeyCode());
		if (arg0.getKeyCode() == 31) {
			System.out.println("Starting");
			body.init();
			body.start();
		}
		if (arg0.getKeyCode() == 25) {
			System.out.println("Pause/unpause");
			body.togglePause();
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		
	}
	
}
