<?php

/*
 * EventUpdater.php
 *
 * Adds new events to database
 * 
 * Uses the output of eventWebScraper.jar to create new events and add
 * them to the database
 *
 * Author: Ethan Lewis
 */

include 'DatabaseConnection.php';

function timeParse($time){
    $ptr = 0;

    //Extract day
    $day = substr($time, 0, 2); 
    if (is_numeric($day[1])){   
        $time = substr($time, 5, strlen($time) - 5);
        $ptr = 4;
    }
    else{
        $day = substr('0'.$day, 0, 2);
        $time = substr($time, 4, strlen($time) - 4);
        $ptr = 3;
    }

    //Any month can be identified by the first 3 letters
    $month = substr($time, 0, 3);
    //Extract month, based on check first 3 characters of month, then skip as needed
    switch($month){
        case "Jan":
            $month = "01";
            $ptr = 8;
            $time = substr($time, $ptr, strlen($time) - $ptr);
        break;
        case "Feb":
            $month = "02";
            $ptr = 9;
            $time = substr($time, $ptr, strlen($time) - $ptr);
        break;
        case "Mar":
            $month = "03";
            $ptr = 6;
            $time = substr($time, $ptr, strlen($time) - $ptr);
        break;
        case "Apr":
            $month = "04";
            $ptr = 6;
            $time = substr($time, $ptr, strlen($time) - $ptr);
        break;
        case "May":
            $month = "05";
            $ptr = 4;
            $time = substr($time, $ptr, strlen($time) - $ptr);
        break;
        case "Jun":
            $month = "06";
            $ptr = 5;
            $time = substr($time, $ptr, strlen($time) - $ptr);
        break;
        case "Jul":
            $month = "07";
            $ptr = 5;
            $time = substr($time, $ptr, strlen($time) - $ptr);
        break;
        case "Aug":
            $month = "08";
            $ptr = 7;
            $time = substr($time, $ptr, strlen($time) - $ptr);
        break;
        case "Sep":
            $month = "09";
            $ptr = 10;
            $time = substr($time, $ptr, strlen($time) - $ptr);
        break;
        case "Oct":
            $month = "10";
            $ptr = 8;
            $time = substr($time, $ptr, strlen($time) - $ptr);
        break;
        case "Nov":
            $month = "11";
            $ptr = 9;
            $time = substr($time, $ptr, strlen($time) - $ptr);
        break;
        case "Dec":
            $month = "12";
            $ptr = 9;
            $time = substr($time, $ptr, strlen($time) - $ptr);
        break;
    }

    //Generate year from system time (data not provided)
    $year = date('Y');

    //Etxract hour, including if data says midnight or midday
    if($time[0] == 'm'){
        if($time[4] = 'd'){
            $hour = '12';
        }
        else{
            $hour = '00';
        }
    }
    else{
        if(is_numeric($time[1])){
            $hour = ($time[0].$time[1]) + 0;
        }
        $hour = $time[0];
    }

    //Extract minutes, including if data says nothing or :xx
    if($time[1] == ':'){
        $minute = substr($time, 2, 2);
        $time = substr($time, 4, strlen($time) - 4);
    }
    else{
        $minute = '00';
        $time = substr($time, 1, strlen($time) - 1);
    }

    //Adjust hour from 12hr to 24hr time
    if($time[0] == 'p'){
        $hour = $hour + 12;
    }

    //Return format is 'YYYY-MM-DD HH:MM:SS'
    return($year.'-'.$month.'-'.$day.' '.$hour.':'.$minute.':'.'00');
}

//Connect to database (can throw PDO)
$connectObj = new DatabaseConnection();
$connectObj->connect();
$dbConn = $connectObj->connection;

//Load in JSON data
$jsonString = file_get_contents('events.json');
$data = json_decode($jsonString, false, 1024, JSON_INVALID_UTF8_IGNORE);
$data = json_decode(json_encode($data), true);

//Cycle through JSON data, construct SQL INSERT Queries, execute them
foreach($data as $arr){
    foreach($arr as $arr2){
        $description = $arr2['Description']; 
        $time = timeParse($arr2['Time']);  //Convert date into proper format for DB
        $img = $arr2['Img Src URL'];
        $name = $arr2['Name'];
        $location = 'point(0 0)'; //At the moment the webscraper location data is not usable
    }

    //Use prepared statement to carry out insert query on the DB
    $query = $dbConn->prepare(  'INSERT INTO events (Title, Description, Date, Location, ImgPath)
                                 VALUES (?, ?, ?, ST_GeomFromText(?, 1), ?)');
    $query->bindparam(1, $name);
    $query->bindparam(2, $description);
    $query->bindparam(3, $time);
    $query->bindparam(4, $location);
    $query->bindparam(5, $img);
    $query->execute();
}
 ?>