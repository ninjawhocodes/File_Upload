package com.ninjawhocodes.fileupload.repository;
/**
 * @author NinjaWhoCodes
 *
 */
import org.springframework.data.jpa.repository.JpaRepository;

import com.ninjawhocodes.fileupload.entity.FileUploadEO;

public interface FileUploadRepo extends JpaRepository<FileUploadEO, String>{

}

