package gui;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import models.RawModel;
import renderEngine.Loader;
import toolbox.Maths;

public class GuiRenderer {
	private GuiShader shader;
	private RawModel model;
	
	public GuiRenderer(Loader loader){
		shader = new GuiShader();
		float[] positions = {
				-1, 1, 
				-1, -1, 
				1, 1, 
				1, -1};
		model = loader.loadToVAO(positions);
	}
	
	public void render(List<GuiTexture> guis){
		shader.start();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		for(GuiTexture gui:guis){
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
			Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
			shader.loadTransformationMatrix(matrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, model.getVertexCount());
		}
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	public void cleanUp(){
		shader.cleanUp();
	}
}
