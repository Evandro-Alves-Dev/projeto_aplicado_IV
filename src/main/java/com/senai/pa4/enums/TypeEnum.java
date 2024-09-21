package com.senai.pa4.enums;

import com.senai.pa4.exceptions.ParametrosException;

public enum TypeEnum {

    ADMINISTRADOR("admin"),  //Administrador
    OPERADOR("user"); // Operador

    private String tipo;

    TypeEnum(String tipo) {
        this.tipo = tipo;
    }

    public static String parse(String type) {
        for (TypeEnum tipoEnum : TypeEnum.values()) {
            if (tipoEnum.tipo.equals(type)) {
                return type;
            }
        }
       throw new ParametrosException("Informar um tipo válido: Administrador ou Operador");
    }
}