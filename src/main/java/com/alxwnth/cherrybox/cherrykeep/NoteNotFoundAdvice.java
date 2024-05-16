package com.alxwnth.cherrybox.cherrykeep;

import com.alxwnth.cherrybox.cherrykeep.exception.NoteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NoteNotFoundAdvice {
    @ExceptionHandler(NoteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String noteNotFound(NoteNotFoundException ex) {
        return ex.getMessage();
    }
}
