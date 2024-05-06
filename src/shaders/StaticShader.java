package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	private static int location_transformationMatrix;
	private static int location_projectionMatrix;
	private static int location_viewMatrix;
	private static int location_inverseViewMatrix;
	private static int location_lightPosition;
	private static int location_lightColour;
	private static int location_ambientLight;
	private static int location_reflectivity;
	private static int location_shineDamper;
	private static int location_useFakeLighting; 
	private static int location_skyColour;
	private static int location_numberOfRows;
	private static int location_offset;
	
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocation() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_inverseViewMatrix = super.getUniformLocation("inverseViewMatrix");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColour  = super.getUniformLocation("lightColour");
		location_ambientLight  = super.getUniformLocation("ambientLight");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity  = super.getUniformLocation("reflectivity");
		location_useFakeLighting  = super.getUniformLocation("useFakeLighting");
		location_skyColour = super.getUniformLocation("skyColour");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_offset = super.getUniformLocation("offset");
	}
	
	public void loadNumberOfRows(int rows){
		super.loadFloat(location_numberOfRows, rows);
	}
	
	public void loadOffset(float xOffset, float yOffset){
		super.load2DVector(location_offset, new Vector2f(xOffset, yOffset));
	}
	
	public void loadFakeLighting(boolean useFake) {
		super.loadBoolean(location_useFakeLighting, useFake);
	}
	
	public void loadSkyColour(float r, float g, float b) {
		super.loadVector(location_skyColour, new Vector3f(r, g, b));
	}
	
	public void loadShineVariables(float shineDamper, float reflectivity) {
		super.loadFloat(location_reflectivity, reflectivity);
		super.loadFloat(location_shineDamper, shineDamper);
	}
	
	
	
	public void loadLight(Light light) {
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColour, light.getColour());
		super.loadFloat(location_ambientLight, light.getAmbientLight());
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		Matrix4f inverseViewMatrix = Maths.createInverseViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
		super.loadMatrix(location_inverseViewMatrix, inverseViewMatrix);
	}
	
}
