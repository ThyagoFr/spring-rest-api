package github.thyago.spaceflightnewsintegration.web.exceptionhandler;

import github.thyago.spaceflightnewsintegration.domain.exception.ArticleNotFound;
import github.thyago.spaceflightnewsintegration.web.dto.ErrorResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(value = {ArticleNotFound.class})
    @ResponseStatus(value = NOT_FOUND)
    public ErrorResponse handleArticleNotFoundException(ArticleNotFound ex, HttpServletRequest request) {
        var message = new ErrorResponse();
        message.setMessage(ex.getMessage());
        message.setTimestamp(now());
        message.setPath(request.getRequestURI());
        return message;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        var message = new ErrorResponse();
        message.setMessage("There are some errors in user input data");
        message.setTimestamp(now());
        message.setPath(request.getRequestURI());
        message.setErrors(new HashMap<>());

        ex.getBindingResult().getAllErrors().forEach(err -> {
            String fieldWithError = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            message.getErrors().put(fieldWithError, errorMessage);
        });
        return message;
    }

}