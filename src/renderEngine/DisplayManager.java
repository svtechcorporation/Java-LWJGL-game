package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	private static final int WIDTH = 1080;
	private static final int HEIGHT = 620;
	private static final int FPS_CAP = 120;
	private static long time;
	private static int frame;
	private static long lastFrameTime;
	private static float delta;
	
	
	public static void createDisplay() {
		ContextAttribs attribs = new ContextAttribs(3,2)
				.withForwardCompatible(true)
				.withProfileCore(true);
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Opengl 3 Game");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		time = System.currentTimeMillis();
		frame = 0;
		lastFrameTime = getCurrentTime();
		delta = 0;
	}
	
	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
		frame++;
		if(System.currentTimeMillis() > (time+1000)) {
			Display.setTitle("Opengl 3 Game | FPS: "+frame);
			time = System.currentTimeMillis();
			frame = 0;
		}
		
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	public static void closeDisplay() {
		Display.destroy();
	}
	
	private static long getCurrentTime() {
		return Sys.getTime() * 1000 /Sys.getTimerResolution();
	}
}
