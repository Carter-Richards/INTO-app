<?php
class DatabaseConnection{

    private $settings = array();
    public $connection;

    public function __construct(){
        $this->settings['Host'] = "";
        $this->settings['Name'] = "";
        $this->settings['Username'] = "";
        $this->settings['Password'] = "";
        $index = 0;

        $fh = fopen('DBConnectionConfig.txt', 'r');
        while($line = fgets($fh)){
            $line = substr($line, 0, -2);
            switch($index){
                case 0:
                    $this->settings['Host'] = $line;
                    break;               
                case 1:
                    $this->settings['Name'] = $line;
                    break;               
                case 2:
                    $this->settings['Username'] = $line;
                    break;                
                case 3:
                    $this->settings['Password'] = $line;
                    break;
                default:
                    break;
            }
            $index++;
        } 
        fclose($fh);
    }

    public function connect(){
        try{
            $this->connection = new PDO("mysql:host=" . $this->settings['Host'] . ";dbname=" . $this->settings['Name'], $this->settings['Username'], $this->settings['Password']);
            //Code from top answer of https://stackoverflow.com/questions/60174/how-can-i-prevent-sql-injection-in-php, this is to allow the connection to use real prepared statements, which guards against SQL injection
            $this->connection->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
            $this->connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            print(nl2br("Connection Succesfull\n"));
        }
         catch (PDOException $PDOe) {
            print("ERROR: Could not create connection to database");
            print($PDOe);
        } 
        
    }

    public function __destruct(){
        $this->connection = null;
    }
}
?>
