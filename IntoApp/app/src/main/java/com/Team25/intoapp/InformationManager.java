package com.Team25.intoapp;
import java.util.*;

public class InformationManager {
    private LinkedList<InformationObject> informationObjects;
    private String name;
    private LinkedList<String> metaData = new LinkedList<>();

    public InformationManager(String name){
        informationObjects = new LinkedList<InformationObject>();
        this.name = name;
    }

    public LinkedList<InformationObject> getInformationObjects() {
        return informationObjects;
    }

    public void setInformationObjects(LinkedList<InformationObject> informationObjects) {
        this.informationObjects = informationObjects;
    }
}
