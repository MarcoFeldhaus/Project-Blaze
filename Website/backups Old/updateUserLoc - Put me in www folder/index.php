


<?php

$dbhost="localhost";
$dbuser="root";
$dbpass="";
$dbname="updateuserloc";



$dbhandle =  new mysqli($dbhost, $dbuser, $dbpass, $dbname) 
  or die("Unable to connect to MySQL");

  /* check the sql statement for errors and if errors report them */
    $query_count = ("SELECT max(id) FROM userloc");
	$stmt1 = $dbhandle->query($query_count);
	$row = mysqli_fetch_array($stmt1);
	$count = $row[0];
	if($count){
	echo $count."</br>";
	}
	
	
	
	$count++;
	echo $count."</br>";


	$u_lat = $_POST['u_lat'];
	$u_lng = $_POST['u_lng'];
	$created_date = date("Y-m-d H:i:s" ,strtotime('+8 hour'));
	$sql = "INSERT INTO userloc VALUES ($count,'$u_lat','$u_lng','$created_date')";

	$stmt2 = $dbhandle->query($sql);

	$row = mysqli_fetch_array($stmt2);
	$data = $row[0];
	if($data){
	echo $data;
	}
	$dbhandle->close();

?>