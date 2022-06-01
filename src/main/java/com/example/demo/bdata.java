package com.example.demo;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "billboarddata")
public class bdata {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer billboard_id;
	private String billboard_title;
	private Date submission_date;
	private Date end_date;
	private String billboard_name;
	private String billboard_contend;
	private String Filequantity;
	private String AllFileName;
	

	public bdata() {

	}

	public int getId() {
		return billboard_id;
	}

	public void setId(int id) {
		this.billboard_id = id;
	}

	public String getTitle() {
		return billboard_title;
	}

	public void setTitle(String title) {
		this.billboard_title = title;
	}

	public Date getSdate() {
		return submission_date;
	}

	public void setSdate(Date sdate) {
		this.submission_date = sdate;
	}

	public Date getEdate() {
		return end_date;
	}

	public void setEdate(Date edate) {
		this.end_date = edate;
	}

	public String getName() {
		return billboard_name;
	}

	public void setName(String name) {
		this.billboard_name = name;
	}

	public String getContend() {
		return billboard_contend;
	}

	public void setContend(String contend) {
		this.billboard_contend = contend;
	}

	public String getFilequantity() {
		return Filequantity;
	}

	public void setFilequantity(String Filequantity) {
		this.Filequantity = Filequantity;
	}
	
	public String getAllFileName() {
		return AllFileName;
	}

	public void setAllFileName(String AllFileName) {
		this.AllFileName = AllFileName;
	}

	public String getdetail() {
		return "<a href=\".\\" + billboard_id + "\"> " + billboard_title + " </a>";
	}

	public String getoperate() {
		return "<a href=\".\\edit" + billboard_id + "\"> 編輯 </a>" + "<a href=\".\\delete" + billboard_id
				+ "\"> 刪除 </a>";
	}

	@Override
	public String toString() {
		return "{" + "\"bid\":" + billboard_id + ", \"btitle\":\"" + billboard_title + "\"" + ", \"sdate\":\""
				+ submission_date + "\"" + ", \"edate\":\"" + end_date + "\"" + ", \"bname\":\"" + billboard_name + "\""
				+ ", \"bcontend\":\"" + billboard_contend + "\"" + '}';
	}

}
