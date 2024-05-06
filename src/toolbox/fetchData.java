package toolbox;

public class fetchData {

	float[] rectvertices = {
			-0.5f, 0.5f, 0f,
			-0.5f, -0.5f, 0f,
			0.5f, -0.5f , 0f,
			0.5f, 0.5f, 0f
	};
	int[] rectindices = {
			0, 1, 3,
			3, 1, 2
	};
	float[] recttextureCoords = {
			0,0,
			0,1,
			1,1,
			1,0
	};
	
	float[] cube_vertices = {			
			-0.5f,0.5f,-0.5f,	
			-0.5f,-0.5f,-0.5f,	
			0.5f,-0.5f,-0.5f,	
			0.5f,0.5f,-0.5f,		
			
			-0.5f,0.5f,0.5f,	
			-0.5f,-0.5f,0.5f,	
			0.5f,-0.5f,0.5f,	
			0.5f,0.5f,0.5f,
			
			0.5f,0.5f,-0.5f,	
			0.5f,-0.5f,-0.5f,	
			0.5f,-0.5f,0.5f,	
			0.5f,0.5f,0.5f,
			
			-0.5f,0.5f,-0.5f,	
			-0.5f,-0.5f,-0.5f,	
			-0.5f,-0.5f,0.5f,	
			-0.5f,0.5f,0.5f,
			
			-0.5f,0.5f,0.5f,
			-0.5f,0.5f,-0.5f,
			0.5f,0.5f,-0.5f,
			0.5f,0.5f,0.5f,
			
			-0.5f,-0.5f,0.5f,
			-0.5f,-0.5f,-0.5f,
			0.5f,-0.5f,-0.5f,
			0.5f,-0.5f,0.5f
			
	};
	
float[] cube_textureCoords = {
			
			0,0,
			0,1,
			1,1,
			1,0,			
			0,0,
			0,1,
			1,1,
			1,0,			
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0

			
	};
	
	int[] cube_indices = {
			0,1,3,	
			3,1,2,	
			4,5,7,
			7,5,6,
			8,9,11,
			11,9,10,
			12,13,15,
			15,13,14,	
			16,17,19,
			19,17,18,
			20,21,23,
			23,21,22

	};
	
	
	public float[] getCube_textureCoords() {
		return cube_textureCoords;
	}

	public int[] getCube_indices() {
		return cube_indices;
	}

	public fetchData() {}

	public float[] getRectvertices() {
		return rectvertices;
	}

	public int[] getRectindices() {
		return rectindices;
	}

	public float[] getRecttextureCoords() {
		return recttextureCoords;
	}

	public float[] getCube_vertices() {
		return cube_vertices;
	}
	
	
}
