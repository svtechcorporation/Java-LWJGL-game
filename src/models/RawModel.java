package models;

public class RawModel {

	private int vaoID;
	private int vertexCount;
	private int vboID;
	
	
	public RawModel(int vaoID,  int vboID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.vboID = vboID;
	}

	public int getVboID() {
		return vboID;
	}
	
	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}	
	
}
