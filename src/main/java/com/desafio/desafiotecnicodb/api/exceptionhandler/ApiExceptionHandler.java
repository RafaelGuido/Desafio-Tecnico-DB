package com.desafio.desafiotecnicodb.api.exceptionhandler;

import com.desafio.desafiotecnicodb.domain.exception.ArquivoInvalidoException;
import com.desafio.desafiotecnicodb.domain.exception.NegocioException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL
            = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
            + "o problema persistir, entre em contato com o administrador do sistema.";

    @ExceptionHandler(ArquivoInvalidoException.class)
    public ResponseEntity<?> handleArquivoInvalidoException(ArquivoInvalidoException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = ex.getMessage();

        Problem problem = new Problem(OffsetDateTime.now(),status.value(), problemType.getUri(), problemType.getTitle(),detail);

        return handleExceptionInternal(ex,problem, new HttpHeaders(), status,request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERRO_NEGOCIO;
        String detail = ex.getMessage();

        Problem problem = new Problem(OffsetDateTime.now(),status.value(),problemType.getUri(),problemType.getTitle(),detail);
        problem.setUserMessage(detail);

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<?> handleArquivoInvalidoException(org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = ex.getMessage();

        Problem problem = new Problem(OffsetDateTime.now(),status.value(), problemType.getUri(), problemType.getTitle(),detail);

        return handleExceptionInternal(ex,problem, new HttpHeaders(), status,request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status, WebRequest request) {

        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            Problem problem =  new Problem(OffsetDateTime.now(),status.value(),null,status.getReasonPhrase(),null);
            problem.setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);
            body = problem;
        }
        else if (body instanceof String){
            Problem problem =  new Problem(OffsetDateTime.now(),status.value(),null,(String) body,null);
            problem.setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);
            body = problem;
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.",
                ex.getRequestURL());

        Problem problem = new Problem(OffsetDateTime.now(),status.value(),problemType.getUri(),problemType.getTitle(),detail);
        problem.setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.METODO_NAO_SUPORTADO;
        String detail = String.format("Método HTTP %s não é suportado para essa requisição", ex.getMethod());

        Problem problem = new Problem(OffsetDateTime.now(),status.value(),problemType.getUri(),problemType.getTitle(),detail);
        problem.setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
                                                            HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.DADOS_INVALIDOS;

        String detail = "Campo inválido. Faça o preenchimento correto e tente novamente.";

        Problem problem = new Problem(OffsetDateTime.now(),status.value(),problemType.getUri(),problemType.getTitle(),detail);
        problem.setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }
}
