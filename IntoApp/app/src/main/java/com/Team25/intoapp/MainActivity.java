package com.Team25.intoapp;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private String currentQuery;
    private LinearLayout main_layout;
    private LinearLayout menu;
    private LayoutInflater factory;
    private boolean menuPresent = false;

    /** So the on create method is called when the application is initially launched. I have configured the manifest file to launch this method first.
     * The Log.d tags are just for us to see in the logcat editor to have a better understanding on what is happening under the hood.
     * @param savedInstanceState - Is a bundle that is used to restore activities if they are killed for some reason.
     * The first line sets the view of the app to be the activity_main xml file.
     * As we have custom view layouts we next need a Layout inflater to enable us to load our other views onto our main view.
     * We load the inital homepage layout into the sub layout in activity_main to give the user something to interact with.
     * We then link our global listView variable to the object in the information layout xml file.
     */
@Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        factory = LayoutInflater.from(this);

        final View myView = factory.inflate(R.layout.activity_homepage, null);

        menu = findViewById(R.id.activity_main_menu);
        main_layout = findViewById(R.id.activity_main_otherContainer);
        main_layout.addView(myView);
        Log.d(TAG, "onCreate: Finised");

        View.OnClickListener menuButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: MenuButtonCLicked!");
                View menuView = factory.inflate(R.layout.menu_layout,null);
                menu.addView(menuView);
                menuPresent = true;
                onClickMenuSetup();
            }
        };

        View.OnClickListener closeMenuListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuPresent){
                    menu.removeAllViews();
                    menuPresent = false;
                }
            }
        };




    /** Here are the linking variables allowing us to program the controls for the menu and the panic buttons at the top of the page
     *  Unfortunatly there is pretty much duplicate code in programming all the buttons which sucks but due to naming conventions
     */


        ImageButton menuButton = (ImageButton) findViewById(R.id.activity_main_menuContainer_menuButton);

        main_layout.setOnClickListener(closeMenuListener);
        menuButton.setOnClickListener(menuButtonListener);
    }

    /** Rather than have the menu always present instead the menu is deleted and destroyed at runtime when the user requires it.
     *  This saves precious memory for the device while it is running.
     *  First of all the listener is definied and then attatched to all of the menus buttons.
     */

    private void onClickMenuSetup(){
        View.OnClickListener menuItemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: MenuItemClicked! "+v.getResources().getResourceName(v.getId()));
                QueryDataBase query = new QueryDataBase();
                query.doInBackground(extractMenuButtonString(v.getResources().getResourceName(v.getId())));
                menu.removeAllViews();
                menuPresent = false;
            }
        };

        LinkedList<ConstraintLayout> menuData = populateMenuData();
        for(ConstraintLayout menuItem: menuData){
            menuItem.setOnClickListener(menuItemListener);
        }
    }

    /** A simple method to extract the unique name based id of the buttons in the menu.
     * @param id - The full Id of the button.
     * @return - The unique identifier of the button.
     */
    private String extractMenuButtonString(String id){
        int _counter = 0;
        String queryValue = "";
        boolean flag = false;
        for(int i = 0;i<id.length();i++){
            if (flag==true){
                queryValue = queryValue + id.charAt(i);
            }else{
                if (id.charAt(i) == '_'){
                    _counter = _counter+1;
                }
            }if(_counter == 2){
                flag = true;
            }
        }
        Log.d(TAG, "extractQueryString: value = "+queryValue);
        return queryValue;
    }

    /** A method used to create a linkedlist of the menu so that if the menu needs to be changed at run time a premade list can be quickly generated or stored.
     *  This method is used so that the on create method isn't over incombored.
     * @return The list of the menu constraints to link on click listeners too and manipulate too.
     */
    private LinkedList<ConstraintLayout> populateMenuData(){
        LinkedList<ConstraintLayout> menuData = new LinkedList<>();

        ConstraintLayout aboutUsButton = findViewById(R.id.menu_layout_aboutUs);
        ConstraintLayout notificationButton = findViewById(R.id.menu_layout_notifications);
        ConstraintLayout thingsToDoButton = findViewById(R.id.menu_layout_thingsToDo);
        ConstraintLayout placesToEatButton = findViewById(R.id.menu_layout_placesToEat);
        ConstraintLayout nclEssentialsButton = findViewById(R.id.menu_layout_nclEssentials);
        ConstraintLayout publicTransportButton = findViewById(R.id.menu_layout_publicTransport);
        ConstraintLayout safteyButton = findViewById(R.id.menu_layout_safety);
        ConstraintLayout mapsButton = findViewById(R.id.menu_layout_maps);
        ConstraintLayout societysButton = findViewById(R.id.menu_layout_societys);

        menuData.add(aboutUsButton);
        menuData.add(notificationButton);
        menuData.add(thingsToDoButton);
        menuData.add(placesToEatButton);
        menuData.add(nclEssentialsButton);
        menuData.add(publicTransportButton);
        menuData.add(safteyButton);
        menuData.add(mapsButton);
        menuData.add(societysButton);
        return menuData;
    }
    /** Below is a private class which uses threads to perform background activity for the user.
     *  We will be using this background method to obtain data from the database if and when queries need to be made.
     *  The DoInBackground method is called first and is then followed by on post execute.
     *  This will give us flexibility when it comes to our menu.
     *  However will have to be programmed correctly.
     */
    private class QueryDataBase extends AsyncTask<String,Void,InformationManager> {
        @Override
        protected void onPostExecute(InformationManager s) {
            super.onPostExecute(s);
            View dataList = factory.inflate(R.layout.informationmanager_activity, null);
            ListView listPages = findViewById(R.id.information_manager_layout_listView);
            Information_ListView_Adapter layoutAdapter = new Information_ListView_Adapter(MainActivity.this, R.layout.information_layout, s.getInformationObjects());
            listPages.setAdapter(layoutAdapter);
            main_layout.removeAllViews();
            main_layout.addView(dataList);
            Log.d(TAG, "onPostExecute: Finished!");
        }

        /** This method creates the query and then creates an information manager object with the sorted information required for the display of the information of the query.
         * @param strings - A string or list of strings that has been passed into the inner class for processing
         * @return - The required sorted data for display
         */
        @Override
        protected InformationManager doInBackground(String... strings) {
            String query = buildQuery(strings[0]);
            return doQuery(query);
        }

        /** This switch class is called to build the query for the database based on the id of the button that has been clicked by the user.
         *  This will allow us to reuse the same query database method for all the users interactions greatly reducing the amount of duplicate code in our code.
         * @param buttonID - The button that has been clicked id. Which is a unique identifier for the widget.
         * @return - A string which will be the complete and specified query for the expected and wanted action.
         */
        private String buildQuery(String buttonID) {
            String actualQuery = "";
            switch (buttonID) {
                case "aboutUs":
                    Log.d(TAG, "queryDataBase: case 1");
                    //actualQuery is the URL used to connect to the database
                    actualQuery = "";
                    break;
                case "notifications":
                    Log.d(TAG, "queryDataBase: case 2");
                    actualQuery = "";
                    break;
                case "thingsToDo":
                    Log.d(TAG, "queryDataBase: case 3");
                    actualQuery = "";
                    break;
                case "placesToEat":
                    Log.d(TAG, "queryDataBase: case 4");
                    actualQuery = "";
                    break;
                case "nclEssentials":
                    Log.d(TAG, "queryDataBase: case 5");
                    actualQuery = "";
                    break;
                case "publicTransport":
                    Log.d(TAG, "queryDataBase: case 6");
                    actualQuery = "";
                    break;
                case "safety":
                    Log.d(TAG, "queryDataBase: case 7");
                    actualQuery = "";
                    break;
                case "maps":
                    Log.d(TAG, "queryDataBase: case 8");
                    actualQuery = "";
                    break;
                case "societies":
                    Log.d(TAG, "queryDataBase: case 9");
                    actualQuery = "";
                    break;
                default:
                    break;
            }
            return actualQuery;
        }

        private InformationManager doQuery(String query) {
            Log.d(TAG, "doQuery: Start");
            InformationManager queryData = new InformationManager();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                URL url = new URL(query);
                URLConnection urlConnection = url.openConnection();
                LinkedList<InformationObject> infoObjs = new LinkedList<InformationObject>();
                ExtractInformationObjects queryInfo = new ExtractInformationObjects(infoObjs);
                queryInfo.extractInformation(urlConnection.getInputStream());
                infoObjs = queryInfo.getInformationObjects();
                queryData.setInformationObjects(infoObjs);
            } catch (Exception e) {
                Log.d(TAG, "doQuery: Exception Thrown in the query " + e.getMessage());
            }
            Log.d(TAG, "doQuery: Finished");
            return queryData;
        }
    }
}
