package com.soumili.productservice.Exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.soumili.productservice.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> HandleResourceNotFoundException(ResourceNotFoundException e) {
		return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	@ExceptionHandler(UserNotLoggedInException.class)
	public ResponseEntity<ApiResponse> handleUserNotLoggedInException(UserNotLoggedInException e) {

		return new ResponseEntity<ApiResponse>(new ApiResponse("User Not Logged In Yet!!"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ProductDoNotExistsException.class)
	public ResponseEntity<ApiResponse> handleProductDoNotExistsException(ProductDoNotExistsException e) {

		return new ResponseEntity<ApiResponse>(new ApiResponse("The product do not exists!!"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserNotAllowedException.class)
	public ResponseEntity<ApiResponse> handleProductDoNotExistsException(UserNotAllowedException e) {

		return new ResponseEntity<ApiResponse>(new ApiResponse("The user is not allowed to perform this operation!!"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ApiResponse HttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		return new ApiResponse("Request Body is missing!!");
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse handleNoHandlerFound(NoHandlerFoundException e, WebRequest request) {

		return new ApiResponse("Enter correct url path!!");
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public HashMap<String, String> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e,
			WebRequest request) {
		HashMap<String, String> response = new HashMap<>();
		response.put("status", "fail");
		response.put("message", e.getLocalizedMessage());
		return response;
	}

}
