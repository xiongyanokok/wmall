package com.xy.wmall.exception;

import com.xy.wmall.enums.ErrorCodeEnum;

/**
 * 自定义异常
 * 
 * @author xiongyan
 * @date 2017年6月6日 下午4:19:08
 */
public class WmallException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 错误码
	 */
	private ErrorCodeEnum errorCode;
	
	public WmallException() {
		 super();
	}

    public WmallException(String message) {
        super(message);
    }
	
    public WmallException(ErrorCodeEnum errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public WmallException(String message, Throwable cause) {
    	super(message, cause);
    }
    
    public WmallException(ErrorCodeEnum errorCode, String message, Throwable cause) {
    	super(message, cause);
    	this.errorCode = errorCode;
    }
    
    public WmallException(Throwable cause) {
    	super(cause);
    }
    
	public ErrorCodeEnum getErrorCode() {
		return errorCode;
	}

}
