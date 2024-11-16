package com.senai.pa4.enums;

import com.senai.pa4.exceptions.ParametrosException;

import java.time.LocalDateTime;

public enum WorkShiftEnum {

    PRIMERO_TURNO("T1"),
    SEGUNDO_TURNO("T2"),
    TERCEIRO_TURNO("T3");

    private String workShift;

    WorkShiftEnum(String workShift) {
        this.workShift = workShift;
    }

    public static String parseToString(LocalDateTime workShift) {
        switch (workShift.getHour()) {
            case 6, 7, 8, 9, 10, 11, 12, 13, 14 -> {
                return PRIMERO_TURNO.workShift;
            }
            case 15, 16, 17, 18, 19, 20, 21, 22 -> {
                return SEGUNDO_TURNO.workShift;
            }
            case 23, 24, 00, 1, 2, 3, 4, 5 -> {
                return TERCEIRO_TURNO.workShift;
            }
        }
        throw new ParametrosException("Informar um turno válido: primeiro turno, segundo turno ou terceiro turno");
    }

    public static String parse(String workShift) {
        switch (workShift) {
            case "PRIMEIRO TURNO" -> {
                return PRIMERO_TURNO.workShift;
            }
            case "SEGUNDO TURNO" -> {
                return SEGUNDO_TURNO.workShift;
            }
            case "TERCEIRO TURNO" -> {
                return TERCEIRO_TURNO.workShift;
            }
        }
        throw new ParametrosException("Informar um turno válido: primeiro turno, segundo turno ou terceiro turno");
    }
}