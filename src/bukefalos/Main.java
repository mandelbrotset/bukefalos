package bukefalos;

import java.util.logging.LogManager;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class Main {
	public static void main(String args[]) {
		Body body = new Body();
		Hook hook = new Hook(body);
		LogManager.getLogManager().reset();
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		GlobalScreen.getInstance().addNativeKeyListener(hook);
	}
}
