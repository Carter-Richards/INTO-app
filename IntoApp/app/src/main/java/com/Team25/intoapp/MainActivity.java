package com.Team25.intoapp;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private String currentLayout = "homepage";
    private LinearLayout main_layout;
    private LinearLayout menu;
    private LayoutInflater factory;
    private boolean menuPresent = false;
    private double[] pointerPosX = {-26.00109,-25.00109,-24.00109,-23.00109};
    private double[] pointerPosY = {140.01316,140.01316,140.01316,140.01316};
    private String[] names = {"one","two","three","four"};
    private View viewToAdd;

    /** So the on create method is called when the application is initially launched. I have configured the manifest file to launch this method first.
     * The Log.d tags are just for us to see in the logcat editor to have a better understanding on what is happening under the hood.
     * @param savedInstanceState - Is a bundle that is used to restore activities if they are killed for some reason (app closed/rotated).
     * The first line sets the view of the app to be the activity_main xml file.
     * As we have custom view layouts we next need a Layout inflater to enable us to load our other views onto our main view.
     * We load the inital homepage layout into the sub layout in activity_main to give the user something to interact with.
     *
     * After initalising the main layout, this method then setups up the 4 main onclick listerners for the layout them being menu, saftey, home and background.
     * From here
     */
@Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        factory = LayoutInflater.from(this);

        final View homePageView = factory.inflate(R.layout.activity_homepage, null);

        menu = findViewById(R.id.activity_main_menu);
        main_layout = findViewById(R.id.activity_main_otherContainer);
        main_layout.addView(homePageView);
        Log.d(TAG, "onCreate: Finised");


        /** This listener sets up the apps icon to always take the user back to the home page of the app.
        */
        View.OnClickListener homePageListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLayout != "homepage"){
                    main_layout.removeAllViews();
                    main_layout.addView(homePageView);
                }
            }
        };
        /** The saftey button is similar to the page above however the content for this page isn't stored locally so it needs to access the async task in order
         *  to get the relevant information from the server.
         *  So the view to be loaded into the main_layout (Basically a container for all the other layouts in the app) is inflated and then manipulated.
         *  After this the async task (queryDataBase) populates the listView with the relevant data and passes it back to be loaded onto the main layout.
        */
        View.OnClickListener safetyButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLayout != "safety"){
                    currentLayout = "safety";
                    viewToAdd = null;
                    View safetyPage = factory.inflate(R.layout.informationmanager_activity,null);
                    viewToAdd = safetyPage;
                    TextView textView = safetyPage.findViewById(R.id.information_manager_layout_title);
                    textView.setText("safety");
                    QueryDataBase query = new QueryDataBase();
                    query.execute("safety");
                    safetyPage = viewToAdd;
                    main_layout.removeAllViews();
                    main_layout.addView(safetyPage);
                }
            }
        };
        /** The menu as from the design had to be openable custom and have all the buttons work.
        *   As we couldn't put an onclick listener within an onclick listener we setup a call method to set up on the onclick listeners of the menu's buttons.
        */
        View.OnClickListener menuButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: MenuButtonCLicked!");
                onClickMenuSetup();
            }
        };

        /** The menu had to be able to be closed otherwise it would obstruct the users use of the app.
        *   This simple method checks the global "menuPresent" flag and removes the menu if it is present based on it.
        */
        View.OnClickListener closeMenuListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuPresent){
                    menu.removeAllViews();
                    menuPresent = false;
                }
            }
        };



    /** Below are were we link objects in the design layouts to objects in the code where that can be programmed.
     *  After linking them we place the relevant onclick listeners on all the views and widgets that require them for the app to work.
     */
        ImageButton menuButton = findViewById(R.id.activity_main_menuContainer_menuButton);
        ImageView homeIcon = findViewById(R.id.activity_main_menuContainer_logo);
        ImageButton safetybutton = findViewById(R.id.activity_main_menuContainer_emergencyButton);

        safetybutton.setOnClickListener(safetyButtonListener);
        homeIcon.setOnClickListener(homePageListener);
        main_layout.setOnClickListener(closeMenuListener);
        menuButton.setOnClickListener(menuButtonListener);
    }

    /** Rather than have the menu always present instead the menu is created/deleted at runtime when the user requires it.
     *  This saves precious memory for the device while it is running as the views in the menu are quite memory expensive.
     *  First the menu view is inflated and added to the relevant layout in the Activity_main visible view.
     *  And the menu present variable flag is set to true so the menu can be removed.
     *
     */

    private void onClickMenuSetup(){
        View menuView = factory.inflate(R.layout.menu_layout,null);
        menu.addView(menuView);
        menuPresent = true;

        View.OnClickListener menuItemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: MenuItemClicked! "+v.getResources().getResourceName(v.getId()));
                String buttonName = extractMenuButtonString(v.getResources().getResourceName(v.getId()));
                if (buttonName.equals("website")){
                    Log.d(TAG, "onClick: website attempt launch");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.intostudy.com/en-gb/universities/newcastle-university"));
                    startActivity(browserIntent);
                }
                else if(buttonName.equals("maps")){
                    Log.d(TAG, "onClick: Maps being processed");
                    startMap();
                }
                else if((!(buttonName == currentLayout))){
                    View myView = null;
                    switch (buttonName) {
                        case "homepage":
                            Log.d(TAG, "buildQuery: homepage");
                            myView = factory.inflate(R.layout.activity_homepage,null);
                            break;
                        case "aboutUs":
                            Log.d(TAG, "quereyDataBase: about us");
                            main_layout.removeAllViews();
                            myView = factory.inflate(R.layout.activity_aboutus,null);
                            break;
                        case "notifications":
                            myView = factory.inflate(R.layout.activity_notifications,null);
                            viewToAdd = myView;
                            queryDataBase(buttonName);
                            myView = viewToAdd;
                            break;
                        case "thingsToDo": case "placesToEat": case "nclEssentials": case "publicTransport": case "safety": case "societies":
                            myView = factory.inflate(R.layout.informationmanager_activity,null);
                            TextView name = (TextView) myView.findViewById(R.id.information_manager_layout_title);
                            name.setText(buttonName);
                            viewToAdd = myView;
                            queryDataBase(buttonName);
                            myView = viewToAdd;
                            break;
                        default:
                            break;
                    }
                    if (myView != null){
                        main_layout.removeAllViews();
                        main_layout.addView(myView);
                        currentLayout = buttonName;
                    }
                }
                menu.removeAllViews();
                menuPresent = false;
            }
        };
        LinkedList<ConstraintLayout> menuData = populateMenuData();
        for(ConstraintLayout menuItem: menuData){
            menuItem.setOnClickListener(menuItemListener);
        }
        ImageView homePageImage = findViewById(R.id.menu_layout_homepage);
        homePageImage.setOnClickListener(menuItemListener);

    }

    /** The only times the app calls out and starts external activity's is for maps and the into website.
     *  To isolate this behaviour we created the start map method as the maps activity requires some specific behavouir.
     *  So if the maps query has been successful in getting the positions of the pointers for the map they will be added to the mapsIntent(Intent)
     *      If not the maps will a launch without any custom pointers and just position itself on the into building.
     */
    private void startMap(){
        queryDataBase("maps");
        Intent mapsIntent = new Intent(this,MapsActivity.class);
        if(pointerPosX != null && pointerPosY != null && names != null){
            Log.e(TAG, "startMap: values passed to maps properly");
            mapsIntent.putExtra("POINTER_POSX",pointerPosX);
            mapsIntent.putExtra("POINTER_POSY",pointerPosY);
            mapsIntent.putExtra("POINTER_NAMES",names);
        }
        startActivity(mapsIntent);
    }

    /** As a fair few methods need to call the async method, it made logical sense to have these two lines in there own method to reduce duplicate code
     * @param queryName - the name of thw query to be completed on the server.
     */
    private void queryDataBase(String queryName){
        QueryDataBase queryDataBase = new QueryDataBase();
        queryDataBase.execute(queryName);
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
        ConstraintLayout safetyButton = findViewById(R.id.menu_layout_safety);
        ConstraintLayout mapsButton = findViewById(R.id.menu_layout_maps);
        ConstraintLayout societiesButton = findViewById(R.id.menu_layout_societies);
        ConstraintLayout websiteButton = findViewById((R.id.menu_layout_website));

        menuData.add(aboutUsButton);
        menuData.add(notificationButton);
        menuData.add(thingsToDoButton);
        menuData.add(placesToEatButton);
        menuData.add(nclEssentialsButton);
        menuData.add(publicTransportButton);
        menuData.add(safetyButton);
        menuData.add(mapsButton);
        menuData.add(societiesButton);
        menuData.add(websiteButton);
        return menuData;
    }
    /** Below is a private class which uses threads to perform background activity for the user.
     *  We will be using this background method to obtain data from the the database if and when queries need to be made.
     *  The DoInBackground method is called first and is then followed by on post execute.
     *  This will give us flexibility when it comes to our menu.
     *  However will have to be programmed correctly.
     */
    private class QueryDataBase extends AsyncTask<String,Void,InformationManager> {

        /** On post execute is used to populate are ListViews with the data obtained from the background activity.
         *  Based on the query performed depends on the listView used and the apater used for it's representation.
         * @param informationManager - A group defined Class to hold the information returned by a query.
         */
        @Override
        protected void onPostExecute(InformationManager informationManager) {
            super.onPostExecute(informationManager);
            if(informationManager.getName().equals("notifications")){
                ListView listPages = viewToAdd.findViewById((R.id.notifications_listView));
                Information_Layout_Adapter layout_adapter = new Information_Layout_Adapter(MainActivity.this,R.layout.notifcation_layout,informationManager.getInformationObjects());
                listPages.setAdapter(layout_adapter);
            }else if(!(informationManager.getName().equals("maps"))){
                ListView listPages = viewToAdd.findViewById(R.id.information_manager_layout_listView);
                Information_ListView_Adapter layoutAdapter = new Information_ListView_Adapter(MainActivity.this, R.layout.information_layout, informationManager.getBitmapInformationObjects());
                listPages.setAdapter(layoutAdapter);
            }

            Log.d(TAG, "onPostExecute: Finished!");
        }

        /** As we only got the file paths of the images, we needed a way while the app was still async to try and transform them into images.
         *  Our solution was this method, it reads the LinkedList of InformationObjects and transforms them into BitmapInformaionObjects within the InformationManger
         * @param informationManager - The data to be translated and adapated
         */

        private void bitmapCreation(InformationManager informationManager){
            LinkedList<InformationObject> informationObjects = informationManager.getInformationObjects();
            Bitmap bitmap;
            for(InformationObject informationObject: informationObjects){
                bitmap = null;
                try{
                    InputStream src = new java.net.URL(informationObject.getImgPath()).openStream();
                    bitmap = BitmapFactory.decodeStream(src);
                }catch(Exception e){
                    Log.e(TAG, "getView: error loading image ",e );
                }
                informationManager.getBitmapInformationObjects().add(new BitmapInformationObject(informationObject.getTitle(),informationObject.getDescription(),informationObject.getLocation(),bitmap,informationObject.getDate()));
            }
        }

        /** The google maps ideally require pointers to determine to the user were all of the places are local to there position.
         *  In order to do this, we needed to pass the values into the map within it's oncreate bundles but we can only pass primitive array types so we need to
         *      create those values to pass. The code below does this using the global variables defined at the top of the clasee.
         * @param rawInfo - The information returned from the database query
         */
        private void mapPointerCoordsSetup(InformationManager rawInfo){
            LinkedList<InformationObject> informationObjects = rawInfo.getInformationObjects();
            pointerPosX = new double[informationObjects.size()];
            pointerPosY = new double[informationObjects.size()];
            names = new String[informationObjects.size()];
            for(int i = 0; i<informationObjects.size();i++){
                String location = informationObjects.get(i).getLocation();
                String[] coords = location.split(" ");
                pointerPosX[i] = Double.parseDouble(coords[0]);
                pointerPosY[i] = Double.parseDouble(coords[1]);
                names[i] = informationObjects.get(i).getTitle();
            }
        }

        /** This method oreates the query and then creates an information manager object with the sorted information required for the display of the information of the query.
         * @param strings - A string or list of strings that has been passed into the inner class for processing
         * @return - The required sorted data for display.
         * The method also preps for the destinction between notifications and maps to do the bitmap creation if it is needed and prep the map pointers if they
         * are needed.
         */

        @Override
        protected InformationManager doInBackground(String... strings) {
           //String query = buildQuery(strings[0]);
           //InformationManager returnInfo = doQuery(query);

            //TEST VALUES
            InformationManager returnInfo = InitialiseData.InitData(strings[0]);
            //
            Log.d(TAG, "doInBackground: is this working?");
            if(!(strings[0].equals("notifications"))){
                bitmapCreation(returnInfo);
            }else if(strings[0].equals("maps")){
                mapPointerCoordsSetup(returnInfo);
            }
            return returnInfo;
        }


        /** This method takes the string of the button pressed and translates it into a query to query the back end data base with.
         *  The switch case statements prepares the return string for the query and then the correct query is returned ready for the parsing and processing of
         *      the information from the data base.
         * @param buttonID - the identifier used to determine which query should be used.
         * @return - The string of the query to be executed.
         */
        private String buildQuery(String buttonID) {
            String actualQuery = "";
            //actualQuery is the URL used to connect to the database
            switch (buttonID) {
                case "notifications":
                    Log.d(TAG, "queryDataBase: case notifications");
                    LocalDate date = java.time.LocalDate.now();
                    String dString = date.toString();
                    LocalTime time = java.time.LocalTime.now();
                    String tString = time.toString().substring(0,8);
                    actualQuery = "localhost/API/src/Event.php?mode=after&date=" + dString + " " + tString + "&category=notif";
                    break;
                case "thingsToDo":
                    Log.d(TAG, "queryDataBase: case thingsToDo");
                    actualQuery = "localhost/API/src/Event.php?category=things";
                    break;
                case "placesToEat":
                    Log.d(TAG, "queryDataBase: case placesToEat");
                    actualQuery = "localhost/API/src/Event.php?category=eat";
                    break;
                case "nclEssentials":
                    Log.d(TAG, "queryDataBase: case nclEssentials");
                    actualQuery = "localhost/API/src/Event.php?category=ncl";
                    break;
                case "publicTransport":
                    Log.d(TAG, "queryDataBase: case publicTransport");
                    actualQuery = "localhost/API/src/Event.php?category=transport";
                    break;
                case "safety":
                    Log.d(TAG, "queryDataBase: case safety");
                    actualQuery = "localhost/API/src/Event.php?category=safety";
                    break;
                case "maps":
                    Log.d(TAG, "queryDataBase: case maps");
                    actualQuery = "localhost/API/src/Event.php?category=maps";
                    break;
                case "societies":
                    Log.d(TAG, "queryDataBase: case societies");
                    actualQuery = "localhost/API/src/Event.php?category=soc";
                    break;
                default:
                    break;
            }
            return actualQuery;
        }

        /** This method connects to the backend of the data base and then parses the returned json.
         *  From this the InformationManager is then populated with the InformationObjects which are used to automate the population of the listviews.
         * @param query - The full query for the backend server
         * @return - The populated InformationManger
         */
        private InformationManager doQuery(String query) {
            Log.d(TAG, "doQuery: Start");
            InformationManager queryData = new InformationManager("queryData");
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
