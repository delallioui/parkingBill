package com.financeactive.test.parkingBill;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ParkingBill {

    private static final String HEADER_FOOTER = "```\n";
    private static final String TYPE_KEY = "- véhicule : ";
    private static final String DURATION_KEY = "- temps passé : ";
    private static final String AMOUNT_KEY = "- montant dû  : ";
    private static final String EUROS_SUFFIX = " euros";
    private static final String GPL = "GPL";

    public static String generateBill(Input input) {
        String type = input.getType();
        Duration duration = Duration.between(input.getArriving(), input.getDeparture());
        BigDecimal amount = getAmount(type, duration);
        return buildBill(type, duration, amount);
    }

    private static BigDecimal getAmount(String type, Duration duration) {
        BigDecimal amount = calculateAmount(duration);

        // RG - Une majoration de 7% s'applique au véhicule GPL
        if(type.toUpperCase().contains(GPL))
            amount = amount.multiply(divideBigDecimals(107,100)).setScale(2, BigDecimal.ROUND_HALF_UP);

        // RG les deux roues paient demi-tarif.
        if (type.toUpperCase().contains("MOTO"))
            amount = divideBigDecimals(amount.doubleValue(), 2);

        //RG Le montant est toujours arrondi au 50 centimes supérieurs.
        amount = round(amount);

        return amount;
    }

    private static BigDecimal round(BigDecimal amount) {
        int ones = amount.multiply(new BigDecimal(100)).intValue() % 50;
        if (ones == 0) {
            return amount;
        }
        return amount.add(divideBigDecimals((50-ones), 100));
    }

    static BigDecimal calculateAmount(Duration duration) {
        long minutes = duration.toMinutes();
        // Case when departure is after midnight
        if (minutes < 0) {
            minutes += 24*60;
        }
        // RG1 - la première heure est gratuite
        if (minutes <= 60)
            return new BigDecimal(0);
        // RG2 - 2 euros par heure entamée jusqu'a 4 h
        if (minutes <= 4*60)
            return divideBigDecimals((minutes - 60) * 2, 60);
        // RG3 - 1,5 euros par demie heure entamée au-dela de 4h
        return new BigDecimal(6)
                .add(divideBigDecimals((minutes - 4*60)*1.5,30));
    }

    private static BigDecimal divideBigDecimals(double a, double b) {
        return new BigDecimal(a).divide(new BigDecimal(b), 2, RoundingMode.HALF_UP);
    }

    private static String buildBill(String type, Duration duration, BigDecimal amount) {
        StringBuilder sb = new StringBuilder();
        sb.append(HEADER_FOOTER)
                .append(TYPE_KEY).append(type).append("\n")
                .append(DURATION_KEY).append(toStr(duration)).append("\n")
                .append(AMOUNT_KEY).append(amount).append(EUROS_SUFFIX).append("\n")
                .append(HEADER_FOOTER);
        return sb.toString();
    }

    private static String toStr(Duration duration) {
        return LocalTime.MIDNIGHT.plus(duration).format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
