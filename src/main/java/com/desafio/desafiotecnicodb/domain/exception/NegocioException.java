package com.desafio.desafiotecnicodb.domain.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(String msg) {
        super(msg);
    }
}
