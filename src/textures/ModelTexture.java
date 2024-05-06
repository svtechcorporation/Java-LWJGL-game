package textures;

public class ModelTexture {

	private int textureID;
	private float reflectivity = 0f;
	private float shineDamper = 1f;
	
	private boolean hasTransparency = false;
	private boolean useFakeLighting = false;
	private int numberOfRows = 1;
	
	
	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}


	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}


	public boolean isHasTransparency() {
		return hasTransparency;
	}


	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}


	public ModelTexture(int textureID) {
		this.textureID = textureID;
	}
	

	public float getReflectivity() {
		return reflectivity;
	}


	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}


	public float getShineDamper() {
		return shineDamper;
	}


	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}


	public int getTextureID() {
		return textureID;
	}
	
}
