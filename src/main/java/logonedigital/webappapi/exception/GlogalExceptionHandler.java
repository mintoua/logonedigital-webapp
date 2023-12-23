package logonedigital.webappapi.exception;

import logonedigital.webappapi.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
@ResponseBody
public class GlogalExceptionHandler {
    @ExceptionHandler(RessourceNotFoundException.class)
    public ErrorMessage RessourceNotFoundExceptionHandler(Exception ex){

    return ErrorMessage.build(
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            new Date(),
            HttpStatus.NOT_FOUND.getReasonPhrase()
    );
}

@ResponseBody
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
@ExceptionHandler(ResourceExistException.class)
public ErrorMessage ResourceExceptionHandler(Exception ex){
        return ErrorMessage.build(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
