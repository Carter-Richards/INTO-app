<?php

/*
 * EventAdapter.php
 *
 * Interface for Database
 * 
 * Provides a multitude of public functions which perform basic paramaterized SELECT SQL queries
 * on the serverside database.
 *
 * Author: Ethan Lewis
 */


include 'DatabaseConnection.php';

class EventAdapter{

    private $dbConn;
    private $connectObj;

    //Can throw PDOException if error occurs when connecting to DB
    public function __construct(){
        $this->connectObj = new DatabaseConnection();
        $this->connectObj->connect();
        $this->dbConn = $this->connectObj->connection;
        
    }

    public function __destruct(){
        $this->connectObj->__destruct();
    }

    //Takes the image URI and makes it fully qualified, eg TestImg.png -> /Images/TestImg.png
    public function makeFullImgLink($recordSet){
        for($x = 0; $x < count($recordSet); $x++){
            foreach($recordSet[$x] as $field => $value){
                if($field == 'ImgPath'){
                    $recordSet[$x][$field] = "/Images/".$recordSet[$x][$field];
                }
            }
        }
        return($recordSet);
    }

    //Gets all events regardless of date or category
    public function getAllEvents(){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location), ImgPath FROM events');
        $query->execute();
        $result = $this->makeFullImgLink($query->fetchAll(PDO::FETCH_ASSOC));
        return($result);
    }

    //Gets all events after a given date regardless of category
    public function getEventsAfterDate($date){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location), ImgPath FROM events 
                            WHERE Date > :date');
        $query->bindParam(':date', $date);
        $query->execute();
        $result = $this->makeFullImgLink($query->fetchAll(PDO::FETCH_ASSOC));
        return($result);
    }

    //Gets all events before a given date regardless of category
    public function getEventsBeforeDate($date){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location), ImgPath FROM events 
                            WHERE Date < :date');
        $query->bindparam(':date', $date);
        $query->execute();
        $result = $this->makeFullImgLink($query->fetchAll(PDO::FETCH_ASSOC));
        return($result);
    }

    //Gets all events on a given date regardless of category
    public function getEventsOnDate($date){
        $lowerBoundDate = substr($date, 0, 10).' 00:00:00';
        $upperBoundDate = substr($date, 0, 10).' 23:59:59';

        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location), ImgPath FROM events 
                            WHERE Date > :lowerBoundDate AND Date < :upperBoundDate');
        $query->bindparam(':lowerBoundDate', $lowerBoundDate);
        $query->bindparam(':upperBoundDate', $upperBoundDate);
        $query->execute();
        $result = $this->makeFullImgLink($query->fetchAll(PDO::FETCH_ASSOC));
        return($result);
    }

    //Gets all events after a given date in a given category
    public function getEventsAfterDateInCategory($date, $category){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location), ImgPath FROM events 
                            WHERE Date > :date AND Category = :category');
        $query->bindParam(':date', $date);
        $query->bindParam(':category', $category);
        $query->execute();
        $result = $this->makeFullImgLink($query->fetchAll(PDO::FETCH_ASSOC));
        return($result);
    }

    //Gets all events before a given date in a given category
    public function getEventsBeforeDateInCategory($date, $category){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location), ImgPath FROM events 
                            WHERE Date < :date AND Category = :category');
        $query->bindparam(':date', $date);
        $query->bindParam(':category', $category);
        $query->execute();
        $result = $this->makeFullImgLink($query->fetchAll(PDO::FETCH_ASSOC));
        return($result);
    }

    //Gets all events on a given date in a given category
    public function getEventsOnDateInCategory($date, $category){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location), ImgPath FROM events 
                            WHERE Date = :date AND Category = :category');
        $query->bindparam(':date', $date);
        $query->bindParam(':category', $category);
        $query->execute();
        $result = $this->makeFullImgLink($query->fetchAll(PDO::FETCH_ASSOC));
        return($result);
    }

    //Gets all events when on the category is specified
    public function getEventsInCategory($category){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location), ImgPath FROM events 
                            WHERE Category = :category');
        $query->bindParam(':category', $category);
        $query->execute();
        $result = $this->makeFullImgLink($query->fetchAll(PDO::FETCH_ASSOC));
        return($result);
    }

    /*Takes raw output from queries and restructures it into a jagged array that represents the records    */
    public function parseRecords($records){
        //Code adapted from https://www.codeofaninja.com/2017/02/create-simple-rest-api-in-php.html

        $result_arr=array();
        $result_arr["records"]=array();

        for($x = 0; $x < count($records); $x++){
            $record = array(
                "Title"=>$records[$x]["Title"],
                "Description"=>$records[$x]["Description"],
                "Date"=>$records[$x]["Date"],
                "Location"=>$records[$x]["ST_AsText(Location)"],
                "ImgPath"=>$records[$x]["ImgPath"],
            );
            array_push($result_arr["records"], $record);
        }

        return($result_arr);
    }
}

?>