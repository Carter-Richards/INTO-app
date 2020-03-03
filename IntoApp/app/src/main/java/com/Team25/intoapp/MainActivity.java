package com.Team25.intoapp;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private ListView listPages;
    private String currentQuery;

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
        populateMenuData();


        final LayoutInflater factory = LayoutInflater.from(this);
        final View myView = factory.inflate(R.layout.activity_homepage, null);
        final ConstraintLayout menu = (ConstraintLayout) findViewById(R.id.activity_main_menuContainer);
        final LinearLayout main_layout = (LinearLayout) findViewById(R.id.activity_main_otherContainer);
        main_layout.addView(myView);
        listPages = findViewById(R.id.information_manager_layout_listView);
        Log.d(TAG, "onCreate: Finised");

        View.OnClickListener menuButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: MenuButtonCLicked!");
            View myView = factory.inflate(R.layout.menu_layout, null);
            menu.addView(myView);
            menu.bringToFront();
            }
        };

        View.OnClickListener menuItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: MenuItemClicked!");


            }
         };


    /** Here are the linking variables allowing us to program the controls for the menu and the panic buttons at the top of the page
     *  Unfortunatly there is pretty much duplicate code in programming all the buttons which sucks but due to naming conventions
     */

        LinkedList<ConstraintLayout> menuData = populateMenuData();
        for(ConstraintLayout menuItem: menuData){
            menuItem.setOnClickListener(menuItemListener);
        }

        ImageButton menuButton = (ImageButton) findViewById(R.id.activity_main_menuContainer_menuButton);


        menuButton.setOnClickListener(menuButtonListener);
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
        ConstraintLayout nclEssentialsButton = findViewById(R.id.menu_layout_nclEssesentials);
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
     *  We will be using this background method to obtain data from the the database if and when queries need to be made.
     *  The DoInBackground method is called first and is then followed by on post execute.
     *  This will give us flexibility when it comes to our menu.
     *  However will have to be programmed correctly.
     */
    private class QueryDataBase extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ExtractInfromationObjects extractInfo = new ExtractInfromationObjects();
            //extractInfo.extract(s);
            //Information_ListView_Adapter layoutAdapter = new Information_ListView_Adapter(MainActivity.this,R.layout.information_layout,extractInfo.getResults());
            //listPages.setAdapter(layoutAdapter);
        }

        @Override
        protected String doInBackground(String... strings) {
            String quereyResults = quereyDataBase(strings[0]);
            return quereyResults;
        }
        private String quereyDataBase(String query){
            String actualQuery;
            switch (query) {
                case "5":
                    break;
                case "thththt":
                    break;

            }
            return null;
        }
    }

}