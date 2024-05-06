package gui;

import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;

public class GuiShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/gui/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/gui/fragmentShader.txt";
	private int location_transformationMatrix;
	
	public GuiShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}


	@Override
	protected void getAllUniformLocation() {
		location_transformationMatrix = super.getUniformLocation("tranformationMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");	
	}

}
