package com.wenge.tbase.nacos.Exception;


import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class WengeException extends RuntimeException {
	 private Integer code;
	    private String msg;
	    private String developMsg;
	    private String uri;

	    public WengeException(Integer code, String message) {
	        this.code = code;
	        this.msg = message;
	    }

	    public WengeException(WengeException wengeException, String developMsg) {
	        this.code = wengeException.getCode();
	        this.msg = wengeException.getMsg();
	        this.developMsg = developMsg;
	    }

	    public WengeException(WengeException wengeException) {
	    	 this.code = wengeException.getCode();
		        this.msg = wengeException.getMsg();
	        this.developMsg = wengeException.getMsg();
	    }

	    public Integer getCode() {
	        return code;
	    }

	    public String getMsg() {
	        return msg;
	    }

	    public String getDevelopMsg() {
	        return developMsg;
	    }

	    public String getUri() {
	        return uri;
	    }

	    public void setCode(Integer code) {
	        this.code = code;
	    }

	    public void setMsg(String msg) {
	        this.msg = msg;
	    }

	    public void setDevelopMsg(String developMsg) {
	        this.developMsg = developMsg;
	    }

	    public void setUri(String uri) {
	        this.uri = uri;
	    }
}
