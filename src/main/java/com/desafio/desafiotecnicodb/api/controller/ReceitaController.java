package com.desafio.desafiotecnicodb.api.controller;

import com.desafio.desafiotecnicodb.domain.service.SincronizacaoReceitaService;
import com.desafio.desafiotecnicodb.utils.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/receitas")
public class ReceitaController {

    @Autowired
    private SincronizacaoReceitaService sincronizacaoReceitaService;



    @PutMapping(value = "/atualiza-conta",produces = "text/csv")
    public ResponseEntity<Resource> atualizarConta(@RequestPart("file") MultipartFile csv) throws IOException {
        List<String[]> csvList = CSVUtils.readCSV(csv);
        sincronizacaoReceitaService.synchCsvResult(csvList);

        InputStreamResource resource = new InputStreamResource(new FileInputStream("dados.csv"));

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE,"text/csv");
        return new ResponseEntity<>(resource,headers, HttpStatus.OK);
    }
}