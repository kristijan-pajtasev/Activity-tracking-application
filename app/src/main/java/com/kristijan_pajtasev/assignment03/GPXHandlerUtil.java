package com.kristijan_pajtasev.assignment03;

import android.content.Context;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class GPXHandlerUtil {

    static public void createFile(ArrayList<LocationPoint> points, String fileName, Context context) {
        String filename = "myfile.xml";
        String fileContents = "Hello world!";
        FileOutputStream outputStream;
// TODO create xml
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static public ArrayList<LocationPoint> readFile(String fileName) {
        // TODO read xml
        return new ArrayList<>();
    }
}
