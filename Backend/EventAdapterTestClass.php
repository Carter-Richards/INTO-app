<?php

    include('EventAdapter.php');

    $api = new EventAdapter();

    // print('getAllEvents test output<br>');
    // $result = $api->getAllEvents();
    // print_r($result);

//    print('getAllEvents test output<br>');
    $result = $api->getAllEvents();
    $result = $api->parseRecords($result);
    http_response_code(200);
    header("Content-Type: application/json; charset=UTF-8");
    echo json_encode($result);
    
    // print('<br><br>');

    // print('getEventsAfterDate 2015-01-01 12:00:00 test output<br>');
    // $result = $api->getEventsAfterDate('2015-01-01 12:00:00');
    // print_r($result);

    // print('<br><br>');

    // print('getEventsAfterDate 2015-01-01 test output<br>');
    // $result = $api->getEventsAfterDate('2015-01-01');
    // print_r($result);


    
?>