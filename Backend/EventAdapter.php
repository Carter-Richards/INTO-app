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

    public function getAllEvents(){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location) FROM events');
        $query->execute();
        return($query->fetchAll());
    }

    public function getEventsAfterDate($date){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location) FROM events 
                            WHERE Date > :date');
        $query->bindParam(':date', $date);
        $query->execute();
        return($query->fetchAll());
    }

    public function getEventsBeforeDate($date){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location) FROM events 
                            WHERE Date < :date');
        $query->bindparam(':date', $date);
        $query->execute();
        return($query->fetchAll());
    }

    public function getEventsOnDate($date){
        $query = $this->dbConn->prepare(  'SELECT Title, Description, Date, ST_AsText(Location) FROM events 
                            WHERE Date = :date');
        $query->bindparam(':date', $date);
        $query->execute();
        return($query->fetchAll());
    }
}

?>