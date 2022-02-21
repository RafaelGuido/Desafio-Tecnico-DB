package com.desafio.desafiotecnicodb.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class SincronizacaoReceitaService {

    @Autowired
    private ReceitaService receitaService;

    public void synchCsvResult(List<String[]> data) {
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        List<String[]> newCsvDataList = new ArrayList<>();

        for (String[] line : data) {
            try {
                line[1] = line[1].replace("-", "");
                Number number = format.parse(line[2]);

                boolean isUpdated = receitaService.atualizarConta(line[0], line[1], number.doubleValue(), line[3]);

                if (isUpdated == true) {

                }

            } catch (ParseException | InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}