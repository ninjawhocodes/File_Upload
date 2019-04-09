package com.ninjawhocodes.fileupload.entity;
/**
 * @author NinjaWhoCodes
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name="mst_files")
@EqualsAndHashCode(of = { "fileId" })
public class FileUploadEO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "file_id")
	private String fileId;
	
	@Column(name = "file_type")
	private String type;
	
	@Column(name = "file_name")
	private String name;
	
	@Column(name = "file_data")
	private byte[] data;
	
	@Column(name = "file_createdOn")
	private Date createdOn;
	public FileUploadEO () {
		
	}
	public FileUploadEO(String type, String name, byte[] data, Date createdOn) {
		super();
		this.type = type;
		this.name = name;
		this.data = data;
		this.createdOn = createdOn;
	}
}
