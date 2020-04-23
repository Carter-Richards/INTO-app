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

        View.OnClickListener homePageListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLayout != "homepage"){
                    main_layout.removeAllViews();
                    main_layout.addView(myView);
                }
            }
        };

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

        View.OnClickListener menuButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: MenuButtonCLicked!");
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
        ImageButton menuButton = findViewById(R.id.activity_main_menuContainer_menuButton);
        ImageView homeIcon = findViewById(R.id.activity_main_menuContainer_logo);
        ImageButton safetybutton = findViewById(R.id.activity_main_menuContainer_emergencyButton);

        safetybutton.setOnClickListener(safetyButtonListener);
        homeIcon.setOnClickListener(homePageListener);
        main_layout.setOnClickListener(closeMenuListener);
        menuButton.setOnClickListener(menuButtonListener);
    }

    /** Rather than have the menu always present instead the menu is deleted and destroyed at runtime when the user requires it.
     *  This saves precious memory for the device while it is running.
     *  First of all the listener is definied and then attatched to all of the menus buttons.
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
    private void queryDataBase(String buttonName){
        QueryDataBase queryDataBase = new QueryDataBase();
        queryDataBase.execute(buttonName);
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
     *  We will be using this background method to obtain data from the database if and when queries need to be made.
     *  The DoInBackground method is called first and is then followed by on post execute.
     *  This will give us flexibility when it comes to our menu.
     *  However will have to be programmed correctly.
     */
    private class QueryDataBase extends AsyncTask<String,Void,InformationManager> {


        @Override
        protected void onPostExecute(InformationManager informationManager) {
            super.onPostExecute(informationManager);
            if(informationManager.getName().equals("notifications")){
                ListView listPages = viewToAdd.findViewById((R.id.notifications_listView));
                Information_Layout_Adapter layout_adapter = new Information_Layout_Adapter(MainActivity.this,R.layout.notification_layout,informationManager.getInformationObjects());
                listPages.setAdapter(layout_adapter);
            }else{
                ListView listPages = viewToAdd.findViewById(R.id.information_manager_layout_listView);
                Information_ListView_Adapter layoutAdapter = new Information_ListView_Adapter(MainActivity.this, R.layout.information_layout, informationManager.getBitmapInformationObjects());
                listPages.setAdapter(layoutAdapter);
            }

            Log.d(TAG, "onPostExecute: Finished!");
        }

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

        /** This switch class is called to build the query for the database based on the id of the button that has been clicked by the user.
         *  This will allow us to reuse the same query database method for all the users interactions greatly reducing the amount of duplicate code in our code.
         * @param buttonID - The button that has been clicked id. Which is a unique identifier for the widget.
         * @return - A string which will be the complete and specified query for the expected and wanted action.
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
