package iiju0z;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
public class JSONWrite {

	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		
		try{
			Object obj=parser.parse(new FileReader("orarendiiju0z.json"));
			JSONObject jsonObject = (JSONObject) obj;
			System.out.println(jsonObject.toJSONString());
			
			try(FileWriter fileWriter = new FileWriter("orarendiiju0z.json")){
				fileWriter.write(jsonObject.toJSONString());
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
