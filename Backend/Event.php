<?php

    include('EventAdapter.php');

    $adapter = new EventAdapter();
    
    $mode = filter_input(INPUT_GET, 'mode', FILTER_SANITIZE_STRING);
    $date = filter_input(INPUT_GET, 'date', FILTER_SANITIZE_STRING);
    //seperate query values out

    if(isset($mode)){
        if($mode=='after'){
            $result = $adapter->getEventsAfterDate($date);
            print_r(json_encode($result, JSON_FORCE_OBJECT));
        }
        if($mode=='before'){
            $result = $adapter->getEventsBeforeDate($date);
            print_r(json_encode($result, JSON_FORCE_OBJECT));
        }
        if($mode=='on'){
            //needs implementation in EventAdapter
        }
    }
    //check if queries are set
    //if no queries, select all future from today
    //if mode = all, select all
    //if mode = before, select all before date param
    //if more = after, select all after date param

    
?>