package com.chc.retail.exception;

import com.chc.retail.util.ApiResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.HibernateJdbcException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    log.debug("Method Arguments Passed Were not valid !");
    List<String> fieldErrors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(field -> field.getField() + ", " + field.getDefaultMessage())
            .collect(Collectors.toList());

    List<String> globalErrors =
        ex.getBindingResult().getGlobalErrors().stream()
            .map(field -> field.getObjectName() + ", " + field.getDefaultMessage())
            .collect(Collectors.toList());
    List<String> errors = new ArrayList<String>();
    errors.addAll(globalErrors);
    errors.addAll(fieldErrors);

    ApiResponseHandler err = new ApiResponseHandler(LocalDateTime.now(), ex.getMessage(), errors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationException(
      Exception ex, WebRequest request) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());
    ApiResponseHandler err =
        new ApiResponseHandler(LocalDateTime.now(), "Constraint Violation", details);
    log.debug("Errors - {}", err.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<Object> handleResourceNotFoundException(CustomerNotFoundException ex) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());
    ApiResponseHandler err =
        new ApiResponseHandler(LocalDateTime.now(), "Customer Details Not Found", details);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(TransactionNotFoundException.class)
  public ResponseEntity<Object> handleResourceNotFoundException(TransactionNotFoundException ex) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());
    ApiResponseHandler err =
        new ApiResponseHandler(LocalDateTime.now(), "Transaction Details were Not Found", details);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(value = {DataAccessException.class, HibernateJdbcException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Object> dataAccessException(DataAccessException ex, WebRequest webRequest) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());
    ApiResponseHandler err = new ApiResponseHandler(LocalDateTime.now(), ex.getMessage(), details);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(value = {Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Object> genericException(Exception ex, WebRequest webRequest) {
    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());
    ApiResponseHandler err = new ApiResponseHandler(LocalDateTime.now(), ex.getMessage(), details);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }
}
