package renderEngine;

import java.io.*;
import java.util.*;

import org.lwjgl.util.vector.*;

import models.RawModel;
import models.Vertex;


public class OBJLoader {
	
	private static final String RES_LOC = "res/";

	public static RawModel loadObjModel(String objFileName, Loader loader) {
		FileReader isr = null;
		File objFile = new File(RES_LOC + objFileName + ".obj");
		try {
			isr = new FileReader(objFile);
		} catch (FileNotFoundException e) {
			System.err.println("File not found in res; don't use any extention");
		}
		BufferedReader reader = new BufferedReader(isr);
		String line;
		List<Vertex> vertices = new ArrayList<Vertex>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		try {
			while (true) {
				line = reader.readLine();
				if (line.startsWith("v ")) {
					String[] currentLine = line.split(" ");
					Vector3f vertex = new Vector3f((float) Float.valueOf(currentLine[1]),
							(float) Float.valueOf(currentLine[2]),
							(float) Float.valueOf(currentLine[3]));
					Vertex newVertex = new Vertex(vertices.size(), vertex);
					vertices.add(newVertex);

				} else if (line.startsWith("vt ")) {
					String[] currentLine = line.split(" ");
					Vector2f texture = new Vector2f((float) Float.valueOf(currentLine[1]),
							(float) Float.valueOf(currentLine[2]));
					textures.add(texture);
				} else if (line.startsWith("vn ")) {
					String[] currentLine = line.split(" ");
					Vector3f normal = new Vector3f((float) Float.valueOf(currentLine[1]),
							(float) Float.valueOf(currentLine[2]),
							(float) Float.valueOf(currentLine[3]));
					normals.add(normal);
				} else if (line.startsWith("f ")) {
					break;
				}
			}
			while (line != null && line.startsWith("f ")) {
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				processVertex(vertex1, vertices, indices);
				processVertex(vertex2, vertices, indices);
				processVertex(vertex3, vertices, indices);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Error reading the file");
		}
		removeUnusedVertices(vertices);
		float[] verticesArray = new float[vertices.size() * 3];
		float[] texturesArray = new float[vertices.size() * 2];
		float[] normalsArray = new float[vertices.size() * 3];
		float furthest = convertDataToArrays(vertices, textures, normals, verticesArray,
				texturesArray, normalsArray);
		int[] indicesArray = convertIndicesListToArray(indices);
		RawModel data = loader.loadToVAO(verticesArray, texturesArray, normalsArray, indicesArray);
//		verticesArray, texturesArray, normalsArray, indicesArray);
		return data;
	}

	private static void processVertex(String[] vertex, List<Vertex> vertices, List<Integer> indices) {
		int index = Integer.parseInt(vertex[0]) - 1;
		Vertex currentVertex = vertices.get(index);
		int textureIndex = Integer.parseInt(vertex[1]) - 1;
		int normalIndex = Integer.parseInt(vertex[2]) - 1;
		if (!currentVertex.isSet()) {
			currentVertex.setTextureIndex(textureIndex);
			currentVertex.setNormalIndex(normalIndex);
			indices.add(index);
		} else {
			dealWithAlreadyProcessedVertex(currentVertex, textureIndex, normalIndex, indices,
					vertices);
		}
	}

	private static int[] convertIndicesListToArray(List<Integer> indices) {
		int[] indicesArray = new int[indices.size()];
		for (int i = 0; i < indicesArray.length; i++) {
			indicesArray[i] = indices.get(i);
		}
		return indicesArray;
	}

	private static float convertDataToArrays(List<Vertex> vertices, List<Vector2f> textures,
			List<Vector3f> normals, float[] verticesArray, float[] texturesArray,
			float[] normalsArray) {
		float furthestPoint = 0;
		for (int i = 0; i < vertices.size(); i++) {
			Vertex currentVertex = vertices.get(i);
			if (currentVertex.getLength() > furthestPoint) {
				furthestPoint = currentVertex.getLength();
			}
			Vector3f position = currentVertex.getPosition();
			Vector2f textureCoord = textures.get(currentVertex.getTextureIndex());
			Vector3f normalVector = normals.get(currentVertex.getNormalIndex());
			verticesArray[i * 3] = position.x;
			verticesArray[i * 3 + 1] = position.y;
			verticesArray[i * 3 + 2] = position.z;
			texturesArray[i * 2] = textureCoord.x;
			texturesArray[i * 2 + 1] = 1 - textureCoord.y;
			normalsArray[i * 3] = normalVector.x;
			normalsArray[i * 3 + 1] = normalVector.y;
			normalsArray[i * 3 + 2] = normalVector.z;
		}
		return furthestPoint;
	}

	private static void dealWithAlreadyProcessedVertex(Vertex previousVertex, int newTextureIndex,
			int newNormalIndex, List<Integer> indices, List<Vertex> vertices) {
		if (previousVertex.hasSameTextureAndNormal(newTextureIndex, newNormalIndex)) {
			indices.add(previousVertex.getIndex());
		} else {
			Vertex anotherVertex = previousVertex.getDuplicateVertex();
			if (anotherVertex != null) {
				dealWithAlreadyProcessedVertex(anotherVertex, newTextureIndex, newNormalIndex,
						indices, vertices);
			} else {
				Vertex duplicateVertex = new Vertex(vertices.size(), previousVertex.getPosition());
				duplicateVertex.setTextureIndex(newTextureIndex);
				duplicateVertex.setNormalIndex(newNormalIndex);
				previousVertex.setDuplicateVertex(duplicateVertex);
				vertices.add(duplicateVertex);
				indices.add(duplicateVertex.getIndex());
			}

		}
	}
	
	private static void removeUnusedVertices(List<Vertex> vertices){
		for(Vertex vertex:vertices){
			if(!vertex.isSet()){
				vertex.setTextureIndex(0);
				vertex.setNormalIndex(0);
			}
		}
	}

}


//public class OBJLoader {
//
//	
//	public static RawModel loadObjModel(String fileName, Loader loader) {
//		FileReader fr = null;
//		try {
//			fr = new FileReader(new File("res/" + fileName + ".obj"));
//		} catch (FileNotFoundException e) {
//			System.err.println("Couldn't load file!");
//			e.printStackTrace();
//		}
//		BufferedReader reader = new BufferedReader(fr);
//		String line;
//		List<Vector3f> vertices = new ArrayList<Vector3f>();
//		List<Vector2f> textures = new ArrayList<Vector2f>();
//		List<Vector3f> normals = new ArrayList<Vector3f>();
//		List<Integer> indices = new ArrayList<Integer>();
//		float[] verticesArray = null;
//		float[] normalsArray = null;
//		float[] textureArray = null;
//		int[] indicesArray = null;
//		try {
//
//			while (true) {
//				line = reader.readLine();
//				String[] currentLine = line.split(" ");
//				if (line.startsWith("v ")) {
//					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]),
//							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
//					vertices.add(vertex);
//				} else if (line.startsWith("vt ")) {
//					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]),
//							Float.parseFloat(currentLine[2]));
//					textures.add(texture);
//				} else if (line.startsWith("vn ")) {
//					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),
//							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
//					normals.add(normal);
//				} else if (line.startsWith("f ")) {
//					textureArray = new float[vertices.size() * 2];
//					normalsArray = new float[vertices.size() * 3];
//					break;
//				}
//			}
//
//			while (line != null) {
//				if (!line.startsWith("f ")) {
//					line = reader.readLine();
//					continue;
//				}
//				String[] currentLine = line.split(" ");
//				
////				for(String vextexN:currentLine) {
////					String[] vertex10 = vextexN.split("/");
////					processVertex(vertex10,indices,textures,normals,textureArray,normalsArray);
////				}
//				
//				String[] vertex1 = currentLine[1].split("/");
//				String[] vertex2 = currentLine[2].split("/");
//				String[] vertex3 = currentLine[3].split("/");
//				
//				processVertex(vertex1,indices,textures,normals,textureArray,normalsArray);
//				processVertex(vertex2,indices,textures,normals,textureArray,normalsArray);
//				processVertex(vertex3,indices,textures,normals,textureArray,normalsArray);
//				line = reader.readLine();
//			}
//			reader.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		verticesArray = new float[vertices.size()*3];
//		indicesArray = new int[indices.size()];
//		
//		int vertexPointer = 0;
//		for(Vector3f vertex:vertices){
//			verticesArray[vertexPointer++] = vertex.x;
//			verticesArray[vertexPointer++] = vertex.y;
//			verticesArray[vertexPointer++] = vertex.z;
//		}
//		
//		for(int i=0;i<indices.size();i++){
//			indicesArray[i] = indices.get(i);
//		}
//		return loader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray);
//
//	}
//
//	private static void processVertex(String[] vertexData, List<Integer> indices,
//			List<Vector2f> textures, List<Vector3f> normals, float[] textureArray,
//			float[] normalsArray) {
//		try {
//		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
//		indices.add(currentVertexPointer);
//		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1])-1);
//		textureArray[currentVertexPointer*2] = currentTex.x;
//		textureArray[currentVertexPointer*2+1] = 1 - currentTex.y;
//		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
//		normalsArray[currentVertexPointer*3] = currentNorm.x;
//		normalsArray[currentVertexPointer*3+1] = currentNorm.y;
//		normalsArray[currentVertexPointer*3+2] = currentNorm.z;	
//		}catch(Exception e) {}
//	}
//	
//}
