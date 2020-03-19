package com.Team25.intoapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import org.json.JSONArray;
import android.os.Message;
import android.util.JsonReader;

public class ExtractInformationObjects {
    private LinkedList<InformationObject> informationObjects;

    public ExtractInformationObjects(LinkedList<InformationObject> informationObjects) {
        this.informationObjects = informationObjects;
    }

    public void extractInformation(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            readMessagesArray(reader, informationObjects);
        } finally {
            reader.close();
        }
    }

    public void readMessagesArray(JsonReader reader, LinkedList<InformationObject> informationObjects) throws IOException {

        reader.beginArray();
        while (reader.hasNext()) {
            readMessage(reader, informationObjects);
        }
        reader.endArray();
    }

    public void readMessage(JsonReader reader, LinkedList<InformationObject> informationObjects) throws IOException {
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
    }

    public LinkedList<InformationObject> getInformationObjects() {
        return informationObjects;
    }
}
