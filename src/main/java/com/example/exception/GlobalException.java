package com.example.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<?> handleNotFoundException(NotFoundException exception) throws JsonProcessingException {
		HttpStatusCodeErrorMessage forbiddenResponse = new HttpStatusCodeErrorMessage();
		if (exception.getMessage() != null && !exception.getMessage().isEmpty()) {
			forbiddenResponse.setMessage(exception.getMessage());
			forbiddenResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(forbiddenResponse, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		body.put("errors", errors);
		return new ResponseEntity<>(body, headers, status);
	}

	/**
	 * HTTP status code handler class for all error type messages
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	class HttpStatusCodeErrorMessage {
		private String message;
		private int statusCode;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public int getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}
	}

}
