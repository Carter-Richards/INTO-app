package com.Team25.intoapp;
import java.util.*;

/** A basic class to hold lists of information objects created by the database querys with basic getters and a setter to obtain the information.
 */
public class InformationManager {
    private LinkedList<InformationObject> informationObjects;
    private LinkedList<BitmapInformationObject> bitmapInformationObject;
    private String name;
    private LinkedList<String> metaData = new LinkedList<>();

    public InformationManager(String name){
        informationObjects = new LinkedList<InformationObject>();
        bitmapInformationObject = new LinkedList<BitmapInformationObject>();
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<BitmapInformationObject> getBitmapInformationObjects() {
        return bitmapInformationObject;
    }

    public LinkedList<InformationObject> getInformationObjects() {
        return informationObjects;
    }

    public String getName() {
        return name;
    }

    public void setInformationObjects(LinkedList<InformationObject> informationObjects) {
        this.informationObjects = informationObjects;
    }
}
