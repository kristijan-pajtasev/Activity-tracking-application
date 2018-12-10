package com.kristijan_pajtasev.assignment03;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class GPXHandlerUtil {

    static public void createFile(ArrayList<LocationPoint> points, String fileName, Context context) {
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

            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(buffer.toString().getBytes());
            outputStream.close();
        } catch (IOException e) {
            Log.e("generateGfx", "Error Writting Path",e);
        }
    }

    public static List<LocationPoint> decodeGPX(Context context, String fileName){
        File file = new File(context.getFilesDir(), fileName);

        List<LocationPoint> list = new ArrayList<LocationPoint>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            FileInputStream fileInputStream = new FileInputStream(file);
            Document document = documentBuilder.parse(fileInputStream);
            Element elementRoot = document.getDocumentElement();

            NodeList nodelist_trkpt = elementRoot.getElementsByTagName("trkpt");

            for(int i = 0; i < nodelist_trkpt.getLength(); i++){

                Node node = nodelist_trkpt.item(i);
                NamedNodeMap attributes = node.getAttributes();

                String newLatitude = attributes.getNamedItem("lat").getTextContent();
                Double newLatitude_double = Double.parseDouble(newLatitude);

                String newLongitude = attributes.getNamedItem("lon").getTextContent();
                Double newLongitude_double = Double.parseDouble(newLongitude);

                list.add(new LocationPoint(newLatitude_double, newLongitude_double, 0, 0));

            }
            fileInputStream.close();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

}
