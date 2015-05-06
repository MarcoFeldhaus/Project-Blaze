<?php

$dbhost="localhost";
$dbuser="root";
$dbpass="";
$dbname="updateuserloc";



  $dbhandle =  new mysqli($dbhost, $dbuser, $dbpass, $dbname) 
  or die("Unable to connect to MySQL");

  /* check the sql statement for errors and if errors report them */
    $query_count = ("SELECT max(id) FROM userloc");
	$stmt = $dbhandle->query($query_count);
	$row = mysqli_fetch_array($stmt);
	$data = $row[0];
	if($data){
	echo $data."</br>";
	}
	$dbhandle->close();
	
	
	$data++;
	echo $data."</br>";
	
	
	?>