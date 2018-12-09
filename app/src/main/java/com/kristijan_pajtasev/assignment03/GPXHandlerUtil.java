package com.kristijan_pajtasev.assignment03;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GPXHandlerUtil {

    static public void createFile(ArrayList<LocationPoint> points, String fileName, Context context) {
        String filename = "myfile.gpx";
        String fileContents = "Hello world!";
        FileOutputStream outputStream;
        StringBuilder buffer = new StringBuilder();

        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><gpx xmlns=\"http://www.topografix.com/GPX/1/1\" creator=\"MapSource 6.15.5\" version=\"1.1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"  xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\"><trk>\n";
        String name = "<name>Assignment3 Activity</name><trkseg>\n";

        String segments = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");


        String footer = "</trkseg></trk></gpx>";

        try {

            buffer.append(header);
            buffer.append(name);
            buffer.append(segments);
            for (LocationPoint point: points) {
                buffer.append("<trkpt lat=\"")
                        .append(point.getLatitude())
                        .append("\" lon=\"")
                        .append(point.getLongitude())
                        .append("\"><time>")
                        .append(df.format(new Date(point.getTime())))
                        .append("</time></trkpt>\n");
            }
            buffer.append(footer);

            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(buffer.toString().getBytes());
            outputStream.close();
        } catch (IOException e) {
            Log.e("generateGfx", "Error Writting Path",e);
        }
    }

    static public ArrayList<LocationPoint> readFile(String fileName) {
        // TODO read xml
        return new ArrayList<>();
    }
}
