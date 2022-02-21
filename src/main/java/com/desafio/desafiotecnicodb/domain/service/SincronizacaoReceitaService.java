package com.desafio.desafiotecnicodb.domain.service;

import com.desafio.desafiotecnicodb.utils.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public void synchCsvResult(List<String[]> data) throws IOException {
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        List<String[]> newCsvDataList = new ArrayList<>();

        for (String[] line : data) {
            try {
                line[1] = line[1].replace("-", "");
                Number number = format.parse(line[2]);

                boolean isUpdated = receitaService.atualizarConta(line[0], line[1], number.doubleValue(), line[3]);

                List<String> arrayToList = new ArrayList<>(Arrays.asList(line));
                arrayToList.add(String.valueOf(isUpdated));
                line = (String[]) arrayToList.toArray(new String[0]);

                newCsvDataList.add(line);

            } catch (ParseException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        CSVUtils.writeCSV(newCsvDataList);
    }
}