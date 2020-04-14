<?php

    include('EventAdapter.php');

    //Attempt to create table adapter, if DB connection error occurs, set status to 500 
    //Internal Server Error and terminate early
    try{ $adapter = new EventAdapter(); }
    catch(PDOException $error){
        http_response_code(500);
        return($error);
    }
    
    $mode = filter_input(INPUT_GET, 'mode', FILTER_SANITIZE_STRING);
    $date = filter_input(INPUT_GET, 'date', FILTER_SANITIZE_STRING);
    $category = filter_input(INPUT_GET, 'category', FILTER_SANITIZE_STRING);
    //seperate query values out

    if(isset($mode)){
        if($mode=='after'){
            if(isset($category)){
                $result = $adapter->getEventsAfterDateInCategory($date, $category);
                http_response_code(200);
                echo json_encode($result);
            }
            else{
                $result = $adapter->getEventsAfterDate($date);
                http_response_code(200);
                echo json_encode($result);
            }
        }
        if($mode=='before'){
            if(isset($category)){
                $result = $adapter->getEventsBeforeDateInCategory($date, $category);
                http_response_code(200);
                echo json_encode($result);
            }
            else{
                $result = $adapter->getEventsBeforeDate($date);
                http_response_code(200);
                echo json_encode($result);
            }
        }
        if($mode=='on'){
            //needs implementation in EventAdapter
            http_response_code(501);
            echo("Not Yet Implemented");
        }
    }
    //check if queries are set
    //if no queries, select all future from today
    //if mode = all, select all
    //if mode = before, select all before date param
    //if more = after, select all after date param
    
?>