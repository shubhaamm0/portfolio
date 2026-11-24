package com.portfolio.exception;

import com.portfolio.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ResourceNotFoundException.class, NoResourceFoundException.class})
    public Object handleNotFound(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage() != null ? ex.getMessage() : "Resource not found"));
        }
        response.setStatus(HttpStatus.NOT_FOUND.value());
        ModelAndView mav = new ModelAndView("error/404");
        mav.setStatus(HttpStatus.NOT_FOUND);
        mav.addObject("statusCode", 404);
        mav.addObject("errorTitle", "Page Not Found");
        mav.addObject("errorMessage", "The page or resource you are looking for could not be found.");
        return mav;
    }

    @ExceptionHandler(FileStorageException.class)
    public Object handleFileStorage(FileStorageException ex, HttpServletRequest request, HttpServletResponse response) {
        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, ex.getMessage()));
        }
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ModelAndView mav = new ModelAndView("error/500");
        mav.setStatus(HttpStatus.BAD_REQUEST);
        mav.addObject("statusCode", 400);
        mav.addObject("errorTitle", "File Upload Error");
        mav.addObject("errorMessage", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Object handleMaxUploadSize(MaxUploadSizeExceededException ex, HttpServletRequest request, HttpServletResponse response) {
        String message = "Uploaded file exceeds the maximum allowed size limit (10MB).";
        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body(new ApiResponse(false, message));
        }
        response.setStatus(HttpStatus.PAYLOAD_TOO_LARGE.value());
        ModelAndView mav = new ModelAndView("error/500");
        mav.setStatus(HttpStatus.PAYLOAD_TOO_LARGE);
        mav.addObject("statusCode", 413);
        mav.addObject("errorTitle", "File Too Large");
        mav.addObject("errorMessage", message);
        return mav;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(false, "Validation failed. Please check the highlighted fields.", errors));
    }

    @ExceptionHandler(Exception.class)
    public Object handleGenericException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("Unhandled exception at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An unexpected server error occurred. Please try again later."));
        }
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ModelAndView mav = new ModelAndView("error/500");
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject("statusCode", 500);
        mav.addObject("errorTitle", "Internal Server Error");
        mav.addObject("errorMessage", "Something went wrong on our end. Please try again shortly.");
        return mav;
    }

    private boolean isApiRequest(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String requestedWith = request.getHeader("X-Requested-With");
        return request.getRequestURI().startsWith("/api/")
                || (accept != null && accept.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(requestedWith);
    }
}
