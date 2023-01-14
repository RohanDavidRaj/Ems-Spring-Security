package com.ty.ems.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ty.ems.dto.ResponseDto;
import com.ty.ems.exception.EmployeeException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(EmployeeException.class)
	public ResponseEntity<ResponseDto> exceptionHandler(HttpServletRequest request, EmployeeException exception) {
		return new ResponseEntity<ResponseDto>(new ResponseDto(true, exception.getMessage(), null),
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
		Map<String, String> map=new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error)->{
			String field = ((FieldError)error).getField();
			String defaultMessage = error.getDefaultMessage();
			map.put(field, defaultMessage);
		});
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.OK);
		
	}

}
