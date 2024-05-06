package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position = new Vector3f(0, 0, 0);
	
	private float yaw = 0;
	private float roll;
	
	private Player player;
	private float pitch = 10;
	private float distanceFromPlayer = 5;
	private float angleAroundPlayer = 0;
	private float rotationY = 0;
	
	
	public Camera(Player player) {
		this.player = player;
	}

	public float calculateHorizontal() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	public float calculateVertical() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}

	
	
	public void move() {
		getInputs();
		float vertical = calculateVertical();
		float horizontal = calculateHorizontal();
		float dx = (float) (horizontal * Math.sin(Math.toRadians(player.getRotY()  + angleAroundPlayer)));
		float dz = (float) (horizontal * Math.cos(Math.toRadians(player.getRotY()  + angleAroundPlayer)));
		position.z =  (player.getPosition().z - dz);
		position.x = (player.getPosition().x - dx);
		position.y = player.getPosition().y + vertical + 0.5f;
		yaw = 180 - (player.getRotY() + angleAroundPlayer);
		
		pitch += rotationY;
		if(pitch < 1) {
			pitch = 1;
		} 
		
		if(pitch > 85){
			pitch = 85;
		}
	}

	
	private float increasefactor = 0.5f;
	
	private void getInputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			distanceFromPlayer -= (increasefactor*2);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			distanceFromPlayer += (increasefactor*2);
		}
		if(distanceFromPlayer < 1) {
			distanceFromPlayer = 1;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			rotationY += increasefactor;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			rotationY -= increasefactor;
		} else {
			rotationY = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			angleAroundPlayer -= (increasefactor * 4);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F)) {
			angleAroundPlayer += (increasefactor * 4);
		}
	}
	
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	
	
}
