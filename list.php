<?php

    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        if(!strpos($_SERVER['REQUEST_URI'],"pages/upload/")){
            exit(header("Location:../index.php"));
        }
    }
	
	

?>

<div id="post">
    <div class="entry">        
       <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             = isset($_GET['action'])?$_GET['action']:NULL;
                $no_rawat           = validTeks4((isset($_GET['no_rawat'])?$_GET['no_rawat']:NULL),20);
                $no_rm              = getOne("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='$no_rawat'");
                $nama_pasien        = getOne("select pasien.nm_pasien from pasien where pasien.no_rkm_medis='$no_rm'");
				
				$_sql       = "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,
                            reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,
                            pasien.umur,pasien.tgl_lahir,poliklinik.nm_poli,reg_periksa.status_lanjut,reg_periksa.umurdaftar,reg_periksa.sttsumur,
                            reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab 
                            from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab 
                            on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
                            and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='$no_rawat' ";
				$hasil        = bukaquery($_sql);
				$baris        = mysqli_fetch_array($hasil);
				$no_rkm_medis = $baris["no_rkm_medis"];
				$nm_pasien    = $baris["nm_pasien"];
				$umurdaftar   = $baris["umurdaftar"];
				$sttsumur     = $baris["sttsumur"];
				$tgl_lahir    = $baris["tgl_lahir"];
				$jk           = $baris["jk"];
				$almt_pj      = $baris["almt_pj"];
				$norawat      = $baris["no_rawat"];
				$tgl_registrasi = $baris["tgl_registrasi"];
				$jam_reg      = $baris["jam_reg"];
				$nm_poli      = $baris["nm_poli"];
				$nm_dokter2="";
				$a=1;
			
			$hasildokter=bukaquery("select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat='".$baris["no_rawat"]."'");
            while($barisdokter = mysqli_fetch_array($hasildokter)) {
                if($a==1){
                    $nm_dokter2=$barisdokter["nm_dokter"];
                }else{
                    $nm_dokter2=$nm_dokter2."#".$barisdokter["nm_dokter"];
                }                
                $a++;
            } 
            
            $nm_dokter    = "";
            if(!empty($nm_dokter2)){
                $nm_dokter=$nm_dokter2;
            }else{
                $nm_dokter= $baris["nm_dokter"];
            }
			
			
			$sep   			= "	select * from bridging_sep where bridging_sep.no_rawat='$no_rawat' ";
							
								$hasilsep	 						= bukaquery($sep);
								$barissep 							= mysqli_fetch_array($hasilsep);
								$sep_nosep							= isset ($barissep["no_sep"]) ? $barissep["no_sep"] : '';
								$sep_nomr							= isset ($barissep["nomr"]) ? $barissep["nomr"] : '';
								$sep_namapasien						= isset ($barissep["nama_pasien"]) ? $barissep["nama_pasien"] : '';
								$sep_tglsep							= isset ($barissep["tglsep"]) ? $barissep["tglsep"] : '';
								$sep_tglrujukan						= isset ($barissep["tglrujukan"]) ? $barissep["tglrujukan"] : '';
								$sep_no_rujukan						= isset ($barissep["no_rujukan"]) ? $barissep["no_rujukan"] : '';
								$sep_kelasrawat						= isset ($barissep["klsrawat"]) ? $barissep["klsrawat"] : '';
								$sep_tanggallahir					= isset ($barissep["tanggal_lahir"]) ? $barissep["tanggal_lahir"] : '';
								$sep_nokartu						= isset ($barissep["no_kartu"]) ? $barissep["no_kartu"] : '';
								$sep_nmrujukan						= isset ($barissep["nmppkrujukan"]) ? $barissep["nmppkrujukan"] : '';
								$sep_dpjp							= isset ($barissep["nmdpdjp"]) ? $barissep["nmdpdjp"] : '';
								$sep_jenispelayanan					= isset ($barissep["jnspelayanan"]) ? $barissep["jnspelayanan"] : '';
								$sep_diagnosaawal					= isset ($barissep["diagawal"]) ? $barissep["diagawal"] : '';
								$sep_nmdiagnosaawal					= isset ($barissep["nmdiagnosaawal"]) ? $barissep["nmdiagnosaawal"] : '';
								$sep_notlp							= isset ($barissep["notelep"]) ? $barissep["notelep"] : '';
								$sep_peserta						= isset ($barissep["peserta"]) ? $barissep["peserta"] : '';
								$sep_nmpoli							= isset ($barissep["nmpolitujuan"]) ? $barissep["nmpolitujuan"] : '';
								$sep_catatan						= isset ($barissep["catatan"]) ? $barissep["catatan"] : '';
								$sep_tujuankunjungan				= isset ($barissep["tujuankunjungan"]) ? $barissep["tujuankunjungan"] : '';
								$sep_noskdp							= isset ($barissep["noskdp"]) ? $barissep["noskdp"] : '';
								$sep_norawat						= isset ($barissep["no_rawat"]) ? $barissep["no_rawat"] : '';
			
			$spri  			= "	select * from bridging_surat_pri_bpjs where bridging_surat_pri_bpjs.no_rawat='$no_rawat' ";
								$hasilspri						= bukaquery($spri);
								$barisspri						= mysqli_fetch_array($hasilspri);
						
			$permintaanlab  = "	select * from permintaan_lab 
								inner join dokter on permintaan_lab.dokter_perujuk=dokter.kd_dokter
								where permintaan_lab.no_rawat='$no_rawat' ";
								$hasilpermintaanlab					= bukaquery($permintaanlab);
								$barispermintaanlab					= mysqli_fetch_array($hasilpermintaanlab);
			
			$periksalab  	= "	select * from periksa_lab
								inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter
								where periksa_lab.no_rawat='$no_rawat'";
								$hasilperiksalab					= bukaquery($periksalab);
								$barisperiksalab 					= mysqli_fetch_array($hasilperiksalab);	
			
			$detaillab  	= "	select * from detail_periksa_lab
								inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template 
								where detail_periksa_lab.no_rawat='$no_rawat' order by detail_periksa_lab.tgl_periksa,detail_periksa_lab.kd_jenis_prw,template_laboratorium.urut";
								$hasildetaillab						= bukaquery($detaillab);
								$barisdetaillab 					= mysqli_fetch_array($hasildetaillab);

		/* Konsultasi Medik   */

			$konsulmedik    = "  select * from konsultasi_medik
                inner join dokter on konsultasi_medik.kd_dokter_dikonsuli=dokter.kd_dokter
                                inner join pegawai on konsultasi_medik.kd_dokter=pegawai.nik
                                
                where konsultasi_medik.no_rawat='$no_rawat'";
                $hasilkonsulmedik            = bukaquery($konsulmedik);
                $bariskonsulmedik            = mysqli_fetch_array($hasilkonsulmedik);
                         $konsulmedik_no                = isset ($bariskonsulmedik["no_permintaan"]) ? $bariskonsulmedik["no_permintaan"] : '';


            $jawabkonsulmedik        = "  select * from jawaban_konsultasi_medik
                
                where jawaban_konsultasi_medik.no_permintaan='$konsulmedik_no'";
                $hasiljawabkonsulmedik            = bukaquery($jawabkonsulmedik);
                $barisjawabkonsulmedik            = mysqli_fetch_array($hasiljawabkonsulmedik);

		/* Konsultasi Medik   */
			$billing  		= "	select * from billing where billing.no_rawat='$no_rawat'";
								$hasilbilling						= bukaquery($billing);
								$barisbilling						= mysqli_fetch_array($hasilbilling);
								
			$billing2  		= "	SELECT SUM(totalbiaya) AS total from billing where billing.no_rawat='$no_rawat'";
								$hasilbilling2						= bukaquery($billing2);
								$barisbilling2						= mysqli_fetch_array($hasilbilling2);
							
			$periksarad  	= "	select * from periksa_radiologi 
								inner join dokter on periksa_radiologi.dokter_perujuk=dokter.kd_dokter
								inner join pegawai on periksa_radiologi.kd_dokter=pegawai.nik
								inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw
								where periksa_radiologi.no_rawat='$no_rawat'";
								$hasilperiksarad					= bukaquery($periksarad);
								$barisperiksarad 					= mysqli_fetch_array($hasilperiksarad);
			
			$suratkonsul  	= "	select * from surat_konsul
								where surat_konsul.no_rawat='$no_rawat'";
								$hasilsuratkonsul					= bukaquery($suratkonsul);
								$barissuratkonsul 					= mysqli_fetch_array($hasilsuratkonsul);
								
			$berkas  	= "	select * from berkas_digital_perawatan where berkas_digital_perawatan.no_rawat='$no_rawat'";
								$hasilberkas					= bukaquery($berkas);
								$barisberkas 					= mysqli_fetch_array($hasilberkas);
								
			$ralan 		= 	"	select * from pemeriksaan_ralan
								
								inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik
								where pemeriksaan_ralan.no_rawat='$no_rawat' ";
								$hasilralan				= bukaquery($ralan);
								$barisralan				= mysqli_fetch_array($hasilralan);
                                $ralan_dokter			= isset ($barisralan["nip"]) ? $barisralan["nip"] : '';
			
			$detailrad  	= "	select * from hasil_radiologi where hasil_radiologi.no_rawat='$no_rawat'";
								$hasildetailrad						= bukaquery($detailrad);
								$barisdetailrad 					= mysqli_fetch_array($hasildetailrad);
			
			$inacbg  		= "	select * from inacbg_klaim_baru2 where inacbg_klaim_baru2.no_rawat='$no_rawat'";
								$hasilinacbg						= bukaquery($inacbg);
								$barisinacbg 						= mysqli_fetch_array($hasilinacbg);
								$barisinacbgpid						= isset ($barisinacbg["patient_id"]) ? $barisinacbg["patient_id"] : '';
								$barisinacbgid						= isset ($barisinacbg["admission_id"]) ? $barisinacbg["admission_id"] : '';
			
			$operasimata  	= "	select * from operasi_mata
								inner join dokter on operasi_mata.kd_dokter=dokter.kd_dokter
								where operasi_mata.no_rawat='$no_rawat'";
								$hasiloperasimata					= bukaquery($operasimata);
								$barisoperasimata 					= mysqli_fetch_array($hasiloperasimata);
			
								
			$ekg  			= "	select * from hasil_pemeriksaan_ekg
								inner join dokter on hasil_pemeriksaan_ekg.kd_dokter=dokter.kd_dokter
								where hasil_pemeriksaan_ekg.no_rawat='$no_rawat'";
								$hasilekg						= bukaquery($ekg);
								$barisekg 						= mysqli_fetch_array($hasilekg);
								
			
			$gambarcatatan    	= getOne("select catatan_pasien.catatan from catatan_pasien where catatan_pasien.no_rkm_medis='$sep_nomr'");

			$gambarencoder    	= getOne("
							    SELECT GROUP_CONCAT(CONCAT(cm.tanggal,' ',cm.jam,' - ',cm.uraian) SEPARATOR '\n') AS semua_catatan
							    FROM catatan_operasi_mata cm
							    INNER JOIN reg_periksa rp ON rp.no_rawat = cm.no_rawat
							    WHERE rp.no_rkm_medis = '$sep_nomr'
							    ORDER BY cm.tanggal ASC, cm.jam ASC
								");

					 $hasilrujukan = bukaquery("
					    SELECT 
					        rp.no_rawat, 
					        rp.no_rkm_medis, 
					        rp.umurdaftar, 
					        rp.sttsumur, 
					        rp.status_lanjut,
					        pas.nm_pasien, 
					        pas.jk, 
					        pas.tgl_lahir,
					        d_asal.nm_dokter AS dokter_asal,
					        p_asal.nm_poli AS poli_asal,
					        d_tuju.nm_dokter AS dokter_tuju,
					        p_tuju.nm_poli AS poli_tuju,
					        rip.diagnosa, 
					        rip.terapi, 
					        rip.alasan, 
					        rip.program,
					        rp.tgl_registrasi
					    FROM reg_periksa rp
					    JOIN pasien pas ON rp.no_rkm_medis = pas.no_rkm_medis
					    JOIN dokter d_asal ON rp.kd_dokter = d_asal.kd_dokter
					    JOIN poliklinik p_asal ON rp.kd_poli = p_asal.kd_poli
					    JOIN rujukan_internal_poli rip ON rp.no_rawat = rip.no_rawat
					    JOIN dokter d_tuju ON rip.kd_dokter = d_tuju.kd_dokter
					    JOIN poliklinik p_tuju ON rip.kd_poli = p_tuju.kd_poli
					    WHERE rp.no_rawat = '$no_rawat'
					    LIMIT 1
					");
					$barirujukan = mysqli_fetch_array($hasilrujukan); 

			
			$usgurologi   	= "	select * from hasil_pemeriksaan_usg_urologi where hasil_pemeriksaan_usg_urologi.no_rawat='$no_rawat' ";
								$hasilusgurologi	 				= bukaquery($usgurologi);
								$barisusgurologi					= mysqli_fetch_array($hasilusgurologi);
								$usgurologi_tanggal					= isset ($barisusgurologi["tanggal"]) ? $barisusgurologi["tanggal"] : '';
								$usgurologi_kddokter				= isset ($barisusgurologi["kd_dokter"]) ? $barisusgurologi["kd_dokter"] : '';
								$usgurologi_diagnosaklinis			= isset ($barisusgurologi["diagnosa_klinis"]) ? $barisusgurologi["diagnosa_klinis"] : '';
								$usgurologi_kirimandari				= isset ($barisusgurologi["kiriman_dari"]) ? $barisusgurologi["kiriman_dari"] : '';
								$usgurologi_ginjalkanan				= isset ($barisusgurologi["ginjal_kanan"]) ? $barisusgurologi["ginjal_kanan"] : '';
								$usgurologi_ginjalkiri				= isset ($barisusgurologi["ginjal_kiri"]) ? $barisusgurologi["ginjal_kiri"] : '';
								$usgurologi_vesica					= isset ($barisusgurologi["vesica_urinaria"]) ? $barisusgurologi["vesica_urinaria"] : '';
								$usgurologi_tambahan				= isset ($barisusgurologi["tambahan"]) ? $barisusgurologi["tambahan"] : '';
							
			$usg  			= "	select * from hasil_pemeriksaan_usg where hasil_pemeriksaan_usg.no_rawat='$no_rawat' ";
								$hasilusg			 				= bukaquery($usg);
								$barisusg							= mysqli_fetch_array($hasilusg);
								$usg_tanggal						= isset ($barisusg["tanggal"]) ? $barisusg["tanggal"] : '';
								$usg_kddokter						= isset ($barisusg["kd_dokter"]) ? $barisusg["kd_dokter"] : '';
								$usg_diagnosaklinis					= isset ($barisusg["diagnosa_klinis"]) ? $barisusg["diagnosa_klinis"] : '';
								$usg_kirimandari					= isset ($barisusg["kiriman_dari"]) ? $barisusg["kiriman_dari"] : '';
								$usg_kesimpulan						= isset ($barisusg["kesimpulan"]) ? $barisusg["kesimpulan"] : '';
			
			$gambarusg    	= getOne("select hasil_pemeriksaan_usg_gambar.photo from hasil_pemeriksaan_usg_gambar where hasil_pemeriksaan_usg_gambar.no_rawat='$no_rawat'");
			
			$gambarekg    	= getOne("select hasil_pemeriksaan_ekg_gambar.photo from hasil_pemeriksaan_ekg_gambar where hasil_pemeriksaan_ekg_gambar.no_rawat='$no_rawat'");

			$echo  			= "	select * from hasil_pemeriksaan_echo
									inner join dokter on hasil_pemeriksaan_echo.kd_dokter=dokter.kd_dokter
									where hasil_pemeriksaan_echo.no_rawat='$no_rawat'";
									$hasilecho						= bukaquery($echo);
									$barisecho 						= mysqli_fetch_array($hasilecho);
									$echo_tanggal					= isset($barisecho["tanggal"])         ? $barisecho["tanggal"]         : '';
									$echo_diagnosaklinis			= isset($barisecho["diagnosa_klinis"]) ? $barisecho["diagnosa_klinis"] : '';
									$echo_kirimandari				= isset($barisecho["kiriman_dari"])    ? $barisecho["kiriman_dari"]    : '';
									$echo_kesimpulan				= isset($barisecho["kesimpulan"])      ? $barisecho["kesimpulan"]      : '';
									$echo_nmdokter					= isset($barisecho["nm_dokter"])       ? $barisecho["nm_dokter"]       : '';
			$gambarecho    	= getOne("select hasil_pemeriksaan_echo_gambar.photo from hasil_pemeriksaan_echo_gambar where hasil_pemeriksaan_echo_gambar.no_rawat='$no_rawat'");
			
			$gambarrad    	= getOne("select gambar_radiologi.lokasi_gambar from gambar_radiologi where gambar_radiologi.no_rawat='$no_rawat'");				
							
			$gambarusgu    	= getOne("select hasil_pemeriksaan_usg_urologi_gambar.photo from hasil_pemeriksaan_usg_urologi_gambar where hasil_pemeriksaan_usg_urologi_gambar.no_rawat='$no_rawat'");
			$gambarmta = getOne("select hasil_pemeriksaan_mta_gambar.photo from hasil_pemeriksaan_mta_gambar where hasil_pemeriksaan_mta_gambar.no_rawat='$no_rawat'");
			
			$kamarinap   	= "	select * from kamar_inap where kamar_inap.no_rawat='$no_rawat' ORDER by tgl_masuk DESC LIMIT 1";
								$hasilkamarinap 					= bukaquery($kamarinap);
								$bariskamarinap 					= mysqli_fetch_array($hasilkamarinap);	
			
			$resumeranap   	= "	select * from resume_pasien_ranap 
								inner join dokter on resume_pasien_ranap.kd_dokter=dokter.kd_dokter
								where resume_pasien_ranap.no_rawat='$no_rawat' ";
								$hasilresumeranap 					= bukaquery($resumeranap);
								$barisresumeranap 					= mysqli_fetch_array($hasilresumeranap);				
			
			$operasi 		= "	select * from operasi
								inner join dokter on operasi.operator1=dokter.kd_dokter
								inner join pegawai on operasi.dokter_anestesi=pegawai.nik
								where operasi.no_rawat='$no_rawat' ";
								$hasiloperasi				= bukaquery($operasi);
								$barisoperasi				= mysqli_fetch_array($hasiloperasi);	
								
								
			$rujukaninternal 		= "	select * from rujukan_internal_poli
								inner join dokter on rujukan_internal_poli.kd_dokter=dokter.kd_dokter
								inner join poliklinik on rujukan_internal_poli.kd_poli=poliklinik.kd_poli
								where rujukan_internal_poli.no_rawat='$no_rawat' ";
								$hasilrujukaninternal				= bukaquery($rujukaninternal);
								$barisrujukaninternal				= mysqli_fetch_array($hasilrujukaninternal);	
			
			
			$laporanoperasi = "	select * from laporan_operasi 
								where laporan_operasi.no_rawat='$no_rawat' ";
								$hasillaporanoperasi				= bukaquery($laporanoperasi);
								$barislaporanoperasi				= mysqli_fetch_array($hasillaporanoperasi);	
		
			$bayi 			= "	select * from pasien_bayi 
								inner join dokter on pasien_bayi.penolong=dokter.kd_dokter
								where pasien_bayi.no_rkm_medis='$sep_nomr' ";
								$hasilbayi							= bukaquery($bayi);
								$barisbayi							= mysqli_fetch_array($hasilbayi);
			
			$resumepasien   = "	select * from resume_pasien where resume_pasien.no_rawat='$no_rawat' ";
								$hasilresume 						= bukaquery($resumepasien);
								$barisresume 						= mysqli_fetch_array($hasilresume);
								$resume_keluhanutama				= isset ($barisresume["keluhan_utama"]) ? $barisresume["keluhan_utama"] : '';
								$resume_jalannyapenyakit			= isset ($barisresume["jalannya_penyakit"]) ? $barisresume["jalannya_penyakit"] : '';
								$resume_pemeriksaanpenunjang		= isset ($barisresume["pemeriksaan_penunjang"]) ? $barisresume["pemeriksaan_penunjang"] : '';
								$resume_hasillaborat				= isset ($barisresume["hasil_laborat"]) ? $barisresume["hasil_laborat"] : '';
								$resume_diagnosautama				= isset ($barisresume["diagnosa_utama"]) ? $barisresume["diagnosa_utama"] : '';
								$resume_kddiagnosautama				= isset ($barisresume["kd_diagnosa_utama"]) ? $barisresume["kd_diagnosa_utama"] : '';
								$resume_diagnosasekunder			= isset ($barisresume["diagnosa_sekunder"]) ? $barisresume["diagnosa_sekunder"] : '';
								$resume_kddiagnosasekunder			= isset ($barisresume["kd_diagnosa_sekunder"]) ? $barisresume["kd_diagnosa_sekunder"] : '';
								$resume_diagnosasekunder2			= isset ($barisresume["diagnosa_sekunder2"]) ? $barisresume["diagnosa_sekunder2"] : '';
								$resume_kddiagnosasekunder2			= isset ($barisresume["kd_diagnosa_sekunder2"]) ? $barisresume["kd_diagnosa_sekunder2"] : '';
								$resume_diagnosasekunder3			= isset ($barisresume["diagnosa_sekunder3"]) ? $barisresume["diagnosa_sekunder3"] : '';
								$resume_kddiagnosasekunder3			= isset ($barisresume["kd_diagnosa_sekunder3"]) ? $barisresume["kd_diagnosa_sekunder3"] : '';
								$resume_diagnosasekunder4			= isset ($barisresume["diagnosa_sekunder4"]) ? $barisresume["diagnosa_sekunder4"] : '';
								$resume_kddiagnosasekunder4			= isset ($barisresume["d_diagnosa_sekunder4"]) ? $barisresume["d_diagnosa_sekunder4"] : '';
								$resume_prosedurutama				= isset ($barisresume["prosedur_utama"]) ? $barisresume["prosedur_utama"] : '';
								$resume_kdprosedurutama				= isset ($barisresume["kd_prosedur_utama"]) ? $barisresume["kd_prosedur_utama"] : '';
								$resume_prosedursekunder			= isset ($barisresume["prosedur_sekunder"]) ? $barisresume["prosedur_sekunder"] : '';
								$resume_kdprosedursekunder			= isset ($barisresume["kd_prosedur_sekunder"]) ? $barisresume["kd_prosedur_sekunder"] : '';
								$resume_prosedursekunder2			= isset ($barisresume["prosedur_sekunder2"]) ? $barisresume["prosedur_sekunder2"] : '';
								$resume_kdprosedursekunder2			= isset ($barisresume["kd_prosedur_sekunder2"]) ? $barisresume["kd_prosedur_sekunder2"] : '';
								$resume_prosedursekunder3			= isset ($barisresume["prosedur_sekunder3"]) ? $barisresume["prosedur_sekunder3"] : '';
								$resume_kdprosedursekunder3			= isset ($barisresume["kd_prosedur_sekunder3"]) ? $barisresume["kd_prosedur_sekunder3"] : '';
								$resume_kondisipulang				= isset ($barisresume["kondisi_pulang"]) ? $barisresume["kondisi_pulang"] : '';
								$resume_obatpulang					= isset ($barisresume["obat_pulang"]) ? $barisresume["obat_pulang"] : '';
		
			$triase = 			"select * from data_triase_igd
								inner join master_triase_macam_kasus on data_triase_igd.kode_kasus=master_triase_macam_kasus.kode_kasus
								where data_triase_igd.no_rawat='$no_rawat' ";
								$hasiltriase				= bukaquery($triase);
								$baristriase				= mysqli_fetch_array($hasiltriase);	
			
			$triase1 = "select * from data_triase_igddetail_skala1 
			inner join master_triase_skala1 on data_triase_igddetail_skala1.kode_skala1=master_triase_skala1.kode_skala1
						inner join master_triase_pemeriksaan on master_triase_skala1.kode_pemeriksaan=master_triase_pemeriksaan.kode_pemeriksaan
						where data_triase_igddetail_skala1.no_rawat='$no_rawat' ";
								$hasiltriase1				= bukaquery($triase1);
								$baristriase1				= mysqli_fetch_array($hasiltriase1);	
								
			$triase2 = "select * from data_triase_igddetail_skala2 
			inner join master_triase_skala2 on data_triase_igddetail_skala2.kode_skala2=master_triase_skala2.kode_skala2
						inner join master_triase_pemeriksaan on master_triase_skala2.kode_pemeriksaan=master_triase_pemeriksaan.kode_pemeriksaan
			where data_triase_igddetail_skala2.no_rawat='$no_rawat' ";
								$hasiltriase2				= bukaquery($triase2);
								$baristriase2				= mysqli_fetch_array($hasiltriase2);

			$triase3 = "select * from data_triase_igddetail_skala3 
						inner join master_triase_skala3 on data_triase_igddetail_skala3.kode_skala3=master_triase_skala3.kode_skala3
						inner join master_triase_pemeriksaan on master_triase_skala3.kode_pemeriksaan=master_triase_pemeriksaan.kode_pemeriksaan
						where data_triase_igddetail_skala3.no_rawat='$no_rawat' ";
								$hasiltriase3				= bukaquery($triase3);
								$baristriase3				= mysqli_fetch_array($hasiltriase3);
			
			$triase4 = "select * from data_triase_igddetail_skala4 
			inner join master_triase_skala4 on data_triase_igddetail_skala4.kode_skala4=master_triase_skala4.kode_skala4
						inner join master_triase_pemeriksaan on master_triase_skala4.kode_pemeriksaan=master_triase_pemeriksaan.kode_pemeriksaan
			
			where data_triase_igddetail_skala4.no_rawat='$no_rawat' ";
								$hasiltriase4				= bukaquery($triase4);
								$baristriase4				= mysqli_fetch_array($hasiltriase4);
								
			$triase5 = "select * from data_triase_igddetail_skala5 
			inner join master_triase_skala5 on data_triase_igddetail_skala5.kode_skala5=master_triase_skala5.kode_skala5
						inner join master_triase_pemeriksaan on master_triase_skala5.kode_pemeriksaan=master_triase_pemeriksaan.kode_pemeriksaan
			where data_triase_igddetail_skala5.no_rawat='$no_rawat' ";
								$hasiltriase5				= bukaquery($triase5);
								$baristriase5				= mysqli_fetch_array($hasiltriase5);
								
			$triasesekunder = 	"select * from data_triase_igdsekunder 
			inner join pegawai on data_triase_igdsekunder.nik=pegawai.nik
			where data_triase_igdsekunder.no_rawat='$no_rawat' ";
								$hasiltriasesekunder		= bukaquery($triasesekunder);
								$baristriasesekunder		= mysqli_fetch_array($hasiltriasesekunder);	
								
		
			$triaseprimer = 	"select * from data_triase_igdprimer
								inner join pegawai on data_triase_igdprimer.nik=pegawai.nik
								where data_triase_igdprimer.no_rawat='$no_rawat' ";
								$hasiltriaseprimer				= bukaquery($triaseprimer);
								$baristriaseprimer				= mysqli_fetch_array($hasiltriaseprimer);	

			/* ======= SURAT PULANG ATAS PERMINTAAN SENDIRI ======= */
			$spaps = "select * from surat_pulang_atas_permintaan_sendiri
					  where surat_pulang_atas_permintaan_sendiri.no_rawat='$no_rawat'";
			$hasilspaps              = bukaquery($spaps);
			$barisspaps              = mysqli_fetch_array($hasilspaps);
			$spaps_nosurat           = isset($barisspaps["no_surat"])       ? $barisspaps["no_surat"]       : '';
			$spaps_tglpulang         = isset($barisspaps["tgl_pulang"])     ? $barisspaps["tgl_pulang"]     : '';
			$spaps_rspilihan         = isset($barisspaps["rs_pilihan"])     ? $barisspaps["rs_pilihan"]     : '';
			$spaps_namapj            = isset($barisspaps["nama_pj"])        ? $barisspaps["nama_pj"]        : '';
			$spaps_lahir             = isset($barisspaps["lahir"])          ? $barisspaps["lahir"]          : '';
			$spaps_umur              = isset($barisspaps["umur"])           ? $barisspaps["umur"]           : '';
			$spaps_jkpj              = isset($barisspaps["jkpj"])           ? $barisspaps["jkpj"]           : '';
			$spaps_alamat            = isset($barisspaps["alamat"])         ? $barisspaps["alamat"]         : '';
			$spaps_hubungan          = isset($barisspaps["hubungan"])       ? $barisspaps["hubungan"]       : '';
			$spaps_saksikeluarga     = isset($barisspaps["saksi_keluarga"]) ? $barisspaps["saksi_keluarga"] : '';
			$spaps_nip               = isset($barisspaps["nip"])            ? $barisspaps["nip"]            : '';
			$spaps_foto_pembuat      = getOne("select photo from surat_pulang_atas_permintaan_sendiri_pembuat_pernyataan where no_surat='$spaps_nosurat'");
			$spaps_foto_saksi        = getOne("select photo from surat_pulang_atas_permintaan_sendiri_saksi_keluarga where no_surat='$spaps_nosurat'");
			$spaps_nmptugas          = getOne("select nm_pegawai from pegawai where nik='$spaps_nip'");
			/* ======= END SURAT PULANG APS ======= */
			
			
                echo "<input type=hidden name=no_rawat value=$no_rawat>
                      <input type=hidden name=action value=$action>";	

					  
				//$pdfs = "http://172.30.200.62/E-Klaim/print/klaim.php?pid=$barisinacbgpid&adm=$barisinacbgid ";
				//$save = "gambarklaim/$sep_nosep.jpg";
				//exec('magick -density 200 "'.$pdf.'" "'.$save.'"', $output, $return_var);	  
				
			
				//$file = file_get_contents("http://172.30.200.62/E-Klaim/print/klaim.php?pid=$barisinacbgpid&adm=$barisinacbgid");
				//$base64_pdf = base64_encode($file);
				//$base64_decode = base64_decode($base64_pdf);
				//$pdf = fopen("pdf/$sep_nosep.pdf",'w');
				//fwrite($pdf, $base64_decode);
				//fclose($pdf);
		
            ?>
    </div>

<html>
<head>
<style>
		table {
			font-family: sans-serif;
			font-size: 8pt; 
			width: 100%;
		}
		
		.table1{
			background-image:url('https://png.pngtree.com/png-vector/20220525/ourmid/pngtree-validated-grunge-rubber-stamp-on-white-background-png-image_4739606.png');  //replace your correct path
			background-repeat:no-repeat;
			background-size:cover;

		}
		

		th, td {
			text-align: left;
			padding: 2px;
		}
		iframe{
        width: 100%;
        border: 2px solid #ccc;
		}
		
		@media print {
		#printPageButton {
			display: none;
			}
			
		#rep {
			display: none;
			}
		}
	
</style>

<style>
      #canvas_container {
          width: 100%;
		  
          overflow: auto;
      }
      #canvas_container {
        background: #fff;
        text-align: center;
       
      }
  </style>
    
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.3/html2pdf.bundle.min.js" ></script>
	<script src="https://unpkg.com/pdf-lib@1.4.0"></script>
    <script src="https://unpkg.com/downloadjs@1.4.7"></script>
	<script type="text/javascript" src="assets/jscripts/jquery.qrcode.js"></script>
    <script type="text/javascript" src="assets/jscripts/qrcode.js"></script>
	<script type="text/javascript" src="assets/jscripts/pdf-to-base64.js"></script>
	

</head>

<body>

<div class="container">
<br>
<button type="button" id="rep" class="btn btn-primary btn_print">Cetak Ke PDF</button>
<button type="button" id="printPageButton" onclick="document.title='<?php echo $sep_nosep;?>';window.print()" class="btn btn-success">Capture</button>
<hr>

<div id="element">
	<?php if (isset($barissep["no_rawat"])) { ?>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="10%" ><img src="images\bpjs.png" alt="Image" height="30"></td>
		<td valign="top" width="80%" >
				<div style="font-size:17px;text-align:left;font-weight:bold;">
                  SURAT ELEGIBILITAS PESERTA
                </div>
                <div style="font-size:10px;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
		</td>
		</tr>
		</table>
		<br>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" >No. SEP</td>
		<td valign="top" width="40%" >: <?php echo $sep_nosep;?></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Tgl. SEP</td>
		<td valign="top" width="30%" >: <?php echo $sep_tglsep;?></td>
		<td valign="top" width="15%" >Peserta</td>
		<td valign="top" width="20%" >: <?php echo $sep_peserta;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >No. Kartu / MR</td>
		<td valign="top" width="30%" >: <?php echo $sep_nokartu;?> (MR : <?php echo $sep_nomr;?>)</td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Nama Peserta</td>
		<td valign="top" width="30%" >: <?php echo $sep_namapasien;?></td>
		<td valign="top" width="15%" >Jenis Rawat</td>
		<td valign="top" width="20%" >: <?php 
										
										if($sep_jenispelayanan=='2')
										{
											echo'Rawat Jalan';
										}
										else
										{
											echo'Rawat Inap';
										}
										?></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Tgl. Lahir</td>
		<td valign="top" width="30%" >: <?php echo $sep_tanggallahir;?></td>
		<td valign="top" width="15%" >Jenis Kunjungan</td>
		<td valign="top" width="20%" >: - <?php 
										
										if($sep_tujuankunjungan=='2')
										{
											echo'Kunjungan Kontrol (ulangan)';
										}
										else
										{
											echo'Rujukan Awal';
										}
										?></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >No. Telepon</td>
		<td valign="top" width="30%" >: <?php echo $sep_notlp;?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" >: - Prosedur tidak berkelanjutan</td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Sub/Spesialis</td>
		<td valign="top" width="30%" >: <?php echo $sep_nmpoli;?></td>
		<td valign="top" width="15%" >Poli Perujuk</td>
		<td valign="top" width="20%" >: -</td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Dokter</td>
		<td valign="top" width="30%" >: <?php echo $sep_dpjp;?></td>
		<td valign="top" width="15%" >Kelas Hak</td>
		<td valign="top" width="20%" >: <?php 
										
										if($sep_kelasrawat=='1')
										{
											echo'Kelas 1';
										}
										else if($sep_kelasrawat=='2')
										{
											echo'Kelas 2';
										}
										else
										{
											echo'Kelas 3';
										}
										?></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Faskes Perujuk</td>
		<td valign="top" width="30%" >: <?php echo $sep_nmrujukan;?></td>
		<td valign="top" width="15%" >Kelas Rawat</td>
		<td valign="top" width="20%" >: -</td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Diagnosa Awal</td>
		<td valign="top" width="30%" >: <?php echo $sep_diagnosaawal;?> - <?php echo $sep_nmdiagnosaawal;?></td>
		<td valign="top" width="15%" >Penjamin</td>
		<td valign="top" width="20%" >: -</td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Catatan</td>
		<td valign="top" width="30%" >: <?php echo $sep_catatan;?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" colspan="2" style="font-size:6pt;text-align:left;">*Saya menyetujui BPJS Kesehatan untuk :<br>
		a. membuka dan atau menggunakan informasi medis Pasien untuk keperluan administrasi, pembayaran asuransi atau jaminan pembiayaan kesehatan<br>
		b. memberikan akses informasi medis atau riwayat pelayanan kepada dokter/tenaga medis pada Rumah Sakit Mitra Manakara untuk kepentingan pemeliharaan kesehatan, pengobatan, penyembuhan, dan perawatan Pasien<br>
			*Saya mengetahui dan memahami :<br>
		a. Rumah Sakit dapat melakukan koordinasi dengan PT Jasa Raharja / PT Taspen / PT ASABRI / BPJS Ketenagakerjaan atau Penjamin lainnya, jika Peserta merupakan pasien yang mengalami kecelakaan lalulintas dan / atau kecelakaan kerja<br>
		b. SEP bukan sebagai bukti penjaminan peserta<br>
			** Dengan tampilnya luaran SEP elektronik ini merupakan hasil validasi terhadap eligibilitas Pasien secara elektronik (validasi finger print atau biometrik / sistem validasi lain) dan selanjutnya Pasien dapat mengakses pelayanan kesehatan rujukan sesuai ketentuan berlaku.<br> Kebenaran dan keaslian atas informasi data Pasien menjadi tanggung jawab penuh FKRTL
		</td>
		<td valign="top" colspan="3">Persetujuan<br>Pasien/Keluarga Pasien<br><br>
		<div class="qrcodeSEP" style="margin-top:0px;margin-bottom:0px;;"></div>
				<script>
				jQuery('.qrcodeSEP').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php echo $sep_namapasien;?>"
		  
				});
				</script>
		</td>
		</tr>		
		</table>
	
	<?php } ?>
	
	
	<?php if($sep_tujuankunjungan=='2') { ?>
		<hr>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="10%" ><img src="images\bpjs.png" alt="Image" height="30"></td>
		<td valign="top" width="60%" >
				<div style="font-size:17px;text-align:left;font-weight:bold;">
                  SURAT RENCANA KONTROL
                </div>
                <div style="font-size:10px;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
		</td>
		<td valign="top" width="30%" >No : <?php echo $sep_noskdp;?></td>
		</tr>
		</table>
		
		<br>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" >Kepada Yth</td>
		<td valign="top" width="40%" ><?php echo $sep_nmpoli;?></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		</tr>
		<tr>
		<td valign="top" width="20%" ></td>
		<td valign="top" width="30%" >Dokter Spesialis <?php echo $sep_nmpoli;?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td colspan="4" >Mohon Pemeriksaan dan Penanganan Lebih Lanjut :</td>
		</tr>
		<tr>
		<td valign="top" width="15%" >No. Kartu</td>
		<td valign="top" width="30%" >: <?php echo $sep_nokartu;?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Nama Peserta</td>
		<td valign="top" width="30%" >: <?php echo $sep_namapasien;?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Tgl. Lahir</td>
		<td valign="top" width="30%" >: <?php echo $sep_tanggallahir;?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Diagnosa</td>
		<td valign="top" width="30%" >: <?php echo $sep_diagnosaawal;?> - <?php echo $sep_nmdiagnosaawal;?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Rencana Kontrol</td>
		<td valign="top" width="30%" >: <?php echo $sep_tglsep;?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td colspan="4" >Demikian atas Bantuannya, diucapkan banyak terima kasih.</td>
		</tr>
		</table>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Mengetahui<br><br>
		<div class="qrcodeSKDP" style="margin-top:0px;margin-bottom:0px;;"></div>
				
	   
				<script>
				jQuery('.qrcodeSKDP').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php echo $sep_dpjp;?>"
		  
				});
				</script>
		</td>
		</tr>
		</table>
		
	
    <?php } ?>
	
	
	
	<?php if (isset($barisspri["no_rawat"])) { ?>
		<hr>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="10%" ><img src="images\bpjs.png" alt="Image" height="30"></td>
		<td valign="top" width="60%" >
				<div style="font-size:17px;text-align:left;font-weight:bold;">
                  SURAT PERINTAH RAWAT INAP
                </div>
                <div style="font-size:10px;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
		</td>
		<td valign="top" width="30%" >No : <?php if (isset($barisspri["no_surat"])) { echo $barisspri["no_surat"]; }?></td>
		</tr>
		</table>
		
		<br>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" >Kepada Yth</td>
		<td valign="top" width="40%" ><?php if (isset($barisspri["nm_dokter_bpjs"])) { echo $barisspri["nm_dokter_bpjs"]; }?></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		</tr>
		<tr>
		<td valign="top" width="20%" ></td>
		<td valign="top" width="30%" >Dokter Spesialis <?php if (isset($barisspri["nm_poli_bpjs"])) { echo $barisspri["nm_poli_bpjs"]; }?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td colspan="4" >Mohon Pemeriksaan dan Penanganan Lebih Lanjut :</td>
		</tr>
		<tr>
		<td valign="top" width="15%" >No. Kartu</td>
		<td valign="top" width="30%" >: <?php if (isset($barisspri["no_kartu"])) { echo $barisspri["no_kartu"]; }?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Nama Peserta</td>
		<td valign="top" width="30%" >: <?php echo $sep_namapasien;?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Tgl. Lahir</td>
		<td valign="top" width="30%" >: <?php echo $sep_tanggallahir;?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Diagnosa</td>
		<td valign="top" width="30%" >: <?php if (isset($barisspri["diagnosa"])) { echo $barisspri["diagnosa"]; }?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Tanggal Surat</td>
		<td valign="top" width="30%" >: <?php if (isset($barisspri["tgl_surat"])) { echo $barisspri["tgl_surat"]; }?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td colspan="4" >Demikian atas Bantuannya, diucapkan banyak terima kasih.</td>
		</tr>
		</table>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Mengetahui<br><br>
		<div class="qrcodeSPRI" style="margin-top:0px;margin-bottom:0px;;"></div>
				
	   
				<script>
				jQuery('.qrcodeSPRI').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php if (isset($barisspri["nm_dokter_bpjs"])) { echo $barisspri["nm_dokter_bpjs"]; }?>"
		  
				});
				</script>
		</td>
		</tr>
		</table>
		
	
    <?php } ?>
	
	<?php if (isset($barisrujukaninternal["no_rawat"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">SURAT RUJUKAN INTERNAL</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" >Kepada Yth</td>
		<td valign="top" width="40%" ><?php echo $barisrujukaninternal["nm_dokter"];?></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		</tr>
		<tr>
		<td valign="top" width="20%" ></td>
		<td valign="top" width="30%" >Poliklinik <?php echo $barisrujukaninternal["nm_poli"];?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td colspan="4" >Mohon Pemeriksaan dan Penanganan Lebih Lanjut :</td>
		</tr>
		
		<tr>
		<td valign="top" width="15%" >    Nama Peserta</td>
		<td valign="top" width="30%" >: <?php echo $sep_namapasien;?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >    Tgl. Lahir</td>
		<td valign="top" width="30%" >: <?php echo $sep_tanggallahir;?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >    Alamat</td>
		<td valign="top" width="30%" >: <?php echo $almt_pj;?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Keluhan</td>
		<td valign="top" width="30%" >: <?php echo $barisralan["keluhan"];?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Diagnosa</td>
		<td valign="top" width="30%" >: <?php echo $barisralan["penilaian"];?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
		<td valign="top" width="15%" >Pengobatan yang dilakukan</td>
		<td valign="top" width="30%" >: <?php echo $barisralan["rtl"];?></td>
		<td valign="top" width="15%" ></td>
		<td valign="top" width="20%" ></td>
		</tr>
		<tr>
	    <td valign="top" width="15%">Terapi</td>
	    <td valign="top" width="30%">: <?= $barirujukan['terapi'] ?></td>
	    <td valign="top" width="15%"></td>
	    <td valign="top" width="20%"></td>
		</tr>
		<tr>
	    <td valign="top" width="15%">Alasan Rujukan</td>
	    <td valign="top" width="30%">: <?= $barirujukan['alasan'] ?></td>
	    <td valign="top" width="15%"></td>
	    <td valign="top" width="20%"></td>
		</tr>

		<tr>
		<td colspan="4" >Demikian Surat Rujukan ini diberikan agar dipergunakan sebagaimana mestinya.</td>
		</tr>
		</table>
   <!-- Tanda Tangan -->
		<table width="100%" style="font-size:10pt;">
		    <tr>
		        <td></td>
		        <td align="center">
		            Mamuju, <?= date("d-m-Y", strtotime($barirujukan['tgl_registrasi'])) ?><br>
		            Dokter Perujuk<br><br>
		            <div class="qrcodeRujukan"></div><br>
		            <?= $barirujukan['dokter_asal'] ?>
		        </td>
		    </tr>
		</table>

		<script>
		    jQuery('.qrcodeRujukan').qrcode({
		        text: "Ditandatangani secara elektronik oleh : <?= addslashes($barirujukan['dokter_asal']) ?>"
		    });
		</script>
    <?php } ?>
	
	
	<?php if (isset($barisresume["no_rawat"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">RESUME MEDIS PASIEN RAWAT JALAN</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >Nama</td>
		<td valign="top" width="30%" >: <?php echo $nama_pasien;?></td>
		<td valign="top" width="20%" >No. Rawat</td>
		<td valign="top" width="30%" >: <?php echo $norawat;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Lahir / Umur</td>
		<td valign="top" width="30%" >: <?php echo $umurdaftar;?> / <?php echo $tgl_lahir;?></td>
		<td valign="top" width="20%" >No. Rekam Medis</td>
		<td valign="top" width="30%" >: <?php echo $no_rkm_medis;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Jenis Kelamin</td>
		<td valign="top" width="30%" >: <?php echo $jk;?></td>
		<td valign="top" width="20%" >Ruang / Unit</td>
		<td valign="top" width="30%" >: <?php echo $nm_poli;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Pekerjaan</td>
		<td valign="top" width="30%" >: -</td>
		<td valign="top" width="20%" >Tanggal Masuk</td>
		<td valign="top" width="30%" >: <?php echo $tgl_registrasi;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Alamat</td>
		<td colspan="3" valign="top" >: <?php echo $almt_pj;?></td>
		
		</tr>
		</table>
		
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" >Keluhan Utama</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php echo $resume_keluhanutama;?></td>
		</tr>
		<tr>
		<td valign="top" >Jalannya Penyakit</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $resume_jalannyapenyakit;?></td>
		</tr>
		<tr>
		<td valign="top" >Pemeriksaan Penunjang</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $resume_pemeriksaanpenunjang;?></td>
		</tr>
		<tr>
		<td valign="top" >Hasil Laboratorium</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $resume_hasillaborat;?></td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" style="font-size:9pt;font-weight:bold;">Diagnosa Akhir</td>
		<td valign="top" width="1%"  ></td>
		<td valign="top" width="55%" ></td>
		<td valign="top" width="14%" style="font-size:9pt;font-weight:bold;">ICD</td>
		</tr>
		<tr>
		<td valign="top" >Diagnosa Utama</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $resume_diagnosautama;?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >1. Diagnosa Sekunder</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $resume_diagnosasekunder;?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >2. Diagnosa Sekunder</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $resume_diagnosasekunder2;?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >3. Diagnosa Sekunder</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $resume_diagnosasekunder3;?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >4. Diagnosa Sekunder</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $resume_diagnosasekunder4;?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >Prosedur / Tindakan Utama</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $resume_prosedurutama;?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >1. Prosedur Sekunder</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $resume_prosedursekunder;?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >2. Prosedur Sekunder</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $resume_prosedursekunder2;?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >3. Prosedur Sekunder</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $resume_prosedursekunder3;?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >Kondisi Pulang</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $resume_kondisipulang;?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >Obat - Obatan Pulang / Nasehat</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $resume_obatpulang;?></td>
		<td valign="top" ></td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Mamuju, <?php echo date('d-m-Y');?><br>Dokter Penanggung Jawab<br><br>
		<div class="qrcodeRESUME" style="margin-top:0px;margin-bottom:0px;;"></div>
				<br><?php echo $nm_dokter;?><br>
	   
				<script>
				jQuery('.qrcodeRESUME').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php echo $nm_dokter;?>"
		  
				});
				</script>
		</td>
		</tr>
		</table>
		
		
	<?php } ?>
	
	<?php if (isset($barisresumeranap["no_rawat"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">RESUME MEDIS PASIEN RAWAT INAP</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >Nama</td>
		<td valign="top" width="30%" >: <?php echo $nama_pasien;?></td>
		<td valign="top" width="20%" >No. Rawat</td>
		<td valign="top" width="30%" >: <?php echo $norawat;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Umur</td>
		<td valign="top" width="30%" >: <?php echo $umurdaftar;?></td>
		<td valign="top" width="20%" >No. Rekam Medis</td>
		<td valign="top" width="30%" >: <?php echo $no_rkm_medis;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Jenis Kelamin</td>
		<td valign="top" width="30%" >: <?php echo $jk;?></td>
		<td valign="top" width="20%" >Ruang / Unit</td>
		<td valign="top" width="30%" >: <?php echo $bariskamarinap["kd_kamar"];?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Lahir</td>
		<td valign="top" width="30%" >: <?php echo $tgl_lahir;?></td>
		<td valign="top" width="20%" >Tanggal Masuk</td>
		<td valign="top" width="30%" >: <?php echo $sep_tglsep;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Pekerjaan</td>
		<td valign="top" width="30%" >: -</td>
		<td valign="top" width="20%" >Tanggal Keluar</td>
		<td valign="top" width="30%" >: <?php echo $bariskamarinap["tgl_keluar"];?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Alamat</td>
		<td colspan="3" valign="top" >: <?php echo $almt_pj;?></td>
		
		</tr>
		</table>
		
		<hr>
		
		<table width="100%"  style="font-size:8pt;" border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" >Diagnosa Awal Masuk</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php echo $barisresumeranap["diagnosa_awal"];?></td>
		</tr>
		<tr>
		<td valign="top" >Alasan Masuk Dirawat</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["alasan"];?></td>
		</tr>
		<tr>
		<td valign="top" >Keluhan Utama</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["keluhan_utama"];?></td>
		</tr>
		<tr>
		<td valign="top" >Pemeriksaan Fisik</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["pemeriksaan_fisik"];?></td>
		</tr>
		<tr>
		<td valign="top" >Jalannya Penyakit Selama Perawatan</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["jalannya_penyakit"];?></td>
		</tr>
		<tr>
		<td valign="top" >Pemeriksaan Radiologi</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["pemeriksaan_penunjang"];?></td>
		</tr>
		<tr>
		<td valign="top" >Pemeriksaan Laboratorium</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["hasil_laborat"];?></td>
		</tr>
		<tr>
		<td valign="top" >Tindakan/Operasi</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["tindakan_dan_operasi"];?></td>
		</tr>
		<tr>
		<td valign="top" >Pemberian Obat</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["obat_di_rs"];?></td>
		</tr>
		</table>
		<hr>
		<table width="100%"  style="font-size:8pt;" border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" style="font-size:9pt;font-weight:bold;">Diagnosa Akhir</td>
		<td valign="top" width="1%"  ></td>
		<td valign="top" width="55%" ></td>
		<td valign="top" width="14%" style="font-size:9pt;font-weight:bold;">ICD</td>
		</tr>
		<tr>
		<td valign="top" >Diagnosa Utama</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["diagnosa_utama"];?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >1. Diagnosa Sekunder</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["diagnosa_sekunder"];?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >2. Diagnosa Sekunder</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["diagnosa_sekunder2"];?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >3. Diagnosa Sekunder</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["diagnosa_sekunder3"];?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >4. Diagnosa Sekunder</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["diagnosa_sekunder4"];?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >Prosedur / Tindakan Utama</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["prosedur_utama"];?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >1. Prosedur Sekunder</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["prosedur_sekunder"];?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >2. Prosedur Sekunder</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["prosedur_sekunder2"];?></td>
		<td valign="top" ></td>
		</tr>
		<tr>
		<td valign="top" >3. Prosedur Sekunder</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["prosedur_sekunder3"];?></td>
		<td valign="top" ></td>
		</tr>
		</table>
		<hr>
		<table width="100%"  style="font-size:8pt;" border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" >Alergi/Reaksi Obat</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php echo $barisresumeranap["alergi"];?></td>
		</tr>
		<tr>
		<td valign="top" >Diet</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["diet"];?></td>
		</tr>
		<tr>
		<td valign="top" >Instruksi dan Edukasi</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisresumeranap["edukasi"];?></td>
		</tr>
		</table>
		<hr>
		<table width="100%"  style="font-size:8pt;" border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >Keadaan Pulang</td>
		<td valign="top" width="30%" >: <?php echo $barisresumeranap["keadaan"];?></td>
		<td valign="top" width="20%" >Cara Keluar</td>
		<td valign="top" width="30%" >: <?php echo $barisresumeranap["cara_keluar"];?></td>
		</tr>
		<tr>
		<td valign="top" >Dilanjutkan</td>
		<td valign="top" >: <?php echo $barisresumeranap["dilanjutkan"];?></td>
		<td valign="top" >Tanggal Kontrol</td>
		<td valign="top" >: <?php echo $barisresumeranap["kontrol"];?></td>
		</tr>
		<tr>
		<td valign="top" >Obat Pulang</td>
		<td colspan="3" valign="top" >: <?php echo $barisresumeranap["obat_pulang"];?></td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Mamuju, <?php echo date('d-m-Y');?><br>DPJP<br><br>
		<div class="qrcodeRESUMERANAP" style="margin-top:0px;margin-bottom:0px;;"></div>
				<br><?php if (isset($barisresumeranap["nm_dokter"])) { echo $barisresumeranap["nm_dokter"]; }?><br>
	   
				<script>
				jQuery('.qrcodeRESUMERANAP').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php if (isset($barisresumeranap["nm_dokter"])) { echo $barisresumeranap["nm_dokter"]; }?>"
		  
				});
				</script>
		</td>
		</tr>
		</table>
	<?php } ?>	
		
	<?php if($sep_nmpoli=='GIGI' or $sep_nmpoli=='GIGI BEDAH MULUT' or $sep_nmpoli=='REHABILITASI MEDIK' or $sep_nmpoli=='GIGI PROSTHODONTI' or $sep_nmpoli=='GIGI ENDODONSI' or $sep_nmpoli=='REHABILITASI MEDIK') { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">LEMBAR ASESSMENT PELAYANAN GIGI</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >Nama Pasien</td>
		<td valign="top" width="70%" >: <?php echo $nama_pasien;?></td>
		
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Lahir</td>
		<td valign="top" width="70%" >: <?php echo $tgl_lahir;?></td>
		
		</tr>
		<tr>
		<td valign="top" width="20%" >Nomor Rekam Medis</td>
		<td valign="top" width="70%" >: <?php echo $no_rkm_medis;?></td>
		
		</tr>
		
		<tr>
		<td valign="top" width="20%" >Nomor HP</td>
		<td valign="top" width="70%" >: <?php echo $sep_notlp;?></td>
		
		</tr>
		<tr>
		<td valign="top" width="20%" >Nomor JKN</td>
		<td valign="top" width="70%" >: <?php echo $sep_nokartu;?></td>
		
		</tr>
		
		</table>
		<hr>
		<table width="100%" border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >Tanggal Asessmen</td>
		<td valign="top" width="70%" >: <?php echo $barisralan["tgl_perawatan"];?></td>
		
		</tr>
		<tr>
		<td valign="top" width="20%" >Diagnosis</td>
		<td valign="top" width="70%" >: <?php echo $barisralan["penilaian"];?></td>
		
		</tr>
		<tr>
		<td valign="top" width="20%" >Frekuensi Kunjungan</td>
		<td valign="top" width="70%" >: <?php echo $barisralan["evaluasi"];?></td>
		
		</tr>
		
		<tr>
		<td valign="top" width="20%" >DPJP </td>
		<td valign="top" width="70%" >: <?php echo $barisralan["nama"];?></td>
		
		</tr>
		
		</table>
		
		<hr>
		
		<table class="table table-bordered">
		<thead>
		<tr >
		<th style="font-weight:bold;" valign="top" width="20%" >Tanggal Kunjungan</th>
		<th style="font-weight:bold;" valign="top" width="40%" >Tindakan</th>
		<th style="font-weight:bold;" valign="top" width="20%" >Tanda Tangan Pasien</th>
		<th style="font-weight:bold;" valign="top" width="20%" >Tanda Tangan DPJP</th>
		
		</tr>
		</thead>
		<tbody>
		<tr>
		<td valign="top">	<?php echo $barisralan["tgl_perawatan"];?></td>
		<td valign="top">	<?php echo $barisralan["instruksi"];?></td>
		<td valign="top">
		<div class="qrcodePASIENGIGI" style="margin-top:0px;margin-bottom:0px;;"></div>
				
	   
				<script>
				jQuery('.qrcodePASIENGIGI').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php echo $nama_pasien;?>"
		  
				});
				</script>
		</td>
		<td valign="top">
		<div class="qrcodeDOKTERGIGI" style="margin-top:0px;margin-bottom:0px;;"></div>
			
	   
				<script>
				jQuery('.qrcodeDOKTERGIGI').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php echo $barisralan["nama"];?>"
		  
				});
				</script>
		</td>
		
		</tr>
		</tbody>
		
		</table>
		
		

	
	<?php } ?>
		
	<?php if (isset($baristriase["no_rawat"])) { ?>
	
	<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">LEMBAR TRIASE IGD</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >Nama</td>
		<td valign="top" width="30%" >: <?php echo $nama_pasien;?></td>
		<td valign="top" width="20%" >No. Rawat</td>
		<td valign="top" width="30%" >: <?php echo $norawat;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Lahir / Umur</td>
		<td valign="top" width="30%" >: <?php echo $umurdaftar;?> / <?php echo $tgl_lahir;?></td>
		<td valign="top" width="20%" >No. Rekam Medis</td>
		<td valign="top" width="30%" >: <?php echo $no_rkm_medis;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Jenis Kelamin</td>
		<td valign="top" width="30%" >: <?php echo $jk;?></td>
		<td valign="top" width="20%" >Ruang / Unit</td>
		<td valign="top" width="30%" >: <?php echo $nm_poli;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Pekerjaan</td>
		<td valign="top" width="30%" >: -</td>
		<td valign="top" width="20%" >Tanggal Masuk</td>
		<td valign="top" width="30%" >: <?php echo $tgl_registrasi;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Alamat</td>
		<td colspan="3" valign="top" >: <?php echo $almt_pj;?></td>
		
		</tr>
		</table>
		
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" >Tanggal Kunjungan</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php if (isset($baristriase["tgl_kunjungan"])) { echo $baristriase["tgl_kunjungan"]; }?></td>
		</tr>
		<tr>
		<td valign="top" >Cara Datang</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php if (isset($baristriase["cara_masuk"])) { echo $baristriase["cara_masuk"]; }?></td>
		</tr>
		<tr>
		<td valign="top" >Macam Kasus</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php if (isset($baristriase["macam_kasus"])) { echo $baristriase["macam_kasus"]; }?></td>
		</tr>
		<tr>
		<td valign="top" >Anamnesa Singkat</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php if (isset($baristriasesekunder["anamnesa_singkat"])) { echo $baristriasesekunder["anamnesa_singkat"]; }?> <?php if (isset($baristriaseprimer["keluhan_utama"])) { echo $baristriaseprimer["keluhan_utama"]; }?></td>
		</tr>
		<tr>
		<td valign="top" >Tanda Vital</td>
		<td valign="top" >:</td>
		<td valign="top" >Tekanan Darah : <?php if (isset($baristriase["tekanan_darah"])) { echo $baristriase["tekanan_darah"]; }?> mmHg <br> Nadi : <?php if (isset($baristriase["nadi"])) { echo $baristriase["nadi"]; }?> x/menit <br> Pernafasan : <?php if (isset($baristriase["pernafasan"])) { echo $baristriase["pernafasan"]; }?> x/menit <br> Suhu : <?php if (isset($baristriase["suhu"])) { echo $baristriase["suhu"]; }?> C <br> SPO2 : <?php if (isset($baristriase["saturasi_o2"])) { echo $baristriase["saturasi_o2"]; }?></td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<?php foreach ($hasiltriase1 as $value):?>
		<tr>
		<td valign="top" width="30%" ><?php echo $value["nama_pemeriksaan"] ?></td> 
		<td valign="top" width="1%">:</td>
		<td valign="top" width="69%" ><?php echo $value["pengkajian_skala1"] ?></td>
		</tr>
		<?php endforeach; ?>
		</table><table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<?php foreach ($hasiltriase2 as $value):?>
		<tr>
		<td valign="top" width="30%" ><?php echo $value["nama_pemeriksaan"] ?></td> 
		<td valign="top" width="1%">:</td>
		<td valign="top" width="69%" ><?php echo $value["pengkajian_skala2"] ?></td>
		</tr>
		<?php endforeach; ?>
		</table>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<?php foreach ($hasiltriase3 as $value):?>
		<tr>
		<td valign="top" width="30%" ><?php echo $value["nama_pemeriksaan"] ?></td> 
		<td valign="top" width="1%">:</td>
		<td valign="top" width="69%" ><?php echo $value["pengkajian_skala3"] ?></td>
		</tr>
		<?php endforeach; ?>
		</table>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<?php foreach ($hasiltriase4 as $value):?>
		<tr>
		<td valign="top" width="30%" ><?php echo $value["nama_pemeriksaan"] ?></td> 
		<td valign="top" width="1%">:</td>
		<td valign="top" width="69%" ><?php echo $value["pengkajian_skala4"] ?></td>
		</tr>
		<?php endforeach; ?>
		</table>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<?php foreach ($hasiltriase5 as $value):?>
		<tr>
		<td valign="top" width="30%" ><?php echo $value["nama_pemeriksaan"] ?></td> 
		<td valign="top" width="1%">:</td>
		<td valign="top" width="69%" ><?php echo $value["pengkajian_skala5"] ?></td>
		</tr>
		<?php endforeach; ?>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" ></td>
		<td valign="top" width="1%" ></td>
		<td valign="top" width="69%" ></td>
		</tr>
		<tr>
		<td valign="top" >PLAN</td>
		<td valign="top" >:</td>
		<td valign="top" style="font-weight:bold"><?php if (isset($baristriasesekunder["plan"])) { echo $baristriasesekunder["plan"]; }?> <?php if (isset($baristriaseprimer["plan"])) { echo $baristriaseprimer["plan"]; }?></td>
		</tr>
		</table>



		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Mamuju, <?php if (isset($baristriasesekunder["tanggaltriase"])) { echo $baristriasesekunder["tanggaltriase"]; }?> <?php if (isset($baristriaseprimer["tanggaltriase"])) { echo $baristriaseprimer["tanggaltriase"]; }?><br>Pelaksana Triase<br><br>
		<div class="qrcodeTRIASE" style="margin-top:0px;margin-bottom:0px;;"></div>
				<br><?php if (isset($baristriasesekunder["nama"])) { echo $baristriasesekunder["nama"]; }?> <?php if (isset($baristriaseprimer["nama"])) { echo $baristriaseprimer["nama"]; }?><br>
	   
				<script>
				jQuery('.qrcodeTRIASE').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php if (isset($baristriasesekunder["nama"])) { echo $baristriasesekunder["nama"]; }?> <?php if (isset($baristriaseprimer["nama"])) { echo $baristriaseprimer["nama"]; }?>"
		  
				});
				</script>
		</td>
		</tr>
		</table>
		
	<?php } ?>

		<!-- Penilaian Dehidrasi -->
<?php
$dehidrasi=mysqli_fetch_array(bukaquery("
SELECT penilaian_dehidrasi_baru.*,dokter.nm_dokter
FROM penilaian_dehidrasi_baru
INNER JOIN dokter ON penilaian_dehidrasi_baru.kd_dokter=dokter.kd_dokter
WHERE penilaian_dehidrasi_baru.no_rawat='$norawat'
ORDER BY tanggal DESC LIMIT 1
"));

/* ambil data pasien untuk alamat + tanggal registrasi */
$data_pasien=mysqli_fetch_array(bukaquery("
SELECT pasien.alamat, reg_periksa.tgl_registrasi
FROM reg_periksa 
INNER JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis
WHERE reg_periksa.no_rawat='$norawat'
"));

$alamat = isset($data_pasien["alamat"]) ? $data_pasien["alamat"] : "";
$tgl_registrasi = isset($data_pasien["tgl_registrasi"]) ? $data_pasien["tgl_registrasi"] : date("Y-m-d");

/* hitung umur */
$umur = isset($tgl_lahir) ? date_diff(date_create($tgl_lahir), date_create('today'))->y." Tahun" : "";

?>

<?php if(isset($dehidrasi["no_rawat"])) { ?>

<div style="page-break-before:always;"></div>

<table width="100%" border="0">
<tr>

<td width="8%">
<img src="images/logo.png" height="60">
</td>

<td>

<div style="font-size:14pt;font-weight:bold;">
RUMAH SAKIT MITRA MANAKARRA
</div>

<div style="font-size:9pt;font-weight:bold;">
Melayani Setulus Hati
</div>

<div style="font-size:8pt;">
Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Sulawesi Barat
</div>

<div style="font-size:8pt;">
Email : rs.mitra.manakarra@gmail.com | Telp : 08123456789
</div>

</td>

</tr>
</table>

<hr>

<table width="100%">
<tr>
<td align="center" style="font-size:12pt;font-weight:bold;">
LEMBAR PENILAIAN DERAJAT DEHIDRASI
</td>
</tr>
</table>

<hr>

<!-- DATA PASIEN -->

<table width="100%" border="0" cellpadding="4">

<tr>
<td width="15%">Nama</td>
<td width="35%">: <?php echo $nama_pasien;?></td>

<td width="18%">No. Rekam Medis</td>
<td width="32%">: <?php echo $no_rkm_medis;?></td>
</tr>

<tr>
<td>Tgl Lahir / Umur</td>
<td>: <?php echo $tgl_lahir;?> / <?php echo $umur;?></td>

<td>No. Rawat</td>
<td>: <?php echo $norawat;?></td>
</tr>

<tr>
<td>Jenis Kelamin</td>
<td>: <?php echo $jk;?></td>

<td>Ruang / Unit</td>
<td>: <?php echo $nm_poli;?></td>
</tr>

<tr>
<td>Alamat</td>
<td colspan="3">: <?php echo $alamat;?></td>
</tr>

</table>

<br>

<!-- TABEL PENILAIAN -->

<table width="100%" border="1" cellspacing="0" cellpadding="5">

<tr style="background:#eeeeee">
<td width="5%" align="center"><b>No</b></td>
<td width="35%" align="center"><b>Pemeriksaan</b></td>
<td width="45%" align="center"><b>Hasil Penilaian</b></td>
<td width="15%" align="center"><b>Skor</b></td>
</tr>

<tr>
<td align="center">1</td>
<td>Status Mental</td>
<td><?php echo $dehidrasi["penilaian1"];?></td>
<td align="center"><?php echo $dehidrasi["penilaian_nilai1"];?></td>
</tr>

<tr>
<td align="center">2</td>
<td>Rasa Haus</td>
<td><?php echo $dehidrasi["penilaian2"];?></td>
<td align="center"><?php echo $dehidrasi["penilaian_nilai2"];?></td>
</tr>

<tr>
<td align="center">3</td>
<td>Degup Jantung</td>
<td><?php echo $dehidrasi["penilaian3"];?></td>
<td align="center"><?php echo $dehidrasi["penilaian_nilai3"];?></td>
</tr>

<tr>
<td align="center">4</td>
<td>Kualitas Denyut Nadi</td>
<td><?php echo $dehidrasi["penilaian4"];?></td>
<td align="center"><?php echo $dehidrasi["penilaian_nilai4"];?></td>
</tr>

<tr>
<td align="center">5</td>
<td>Turgor Kulit</td>
<td><?php echo $dehidrasi["penilaian5"];?></td>
<td align="center"><?php echo $dehidrasi["penilaian_nilai5"];?></td>
</tr>

<tr>
<td align="center">6</td>
<td>Pernafasan</td>
<td><?php echo $dehidrasi["penilaian6"];?></td>
<td align="center"><?php echo $dehidrasi["penilaian_nilai6"];?></td>
</tr>

<tr>
<td align="center">7</td>
<td>Mata</td>
<td><?php echo $dehidrasi["penilaian7"];?></td>
<td align="center"><?php echo $dehidrasi["penilaian_nilai7"];?></td>
</tr>

<tr>
<td align="center">8</td>
<td>Air Mata</td>
<td><?php echo $dehidrasi["penilaian8"];?></td>
<td align="center"><?php echo $dehidrasi["penilaian_nilai8"];?></td>
</tr>

<tr>
<td align="center">9</td>
<td>Mulut dan Lidah</td>
<td><?php echo $dehidrasi["penilaian9"];?></td>
<td align="center"><?php echo $dehidrasi["penilaian_nilai9"];?></td>
</tr>

<tr style="background:#f5f5f5">
<td colspan="3" align="right"><b>Total Score</b></td>
<td align="center"><b><?php 
    $total = (float)$dehidrasi["penilaian_nilai1"]
           + (float)$dehidrasi["penilaian_nilai2"]
           + (float)$dehidrasi["penilaian_nilai3"]
           + (float)$dehidrasi["penilaian_nilai4"]
           + (float)$dehidrasi["penilaian_nilai5"]
           + (float)$dehidrasi["penilaian_nilai6"]
           + (float)$dehidrasi["penilaian_nilai7"]
           + (float)$dehidrasi["penilaian_nilai8"]
           + (float)$dehidrasi["penilaian_nilai9"];
    $rata2 = $total / 9;
    $persen = round($rata2 + 6, 2);
    echo $persen . " % (Persen)";
?></b></td>
</tr>

<tr style="background:#f5f5f5">
<td colspan="3"></td>
<td align="center"><b><?php echo $dehidrasi["hasil_penilaian"];?></b></td>
</tr>

</table>

<br><br>

<!-- TANDA TANGAN -->

<table width="100%">
<tr>
<td width="70%"></td>

<td width="30%" align="center">

Mamuju, <?php echo date("d-m-Y", strtotime($tgl_registrasi)); ?><br>
Dokter Pemeriksa

<br><br>

<div class="qrcode_dehidrasi"></div>

<br>

<b><?php echo $dehidrasi["nm_dokter"];?></b>

</td>

</tr>
</table>

<script>
jQuery('.qrcode_dehidrasi').qrcode({
width:90,
height:90,
text:"Dokumen ini ditandatangani secara elektronik oleh <?php echo $dehidrasi['nm_dokter'];?>"
});
</script>

<?php } ?>
<!-- Penilaian Dehidrasi -->

		<!-- SURAT PERNYATAAN KRONOLOGIS -->
		<?php
		// ========================
		// Surat Pernyataan Kronologis (Final Fix Gambar)
		// ========================

		// Base URL aplikasi
		$base_url = "https://192.168.100.222/webapps/pernyataankronologis/";

		// Inisialisasi variabel
		$barissurat  = null;
		$gambarsurat = null;

		// Ambil no_surat dari GET jika ada
		$no_surat = isset($_GET['no_surat']) ? validTeks4($_GET['no_surat'], 50) : null;

		// Kalau no_surat kosong, coba ambil dari no_rawat terakhir
		if (empty($no_surat) && !empty($no_rawat)) {
		    $no_surat = getOne("
		        SELECT no_surat 
		        FROM surat_pernyataan_pasien_kronologis 
		        WHERE no_rawat = '$no_rawat' 
		        ORDER BY tanggal DESC 
		        LIMIT 1
		    ");
		}

		// Jalankan query hanya kalau ada no_surat
		if (!empty($no_surat)) {
		    // Ambil foto pembuat pernyataan
		    $gambarsurat = getOne("
		        SELECT photo 
		        FROM surat_pernyataan_pasien_kronologis_pembuat_pernyataan 
		        WHERE no_surat='$no_surat'
		    ");

		    // Ambil data surat + join pasien + petugas
		    $sqlsurat = "
		        SELECT 
		            s.*, 
		            pg.nama AS nama_petugas, 
		            rp.no_rkm_medis, 
		            p.nm_pasien, 
		            p.jk, 
		            p.tgl_lahir, 
		            CONCAT(rp.umurdaftar,' ',rp.sttsumur) AS umur, 
		            p.pekerjaan, 
		            p.alamat
		        FROM surat_pernyataan_pasien_kronologis s
		        INNER JOIN reg_periksa rp ON s.no_rawat = rp.no_rawat
		        INNER JOIN pasien p ON rp.no_rkm_medis = p.no_rkm_medis
		        LEFT JOIN petugas pg ON s.nip = pg.nip
		        WHERE s.no_surat = '$no_surat'
		        LIMIT 1
		    ";
		    $hasilsurat = bukaquery($sqlsurat);
		    if ($hasilsurat && mysqli_num_rows($hasilsurat) > 0) {
		        $barissurat = mysqli_fetch_array($hasilsurat);
		    }
		}
		?>

		<?php if ($barissurat) { ?>
		<div style="page-break-before:always;"></div>

		<!-- Header RS -->
		<table border="0" width="100%">
		  <tr>
		    <td width="10%"><img src="images/logo.png" height="60"></td>
		    <td align="center" width="90%">
		      <div style="font-size:14pt;font-weight:bold;">RUMAH MITRA MANAKARRA</div>
		      <div style="font-size:10pt;">Melayani Setulus Hati</div>
		      <div style="font-size:9pt;">Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat</div>
		      <div style="font-size:8pt;">Email: rs.mitra.manakarra@gmail.com | No.Telp : 08123456789</div>
		    </td>
		  </tr>
		</table>
		<hr>

		<h3 style="text-align:center;">SURAT PERNYATAAN KRONOLOGIS</h3>
		<p style="text-align:center;">Nomor : <?= $barissurat["no_surat"]; ?></p>
		<hr>

		<!-- Identitas Penanggung Jawab -->
		<p>Yang bertanda tangan di bawah ini:</p>
		<table width="100%" cellpadding="3" style="font-size:10pt;">
		  <tr><td width="30%">No. KTP</td><td>: <?= $barissurat["no_ktppj"]; ?></td></tr>
		  <tr><td>Nama</td><td>: <?= $barissurat["nama_pj"]; ?></td></tr>
		  <tr><td>Tempat, Tgl Lahir</td><td>: <?= $barissurat["tempat_lahirpj"].", ".date("d-m-Y", strtotime($barissurat["lahirpj"])); ?></td></tr>
		  <tr><td>Jenis Kelamin</td><td>: <?= $barissurat["jkpj"]; ?></td></tr>
		  <tr><td>Alamat</td><td>: <?= $barissurat["alamatpj"]; ?></td></tr>
		  <tr><td>Hubungan</td><td>: <?= $barissurat["hubungan"]; ?></td></tr>
		  <tr><td>No. Telp</td><td>: <?= $barissurat["no_telp"]; ?></td></tr>
		</table>

		<br>
		<!-- Isi kronologis -->
		<p>Kronologis kejadian:</p>
		<div style="border:1px solid #000; padding:10px; min-height:100px; font-size:10pt;">
		  <?= nl2br($barissurat["kronologis"]); ?>
		</div>

		<p style="font-size:10pt;">
		Demikian Surat Pernyataan Kronologis ini saya buat dengan sadar tanpa ada paksaan dari pihak manapun.
		</p>

		<!-- Tanda tangan -->
		<table width="100%" style="margin-top:20px; font-size:10pt;">
		  <tr>
		    <!-- Petugas -->
		    <td width="50%" align="center">
		      Petugas<br><br>
		      <div class="qrcodeKRONOLOGIS"></div>
		      <br><b><?= $barissurat["nama_petugas"]; ?></b>
		    </td>

		    <!-- Penanggung Jawab -->
		    <td width="50%" align="center">
		      Mamuju, <?= date("d-m-Y", strtotime($barissurat["tanggal"])); ?><br>
		      Penanggung Jawab Pasien<br><br>

		      <?php if (!empty($gambarsurat)) { ?>
		        <img src="<?= $base_url . $gambarsurat; ?>" 
		             alt="Tanda Tangan" style="max-width:200px;max-height:120px;"><br>
		      <?php } else { ?>
		        <div style="height:60px;"></div> <!-- placeholder kosong -->
		      <?php } ?>

		      <b><?= $barissurat["nama_pj"]; ?></b>
		    </td>
		  </tr>
		</table>

		<script>
		jQuery('.qrcodeKRONOLOGIS').qrcode({
		    text: "Ditandatangani secara elektronik oleh: <?= addslashes($barissurat['nama_petugas']); ?>",
		    width: 80,
		    height: 80
		});
		</script>
		<?php } ?>

		<!-- SURAT PERNYATAAN KRONOLOGIS -->
	
	<?php if (isset($barisusgurologi["no_rawat"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">HASIL PEMERIKSAAN USG UROLOGI</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >Nama</td>
		<td valign="top" width="30%" >: <?php echo $nama_pasien;?></td>
		<td valign="top" width="20%" >No. Rawat</td>
		<td valign="top" width="30%" >: <?php echo $norawat;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Lahir / Umur</td>
		<td valign="top" width="30%" >: <?php echo $umurdaftar;?> / <?php echo $tgl_lahir;?></td>
		<td valign="top" width="20%" >No. Rekam Medis</td>
		<td valign="top" width="30%" >: <?php echo $no_rkm_medis;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Jenis Kelamin</td>
		<td valign="top" width="30%" >: <?php echo $jk;?></td>
		<td valign="top" width="20%" >Ruang / Unit</td>
		<td valign="top" width="30%" >: <?php echo $nm_poli;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Pekerjaan</td>
		<td valign="top" width="30%" >: -</td>
		<td valign="top" width="20%" >Tanggal Masuk</td>
		<td valign="top" width="30%" >: <?php echo $tgl_registrasi;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Alamat</td>
		<td colspan="3" valign="top" >: <?php echo $almt_pj;?></td>
		
		</tr>
		</table>
		
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" >Diagnosa Klinis</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php echo $usgurologi_diagnosaklinis;?></td>
		</tr>
		<tr>
		<td valign="top" >Kiriman Dari</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $usgurologi_kirimandari;?></td>
		</tr>
		<tr>
		<td valign="top" >Ginjal Kanan</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $usgurologi_ginjalkanan;?></td>
		</tr>
		<tr>
		<td valign="top" >Ginjal Kiri</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $usgurologi_ginjalkiri;?></td>
		</tr>
		<tr>
		<td valign="top" >Ginjal Vesica Urinaria</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $usgurologi_vesica;?></td>
		</tr>
		<tr>
		<td valign="top" >Kesimpulan Tambahan</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $usgurologi_tambahan;?></td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="100%" style="font-size:9pt;font-weight:bold;">Gambar Pemeriksaan</td>
		</tr>
		<tr>
        <td><img src="http://192.168.100.222/webapps/hasilpemeriksaanusgurologi/<?php echo $gambarusgu;?>" style="max-width: 300px;"></td>
        </tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Mamuju, <?php echo date('d-m-Y');?><br>Dokter Pemeriksa<br><br>
		<div class="qrcodeDPJPUSGU" style="margin-top:0px;margin-bottom:0px;;"></div>
				<br><?php echo $nm_dokter;?><br>
	   
				<script>
				jQuery('.qrcodeDPJPUSGU').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php echo $nm_dokter;?>"
		  
				});
				</script>
		</td>
		</tr>
		</table>
	
	<?php } ?>
	
	<?php if (isset($barisusg["no_rawat"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">HASIL PEMERIKSAAN USG OBSTETRI DAN GINEKOLOGI</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >Nama</td>
		<td valign="top" width="30%" >: <?php echo $nama_pasien;?></td>
		<td valign="top" width="20%" >No. Rawat</td>
		<td valign="top" width="30%" >: <?php echo $norawat;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Lahir / Umur</td>
		<td valign="top" width="30%" >: <?php echo $umurdaftar;?> / <?php echo $tgl_lahir;?></td>
		<td valign="top" width="20%" >No. Rekam Medis</td>
		<td valign="top" width="30%" >: <?php echo $no_rkm_medis;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Jenis Kelamin</td>
		<td valign="top" width="30%" >: <?php echo $jk;?></td>
		<td valign="top" width="20%" >Ruang / Unit</td>
		<td valign="top" width="30%" >: <?php echo $nm_poli;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Pekerjaan</td>
		<td valign="top" width="30%" >: -</td>
		<td valign="top" width="20%" >Tanggal Masuk</td>
		<td valign="top" width="30%" >: <?php echo $tgl_registrasi;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Alamat</td>
		<td colspan="3" valign="top" >: <?php echo $almt_pj;?></td>
		
		</tr>
		</table>
		
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" >Diagnosa Klinis</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php echo $usg_diagnosaklinis;?></td>
		</tr>
		<tr>
		<td valign="top" >Kiriman Dari</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $usg_kirimandari;?></td>
		</tr>
		<tr>
		<td valign="top" >Kesimpulan</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $usg_kesimpulan;?></td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="100%" style="font-size:9pt;font-weight:bold;">Gambar Pemeriksaan</td>
		</tr>
		
		<tr>
        <td><img src="http://192.168.100.222/webapps/hasilpemeriksaanusg/<?php echo $gambarusg;?>" style="max-width: 300px;"></td>
        </tr>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Mamuju, <?php echo date('d-m-Y');?><br>Dokter Pemeriksa<br><br>
		<div class="qrcodeUSG" style="margin-top:0px;margin-bottom:0px;;"></div>
				<br><?php echo $nm_dokter;?><br>
	   
				<script>
				jQuery('.qrcodeUSG').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php echo $nm_dokter;?>"
		  
				});
				</script>
		</td>
		</tr>
		</table>
	
	<?php } ?>
	
	<?php if (isset($barisekg["no_rawat"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">HASIL PEMERIKSAAN EKG</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >Nama</td>
		<td valign="top" width="30%" >: <?php echo $nama_pasien;?></td>
		<td valign="top" width="20%" >No. Rawat</td>
		<td valign="top" width="30%" >: <?php echo $norawat;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Lahir / Umur</td>
		<td valign="top" width="30%" >: <?php echo $umurdaftar;?> / <?php echo $tgl_lahir;?></td>
		<td valign="top" width="20%" >No. Rekam Medis</td>
		<td valign="top" width="30%" >: <?php echo $no_rkm_medis;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Jenis Kelamin</td>
		<td valign="top" width="30%" >: <?php echo $jk;?></td>
		<td valign="top" width="20%" >Ruang / Unit</td>
		<td valign="top" width="30%" >: <?php echo $nm_poli;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Pekerjaan</td>
		<td valign="top" width="30%" >: -</td>
		<td valign="top" width="20%" >Tanggal Masuk</td>
		<td valign="top" width="30%" >: <?php echo $tgl_registrasi;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Alamat</td>
		<td colspan="3" valign="top" >: <?php echo $almt_pj;?></td>
		
		</tr>
		</table>
		
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" >Diagnosa Klinis</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php if (isset($barisekg["diagnosa_klinis"])) { echo $barisekg["diagnosa_klinis"]; }?></td>
		</tr>
		<tr>
		<td valign="top" >Kiriman Dari</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php if (isset($barisekg["kiriman_dari"])) { echo $barisekg["kiriman_dari"]; }?></td>
		</tr>
		<tr>
		<td valign="top" >Kesimpulan</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php if (isset($barisekg["kesimpulan"])) { echo $barisekg["kesimpulan"]; }?></td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="100%" style="font-size:9pt;font-weight:bold;">Gambar Pemeriksaan</td>
		</tr>
		
		<tr>
        <td><img src="http://192.168.100.222/webapps/hasilpemeriksaanekg/<?php echo $gambarekg;?>" style="max-width: 300px;"></td>
        </tr>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="50%" style="font-size:7pt;text-align:left;">
		</td>
		<td valign="top" width="50%" >Mamuju, <?php echo date('d-m-Y');?><br>Dokter Penanggung Jawab<br><br>
		<img src="https://cdn.shopify.com/shopifycloud/growth_tools/assets/qr-code/shopify-faae7065b7b351d28495b345ed76096c03de28bac346deb1e85db632862fd0e4.png" alt="Image" height="70">
		
		<br><br><?php if (isset($barisekg["nm_dokter"])) { echo $barisekg["nm_dokter"]; }?></td>
		</tr>
		</table>
	
	<?php } ?>

	<?php if (isset($barisecho["no_rawat"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>

		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">HASIL PEMERIKSAAN ECHO</td>
		</tr>
		</table>
		<hr>

		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >Nama</td>
		<td valign="top" width="30%" >: <?php echo $nama_pasien;?></td>
		<td valign="top" width="20%" >No. Rawat</td>
		<td valign="top" width="30%" >: <?php echo $norawat;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Lahir / Umur</td>
		<td valign="top" width="30%" >: <?php echo $umurdaftar;?> / <?php echo $tgl_lahir;?></td>
		<td valign="top" width="20%" >No. Rekam Medis</td>
		<td valign="top" width="30%" >: <?php echo $no_rkm_medis;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Jenis Kelamin</td>
		<td valign="top" width="30%" >: <?php echo $jk;?></td>
		<td valign="top" width="20%" >Ruang / Unit</td>
		<td valign="top" width="30%" >: <?php echo $nm_poli;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Pekerjaan</td>
		<td valign="top" width="30%" >: -</td>
		<td valign="top" width="20%" >Tanggal Masuk</td>
		<td valign="top" width="30%" >: <?php echo $tgl_registrasi;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Alamat</td>
		<td colspan="3" valign="top" >: <?php echo $almt_pj;?></td>
		</tr>
		</table>
		<hr>

		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" >Tanggal Pemeriksaan</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php echo $echo_tanggal;?></td>
		</tr>
		<tr>
		<td valign="top" >Diagnosa Klinis</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $echo_diagnosaklinis;?></td>
		</tr>
		<tr>
		<td valign="top" >Kiriman Dari</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $echo_kirimandari;?></td>
		</tr>
		<tr>
		<td valign="top" >Kesimpulan</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $echo_kesimpulan;?></td>
		</tr>
		</table>
		<hr>

		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="100%" style="font-size:9pt;font-weight:bold;">Gambar Pemeriksaan</td>
		</tr>
		<tr>
		<td><img src="http://192.168.100.222/webapps/hasilpemeriksaanecho/<?php echo $gambarecho;?>" style="max-width: 300px;"></td>
		</tr>
		</table>
		<hr>

		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="50%" style="font-size:7pt;text-align:left;">
		</td>
		<td valign="top" width="50%" >Mamuju, <?php echo date('d-m-Y', strtotime($echo_tanggal));?><br>Dokter Penanggung Jawab<br><br>
		<div class="qrcodeECHO" style="display:inline-block;"></div>
		<script>
		jQuery('.qrcodeECHO').qrcode({
			text: "ditandatangani secara elektronik oleh : <?php echo $echo_nmdokter;?>"
		});
		</script>
		<br><br><?php echo $echo_nmdokter;?></td>
		</tr>
		</table>

	<?php } ?>

<?php
$no_rawat = validTeks4((isset($_GET['no_rawat']) ? $_GET['no_rawat'] : NULL), 20);

// Ambil data ESWL
$query = "
    SELECT eswl.*, d.nm_dokter, p.nama AS nama_petugas
    FROM hasil_tindakan_eswl eswl
    JOIN dokter d ON eswl.kd_dokter = d.kd_dokter
    JOIN petugas p ON eswl.nip = p.nip
    WHERE eswl.no_rawat = '$no_rawat'
    ORDER BY eswl.mulai DESC
    LIMIT 1
";

$hasil = bukaquery($query);
$data = mysqli_fetch_array($hasil);
if ($data) { // custom by ChatGPT: hanya tampil jika ada data ESWL
?>

<div style="page-break-before:always;"></div>
<br>

<!-- Header Rumah Sakit -->


<table border="0">
   		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="70"></td>
		<td valign="top" width="88%" >
				<div style="font-size:14pt;text-align:center;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:16pt;text-align:center;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:center;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:center;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="70"></td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">LAPORAN HASIL TINDAKAN ESWL</td>
		</tr>
		</table>
		<hr>


    <table width="100%" cellpadding="5" style="font-size:10pt;">
        <tr><td width="30%">No. Rawat</td><td>: <?= $data['no_rawat'] ?></td></tr>
        <tr><td width="25%">No. RM</td><td>: <?php echo $no_rkm_medis; ?></td></tr>
        <tr><td>Nama Pasien</td><td>: <?php echo $nm_pasien; ?></td></tr>
        <tr><td>Umur</td><td>: <?php echo $umurdaftar . " " . $sttsumur; ?></td></tr>
        <tr><td>Jenis Kelamin</td><td>: <?php echo $jk; ?></td></tr>
        <tr><td>Tanggal Lahir</td><td>: <?php echo $tgl_lahir; ?></td></tr>
   <!--     <tr><td>Tanggal Registrasi</td><td>: <?php echo $tgl_registrasi; ?> <?php echo $jam_reg; ?></td></tr>  -->
        <tr><td>Waktu Mulai</td><td>: <?= $data['mulai'] ?></td></tr>
        <tr><td>Waktu Selesai</td><td>: <?= $data['selesai'] ?></td></tr>
        <tr><td>Dokter Ahli Bedah</td><td>: <?= $data['nm_dokter'] ?></td></tr>
        <tr><td>Asisten/Perawat</td><td>: <?= $data['nama_petugas'] ?></td></tr>
        <tr><td>Keluhan</td><td>: <?= $data['keluhan'] ?></td></tr>
        <tr><td>Riwayat Penyakit</td><td>: <?= $data['riwayat_penyakit'] ?></td></tr>
        <tr><td>Riwayat Operasi</td><td>: <?= $data['riwayat_operasi'] ?></td></tr>
        <tr><td>Riwayat ESWL</td><td>: <?= $data['riwayat_eswl'] ?></td></tr>
        <tr><td>Tekanan Darah</td><td>: <?= $data['td'] ?></td></tr>
        <tr><td>Nadi</td><td>: <?= $data['nadi'] ?></td></tr>
        <tr><td>RR</td><td>: <?= $data['rr'] ?></td></tr>
        <tr><td>Suhu</td><td>: <?= $data['suhu'] ?></td></tr>
        <tr><td>Status Urologi</td><td>: <?= $data['status_urologi'] ?></td></tr>
        <tr><td>Urine Rutin</td><td>: <?= $data['penunjang_urine'] ?></td></tr>
        <tr><td>Darah Rutin</td><td>: <?= $data['penunjang_darah'] ?></td></tr>
        <tr><td>USG Urologi</td><td>: <?= $data['penunjang_usg'] ?></td></tr>
        <tr><td>BNO</td><td>: <?= $data['penunjang_bno'] ?></td></tr>
        <tr><td>CT Scan</td><td>: <?= $data['penunjang_ctscan'] ?></td></tr>
        <tr><td>Lokasi Tembakan</td><td>: <?= $data['lokasi'] ?></td></tr>
        <tr><td>Pole</td><td>: <?= $data['pole'] ?></td></tr>
        <tr><td>Hidronefrosis</td><td>: <?= $data['hidroneprosis'] ?></td></tr>
        <tr><td>Tindakan Eswl Ke</td><td>: <?= $data['tindakan_eswl'] ?></td></tr>
        <tr><td>Guide</td><td>: <?= $data['tindakan_guide'] ?></td></tr>
        <tr><td>Ukuran Batu</td><td>: <?= $data['tindakan_ukuran'] ?></td></tr>
        <tr><td>Analgetik</td><td>: <?= $data['tindakan_analgetik'] ?></td></tr>
        <tr><td>Power Max</td><td>: <?= $data['tindakan_power'] ?></td></tr>
        <tr><td>Frekuensi</td><td>: <?= $data['tindakan_frekuensi'] ?></td></tr>
        <tr><td>Jumlah Tembakan</td><td>: <?= $data['tindakan_tembakan'] ?></td></tr>
        <tr><td>Durasi</td><td>: <?= $data['tindakan_durasi'] ?></td></tr>
        <tr><td>Keluhan Selama Tindakan</td><td>: <?= $data['tindakan_keluhan'] ?></td></tr>
        <tr><td>Komplikasi</td><td>: <?= $data['tindakan_komplikasi'] ?></td></tr>
        <tr><td>Evaluasi Post Tindakan</td><td>: <?= $data['tindakan_evaluasi'] ?></td></tr>
        <tr><td>Advice Post Tindakan</td><td>: <?= $data['tindakan_advice'] ?></td></tr>
    </table>

    <hr>
    <table width="100%" style="font-size:10pt;">
        <tr>
            <td></td>
            <td align="center">
                Mamuju, <?= date("d-m-Y") ?><br>
                Dokter Penanggung Jawab<br><br>
                <div class="qrcodeESWL"></div><br>
                <?= $data['nm_dokter'] ?>
            </td>
        </tr>
    </table>

    <script>
        jQuery('.qrcodeESWL').qrcode({
            text: "ditandatangani secara elektronik oleh : <?= $data['nm_dokter'] ?>"
        });
    </script>

<?php }  ?>

<!-- contoh latihan eswl yang dirapikan -->


	<?php if (isset($barispermintaanlab["no_rawat"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">RINGKASAN HASIL PEMERIKSAAN LABORATORIUM</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >No. Rekam Medis</td>
		<td valign="top" width="30%" >: <?php echo $no_rkm_medis;?></td>
		<td valign="top" width="20%" >No. Rawat</td>
		<td valign="top" width="30%" >: <?php echo $norawat;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Nama</td>
		<td valign="top" width="30%" >: <?php echo $nama_pasien;?></td>
		<td valign="top" width="20%" >No. Permintaan Lab</td>
		<td valign="top" width="30%" >: -</td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Lahir / Umur</td>
		<td valign="top" width="30%" >: <?php echo $umurdaftar;?> / <?php echo $tgl_lahir;?></td>
		<td valign="top" width="20%" >Poliklinik/Kamar</td>
		<td valign="top" width="30%" >: -</td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Jenis Kelamin</td>
		<td valign="top" width="30%" >: <?php echo $jk;?></td>
		<td valign="top" width="20%" >Dokter Pengirim</td>
		<td valign="top" width="30%" >: <?php if (isset($barispermintaanlab["nm_dokter"])) { echo $barispermintaanlab["nm_dokter"]; }?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Alamat</td>
		<td colspan="3" valign="top" width="30%" >: <?php echo $almt_pj;?></td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td style="font-weight:bold;" valign="top" width="20%" >Tanggal</td>
		<td style="font-weight:bold;" valign="top" width="20%" >Pemeriksaan</td>
		<td style="font-weight:bold;" valign="top" width="10%" >Hasil</td>
		<td style="font-weight:bold;" valign="top" width="10%" >Satuan</td>
		<td style="font-weight:bold;" valign="top" width="30%" >Nilai Rujukan</td>
		<td style="font-weight:bold;" valign="top" width="10%" >Keterangan</td>
		</tr>
		<?php foreach ($hasildetaillab as $value):?>
		
		<tr>
		<td valign="top">	<?php echo $value["tgl_periksa"] ?> / <?php echo $value["jam"] ?></td>
		<td valign="top">	<?php echo $value["Pemeriksaan"] ?></td>
		<td valign="top">	<?php echo $value["nilai"] ?></td>
		<td valign="top">	<?php echo $value["satuan"] ?></td>
		<td valign="top">	<?php echo $value["nilai_rujukan"] ?></td>
		<td valign="top">	<?php echo $value["keterangan"] ?></td>
		</tr>
		<?php endforeach; ?>
		
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Mamuju, <?php echo date('d-m-Y');?><br>Dokter Penanggung Jawab Laboratorium<br><br>
		<div class="qrcodeLAB" style="margin-top:0px;margin-bottom:0px;;"></div>
				<br><?php if (isset($barisperiksalab["nm_dokter"])) { echo $barisperiksalab["nm_dokter"]; }?><br>
	   
				<script>
				jQuery('.qrcodeLAB').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php if (isset($barisperiksalab["nm_dokter"])) { echo $barisperiksalab["nm_dokter"]; }?><"
		  
				});
				</script>
		</td>
		</tr>
		</table>
	
	<?php } ?>
	
	<?php if (isset($barisperiksarad["no_rawat"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">HASIL PEMERIKSAAN RADIOLOGI</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >No. Rekam Medis</td>
		<td valign="top" width="30%" >: <?php echo $no_rkm_medis;?></td>
		<td valign="top" width="20%" >No. Rawat</td>
		<td valign="top" width="30%" >: <?php echo $norawat;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Nama</td>
		<td valign="top" width="30%" >: <?php echo $nama_pasien;?></td>
		<td valign="top" width="20%" >Pemeriksaan</td>
		<td valign="top" width="30%" >: <?php if (isset($barisperiksarad["nm_perawatan"])) { echo $barisperiksarad["nm_perawatan"]; }?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Lahir / Umur</td>
		<td valign="top" width="30%" >: <?php echo $umurdaftar;?> / <?php echo $tgl_lahir;?></td>
		<td valign="top" width="20%" >Poliklinik/Kamar</td>
		<td valign="top" width="30%" >: <?php echo $nm_poli;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Jenis Kelamin</td>
		<td valign="top" width="30%" >: <?php echo $jk;?></td>
		<td valign="top" width="20%" >Dokter Pengirim</td>
		<td valign="top" width="30%" >: <?php if (isset($barisperiksarad["nm_dokter"])) { echo $barisperiksarad["nm_dokter"]; }?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Alamat</td>
		<td colspan="3" valign="top" width="30%" >: <?php echo $almt_pj;?></td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="100%" >Hasil Pemeriksaan :</td>
		</tr>
		<tr>
		<td valign="top" ><?php if (isset($barisdetailrad["hasil"])) { echo $barisdetailrad["hasil"]; }?></td>
		</tr>
		
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="100%" style="font-size:9pt;font-weight:bold;">Gambar Pemeriksaan</td>
		</tr>
		
		<tr>
          <td><img src="http://192.168.100.222/webapps/radiologi/<?php echo $gambarrad;?>" style="max-width: 300px;"></td>
        </tr>
		</tr>
		</table>
		<hr>

		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Mamuju, <?php echo date('d-m-Y');?><br>Dokter Penanggung Jawab Radiologi<br><br>
		<div class="qrcodeRAD" style="margin-top:0px;margin-bottom:0px;;"></div>
				<br><?php if (isset($barisperiksarad["nama"])) { echo $barisperiksarad["nama"]; }?><br>
	   
				<script>
				jQuery('.qrcodeRAD').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php if (isset($barisperiksarad["nama"])) { echo $barisperiksarad["nama"]; }?><"
		  
				});
				</script>
		</td>
		</tr>
		</table>
	
	<?php } ?>
	
	<?php if (isset($barisoperasi["no_rawat"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">LAPORAN OPERASI</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >Nama</td>
		<td valign="top" width="30%" >: <?php echo $nama_pasien;?></td>
		<td valign="top" width="20%" >No. Rawat</td>
		<td valign="top" width="30%" >: <?php echo $norawat;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Umur</td>
		<td valign="top" width="30%" >: <?php echo $umurdaftar;?></td>
		<td valign="top" width="20%" >No. Rekam Medis</td>
		<td valign="top" width="30%" >: <?php echo $no_rkm_medis;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Jenis Kelamin</td>
		<td valign="top" width="30%" >: <?php echo $jk;?></td>
		<td valign="top" width="20%" >Ruang / Unit</td>
		<td valign="top" width="30%" >: <?php echo $bariskamarinap["kd_kamar"];?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Lahir</td>
		<td valign="top" width="30%" >: <?php echo $tgl_lahir;?></td>
		<td valign="top" width="20%" >Tanggal Masuk</td>
		<td valign="top" width="30%" >: <?php echo $bariskamarinap["tgl_masuk"];?></td>
		</tr>
		</table>
		
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >Operator</td>
		<td valign="top" width="30%" >: <?php echo $barisoperasi["nm_dokter"];?></td>
		<td valign="top" width="20%" >Jenis Anastesi</td>
		<td valign="top" width="30%" >: <?php echo $barisoperasi["jenis_anasthesi"];?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Dokter Anastesi</td>
		<td valign="top" width="30%" >: <?php echo $barisoperasi["nama"];?></td>
		<td valign="top" width="20%" >Kategori</td>
		<td valign="top" width="30%" >: <?php echo $barisoperasi["kategori"];?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Mulai</td>
		<td valign="top" width="30%" >: <?php echo $barisoperasi["tgl_operasi"];?></td>
		<td valign="top" width="20%" >Jenis Operasi</td>
		<td valign="top" width="30%" >: <?php echo $barisoperasi["joperasi"];?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Selesai</td>
		<td valign="top" width="30%" >: <?php echo $barislaporanoperasi["selesaioperasi"];?></td>
		<td valign="top" width="20%" >Macam Operasi</td>
		<td valign="top" width="30%" >: <?php echo $barisoperasi["moperasi"];?></td>
		</tr>
		</table>
		<hr>
		<table width="100%"  style="font-size:8pt;" border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" >Diagnosa Pre Operasi</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php echo $barislaporanoperasi["diagnosa_preop"];?></td>
		</tr>
		<tr>
		<td valign="top" width="30%" >Diagnosa Post Operasi</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php echo $barislaporanoperasi["diagnosa_postop"];?></td>
		</tr>
		<tr>
		<td valign="top" width="30%" >Jaringan</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php echo $barislaporanoperasi["jaringan_dieksekusi"];?></td>
		</tr>
		<tr>
		<td valign="top" width="30%" >Laporan Pembedahan</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php echo $barislaporanoperasi["laporan_operasi"];?></td>
		</tr>
		<tr>
		<td valign="top" width="30%" >Komplikasi</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php echo $barislaporanoperasi["komplikasi"];?></td>
		</tr>
		<tr>
		<td valign="top" >Pendarahan</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barislaporanoperasi["pendarahan"];?></td>
		</tr>
		<tr>
		<td valign="top" >Jaringan yang dikirim</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barislaporanoperasi["permintaan_pa"];?></td>
		</tr>
		<tr>
		<td valign="top" >Nomor alat yang di pasang</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barislaporanoperasi["nomoralat"];?></td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Mamuju, <?php echo date('d-m-Y');?><br>OPERATOR<br><br>
		<div class="qrcodeOPERASI" style="margin-top:0px;margin-bottom:0px;;"></div>
				<br><?php echo $nm_dokter;?><br>
	   
				<script>
				jQuery('.qrcodeOPERASI').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php if (isset($barisoperasi["nm_dokter"])) { echo $barisoperasi["nm_dokter"]; }?>"
		  
				});
				</script>
		</td>
		</tr>
		</table>
		
	<?php } ?>

	<?php if (isset($barisbayi["no_rkm_medis"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">APGAR SCORE</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >Nama</td>
		<td valign="top" width="30%" >: <?php echo $nama_pasien;?></td>
		<td valign="top" width="20%" >No. Rawat</td>
		<td valign="top" width="30%" >: <?php echo $norawat;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Umur</td>
		<td valign="top" width="30%" >: <?php echo $umurdaftar;?></td>
		<td valign="top" width="20%" >No. Rekam Medis</td>
		<td valign="top" width="30%" >: <?php echo $no_rkm_medis;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Jenis Kelamin</td>
		<td valign="top" width="30%" >: <?php echo $jk;?></td>
		<td valign="top" width="20%" >Ruang / Unit</td>
		<td valign="top" width="30%" >: <?php echo $bariskamarinap["kd_kamar"];?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Lahir</td>
		<td valign="top" width="30%" >: <?php echo $tgl_lahir;?></td>
		<td valign="top" width="20%" >Tanggal Masuk</td>
		<td valign="top" width="30%" >: <?php echo $bariskamarinap["tgl_masuk"];?></td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  style="font-size:8pt;" border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" >Jam Lahir</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php echo $barisbayi["jam_lahir"];?></td>
		</tr>
		<tr>
		<td valign="top" >Berat Badan</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisbayi["berat_badan"];?></td>
		</tr>
		<tr>
		<td valign="top" >Panjang Badan</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisbayi["panjang_badan"];?></td>
		</tr>
		<tr>
		<td valign="top" >Lingkar Dada</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisbayi["lingkar_dada"];?></td>
		</tr>
		<tr>
		<td valign="top" >Lingkar Kepala</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisbayi["lingkar_kepala"];?></td>
		</tr>
		<tr>
		<td valign="top" >Proses Lahir</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php echo $barisbayi["proses_lahir"];?></td>
		</tr>
		</table>
		
		<hr>
		
		<table width="100%"  border="1" align="center" cellpadding="3px" cellspacing="0">
			<tr>
             <td valign="top" width="35%" bgcolor="">TANDA</td>
             <td valign="top" width="15%" bgcolor="">0</td>
             <td valign="top" width="15%" bgcolor="">1</td>
             <td valign="top" width="15%" bgcolor="">2</td>
             <td valign="top" width="10%" bgcolor="">1 Menit</td>
			 <td valign="top" width="10%" bgcolor="">5 Menit</td>
           </tr>
			<tr>
              <td valign='top'>Denyut Jantung</td>
              <td valign='top'>Tidak Ada</td>
              <td valign='top'>Kecil 100</td>
              <td valign='top'>Lebih 100</td>
              <td valign='top'><?php echo $barisbayi["f1"];?></td>
			  <td valign='top'><?php echo $barisbayi["f5"];?></td>
            </tr>
			<tr>
              <td valign='top'>Pernafasan</td>
              <td valign='top'>Tidak Ada</td>
              <td valign='top'>Tak Teratur</td>
              <td valign='top'>Baik</td>
              <td valign='top'><?php echo $barisbayi["u1"];?></td>
			  <td valign='top'><?php echo $barisbayi["u5"];?></td>
            </tr>
			<tr>
              <td valign='top'>Torut Otot</td>
              <td valign='top'>Lemah</td>
              <td valign='top'>Sedang</td>
              <td valign='top'>Baik</td>
              <td valign='top'><?php echo $barisbayi["t1"];?></td>
			  <td valign='top'><?php echo $barisbayi["t5"];?></td>
            </tr>
			<tr>
              <td valign='top'>Peka Rangsang</td>
              <td valign='top'>Tidak Ada</td>
              <td valign='top'>Meringis</td>
              <td valign='top'>Menangis</td>
              <td valign='top'><?php echo $barisbayi["r1"];?></td>
			  <td valign='top'><?php echo $barisbayi["r5"];?></td>
            </tr>
			<tr>
              <td valign='top'>Warna</td>
              <td valign='top'>Biru/Putih</td>
              <td valign='top'>Merah Jambu Ujung-Ujung Biru</td>
              <td valign='top'>Merah Jambu</td>
              <td valign='top'><?php echo $barisbayi["w1"];?></td>
			  <td valign='top'><?php echo $barisbayi["w5"];?></td>
			 </tr>
		</table>
		<hr>
		
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Mamuju, <?php echo date('d-m-Y');?><br>Dokter Penolong<br><br>
		<div class="qrcodeAPGAR" style="margin-top:0px;margin-bottom:0px;;"></div>
				<br><?php if (isset($barisbayi["nm_dokter"])) { echo $barisbayi["nm_dokter"]; }?><br>
	   
				<script>
				jQuery('.qrcodeAPGAR').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php if (isset($barisbayi["nm_dokter"])) { echo $barisbayi["nm_dokter"]; }?>"
		  
				});
				</script>
		</td>
		</tr>
		</table>		
	
	<?php } ?>
	
	<?php if (isset($barisoperasimata["no_rawat"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">LEMBAR OPERASI MATA</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >No. Rekam Medis</td>
		<td valign="top" width="30%" >: <?php echo $no_rkm_medis;?></td>
		<td valign="top" width="20%" >No. Rawat</td>
		<td valign="top" width="30%" >: <?php echo $norawat;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Nama</td>
		<td valign="top" width="30%" >: <?php echo $nama_pasien;?></td>
		<td valign="top" width="20%" >Pemeriksaan</td>
		<td valign="top" width="30%" >: <?php if (isset($barisoperasimata["prosedur"])) { echo $barisoperasimata["prosedur"]; }?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Tanggal Lahir / Umur</td>
		<td valign="top" width="30%" >: <?php echo $umurdaftar;?> / <?php echo $tgl_lahir;?></td>
		<td valign="top" width="20%" >Poliklinik/Kamar</td>
		<td valign="top" width="30%" >: <?php echo $nm_poli;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Jenis Kelamin</td>
		<td valign="top" width="30%" >: <?php echo $jk;?></td>
		<td valign="top" width="20%" >Dokter Pemeriksa</td>
		<td valign="top" width="30%" >: <?php if (isset($barisoperasimata["nm_dokter"])) { echo $barisoperasimata["nm_dokter"]; }?></td>
		</tr>

		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" >Prosedur / Tindakan Operasi</td>
		<td valign="top" width="1%" >:</td>
		<td valign="top" width="69%" ><?php if (isset($barisoperasimata["prosedur"])) { echo $barisoperasimata["prosedur"]; }?></td>
		</tr>
		<tr>
		<td valign="top" >Mata Yang Di Operasi</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php if (isset($barisoperasimata["mata"])) { echo $barisoperasimata["mata"]; }?></td>
		</tr>
		<tr>
		<td valign="top" >Persetujuan Informed Consent</td>
		<td valign="top" >:</td>
		<td valign="top" ><?php if (isset($barisoperasimata["persetujuan"])) { echo $barisoperasimata["persetujuan"]; }?></td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="30%" ></td>
		<td valign="top" width="35%" >Mata Kanan</td>
		<td valign="top" width="35%" >Mata Kiri</td>
		</tr>
		<tr>
		<td valign="top" >Visus</td>
		<td valign="top" >: <?php if (isset($barisoperasimata["visus_kanan"])) { echo $barisoperasimata["visus_kanan"]; }?></td>
		<td valign="top" >: <?php if (isset($barisoperasimata["visus_kiri"])) { echo $barisoperasimata["visus_kiri"]; }?></td>
		</tr>
		<tr>
		<td valign="top" >Power IOA</td>
		<td valign="top" >: <?php if (isset($barisoperasimata["power_kanan"])) { echo $barisoperasimata["power_kanan"]; }?></td>
		<td valign="top" >: <?php if (isset($barisoperasimata["power_kiri"])) { echo $barisoperasimata["power_kiri"]; }?></td>
		</tr>
		<tr>
		<td valign="top" >K1</td>
		<td valign="top" >: <?php if (isset($barisoperasimata["k1_kanan"])) { echo $barisoperasimata["k1_kanan"]; }?></td>
		<td valign="top" >: <?php if (isset($barisoperasimata["k1_kiri"])) { echo $barisoperasimata["k1_kiri"]; }?></td>
		</tr>
		<tr>
		<td valign="top" >K2</td>
		<td valign="top" >: <?php if (isset($barisoperasimata["k2_kanan"])) { echo $barisoperasimata["k2_kanan"]; }?></td>
		<td valign="top" >: <?php if (isset($barisoperasimata["k2_kiri"])) { echo $barisoperasimata["k2_kiri"]; }?></td>
		</tr>
		<tr>
		<td valign="top" >Axial</td>
		<td valign="top" >: <?php if (isset($barisoperasimata["axial_kanan"])) { echo $barisoperasimata["axial_kanan"]; }?></td>
		<td valign="top" >: <?php if (isset($barisoperasimata["axial_kiri"])) { echo $barisoperasimata["axial_kiri"]; }?></td>
		</tr>
		<tr>
		<td valign="top" >ACD</td>
		<td valign="top" >: <?php if (isset($barisoperasimata["acd_kanan"])) { echo $barisoperasimata["acd_kanan"]; }?></td>
		<td valign="top" >: <?php if (isset($barisoperasimata["acd_kiri"])) { echo $barisoperasimata["acd_kiri"]; }?></td>
		</tr>
		<tr>
		<td valign="top" >Nomor Lensa </td>
		<td colspan="2"valign="top" >: <?php if (isset($barisoperasimata["nomor_lensa"])) { echo $barisoperasimata["nomor_lensa"]; }?></td>
		
		</tr>
		</table>
		
		<hr>
		
		<!-- <table border="0" >
		<tr>
		<td valign="top" align="left" width="10%" ><img src="data:image/jpeg;base64,<?php echo $gambarcatatan;?>" alt="" height="100"></td>
	
		</tr>
		</table>  isi komentar di sini -->
		<hr>
		<table border="0" >
		<tr>
		<td>
  		<img src="http://192.168.100.222/webapps/hasilpemeriksaanmta/<?php echo $gambarmta;?>" style="max-width: 300px;"></td>
		</tr>
		</table>
		<!-- isi komentar di sini -->
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Mamuju, <?php echo date('d-m-Y');?><br>Operator<br><br>
		<div class="qrcodeOPMATA" style="margin-top:0px;margin-bottom:0px;;"></div>
				<br><?php if (isset($barisoperasimata["nm_dokter"])) { echo $barisoperasimata["nm_dokter"]; }?><br>
	   
				<script>
				jQuery('.qrcodeOPMATA').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php if (isset($barisoperasimata["nm_dokter"])) { echo $barisoperasimata["nm_dokter"]; }?><"
		  
				});
				</script>
		</td>
		</tr>
		</table>
			
	<?php } ?>
	
	
	<?php if (isset($barissuratkonsul["no_rawat"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">LEMBAR SURAT KONSULTASI</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="20%" >No. Rekam Medis</td>
		<td valign="top" width="30%" >: <?php echo $no_rkm_medis;?></td>
		<td valign="top" width="20%" >No. Rawat</td>
		<td valign="top" width="30%" >: <?php echo $norawat;?></td>
		</tr>
		<tr>
		<td valign="top" width="20%" >Nama</td>
		<td valign="top" width="30%" >: <?php echo $nama_pasien;?></td>
		<td valign="top" width="20%" >Nomor Surat</td>
		<td valign="top" width="30%" >: <?php if (isset($barissuratkonsul["no_surat"])) { echo $barissuratkonsul["no_surat"]; }?></td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" style="text-align:right;" width="100%" >Tanggal : <?php if (isset($barissuratkonsul["tanggalsurat"])) { echo $barissuratkonsul["tanggalsurat"]; }?></td>
		</tr>
		<tr>
		<td valign="top" width="100%" >Kepada Yth : <?php if (isset($barissuratkonsul["doktertujuan"])) { echo $barissuratkonsul["doktertujuan"]; }?>
		<br><br>
		Dengan Hormat, <br>
		Mohon Bantuan Sejawat Untuk : <?php if (isset($barissuratkonsul["tujuan_konsul"])) { echo $barissuratkonsul["tujuan_konsul"]; }?>
		
		<br><br>
		
		Keterangan penting saat ini adalah : <br><br><?php if (isset($barissuratkonsul["isikonsul"])) { echo $barissuratkonsul["isikonsul"]; }?>
		
		</td>
		</tr>
		</table>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Hormat Kami<br><br>
		<div class="qrcodeKONSULASAL" style="margin-top:0px;margin-bottom:0px;;"></div>
				<br><?php if (isset($barissuratkonsul["dokterperujuk"])) { echo $barissuratkonsul["dokterperujuk"]; }?><br>
	   
				<script>
				jQuery('.qrcodeKONSULASAL').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php if (isset($barissuratkonsul["dokterperujuk"])) { echo $barissuratkonsul["dokterperujuk"]; }?><"
		  
				});
				</script>
		</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">JAWABAN</td>
		</tr>
		</table>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" style="text-align:right;" width="100%" >Tanggal : <?php if (isset($barissuratkonsul["tanggaljawaban"])) { echo $barissuratkonsul["tanggaljawaban"]; }?></td>
		</tr>
		<tr>
		<td valign="top" width="100%" >Dengan Hormat
		<br>
		Sesuai permintaan konsultasi Sejawat, pada pemeriksaan pasien kami dapatkan saat ini adalah : 
		<br><br><?php if (isset($barissuratkonsul["jawabankonsul"])) { echo $barissuratkonsul["jawabankonsul"]; }?>
		<br><br>
		Terima kasih atas perhatian dan kerjasamanya.
	
	
		</td>
		</tr>

		</table>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Hormat Kami<br><br>
		<div class="qrcodeKONSULTUJUAN" style="margin-top:0px;margin-bottom:0px;;"></div>
				<br><?php if (isset($barissuratkonsul["doktertujuan"])) { echo $barissuratkonsul["doktertujuan"]; }?><br>
	   
				<script>
				jQuery('.qrcodeKONSULTUJUAN').qrcode({

				text: "ditandatangani secara elektronik oleh : <?php if (isset($barissuratkonsul["doktertujuan"])) { echo $barissuratkonsul["doktertujuan"]; }?><"
		  
				});
				</script>
		</td>
		</tr>
		</table>
		
	<?php } ?>	
<!-- Konsul Ori Khanza -->
	<?php if (isset($bariskonsulmedik["no_rawat"])) { ?>
	    <div style="page-break-before:always;"></div>
	    <br>
	    <table border="0" >
	    <tr>
	    <td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="70"></td>
	    <td valign="top" width="88%" >
	        <div style="font-size:14pt;text-align:center;font-weight:bold;">
	                  RUMAH SAKIT MITRA MANAKARRA
	                </div>
	        <div style="font-size:16pt;text-align:center;font-weight:bold;">
	                  Melayani Setulus Hati
	                </div>
	                <div style="font-size:6pt;text-align:center;">
	                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
	                </div>
	        <div style="font-size:6pt;text-align:center;">
	                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
	                </div>
	    </td>
	    <td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="70"></td>
	    </tr>
	    </table>
	    <hr>
	    
	    <table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
	    <tr>
	    <td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">SURAT KONSULTASI INTERNAL POLI</td>
	    </tr>
	    </table>
	    <hr>
	    <table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
	    <tr>
	    <td valign="top" width="20%" >Nama</td>
	    <td valign="top" width="30%" >: <?php echo $nama_pasien;?></td>
	    <td valign="top" width="20%" >No. Rawat</td>
	    <td valign="top" width="30%" >: <?php echo $norawat;?></td>
	    </tr>
	    <tr>
	    <td valign="top" width="20%" >Tanggal Lahir / Umur</td>
	    <td valign="top" width="30%" >: <?php echo $umurdaftar;?> / <?php echo $tgl_lahir;?></td>
	    <td valign="top" width="20%" >No. Rekam Medis</td>
	    <td valign="top" width="30%" >: <?php echo $no_rkm_medis;?></td>
	    </tr>
	    
	        
	    
	    </tr>
	    </table>
	    
	    <hr>
	    
	    <table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
	    <tr>
	    <td valign="top" width="30%" style="font-size:9pt;font-weight:bold;">PERMINTAAN</td>
	    <td valign="top" width="1%" ></td>
	    <td valign="top" width="69%"></td>
	    </tr>
	    <tr>
	    <td valign="top" >Permintaan</td>
	    <td valign="top" >:</td>
	    <td valign="top" ><?php echo $bariskonsulmedik["jenis_permintaan"];?></td>
	    </tr>
	    <tr>
	    <td valign="top" >Kepada Yth Teman Sejawat</td>
	    <td valign="top" >:</td>
	    <td valign="top" ><?php echo $bariskonsulmedik["nm_dokter"];?></td>
	    </tr>
	        <tr>
	    <td valign="top" >Diagnosa</td>
	    <td valign="top" >:</td>
	    <td valign="top" ><?php echo $bariskonsulmedik["diagnosa_kerja"];?></td>
	    </tr>
	        <tr>
	    <td valign="top" >Uraian</td>
	    <td valign="top" >:</td>
	    <td valign="top" ><?php echo $bariskonsulmedik["uraian_konsultasi"];?></td>
	    </tr>
	    </table>
	        <table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
	    <tr>
	    <td valign="top" width="10%" ></td>
	    <td valign="top" width="40%" ></td>
	    <td valign="top" width="10%" ></td>
	    <td valign="top" width="40%" >Mamuju, <?php echo $bariskonsulmedik["tanggal"];?><br>Dokter<br><br>
	    <div class="qrcodekonsul" style="margin-top:0px;margin-bottom:0px;;"></div>
	        <br><?php echo $bariskonsulmedik["nama"];?><br>
	     
	        <script>
	        jQuery('.qrcodekonsul').qrcode({

	        text: "ditandatangani secara elektronik oleh :<?php echo $bariskonsulmedik["nama"];?>"
	      
	        });
	        </script>
	    </td>
	    </tr>
	    </table>
	    <hr>
	    <table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
	    <tr>
	    <td valign="top" width="30%" style="font-size:9pt;font-weight:bold;">JAWABAN</td>
	    <td valign="top" width="1%" ></td>
	    <td valign="top" width="69%"></td>
	    </tr>
	    <tr>
	    <td valign="top" >Kepada Yth Teman Sejawat</td>
	    <td valign="top" >:</td>
	    <td valign="top" ><?php echo $bariskonsulmedik["nama"];?></td>
	    </tr>
	    <tr>
	    <td valign="top" >Diagnosa</td>
	    <td valign="top" >:</td>
	    <td valign="top" ><?php echo $barisjawabkonsulmedik["diagnosa_kerja"];?></td>
	    </tr>
	        <tr>
	    <td valign="top" >Uraian Jawaban</td>
	    <td valign="top" >:</td>
	    <td valign="top" ><?php echo $barisjawabkonsulmedik["uraian_jawaban"];?></td>
	    </tr>
	        
	    </table>
	    
	       
	    <table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
	    <tr>
	    <td valign="top" width="10%" ></td>
	    <td valign="top" width="40%" ></td>
	    <td valign="top" width="10%" ></td>
	    <td valign="top" width="40%" >Mamuju, <?php echo $barisjawabkonsulmedik["tanggal"];?><br>Dokter<br><br>
	    <div class="qrcodejawabkonsul" style="margin-top:0px;margin-bottom:0px;;"></div>
	        <br><?php echo $bariskonsulmedik["nm_dokter"];?><br>
	     
	        <script>
	        jQuery('.qrcodejawabkonsul').qrcode({

	        text: "ditandatangani secara elektronik oleh : <?php echo $bariskonsulmedik["nm_dokter"];?>"
	      
	        });
	        </script>
	    </td>
	    </tr>
	    </table>		
	   <?php } ?> 
	<!-- Konsul Ori Khanza -->


	<?php if (isset($barisberkas["no_rawat"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">BERKAS DIGITAL PERAWATAN</td>
		</tr>
		</table>
		<hr>
		
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="100%" style="font-size:9pt;font-weight:bold;"></td>
		</tr>
		<?php foreach ($hasilberkas as $value):?>
		<tr>
          <td align="center"><img src="http://192.168.100.222/webapps/berkasrawat/<?php echo $value["lokasi_file"];?>" style="max-width: 400px;"></td>
        </tr>
	
		<?php endforeach; ?>
		</tr>
		</table>
		<hr>
	
			
	
	<?php } ?>
	
	
		<?php if (isset($barisspaps["no_rawat"])) { ?>
		<div style="page-break-before:always;"></div>
		<br>
		<!-- HEADER RS - SURAT PULANG APS -->
		<table border="0" width="100%">
		<tr>
		<td valign="top" align="left" width="6%"><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" align="center">
				<div style="font-size:12pt;text-align:center;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:8pt;text-align:center;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:8pt;text-align:center;">
                  Email : rs.mitra.manakarra@gmail.com &nbsp;|&nbsp; No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>

		<!-- JUDUL -->
		<table width="100%" border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">
			SURAT PERNYATAAN<br>PULANG ATAS PERMINTAAN SENDIRI
		</td>
		</tr>
		<tr>
		<td colspan="4" style="font-size:9pt;text-align:center;">
			Nomor : <?php echo $spaps_nosurat; ?>
		</td>
		</tr>
		</table>
		<br>

		<!-- IDENTITAS PEMBUAT PERNYATAAN (PJ) -->
		<table width="100%" border="0" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:9pt;">Yang bertanda tangan di bawah ini :</td>
		</tr>
		<tr>
		<td width="30%" style="font-size:9pt;">Nama</td>
		<td width="2%">:</td>
		<td colspan="2" style="font-size:9pt;"><?php echo $spaps_namapj; ?></td>
		</tr>
		<tr>
		<td style="font-size:9pt;">Tgl. Lahir / JK</td>
		<td>:</td>
		<td colspan="2" style="font-size:9pt;"><?php echo $spaps_lahir; ?> / <?php echo $spaps_jkpj; ?></td>
		</tr>
		<tr>
		<td style="font-size:9pt;">Usia</td>
		<td>:</td>
		<td colspan="2" style="font-size:9pt;"><?php echo $spaps_umur; ?> Th</td>
		</tr>
		<tr>
		<td style="font-size:9pt;">Alamat</td>
		<td>:</td>
		<td colspan="2" style="font-size:9pt;"><?php echo $spaps_alamat; ?></td>
		</tr>
		<tr>
		<td style="font-size:9pt;">Hubungan Dengan Pasien</td>
		<td>:</td>
		<td colspan="2" style="font-size:9pt;"><?php echo $spaps_hubungan; ?></td>
		</tr>
		</table>
		<br>

		<!-- IDENTITAS PASIEN -->
		<table width="100%" border="0" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:9pt;">Terhadap Pasien a/n :</td>
		</tr>
		<tr>
		<td width="30%" style="font-size:9pt;">No. Rawat</td>
		<td width="2%">:</td>
		<td width="33%" style="font-size:9pt;"><?php echo $no_rawat; ?></td>
		<td width="35%" style="font-size:9pt;">No. RM &nbsp;&nbsp;&nbsp;: <?php echo $no_rkm_medis; ?></td>
		</tr>
		<tr>
		<td style="font-size:9pt;">Nama</td>
		<td>:</td>
		<td style="font-size:9pt;"><?php echo $nm_pasien; ?></td>
		<td style="font-size:9pt;">Jenis Kelamin : <?php echo ($jk=='L' ? 'Laki-Laki' : 'Perempuan'); ?></td>
		</tr>
		<tr>
		<td style="font-size:9pt;">Umur</td>
		<td>:</td>
		<td colspan="2" style="font-size:9pt;"><?php echo $umurdaftar; ?> <?php echo $sttsumur; ?></td>
		</tr>
		<tr>
		<td style="font-size:9pt;">Tempat, Tgl Lahir</td>
		<td>:</td>
		<td colspan="2" style="font-size:9pt;"><?php echo $nm_pasien; ?>, <?php echo $tgl_lahir; ?></td>
		</tr>
		<tr>
		<td style="font-size:9pt;">Pekerjaan</td>
		<td>:</td>
		<td colspan="2" style="font-size:9pt;">-</td>
		</tr>
		<tr>
		<td style="font-size:9pt;">Alamat</td>
		<td>:</td>
		<td colspan="2" style="font-size:9pt;"><?php echo $almt_pj; ?></td>
		</tr>
		</table>
		<br>

		<!-- ISI PERNYATAAN -->
		<table width="100%" border="0" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="2" style="font-size:9pt;">Dengan ini menyatakan bahwa :</td>
		</tr>
		<tr>
		<td valign="top" width="4%" style="font-size:9pt;">1.</td>
		<td style="font-size:9pt;">
			Dengan sadar tanpa paksaan dari pihak manapun meminta kepada pihak Rumah Sakit untuk PULANG
			ATAS PERMINTAAN SENDIRI yang merupakan hak saya / pasien dengan alasan :<br>
			<b><?php echo $spaps_rspilihan; ?></b> ............................................................
		</td>
		</tr>
		<tr>
		<td valign="top" style="font-size:9pt;">2.</td>
		<td style="font-size:9pt;">
			Saya telah memahami sepenuhnya penjelasan yang diberikan dari pihak Rumah Sakit mengenai
			penyakit dan kemungkinan / konsekuensi terbaik sampai dengan terburuk atas keputusan yang saya
			ambil, serta tanggung jawab saya dalam mengambil keputusan ini
		</td>
		</tr>
		<tr>
		<td valign="top" style="font-size:9pt;">3.</td>
		<td style="font-size:9pt;">
			Apabila terjadi sesuatu hal berkaitan dengan putusan yang telah diambil, maka hal tersebut adalah
			menjadi tanggung jawab pasien / keluarga sepenuhnya dan tidak akan menyangkut pautkan / menuntut
			Rumah Sakit ini
		</td>
		</tr>
		<tr>
		<td valign="top" style="font-size:9pt;">4.</td>
		<td style="font-size:9pt;">
			Atas keputusan saya ini, rumah sakit telah memberikan penjelasan mengenai alternatif pengobatan
			selanjutnya
		</td>
		</tr>
		</table>
		<br>

		<!-- TANDA TANGAN + FOTO -->
		<table width="100%" border="0" cellpadding="3px" cellspacing="0">
		<tr>
		<td width="33%" align="center" style="font-size:8pt;">Saksi II Perawat / Petugas</td>
		<td width="33%" align="center" style="font-size:8pt;">Saksi I Keluarga</td>
		<td width="33%" align="center" style="font-size:8pt;">
			MAMUJU, <?php echo date('d-m-Y', strtotime($spaps_tglpulang)); ?><br>Pembuat Pernyataan
		</td>
		</tr>
		<tr>
		<td align="center" style="padding:8px;">
			<div class="qrcodeSPAPS_PETUGAS" style="display:inline-block;margin-top:0px;margin-bottom:0px;"></div>
			<script>
			jQuery('.qrcodeSPAPS_PETUGAS').qrcode({
				text: "ditandatangani secara elektronik oleh <?php echo $spaps_nmptugas; ?> - RS Mitra Manakarra"
			});
			</script>
		</td>
		<td align="center" style="padding:8px;">
			<?php if (!empty($spaps_foto_saksi)) { ?>
			<img src="<?php echo $spaps_foto_saksi; ?>" height="80" style="max-height:80px;">
			<?php } ?>
		</td>
		<td align="center" style="padding:8px;">
			<?php if (!empty($spaps_foto_pembuat)) { ?>
			<img src="<?php echo $spaps_foto_pembuat; ?>" height="80" style="max-height:80px;">
			<?php } ?>
		</td>
		</tr>
		<tr>
		<td align="center" style="font-size:8pt;font-weight:bold;"><?php echo $spaps_nmptugas; ?></td>
		<td align="center" style="font-size:8pt;font-weight:bold;"><?php echo $spaps_saksikeluarga; ?></td>
		<td align="center" style="font-size:8pt;font-weight:bold;"><?php echo $spaps_namapj; ?></td>
		</tr>
		</table>

		<?php } /* end if isset barisspaps */ ?>

		<div style="page-break-before:always;"></div>
		<br>
		<table border="0" >
		<tr>
		<td valign="top" align="left" width="6%" ><img src="images\logo.png" alt="Image" height="50"></td>
		<td valign="top" width="90%" >
				<div style="font-size:12pt;text-align:left;font-weight:bold;">
                  RUMAH SAKIT MITRA MANAKARRA
                </div>
				<div style="font-size:10pt;text-align:left;font-weight:bold;">
                  Melayani Setulus Hati
                </div>
                <div style="font-size:6pt;text-align:left;">
                  Jl. Pongtiku No.2, Kel. Rimuku, Kec. Karema, Kab. Mamuju, Prov. Sulawesi Barat
                </div>
				<div style="font-size:6pt;text-align:left;">
                  Email : rs.mitra.manakarra@gmail.com, No.Telp : 08123456789
                </div>
		</td>
		</tr>
		</table>
		<hr>
		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td colspan="4" style="font-size:12pt;text-align:center;font-weight:bold;">BILLING PEMBAYARAN</td>
		</tr>
		</table>
		<hr>
 
		<table width="100%" bgcolor="#ffffff" align="left" border="0" padding="0" cellspacing="0" cellpadding="0">
		<?php foreach ($hasilbilling as $value):?>
		
		<tr>
          <td padding="0" width="18%"><font color="111111" size="1"  face="Tahoma"><?php echo $value["no"] ?></td>
          <td padding="0" width="40%"><font color="111111" size="1"  face="Tahoma"><?php echo $value["nm_perawatan"] ?></td>
          <td padding="0" width="2%"><font color="111111" size="1"  face="Tahoma"><?php echo $value["pemisah"] ?></td>
          <td padding="0" width="10%" align="right"><font color="111111" size="1"  face="Tahoma"><?php $value["biaya"]; $b = ltrim($value["biaya"], '0'); echo $b; ?></td>
          <td padding="0" width="5%" align="right"><font color="111111" size="1"  face="Tahoma"><?php $value["jumlah"]; $b = ltrim($value["jumlah"], '0'); echo $b; ?></td>
          <td padding="0" width="10%" align="right"><font color="111111" size="1"  face="Tahoma"><?php $value["tambahan"]; $b = ltrim($value["tambahan"], '0'); echo $b; ?></td>
          <td padding="0" width="15%" align="right"><font color="111111" size="1"  face="Tahoma"><?php $value["totalbiaya"]; $b = ltrim($value["totalbiaya"], '0'); echo $b; ?></td>
        </tr>
		<?php endforeach; ?>
		</table>

		<table width="100%" bgcolor="#ffffff" align="left" border="0" padding="0" cellspacing="0" cellpadding="0">
        <tr>
            <td padding="0" valign="top" width="18%"><font color="111111" size="1"  face="Tahoma" style="font-size:10pt;text-align:center;font-weight:bold;">TOTAL BIAYA</td>
            <td padding="0" width="40%"><font color="111111" size="1"  face="Tahoma"><b>:</b></td>
            <td padding="0" width="2%"><font color="111111" size="1"  face="Tahoma"></td>
            <td padding="0" width="10%" align="right"><font color="111111" size="1"  face="Tahoma"></td>
            <td padding="0" width="5%" align="right"><font color="111111" size="1"  face="Tahoma"></td>
            <td padding="0" width="10%" align="right"><font color="111111" size="1"  face="Tahoma"></td>
            <td padding="0" valign="top" width="15%" align="right"><font color="111111" size="1"  face="Tahoma"style="font-size:10pt;text-align:center;font-weight:bold;"><?php echo number_format($barisbilling2["total"]); ?></td>
        </tr>
		</table>

		<table width="100%"  border="0" align="center" cellpadding="3px" cellspacing="0">
		<tr>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" ></td>
		<td valign="top" width="10%" ></td>
		<td valign="top" width="40%" >Mengetahui<br>Kasir Rumah Sakit Mitra Manakarra<br><br>
		<div class="qrcodeKASIR" style="margin-top:0px;margin-bottom:0px;;"></div>
				<script>
				jQuery('.qrcodeKASIR').qrcode({

				text: "ditandatangani secara elektronik oleh KASIR Rumah Sakit Mitra Manakarra Mamuju"
		  
				});
				</script>
		</td>
		</tr>
		</table>
</div>
</div>
<div style="page-break-before:always;"></div>
		<div id="my_pdf_viewer">
        <div id="canvas_container">
        <canvas id="pdf_renderer"></canvas>
        </div> 
		</div>
	
</body>
	
	<script type="text/javascript">

	$(document).ready(function($) 
	{ 

		$(document).on('click', '.btn_print', function(event) 
		{
			event.preventDefault();

			//credit : https://ekoopmans.github.io/html2pdf.js
			
			var element = document.getElementById('element');
			
			//let element = document.get('http://172.30.200.62/E-Klaim/print/klaim.php?pid=<?php echo $barisinacbgpid;?>&adm=<?php echo $barisinacbgid;?>');
			//easy
			//html2pdf().from(element).save();

			//custom file name
			//html2pdf().set({filename: 'code_with_mark_'+js.AutoCode()+'.pdf'}).from(element).save();


			//more custom settings
			var opt = 
			{
				margin: 		10,
				filename:		'<?php echo $sep_nosep;?>.pdf',
				html2canvas: 	{scale:2, dpi:1200, letterRendering: true},
				image: 			{ type: 'jpeg', quality: 1 },
				jsPDF:			{unit:'mm',format:'letter',orientation:'portrait'},
				
			};
			
			// New Promise-based usage:
			html2pdf().set(opt).from(element).save();			 
		});
	});
	</script>
	<script>
	
	//window.open('http://172.30.200.62/E-Klaim/print/klaim.php?pid=<?php echo $barisinacbgpid;?>&adm=<?php echo $barisinacbgid;?>');
	
	
	function addScript(url) {
    var script = document.createElement('script');
    script.type = 'application/javascript';
    script.src = url;
    document.head.appendChild(script);}
	
	addScript('https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js');
	
	
	//html2pdf(document.body)
	
	</script>
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.0.943/pdf.min.js"></script>
	
	
	<script>
	
	
        var myState = {
            pdf: null,
            currentPage: 1,
			pageRendering: false,
            scale: 1.29,
        }
     
        pdfjsLib.getDocument("http://192.168.100.111/E-Klaim/print/klaim.php?pid=<?php echo $barisinacbgpid;?>&adm=<?php echo $barisinacbgid;?>").promise.then((pdf) => {
     
            myState.pdf = pdf;
            render();
        });
		
        function render() {
            myState.pdf.getPage(myState.currentPage).then((page) => {
         
                var canvas = document.getElementById("pdf_renderer");
                var ctx = canvas.getContext('2d');

     
                var viewport = page.getViewport(myState.scale);
                canvas.width = viewport.width;
                canvas.height = viewport.height;

                page.render({
                    canvasContext: ctx,
                    viewport: viewport
                });
            });
        }
    </script>
</html>