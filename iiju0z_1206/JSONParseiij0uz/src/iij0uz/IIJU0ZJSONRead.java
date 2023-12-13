package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IIJU0ZJSONRead {
    public static void main(String[] args) {
        try {
            /*Filepath specified*/
            String filePath = "iiju0z_orarend.xml";
            String outputFilePath ="iiju0z_orarend.json";
            String xmlInput = readXml(filePath);
            /*Create XmlMapper*/
            XmlMapper xmlMapper = new XmlMapper();
            /*Read XML to JsonNode*/
            JsonNode jsonNode = xmlMapper.readTree(xmlInput);
            /*Create ObjectMapper and ObjectWriter with indentation*/
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
            /*Convert JsonNode to formatted JSON string*/
            String jsonString = objectWriter.writeValueAsString(jsonNode);
            /*Printy printy*/
            System.out.println(jsonString);

            /*For fun, this one writes it out onto a file. I think that's efficient*/
            writeJsonToFile(jsonString, outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readXml(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.readString(path);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void writeJsonToFile(String jsonContent, String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.write(path, jsonContent.getBytes());
            System.out.println("JSON content written to file: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
