package com.converter;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java Main <json-input-file>");
            return;
        }

        String jsonFilePath = args[0];
        File jsonFile = new File(jsonFilePath);

        if (!jsonFile.exists()) {
            System.out.println("File not found: " + jsonFilePath);
            return;
        }

        String jsonString = new String(Files.readAllBytes(jsonFile.toPath()));
        System.out.println("JSON Input Successfully Read:\n" + jsonString);

        // Parse JSON
        JsonElement root = JsonParser.parseString(jsonString);

        if (!root.isJsonArray()) {
            System.out.println("JSON must be an array of objects.");
            return;
        }

        JsonArray jsonArray = root.getAsJsonArray();

        // Collect headers
        Set<String> headers = new LinkedHashSet<>();
        for (JsonElement element : jsonArray) {
            if (element.isJsonObject()) {
                headers.addAll(element.getAsJsonObject().keySet());
            }
        }

        // Write CSV
        FileWriter writer = new FileWriter("data/output.csv");
        writer.write(String.join(",", headers) + "\n");

        for (JsonElement element : jsonArray) {
            JsonObject obj = element.getAsJsonObject();
            List<String> row = new ArrayList<>();
            for (String header : headers) {
                JsonElement value = obj.get(header);
                row.add(value != null ? value.getAsString().replace(",", " ") : "");
            }
            writer.write(String.join(",", row) + "\n");
        }

        writer.close();
        System.out.println("CSV written to data/output.csv");
    }
}
