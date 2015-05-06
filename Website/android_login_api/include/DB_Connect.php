<?php
class DB_Connect {
 
    // constructor
    function __construct() {
         
    }
 
    // destructor
    function __destruct() {
        // $this->close();
    }
 
    // Connecting to database
    public function connect() {
        require_once 'include/Config.php';
        //Include Database connect variables
        // connecting to mysql wamp, mamp
        $con = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD) or die(mysql_error());
        // selecting android api database
        mysql_select_db(DB_DATABASE) or die(mysql_error());
 
        // return database handler
        return $con;
    }
 
    // close active database connection
    public function close() {
        mysql_close();
    }
 
}
 
?>