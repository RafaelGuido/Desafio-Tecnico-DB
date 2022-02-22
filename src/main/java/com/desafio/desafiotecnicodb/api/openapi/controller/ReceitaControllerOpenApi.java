package com.desafio.desafiotecnicodb.api.openapi.controller;

import io.swagger.annotations.*;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Receitas")
public interface ReceitaControllerOpenApi {

    @ApiOperation("Atualiza Conta e devolve CSV")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Retorna o CSV")
    })
    public ResponseEntity<Resource> atualizarConta(@ApiParam(value = "arquivo csv", required = true) MultipartFile csv) throws IOException;
}
