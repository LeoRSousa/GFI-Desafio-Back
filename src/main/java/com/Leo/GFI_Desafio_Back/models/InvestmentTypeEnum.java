package com.Leo.GFI_Desafio_Back.models;

public enum InvestmentTypeEnum {
    //Ação, Fundo, Título
    STOCK("STOCK"),
    FUND("FUND"),
    BOND("BOND");

    private final String type;

    InvestmentTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
