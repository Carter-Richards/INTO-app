<?php

include 'DatabaseConnection.php';

class EventAdapter{

    private $dbConn;
    private $connectObj;

    public function __construct(){
        $this->connectObj = new DatabaseConnection();
        $this->connectObj->connect();
        $this->dbConn = $this->connectObj->connection;
    }

    public function __destruct(){
        $this->connectObj->__destruct();
    }

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

    public function getAllEvents(){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location), ImgPath FROM events');
        $query->execute();
        $result = $this->makeFullImgLink($query->fetchAll(PDO::FETCH_ASSOC));
        return($result);
    }

    public function getEventsAfterDate($date){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location), ImgPath FROM events 
                            WHERE Date > :date');
        $query->bindParam(':date', $date);
        $query->execute();
        $result = $this->makeFullImgLink($query->fetchAll(PDO::FETCH_ASSOC));
        return($result);
    }

    public function getEventsBeforeDate($date){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location), ImgPath FROM events 
                            WHERE Date < :date');
        $query->bindparam(':date', $date);
        $query->execute();
        $result = $this->makeFullImgLink($query->fetchAll(PDO::FETCH_ASSOC));
        return($result);
    }

    public function getEventsOnDate($date){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location), ImgPath FROM events 
                            WHERE Date = :date');
        $query->bindparam(':date', $date);
        $query->execute();
        $result = $this->makeFullImgLink($query->fetchAll(PDO::FETCH_ASSOC));
        return($result);
    }
}

?>