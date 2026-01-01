package de.assecor.personen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ProblemDetail> handlePersonNotFound(PersonNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Person nicht gefunden");
        problem.setDetail(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(BrokenResourceException.class)
    public ResponseEntity<ProblemDetail> handleBrokenResource(BrokenResourceException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FAILED_DEPENDENCY);
        problem.setTitle("Datenquelle fehlerhaft");
        problem.setDetail(ex.getMessage());

        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(problem);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFound(ResourceNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Datenquelle nicht gefunden");
        problem.setDetail(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(UnknownColorException.class)
    public ResponseEntity<ProblemDetail> handleUnknownColor(UnknownColorException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Unbekannter Farbcode");
        problem.setDetail(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGeneric(Exception ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Unbekannter Fehler");
        problem.setDetail(ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }
}
