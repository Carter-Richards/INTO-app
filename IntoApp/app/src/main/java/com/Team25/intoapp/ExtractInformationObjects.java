package com.Team25.intoapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import android.util.JsonReader;

/** This class reads in a JSON array through an inputStream and then parses the information and creates a LinkedList of InformationObjects containing the information.
 *  The getter method can then be called to retrieve this LinkedList.
 */

public class ExtractInformationObjects {
    private LinkedList<InformationObject> informationObjects;

    public ExtractInformationObjects(LinkedList<InformationObject> informationObjects) {
        this.informationObjects = informationObjects;
    }

    public void extractInformation(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            readMessagesArray(reader, null);
        } finally {
            reader.close();
        }
    }

    private void readMessagesArray(JsonReader reader, LinkedList<InformationObject> informationObjects) throws IOException {
        LinkedList<InformationObject> infoObjs = informationObjects;
        reader.beginArray();
        while (reader.hasNext()) {
            infoObjs = readMessage(reader, infoObjs);
        }
        reader.endArray();
        this.informationObjects = infoObjs;
    }

    private LinkedList<InformationObject> readMessage(JsonReader reader, LinkedList<InformationObject> informationObjects) throws IOException {
        InformationObject infoObj = new InformationObject(null, null, null, null, null);

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Title")) {
                infoObj.setTitle(reader.nextString());
            } else if (name.equals("Description")) {
                infoObj.setDescription(reader.nextString());
            } else if (name.equals("Date")) {
                infoObj.setDate(reader.nextString());
            } else if (name.equals("Location")) {
                infoObj.setLocation(reader.nextString());
            }else if (name.equals("ImgPath")) {
                infoObj.setImgPath(reader.nextString());
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        informationObjects.add(infoObj);
        return informationObjects;
    }

    public LinkedList<InformationObject> getInformationObjects() {
        return informationObjects;
    }
}
