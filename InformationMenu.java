package com.Team25.intoapp;
import android.graphics.drawable.Icon;
import android.widget.Button;

import java.util.*;

public class InformationMenu {
    LinkedList<InformationManager> informationMangers = new LinkedList<>();
    LinkedList<Icon> icons = new LinkedList<>();
    Button back = new Button();

    public void InformationMenu(){

    }

    public void backButtonClicked(){

    }

    public void showMenu(){

    }

    public void hideMenu(){

    }

    public InformationManager selectInformationManager(){
        return new InformationManager();
    }

    public void searchKeyword(String keyword){

    }

    public void loadDataFromDataBase(String query){

    }

    public LinkedList<InformationManager> getInformationMangers() {
        return informationMangers;
    }

    public void setInformationMangers(LinkedList<InformationManager> informationMangers) {
        this.informationMangers = informationMangers;
    }

    public LinkedList<Icon> getIcons() {
        return icons;
    }

    public void setIcons(LinkedList<Icon> icons) {
        this.icons = icons;
    }

    public Button getBack() {
        return back;
    }

    public void setBack(Button back) {
        this.back = back;
    }
}
