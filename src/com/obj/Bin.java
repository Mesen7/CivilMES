package com.obj;

import java.sql.Date;

public class Bin {
	private String bin_config;
	private String bin_description;
	private String logic_bin;
	private String physic_bin;
	private String is_fail;
	private String bin_parameter;
	private Double min_value;
	private Double max_value;
	private String value_type; //数据类型:String,Double,int 
	private String valid_type; //检查类型：AND,OR
	private String ignore_null; //是否忽略空值
	private String create_user;
	private Date create_time;
	private String lm_user;
	private Date lm_time;
	public String getBin_config() {
		return bin_config;
	}
	public void setBin_config(String bin_config) {
		this.bin_config = bin_config;
	}
	public String getBin_description() {
		return bin_description;
	}
	public void setBin_description(String bin_description) {
		this.bin_description = bin_description;
	}
	public String getLogic_bin() {
		return logic_bin;
	}
	public void setLogic_bin(String logic_bin) {
		this.logic_bin = logic_bin;
	}
	public String getPhysic_bin() {
		return physic_bin;
	}
	public void setPhysic_bin(String physic_bin) {
		this.physic_bin = physic_bin;
	}
	public String getIs_fail() {
		return is_fail;
	}
	public void setIs_fail(String is_fail) {
		this.is_fail = is_fail;
	}
	public String getBin_parameter() {
		return bin_parameter;
	}
	public void setBin_parameter(String bin_parameter) {
		this.bin_parameter = bin_parameter;
	}
	public Double getMin_value() {
		return min_value;
	}
	public void setMin_value(Double min_value) {
		this.min_value = min_value;
	}
	public Double getMax_value() {
		return max_value;
	}
	public void setMax_value(Double max_value) {
		this.max_value = max_value;
	}
	public String getValue_type() {
		return value_type;
	}
	public void setValue_type(String value_type) {
		this.value_type = value_type;
	}
	public String getValid_type() {
		return valid_type;
	}
	public void setValid_type(String valid_type) {
		this.valid_type = valid_type;
	}
	public String getIgnore_null() {
		return ignore_null;
	}
	public void setIgnore_null(String ignore_null) {
		this.ignore_null = ignore_null;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getLm_user() {
		return lm_user;
	}
	public void setLm_user(String lm_user) {
		this.lm_user = lm_user;
	}
	public Date getLm_time() {
		return lm_time;
	}
	public void setLm_time(Date lm_time) {
		this.lm_time = lm_time;
	}
	
}
