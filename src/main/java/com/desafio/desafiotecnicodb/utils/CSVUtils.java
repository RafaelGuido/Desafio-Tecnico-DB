package com.desafio.desafiotecnicodb.utils;

import com.opencsv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CSVUtils {

    public static List<String[]> readCSV(MultipartFile csv) throws IOException {
        Reader reader = new InputStreamReader(csv.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withCSVParser(new CSVParser(';', '"', '\\'))
                .withSkipLines(1)
                .build();

        return csvReader.readAll();
    }

    public static void writeCSV(List<String[]> data) throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get("dados.csv"));

        String[] header = {"agencia", "conta","saldo", "status", "atualizado"};
        ICSVWriter csvWriter = new CSVWriterBuilder(writer)
                .withSeparator(';')
                .withQuoteChar('"')
                .withEscapeChar('\\')
                .withLineEnd(ICSVWriter.DEFAULT_LINE_END)
                .build();

        csvWriter.writeNext(header);

        data.forEach(d -> csvWriter.writeNext(new String[] {d[0], d[1], d[2], d[3], d[4]}));

        csvWriter.close();
        writer.close();
    }


}

