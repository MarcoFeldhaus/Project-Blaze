<?php

$dbhost="localhost";
$dbuser="root";
$dbpass="";
$dbname="marker";


//connection to the database
$dbhandle =  new mysqli($dbhost, $dbuser, $dbpass, $dbname) 
  or die("Unable to connect to MySQL");
//echo "Connected to MySQL<br>";

        $sql = "SELECT id, marker_title, snippet, CONCAT_WS(',',m_lat, m_lng) AS latlng FROM gmarker";
				//echo "Check1<br>";
        $stmt = $dbhandle->query($sql);
		//echo "Check2<br>";
        if($stmt->num_rows){
		while ($row = $stmt->fetch_assoc())
{//echo "Check3<br>";
		$g_marker[] = array(
                            'id'   			=> $row['id'],
							'marker_title' 	=> $row['marker_title'],
							'snippet' 	=> $row['snippet'],
                            'latlng'        => explode(',', $row['latlng'])
                            
                            
                            );
}//end while
//echo "Check4<br>";

        }

    $g_json = json_encode($g_marker);
//echo "Check5<br>";
    $dbhandle->close();
//echo "Check6<br><br>";


    echo $g_json;

?>