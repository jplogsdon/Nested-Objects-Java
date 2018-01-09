package com.nestedobjects.sutproxy;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

public class SutProxyBase {
	/**
	 * This class will capture exception and return the content type as
	 * text/plain.
	 * 
	 * @author jplogsdon
	 *
	 */
	@ControllerAdvice
	@RestController
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public class GlobalExceptionHandler {
		@ExceptionHandler(value = Exception.class)
		public String handleException(Exception e) {
			return e.getClass().getName() + "  " + e.getMessage();
		}
	}
}
