package com.zhaohg.kisso.oauth2.entity;

import java.io.Serializable;

public class Status implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code;

	private String msg;


	public int getCode() {
		return code;
	}


	public void setCode( int code ) {
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg( String msg ) {
		this.msg = msg;
	}

}
