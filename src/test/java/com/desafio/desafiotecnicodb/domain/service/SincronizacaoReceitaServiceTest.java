package com.desafio.desafiotecnicodb.domain.service;


import com.desafio.desafiotecnicodb.utils.CSVUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = {SincronizacaoReceitaService.class, CSVUtils.class, ReceitaService.class})
@ExtendWith(SpringExtension.class)
public class SincronizacaoReceitaServiceTest {

    @Autowired
    private SincronizacaoReceitaService sincronizacaoReceitaService;

    @Captor
    private ArgumentCaptor<List<String[]>> captor;

    @MockBean
    private CSVUtils csvUtils;

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    void deveAdicionarMaisUmElementoNaLista() throws IOException {
        var listaDesatualizada = mockDataBuilder();
        sincronizacaoReceitaService.synchCsvResult(listaDesatualizada);

        Mockito.verify(csvUtils).writeCSV(captor.capture());
        List<String[]> listaAtualizada = captor.getValue();

        Assertions.assertEquals(listaDesatualizada.get(0).length + 1, listaAtualizada.get(0).length);
    }

    private List<String[]> mockDataBuilder() {
        List<String[]> dados = new ArrayList<>();

        dados.add(new String[] {"1231", "12345-1", "100,1","A"});
        dados.add(new String[] {"1235", "12345-5", "100,5","A"});
        dados.add(new String[] {"1234", "12345-6", "100,5","A"});
        dados.add(new String[] {"1234", "12345-6", "100,5","A"});

        return dados;
    }
}
