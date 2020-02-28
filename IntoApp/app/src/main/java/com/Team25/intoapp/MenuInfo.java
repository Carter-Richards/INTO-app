package com.Team25.intoapp;

public class MenuInfo {
    private String iconSourceName;
    private String buttonName;

    public MenuInfo(String iconSourceName,String buttonName) {
        this.iconSourceName = iconSourceName;
        this.buttonName = buttonName;
    }

    public String getIconSourceName() {
        return iconSourceName;
    }

    public void setIconSourceName(String iconSourceName) {
        this.iconSourceName = iconSourceName;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }
}
