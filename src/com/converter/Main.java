package com.converter;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main <input_json_file>");
            return;
        }

        String inputFilePath = args[0];

        if (!Files.exists(Paths.get(inputFilePath))) {
            System.out.println("Error: File does not exist - " + inputFilePath);
            return;
        }

        try (FileReader reader = new FileReader(inputFilePath)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            System.out.println("JSON Input Successfully Read:");
            System.out.println(jsonElement.toString());
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
    }
}
