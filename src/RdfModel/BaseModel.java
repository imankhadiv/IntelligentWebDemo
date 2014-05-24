package RdfModel;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class BaseModel {
	
	/**
	 * This method is implemented for getting the model from rdf file inside servlet
	 * @param fileName
	 * @return
	 */
	public Model getModelFromFile(String fileName) {
		InputStream in = FileManager.get().open(fileName);
		Model model = ModelFactory.createDefaultModel();
		model.read(in, null);
		model.write(System.out);
		return model;
	}
	/**
	 * This method is implemented for saving the model in rdf file inside servlet
	 * @param fileName
	 * @param model
	 */
	public void saveModel(String fileName,Model model) {
		
		FileWriter out = null;
		try {
			out = new FileWriter(fileName);
			model.write(out);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(out!=null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}

}
