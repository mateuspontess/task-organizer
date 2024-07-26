package br.com.mateus.taskorganizer.infra.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Void> handleError404() {
		return ResponseEntity.notFound().build();
	}
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageWithFields> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    	
    	var fields = ex.getFieldErrors().stream()
            .collect(Collectors.toMap(f -> f.getField().toString(), f -> f.getDefaultMessage()));
    	
    	var response = new ErrorMessageWithFields(
            "Input validation error",
            fields);
    	
    	return ResponseEntity
            .badRequest()
            .body(response);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleError400(IllegalArgumentException ex) {
        ex.printStackTrace();
        return ResponseEntity.badRequest().body(new ErrorMessage(ex.getMessage()));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleError400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleErrorBadCredentials(BadCredentialsException ex) {
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage("Invalid credentials"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleErrorAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage("Authentication failed"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleErrorAccessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessage("Access denied"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleError500(Exception ex) {
    	ex.printStackTrace();
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal server error"));
    }
    
    private record ErrorMessage(String error) {};
    private record ErrorMessageWithFields(String error, Object fields) {};
}