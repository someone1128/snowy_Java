package vip.xiaonuo.common.exception;

import vip.xiaonuo.common.enums.ExceptionCodeEnum;

/**
 * 自定义业务异常类
 *
 * @author Administrator
 */
public class BusinessException extends RuntimeException {

	private ExceptionCodeEnum codeEnum = ExceptionCodeEnum.ERROR500;

	public BusinessException(ExceptionCodeEnum codeEnum) {
		this.codeEnum = codeEnum;
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Exception e) {
		super(e);
	}

	public BusinessException(String message, ExceptionCodeEnum codeEnum) {
		super(message);
		this.codeEnum = codeEnum;
	}

	public ExceptionCodeEnum getCodeEnum() {
		return codeEnum;
	}

	public void setCodeEnum(ExceptionCodeEnum codeEnum) {
		this.codeEnum = codeEnum;
	}

}
