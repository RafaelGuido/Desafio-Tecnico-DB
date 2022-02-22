package com.desafio.desafiotecnicodb.api.openapi.controller;

import com.desafio.desafiotecnicodb.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Receitas")
public interface ReceitaControllerOpenApi {

    @ApiOperation("Atualiza Conta e devolve CSV")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Requisição inválida",response = Problem.class)
    })
    public ResponseEntity<?> atualizarConta(@ApiParam(value = "Arquivo CSV", example = "arquivo.csv",required = true) MultipartFile csv) throws IOException;
}
