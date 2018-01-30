package com.xy.wmall.exception;

/**
 * 业务异常
 * 
 * @author xiongyan
 * @date 2017年6月6日 下午4:19:08
 */
public class BizException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }
}
