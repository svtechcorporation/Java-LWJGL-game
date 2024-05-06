package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class TerrainShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/shaders/terrainVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader.txt";
	private static int location_transformationMatrix;
	private static int location_projectionMatrix;
	private static int location_viewMatrix;
	private static int location_inverseViewMatrix;
	private static int location_lightPosition;
	private static int location_lightColour;
	private static int location_ambientLight;
	private static int location_reflectivity;
	private static int location_shineDamper;
	private static int location_skyColour;
	private static int location_backgroundTexture;
	private static int location_rTexture;
	private static int location_gTexture;
	private static int location_bTexture;
	private static int location_blendMap;
	
	
	
	
	public TerrainShader() {
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
		location_skyColour = super.getUniformLocation("skyColour");
		location_backgroundTexture = super.getUniformLocation("backgroundTexture");
		location_rTexture = super.getUniformLocation("rTexture");
		location_gTexture = super.getUniformLocation("gTexture");
		location_bTexture = super.getUniformLocation("bTexture");
		location_blendMap= super.getUniformLocation("blendMap");
	}
	
	public void connectTexture() {
		super.loadInt(location_backgroundTexture, 0);
		super.loadInt(location_rTexture, 1);
		super.loadInt(location_gTexture, 2);
		super.loadInt(location_bTexture, 3);
		super.loadInt(location_blendMap, 4);
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
