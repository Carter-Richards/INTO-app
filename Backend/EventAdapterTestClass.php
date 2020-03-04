<?php

    include('EventAdapter.php');

    $api = new EventAdapter();

    print('Output Start<br>');
    $result = $api->getAllEvents();
    for($x = 0; $x < count($result); $x++){
        $row = $result[$x];
        for($y = 0; $y < count($row); $y++)
            print($row[$y]."<br>");
    }
    print('Output End');

?>