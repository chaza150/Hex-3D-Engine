package control;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import math.Vector3D;
import model.Mesh;

public class ModelReader {

	/**Extract mesh from simple .obj file
	 * 
	 * @param fileName filepath of object file
	 * @return extracted mesh
	 */
	public Mesh importMesh(String fileName) {
		Mesh mesh = new Mesh();
		
		String line = null;
		
		try {
			//Read file in lines
			FileReader fileReader = new FileReader(fileName);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			
			while((line = bufferedReader.readLine()) != null) {
				//Match with vertex declaration
				if(line.matches("^v\\s+.*")) {
					String[] strings = line.split(" ");
					float f1 = Float.parseFloat(strings[1]);
					float f2 = Float.parseFloat(strings[2]);
					float f3 = Float.parseFloat(strings[3]);
					mesh.addVertex(new Vector3D(f1,f2,f3));
				}
				//Match with face declarations (Only up to 4 vertex polygon triangulation currently)
				if(line.matches("^f\\s+.*")) {
					String[] strings = line.split(" ");
					strings[0] = null;
					if(strings.length == 4) {
						int v1 = Integer.parseInt(strings[1].split("//")[0]);
						int v2 = Integer.parseInt(strings[2].split("//")[0]);
						int v3 = Integer.parseInt(strings[3].split("//")[0]);
						mesh.addTriangle(v1-1, v2-1, v3-1);
					} else if (strings.length == 5) {
						int v1 = Integer.parseInt(strings[1].split("//")[0]);
						int v2 = Integer.parseInt(strings[2].split("//")[0]);
						int v3 = Integer.parseInt(strings[3].split("//")[0]);
						int v4 = Integer.parseInt(strings[4].split("//")[0]);
						mesh.addTriangle(v1-1, v2-1, v3-1);
						mesh.addTriangle(v1-1, v4-1, v3-1);
					}
				}
			}
			
			//Close the file reader
			bufferedReader.close();
		} catch (FileNotFoundException fe) {
			System.err.println("Cannot find file: " + fileName);
		} catch (IOException ioe) {
			System.err.println("File read error in file: " + fileName);
		}
		
		return mesh;
	}

}
