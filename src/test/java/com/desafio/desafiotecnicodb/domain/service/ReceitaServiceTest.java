package com.desafio.desafiotecnicodb.domain.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = ReceitaService.class)
@ExtendWith(SpringExtension.class)
public class ReceitaServiceTest {

    @Autowired
    private ReceitaService receitaService;


    private String agencia;
    private String conta;
    private double saldo;
    private String status;

    @BeforeEach
    public void before() {
        this.agencia = "0101";
        this.conta = "122263";
        this.saldo = 100.5;
        this.status = "A";
    }

    @Test
    public void deveRetornarFalseQuandoAgenciaDiferenteDeQuatroDigitos() throws InterruptedException {
        this.agencia = "012";
        boolean response = receitaService.atualizarConta(agencia,conta,saldo,status);

        Assertions.assertFalse(response);
    }

    @Test
    public void deveRetornarFalseQuandoContaDiferenteDeSeisDigitos() throws InterruptedException {
        this.conta = "0165";
        boolean response = receitaService.atualizarConta(agencia,conta,saldo,status);

        Assertions.assertFalse(response);
    }

    @Test
    public void deveRetornarFalseQuandoStatusInvalido() throws InterruptedException {
        this.status = "J";
        boolean response = receitaService.atualizarConta(agencia,conta,saldo,status);

        Assertions.assertFalse(response);
    }

    @Test
    public void deveRetornarTrueQuandoTodosDadosValidos() throws InterruptedException {
        boolean response = receitaService.atualizarConta(agencia,conta,saldo,status);
        Assertions.assertTrue(response);
    }

}
