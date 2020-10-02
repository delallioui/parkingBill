package com.financeactive.test.parkingBill;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ParkingBillTest {

    @Test
    public void should_GenerateBill_given_valid_input_nominalCase1() {
        // Given --
        Input input = new Input();
        input.setType("voiture essence");
        input.setArriving(LocalTime.of(13, 24));
        input.setDeparture(LocalTime.of(15, 10));
        // When --
        String bill = ParkingBill.generateBill(input);
        // Then --
        assertThat(bill).isNotNull();
        assertThat(bill).isEqualTo("```\n" +
                "- véhicule : voiture essence\n" +
                "- temps passé : 01:46\n" +
                "- montant dû  : 2.00 euros\n" +
                "```\n");
    }

    @Test
    public void should_GenerateBill_given_valid_input_nominalCase2() {
        // Given --
        Input input = new Input();
        input.setType("moto essence");
        input.setArriving(LocalTime.of(19, 30));
        input.setDeparture(LocalTime.of(0, 37));
        // When --
        String bill = ParkingBill.generateBill(input);
        // Then --
        assertThat(bill).isNotNull();
        assertThat(bill).isEqualTo("```\n" +
                "- véhicule : moto essence\n" +
                "- temps passé : 05:07\n" +
                "- montant dû  : 5.00 euros\n" +
                "```\n");
    }

    @Test
    public void should_GenerateBill_given_valid_input_nominalCase3() {
        // Given --
        Input input = new Input();
        input.setType("voiture GPL");
        input.setArriving(LocalTime.of(7, 43));
        input.setDeparture(LocalTime.of(15, 10));
        // When --
        String bill = ParkingBill.generateBill(input);
        // Then --
        assertThat(bill).isNotNull();
        assertThat(bill).isEqualTo("```\n" +
                "- véhicule : voiture GPL\n" +
                "- temps passé : 07:27\n" +
                "- montant dû  : 17.50 euros\n" +
                "```\n");
    }

    @Test
    public void should_CalculateAmount_given_duration_lower_than_one_hour() {
        // Given --
        Duration duration = Duration.ofMinutes(25);
        // When --
        BigDecimal amount = ParkingBill.calculateAmount(duration);
        // Then --
        assertThat(amount).isEqualTo(new BigDecimal(0));
    }

    @Test
    public void should_CalculateAmount_given_duration_equals_one_hour() {
        // Given --
        Duration duration = Duration.ofMinutes(60);
        // When --
        BigDecimal amount = ParkingBill.calculateAmount(duration);
        // Then --
        assertThat(amount).isEqualTo(new BigDecimal(0));
    }

    @Test
    public void should_CalculateAmount_given_lower_than_4_hours_and_greater_than_1_hour() {
        // Given --
        Duration duration = Duration.ofMinutes(130);
        // When --
        BigDecimal amount = ParkingBill.calculateAmount(duration);
        // Then --
        assertThat(amount).isEqualTo(new BigDecimal("2.33"));
    }

    @Test
    public void should_CalculateAmount_given_duration_equals_4_hours() {
        // Given --
        Duration duration = Duration.ofMinutes(240);
        // When --
        BigDecimal amount = ParkingBill.calculateAmount(duration);
        // Then --
        assertThat(amount).isEqualTo(new BigDecimal("6.00"));
    }

    @Test
    public void should_CalculateAmount_given_duration_greater_than_4_hour() {
        // Given --
        Duration duration = Duration.ofMinutes(500);
        // When --
        BigDecimal amount = ParkingBill.calculateAmount(duration);
        // Then --
        assertThat(amount).isEqualTo(new BigDecimal("19.00"));
    }
}