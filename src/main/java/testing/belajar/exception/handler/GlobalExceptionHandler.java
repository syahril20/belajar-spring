package testing.belajar.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import testing.belajar.exception.BadRequestException;

import java.util.LinkedHashMap;
import java.util.Map;

import static testing.belajar.utils.GeneralConstant.CODE;
import static testing.belajar.utils.GeneralConstant.MESSAGE;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(CODE, HttpStatus.BAD_REQUEST.value());
        response.put(MESSAGE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Map<String, Object>> handleInvalidRequestBody(HttpMessageNotReadableException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(CODE, HttpStatus.BAD_REQUEST.value());
        response.put(MESSAGE, "Request body tidak boleh kosong atau format salah");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<Map<String, Object>> handleNotFoundRequestBody(NoResourceFoundException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(CODE, HttpStatus.NOT_FOUND.value());
        response.put(MESSAGE, "Request URL tidak ditemukan");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Bisa tambah exception lain di sini
}
