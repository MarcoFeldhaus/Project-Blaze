


<?php

$dbhost="localhost";
$dbuser="root";
$dbpass="root";
$dbname="android_api";



$dbhandle =  new mysqli($dbhost, $dbuser, $dbpass, $dbname) 
  or die("Unable to connect to MySQL");

  /* check the sql statement for errors and if errors report them
    $query_count = ("SELECT max(id) FROM userloc");
	$stmt1 = $dbhandle->query($query_count);
	$row = mysqli_fetch_array($stmt1);
	$count = $row[0];
	if($count){
	echo $count."</br>";
	}
	
	
	
	$count++;
	echo $count."</br>";
*/
	$name = $_POST['name'];
	$u_lat = $_POST['u_lat'];
	$u_lng = $_POST['u_lng'];
	
	$created_date = date("Y-m-d H:i:s" ,strtotime('+8 hour'));
	//$sql = "INSERT INTO users VALUES ($count,'$u_lat','$u_lng','$created_date')";
	//$sql = "INSERT INTO users (u_lat, u_lng, u_lastUpdated) VALUES ('$u_lat','$u_lng','$created_date') WHERE name ='$name'";
	//$sql = "UPDATE users SET u_lat='$u_lat',u_lng='$u_lng',u_lastUpdated='$created_date' WHERE 'uid' ='$name'";
	$sql = "UPDATE users SET `u_lat`='$u_lat',`u_lng`='$u_lng',`u_lastUpdated`='$created_date' WHERE `name`='$name'";
	$stmt2 = $dbhandle->query($sql);

	$row = mysqli_fetch_array($stmt2);
	$data = $row[0];
	if($data){
	echo $data;
	}
	$dbhandle->close();

?>