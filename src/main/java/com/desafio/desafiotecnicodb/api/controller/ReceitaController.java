package com.desafio.desafiotecnicodb.api.controller;

import com.desafio.desafiotecnicodb.domain.service.SincronizacaoReceitaService;
import com.desafio.desafiotecnicodb.utils.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "/receitas", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReceitaController {

    @Autowired
    private SincronizacaoReceitaService sincronizacaoReceitaService;



    @PutMapping(value = "/atualiza-conta")
    public ResponseEntity atualizarConta(@RequestPart("file") MultipartFile csv) throws IOException, InterruptedException, ParseException {
        List<String[]> csvList = CSVUtils.readCSV(csv);
        sincronizacaoReceitaService.synchCsvResult(csvList);
        return null;
    }
}