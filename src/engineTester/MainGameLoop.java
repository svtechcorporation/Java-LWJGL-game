package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import gui.GuiRenderer;
import gui.GuiTexture;
import models.Model3D;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;


public class MainGameLoop {

	public static final float groundLevel = -0.1f;
	

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		MasterRenderer masterRenderer = new MasterRenderer();
		TerrainTexturePack texturePack = new TerrainTexturePack("grassy2", "mud", "grassFlowers", "path", loader);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightMap");
		
			
		TexturedModel stall = Model3D.loadModel(loader, "stall", "stallTexture");
		float x = 0;
		float z = -25;
		float y = terrain.getHeightOfTerrain(x, z);
		Entity entity = new Entity(stall, new Vector3f(x, y, z), 0, 0,0, 0.5f);  
		masterRenderer.processEntity(entity);
		

				
		TexturedModel tree = Model3D.loadModel(loader, "tree", "tree", true);
		TexturedModel fern = Model3D.loadModel(loader, "fern", "ferncomb", true);
		fern.getTexture().setHasTransparency(true);
		fern.getTexture().setNumberOfRows(2);
		
		
		TexturedModel grass = Model3D.loadModel(loader, "grassModel", "grassTexture", true);
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		
		
		
		
	
		
		Random random = new Random();
		for(int i=0;i<200;i++) {
			x = random.nextFloat() * 200 - 100;
			z = random.nextFloat() * 1000 - 500;
			y = terrain.getHeightOfTerrain(x, z);
			Entity entity2 = new Entity(tree, new Vector3f(x, y, z), 0, 0,0, 1);
			
			x = random.nextFloat() * 200 - 100;
			z = random.nextFloat() * 1000 - 500;
			y = terrain.getHeightOfTerrain(x, z);
			Entity entityF = new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, 0,0, 0.2f);
			
			for(int j=0;j<3;j++) {
				x = random.nextFloat() * 200 - 100;
				z = random.nextFloat() * 1000 - 500;
				y = terrain.getHeightOfTerrain(x, z);
				Entity entityG = new Entity(grass, new Vector3f(x, y, z), 0, 0, 0, 0.5f);
				//masterRenderer.processEntity(entityG);
			}
			
			
			masterRenderer.processEntity(entity2);
			masterRenderer.processEntity(entityF);
		}
		
		
		
		masterRenderer.processTerrain(terrain);
		
		TexturedModel person = new TexturedModel(OBJLoader.loadObjModel("person", loader), 
				new ModelTexture(loader.loadTexture("playerTexture")));
		
		Player player = new Player(person, new Vector3f(0, 0, 0), 0, 180, 0, 0.1f);
		masterRenderer.processEntity(player);
		
		
		Camera camera = new Camera(player);
		Light light = new Light(new Vector3f(0, 1000, 0), new Vector3f(1, 1, 1));
		light.setAmbientLight(0.1f);
		
		
		GuiTexture gui = new GuiTexture(loader.loadTexture("health"), new Vector2f(-0.76f, 0.9f), new Vector2f(0.2f, 0.25f));
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		guis.add(gui);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		//game loop for updating
		while(!Display.isCloseRequested()) {
			player.move(terrain);
			camera.move();
//			entity.increaseRotation(0f, 0.3f, 0f);
			masterRenderer.render(light, camera);
			guiRenderer.render(guis);
			DisplayManager.updateDisplay();
		}
		guiRenderer.cleanUp();
		masterRenderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
