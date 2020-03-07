package com.Team25.intoapp;
import java.util.*;

public class InformationManager {
    private LinkedList<InformationObject> informationObjects;
    private String name;
    private LinkedList<String> metaData = new LinkedList<>();

    public void InformationManager(String name){
        informationObjects = new LinkedList<InformationObject>();
        this.name = name;
    }

<<<<<<< Updated upstream
    public void loadAllObjects(){
		
    }

=======
>>>>>>> Stashed changes
    public InformationManager selectInformationManager(){
        return new InformationManager();
    }

    public LinkedList<InformationObject> getInformationObjects() {
        return informationObjects;
    }

    public void setInformationObjects(LinkedList<InformationObject> informationObjects) {
        this.informationObjects = informationObjects;
    }
}
