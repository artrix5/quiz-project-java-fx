package com.example.kvizprojekt.util;

import com.example.kvizprojekt.entities.Change;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SerializationUtil {
    public static void serialize(Change object, Path filePath) throws IOException {
        List<Change> changesMadeList = new ArrayList<>();
        if (Files.exists(filePath) && Files.size(filePath) > 0) {
           changesMadeList = deserialize(filePath);
        }
        changesMadeList.add(object);
        try (FileOutputStream fileOut = new FileOutputStream(filePath.toFile());
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(changesMadeList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Change> deserialize(Path filePath) throws IOException {
        List<Change> changesMadeList = new ArrayList<>();
        if (Files.exists(filePath) && Files.size(filePath) > 0) {
            try (FileInputStream fileInputStream = new FileInputStream(filePath.toFile());
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                 changesMadeList = (List<Change>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return changesMadeList;
    }
}
