package com.Team25.intoapp;
import java.util.*;

public class InformationManager {
    LinkedList<InformationObject> informationObjects = new LinkedList<InformationObject>();
    String name;
    LinkedList<String> metaData = new LinkedList<>();

    public void InformationManager(){

    }

    public void backButtonClicked(){

    }

    public void showInformationManager(){

    }

    public void hideInformationManger(){

    }

    public void loadAllObjects(){

    }

    public InformationManager selectInformationManager(){
        return new InformationManager();
    }

    public InformationObject searchKeyword(String keyword){
        return null;
    }

    public LinkedList<InformationObject> getInformationObjects() {
        return informationObjects;
    }

    public void setInformationObjects(LinkedList<InformationObject> informationObjects) {
        this.informationObjects = informationObjects;
    }
}
