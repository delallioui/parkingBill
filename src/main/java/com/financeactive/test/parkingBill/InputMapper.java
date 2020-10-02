package com.financeactive.test.parkingBill;

import com.financeactive.test.parkingBill.exception.InputException;

import java.time.LocalTime;

public class InputMapper {

    private static final String FIRST_DELIM = "arrivée à";
    private static final String SECOND_DELIM = "et repartie à";
    private static final String HOURS_DELIM = "h";

    public static Input toInput(String str) {
        Input input = new Input();
        String[] split1 = str.split(FIRST_DELIM);
        if (split1.length != 2) {
            throw new InputException("The delimiter '" + FIRST_DELIM + "' is not found in the input");
        }
        String[] split2 = split1[1].split(SECOND_DELIM);
        if (split2.length != 2) {
            throw new InputException("The delimiter '" + SECOND_DELIM + "' is not found in the input");
        }
        input.setType(split1[0].replaceAll("[^ A-Za-z]", "").trim());
        input.setArriving(toTime(split2[0]));
        input.setDeparture(toTime(split2[1]));
        return input;
    }

    private static LocalTime toTime(String s) {
        String[] hm = s.trim().split(HOURS_DELIM);
        if(hm.length != 2) {
            throw new InputException("bad duration format in '"+ s.trim() + "'. Should be HHhmm");
        }
        int hours = Integer.parseInt(hm[0]);
        int minutes = Integer.parseInt(hm[1]);
        return LocalTime.of(hours, minutes);
    }
}
