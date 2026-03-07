<?php
require "inc/fungsi.inc.php";
require "inc/configdb.inc.php";
require "inc/set.inc.php";
if(isset($_GET["cek"])){

	$file=fetch_array($connection1,"SELECT r.id_file,r.nama_file_modif,r.direktori,r.mime_type FROM rme_file_upload r
		WHERE r.id_file='".escape($connection1,$_GET["cek"])."'");

	if($file<>0){
		$nm_file=$file["direktori"].basename($file["nama_file_modif"]);
		//echo $nm_file;
		//-
		if(!file_exists($nm_file)){ // file does not exist
				die("<pre>Dokumen tidak ditemukan");
		}
		else{
			copy($nm_file,$folder_temp.$file["nama_file_modif"]);
			if(!file_exists($folder_temp.$file["nama_file_modif"])){ // file does not exist
				die("<pre>Dokumen tidak dapat ditampilkan");
			}
			else{
				?>
				<iframe src ="inc/pdfjs/web/viewer.html?file=../../../../../file_share/<?php echo $file["nama_file_modif"]?>" width='100%' height='100%' allowfullscreen webkitallowfullscreen></iframe>
				<?php
			}
		}
	}
	else{
		echo "<pre>File tidak ditemukan.";
	}
}
else{
?>
<pre>
<?php
	echo "Unknown.";
}
?>