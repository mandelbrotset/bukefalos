package bukefalos;

<<<<<<< HEAD
public class Main {
	
=======
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
>>>>>>> bb2a8a049c3627ca64f38580b01cb9e33b7d2cf3
}
