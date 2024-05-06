package models;

import renderEngine.Loader;
import renderEngine.OBJLoader;
import textures.ModelTexture;

public class Model3D {
	
	
	public static TexturedModel loadModel(Loader loader, String objFileName, String textureFileName){
		TexturedModel model = new TexturedModel(OBJLoader.loadObjModel(objFileName, loader), 
				new ModelTexture(loader.loadTexture(textureFileName)));
		model.getTexture().setReflectivity(1f);
		model.getTexture().setShineDamper(15f);
		return model;
	}
	
	public static TexturedModel loadModel(Loader loader, String objFileName, String textureFileName, boolean shinning){
		TexturedModel model = new TexturedModel(OBJLoader.loadObjModel(objFileName, loader), 
				new ModelTexture(loader.loadTexture(textureFileName)));
		return model;
	}

	
	
	
	
}
