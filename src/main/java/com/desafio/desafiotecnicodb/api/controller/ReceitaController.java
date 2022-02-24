package com.desafio.desafiotecnicodb.api.controller;

import com.desafio.desafiotecnicodb.api.openapi.controller.ReceitaControllerOpenApi;
import com.desafio.desafiotecnicodb.domain.exception.ArquivoInvalidoException;
import com.desafio.desafiotecnicodb.domain.service.SincronizacaoReceitaService;
import com.desafio.desafiotecnicodb.utils.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/receitas")
public class ReceitaController implements ReceitaControllerOpenApi {

    @Autowired
    private SincronizacaoReceitaService sincronizacaoReceitaService;

    @Autowired
    private CSVUtils csvUtils;

    @PutMapping(value = "/atualiza-conta",produces = "text/csv")
    public ResponseEntity<?> atualizarConta(@RequestPart("file") MultipartFile csv) {
        if (csv.isEmpty() || !Objects.equals(csv.getContentType(), "text/csv")) {
            throw new ArquivoInvalidoException("Arquivo inválido, por favor envie um arquivo do tipo csv");
        }
        try {
            List<String[]> csvList = csvUtils.readCSV(csv);
            sincronizacaoReceitaService.synchCsvResult(csvList);

            InputStreamResource resource = new InputStreamResource(new FileInputStream("dados.csv"));

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
        catch (IOException e) {
            throw new ArquivoInvalidoException("Arquivo inválido, por favor envie um arquivo do tipo csv");
        }
    }
}