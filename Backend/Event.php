<?php

/*
 * Event.php
 *
 * Web Interface
 * 
 * Interprets the client call parameters, processes them and then calls the appropriate
 * table adapter function to query the database. Sets HTTP response code and then returns
 * result of query (assuming request was valid)
 *
 * Author: Ethan Lewis
 */

    include('EventAdapter.php');

    //Attempt to create table adapter, if DB connection error occurs, set status to 500,
    //(Internal Server Error) and terminate early
    try{ $adapter = new EventAdapter(); }
    catch(PDOException $error){
        http_response_code(500);
        return($error);
    }
    
    //seperate out and sanitize query values
    $mode = filter_input(INPUT_GET, 'mode', FILTER_SANITIZE_STRING);
    $date = filter_input(INPUT_GET, 'date', FILTER_SANITIZE_STRING);
    $category = filter_input(INPUT_GET, 'category', FILTER_SANITIZE_STRING);
    
    //Conditions decide which adapter function to call based on which paramaters are set
    if(isset($mode)){
        if($mode=='after'){
            if(isset($category)){
                $result = $adapter->getEventsAfterDateInCategory($date, $category);
                http_response_code(200); //OK
                echo json_encode($result);
            }
            else{
                $result = $adapter->getEventsAfterDate($date);
                http_response_code(200); //OK
                echo json_encode($result);
            }
        }
        if($mode=='before'){
            if(isset($category)){
                $result = $adapter->getEventsBeforeDateInCategory($date, $category);
                http_response_code(200); //OK
                echo json_encode($result);
            }
            else{
                $result = $adapter->getEventsBeforeDate($date);
                http_response_code(200); //OK
                echo json_encode($result);
            }
        }
        if($mode=='on'){
            //needs implementation in EventAdapter
            http_response_code(501); //Not Yet Implemented
            echo("Not Yet Implemented");
        }
    }
    else{
        if(isset($category)){
            $result = $adapter->getEventsInCategory($category);
            http_response_code(200); //OK
            echo json_encode($result);
        }
        else{
            http_response_code(400); //Bad request
            echo ('A category or mode & paramater is required');
        }
    }
    //if no queries, select only via category, if no category also, do nothing
    //if mode = all, select all
    //if mode = before, select all before date param
    //if more = after, select all after date param
    
?>