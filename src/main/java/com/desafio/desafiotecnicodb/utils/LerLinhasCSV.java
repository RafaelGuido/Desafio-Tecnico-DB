package com.desafio.desafiotecnicodb.utils;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.ICSVParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Component
public class LerLinhasCSV {

    public List<String[]> readCSV(MultipartFile csv) throws IOException {
        Reader reader = new InputStreamReader(csv.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withCSVParser(new CSVParser(';', '"', '\\'))
                .withSkipLines(1)
                .build();

        return csvReader.readAll();
    }
}