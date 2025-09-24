package com.example.my_API.exceptionhandler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class mi_APIExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    // Manejo de JSON inválido
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String mensagemUsuario = "Datos obligatorios incompletos o mal formados";
        String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        return handleExceptionInternal(ex, new Erro(mensagemUsuario, mensagemDesenvolvedor), headers,
                HttpStatus.BAD_REQUEST, request);
    }

    // Manejo de validaciones de campos
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<FieldError> errores = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errores.add(new FieldError(err.getField(), err.getDefaultMessage()));
        });

        return handleExceptionInternal(ex, errores, headers, HttpStatus.BAD_REQUEST, request);
    }

    // Clase interna para el detalle de campo
    public static class FieldError {
        private String campo;
        private String mensaje;

        public FieldError(String campo, String mensaje) {
            this.campo = campo;
            this.mensaje = mensaje;
        }

        public String getCampo() { return campo; }
        public String getMensaje() { return mensaje; }
    }

    // Clase de error genérico
    public static class Erro {
        private String mensagemUsuario;
        private String mensageDesenvolverdor;

        public Erro(String mensagemUsuario, String mensageDesenvolverdor) {
            this.mensagemUsuario = mensagemUsuario;
            this.mensageDesenvolverdor = mensageDesenvolverdor;
        }

        public String getMensagemUsuario() { return mensagemUsuario; }
        public String getMensageDesenvolverdor() { return mensageDesenvolverdor; }
    }
}

