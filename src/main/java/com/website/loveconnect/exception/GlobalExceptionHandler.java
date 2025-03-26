package com.website.loveconnect.exception;

import com.website.loveconnect.exception.exceptionmodel.ErrorDetail;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý mọi ngoại lệ chung
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                "An unexpected error occurred",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Xử lý lỗi quyền truy cập
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetail> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                "Access denied",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
    }

    // Xử lý lỗi đọc HTTP message (sửa tên lớp)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetail> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                "Invalid request body",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    // Xử lý lỗi không tìm thấy người dùng
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                ex.getMessage(), // Giữ lỗi cụ thể từ exception
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    //bat loi validation khi du lieu request body khong hop le
    @ExceptionHandler(MethodArgumentNotValidException.class)  // .bind
    public ResponseEntity<Map<String,String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            errors.put(error.getField(),error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    // Xử lý lỗi truy cập dữ liệu
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorDetail> handleDataAccessException(DataAccessException ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                "Database error occurred",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);// trường hợp cho cả ko tìm thấy user
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetail> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorDetail> handleEmailAlreadyInUseException(EmailAlreadyInUseException ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDetail> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        String message;
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                "Incorrect email or password",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }

    // Xử lý sai email/password
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetail> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                "Incorrect email or password",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }

    // Xử lý user không tồn tại
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                "User not found",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }

    // Xử lý token hết hạn (nếu dùng JWT)
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorDetail> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                "Token has expired",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }

    // Xử lý các AuthenticationException khác (nếu cần)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDetail> handleGenericAuthenticationException(AuthenticationException ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                "Authentication failed: " + ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }


    // Xử lý lỗi JOSEException (liên quan đến JWT)
    @ExceptionHandler(com.nimbusds.jose.JOSEException.class)
    public ResponseEntity<ErrorDetail> handleJoseException(com.nimbusds.jose.JOSEException ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                "Error processing JWT: " + ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Xử lý lỗi ParseException (liên quan đến parsing JWT)
    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ErrorDetail> handleParseException(ParseException ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                "Invalid token format: " + ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    // Xử lý AuthenticationException riêng cho introspect
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDetail> handleIntrospectAuthenticationException(AuthenticationException ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                "Token validation failed: " + ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }
}
