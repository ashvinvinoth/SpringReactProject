package com.backend.app.repository;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.backend.app.model.FileUploadTb;


@Repository
public interface FileRepository extends JpaRepository<FileUploadTb, Integer>{

	Stream<FileUploadTb> findAllByUploadBy(String uploadedBy);
	
@Query(value="select * from file_upload_tb",nativeQuery=true)
	Stream<FileUploadTb> findAllBySignedStatusAndUploadedBY();
	
@Query(value="select * from file_upload_tb where upload_by =:uploadedBy",nativeQuery=true)
Stream<FileUploadTb> findAllByUploadByAndSignedStatus(@Param(value = "uploadedBy") String uploadedBy);

@Query(value="select * from file_upload_tb where signed_status=0 and filename=:fileName",nativeQuery=true)
FileUploadTb findByFileNameAndSignStatus(@Param(value = "fileName")String fileName);


@Modifying
@Transactional 
@Query(value="UPDATE file_upload_tb SET signed_status = '1', user_status= '1' WHERE id =:date",nativeQuery=true)
void updatesignstatus(@Param(value = "date")String date);


}
