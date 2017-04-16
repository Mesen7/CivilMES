package com.obj;

import java.sql.Time;
import java.util.Date;

public class IVData {
	private String fab_id;
	private String product_id;
	private String wo_id;
	private String lot_no;
	private String operation_id;
	private String station_id;
	private Date test_date;
	private String id;
	private Double modeff;
	private Double rsh;
	private Double rs;
	private Double ff;
	private Double isc;
	private Double voc;
	private Double ipm;
	private Double vpm;
	private Double pmax;
	private Double temp;
	private Double envtemp;
	private Double tmod;
	private Time test_time;
	
	public Date getTest_date() {
		return test_date;
	}
	public void setTest_date(Date test_date) {
		this.test_date = test_date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getModeff() {
		return modeff;
	}
	public void setModeff(Double modeff) {
		this.modeff = modeff;
	}
	public Double getRsh() {
		return rsh;
	}
	public void setRsh(Double rsh) {
		this.rsh = rsh;
	}
	public Double getRs() {
		return rs;
	}
	public void setRs(Double rs) {
		this.rs = rs;
	}
	public Double getFf() {
		return ff;
	}
	public void setFf(Double ff) {
		this.ff = ff;
	}
	public Double getIsc() {
		return isc;
	}
	public void setIsc(Double isc) {
		this.isc = isc;
	}
	public Double getVoc() {
		return voc;
	}
	public void setVoc(Double voc) {
		this.voc = voc;
	}
	public Double getIpm() {
		return ipm;
	}
	public void setIpm(Double ipm) {
		this.ipm = ipm;
	}
	public Double getVpm() {
		return vpm;
	}
	public void setVpm(Double vpm) {
		this.vpm = vpm;
	}
	public Double getPmax() {
		return pmax;
	}
	public void setPmax(Double pmax) {
		this.pmax = pmax;
	}
	public Double getTemp() {
		return temp;
	}
	public void setTemp(Double temp) {
		this.temp = temp;
	}
	public Double getEnvtemp() {
		return envtemp;
	}
	public void setEnvtemp(Double envtemp) {
		this.envtemp = envtemp;
	}
	public Double getTmod() {
		return tmod;
	}
	public void setTmod(Double tmod) {
		this.tmod = tmod;
	}
	public Time getTest_time() {
		return test_time;
	}
	public void setTest_time(Time test_time) {
		this.test_time = test_time;
	}
	public String getFab_id() {
		return fab_id;
	}
	public void setFab_id(String fab_id) {
		this.fab_id = fab_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getWo_id() {
		return wo_id;
	}
	public void setWo_id(String wo_id) {
		this.wo_id = wo_id;
	}
	public String getLot_no() {
		return lot_no;
	}
	public void setLot_no(String lot_no) {
		this.lot_no = lot_no;
	}
	public String getOperation_id() {
		return operation_id;
	}
	public void setOperation_id(String operation_id) {
		this.operation_id = operation_id;
	}
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	
}
