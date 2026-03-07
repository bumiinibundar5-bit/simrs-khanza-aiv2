<?php
//error cek
ini_set('display_errors', 0);
ini_set('display_startup_errors', 0);
error_reporting(-1);
//
require "inc/configdb.inc.php";
require "inc/fungsi.inc.php";
require "inc/set.inc.php";
require "inc/phpqrcode/qrlib.php"; 
//
$get_json=file_get_contents("php://input");
$arr = json_decode($get_json);
//

/* {
  "metadata": {
    "method": "kirim_berkas"
  },
  "data": {
    "no_rawat": "2024/01/31/0000001",
    "nik": "1704R0040123V005686",
    "password": "0001908100866",
    "jns_dokumen": "RSM1",
    "nama_file":"resume_medis_20202.pdf",
    "petugas":"kode_petugas",
    "status_rawat":"Ranap",
    "nomr":"123456",
    "file_base64": "KPj4Kc3RhcnR4cmVmCjg0NDc5CiUlRU9GCg================"
  }
} */

if (!empty($arr) and $arr->metadata->method=="kirim_berkas"){
	// variable data adalah base64 dari file pdf
	$file=base64_decode($arr->data->file_base64);

	if(preg_match("/^[A-Za-z0-9 ._-]+$/",$arr->data->nama_file)){
		
		// hasilnya adalah berupa binary string $pdf, untuk disimpan:
		$nm_file_lengkap=str_replace(" ","_",$arr->data->nama_file);
		file_put_contents($folder_simpan.$nm_file_lengkap,$file);
		
		//cek format file
		$format_file=mime_content_type($folder_simpan.$nm_file_lengkap);
		//
		if(file_exists($folder_simpan.$nm_file_lengkap) and $format_file=="application/pdf"){
			//generate id_file
			$id_file=date("Ymdhms").bin2hex(random_bytes(3));			
			$isi_qr=$link_qr."rme.php?cek=".$id_file;			
			// create qrcode
			$qrcode_png=$folder_simpan.$id_file.".png";
			QRcode::png($isi_qr, $qrcode_png,  QR_ECLEVEL_L, 3, 1); 
			//
			$target_url="https://ini adalah server kominfo atau bsre/api/sign/pdf";
			$api_key="ini adalah api key dari server bsre/kominfo kabupaten";
			$password="qwerty!12";
			
			//
			$cfile=new CURLFile($folder_simpan.$nm_file_lengkap,"application/pdf",$nm_file_lengkap);
			$cfile_ttd=new CURLFile($qrcode_png,"image/png","20220621100829.png");
			$post=array(
				"file"=>$cfile,
				"image"=>"true",
				"imageTTD"=>$cfile_ttd,
				"nik"=>$arr->data->nik,
				"passphrase"=>$arr->data->password,
				"jenis_response"=>"BASE64",
				"tampilan"=>"visible",
				"linkQR"=>$isi_qr,
				"width"=>"60",
				"height"=>"60",
				"tag_koordinat"=>"##"
				);
			//
			$ch=curl_init();
			curl_setopt($ch,CURLOPT_URL,$target_url);
			curl_setopt($ch,CURLOPT_POST,1);
			curl_setopt($ch,CURLOPT_HEADER,0);
			curl_setopt($ch,CURLOPT_RETURNTRANSFER,1);
			curl_setopt($ch,CURLOPT_HTTPHEADER,array("Content-Type:multipart/form-data"));
			curl_setopt($ch,CURLOPT_FRESH_CONNECT,1);
			curl_setopt($ch,CURLOPT_USERPWD,$api_key.":".$password);
			curl_setopt($ch,CURLOPT_HTTPAUTH,CURLAUTH_BASIC);
			curl_setopt($ch,CURLOPT_FORBID_REUSE,1);
			curl_setopt($ch,CURLOPT_TIMEOUT,100);
			curl_setopt($ch,CURLOPT_POSTFIELDS,$post);
			//
			$result=curl_exec($ch);
			$myarray=json_decode($result,true);
			//
			if(isset($myarray["id_dokumen"]) and isset($myarray["base64_signed_file"]) and !empty($myarray["base64_signed_file"])){
				//
				$nama_file_modif=$id_file.".pdf";
				//
				file_put_contents($folder_tte.$nama_file_modif,base64_decode($myarray["base64_signed_file"]));
				//
				$sql="INSERT INTO rme_file_upload(id_file,no_rawat,nama_file_ori,nama_file_modif,direktori,mime_type,jenis_pemeriksaan,
				petugas,status_rawat,nomr) VALUES(
					'".escape($connection1,$id_file)."',
					'".escape($connection1,$arr->data->no_rawat)."',
					'".escape($connection1,$arr->data->nama_file)."',
					'".escape($connection1,$nama_file_modif)."',
					'".escape($connection1,$folder_tte)."',
					'application/pdf',
					'".escape($connection1,$arr->data->jns_dokumen)."',
					'".escape($connection1,$arr->data->petugas)."',
					'".escape($connection1,$arr->data->status_rawat)."',
					'".escape($connection1,$arr->data->nomr)."'
					)";
				$exec=affected_rows($connection1,$sql);				
				//
				if(file_exists($folder_tte.$nama_file_modif)){
					$status=true;
					$pesan="Berkas sudah di TTE";
					//
					if($exec<>1){
						$status=false;
						$pesan="Gagal simpan ke database. ".$exec;
					}
				}
				else{
					$status=false;
					$pesan="Berkas gagal di TTE. (E01)";				
				}
			}
			else{
				$status=false;
				$pesan="Berkas gagal di TTE. (E02). ".$result;					
			}		
		}
		else{
			$status=false;
			$pesan="Tipe file bukan PDF.";		
		}
	}
	else{
		$status=false;
		$pesan="Nama file hanya boleh ada karakter alphanumerik.";
	}	
	
}
else{
		$status=false;
		$pesan="Instruksi tidak dikenal.";	
}

echo json_encode(array("status"=>$status,"msg"=>$pesan));
?>
