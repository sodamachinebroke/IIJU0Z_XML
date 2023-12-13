package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class IIJU0ZJSONWrite {
    public static void main(String[] args) {
        try{
            String inputJsonFilePath = "iiju0z_orarend.json";
            String outputJsonFilePath = "iiju0z_orarend1.json";

            /*Read JSON content from the input file*/
            String jsonInput = readJsonFromFile(inputJsonFilePath);

            /*Print JSON content to the console*/
            System.out.println("Original JSON content:\n" + jsonInput);

            /* Write JSON content to the output file*/
            writeJsonToFile(jsonInput, outputJsonFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readJsonFromFile(String filePath) throws Exception {
        Path path = Path.of(filePath);
        return Files.readString(path);
    }

    private static void writeJsonToFile(String jsonContent, String filePath) throws Exception {
        Path path = Path.of(filePath);
        String jsonWithoutLineBreaks = jsonContent.replaceAll("\\s+", " ");
        Files.writeString(path, jsonWithoutLineBreaks.trim(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        System.out.println("JSON content written to file: " + filePath);
    }
}
