package com.desafio.desafiotecnicodb.api.exceptionhandler;

public enum ProblemType {

    DADOS_INVALIDOS("/dados-invalidos","Dados inválidos"),
    METODO_NAO_SUPORTADO("/metodo-nao-suportado", "Método HTTP não suportado"),
    ERRO_DE_SISTEMA("/erro-de-sistema","Erro de Sistema"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado","Recurso não encontrado"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");


    private String title;
    private String uri;

    private ProblemType(String path, String title) {
        this.uri = "https://desafiotecnicodb.com" + path;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }
}
