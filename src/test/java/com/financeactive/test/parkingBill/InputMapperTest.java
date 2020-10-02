package com.financeactive.test.parkingBill;

import com.financeactive.test.parkingBill.exception.InputException;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;


public class InputMapperTest {

    @Test
    public void should_split_input_string_to_object_nominalCase1() throws InputException {
        // Given --
        String inputStr = "* 1 voiture essence arrivée à 13h24 et repartie à 15h10";
        // When --
        Input input = InputMapper.toInput(inputStr);
        // Then --
        assertThat(input).isNotNull();
        assertThat(input.getType()).isEqualTo("voiture essence");
        assertThat(input.getArriving()).isEqualTo(LocalTime.of(13, 24));
        assertThat(input.getDeparture()).isEqualTo(LocalTime.of(15, 10));
    }

    @Test
    public void should_split_input_string_to_object_nominalCase2() throws InputException {
        // Given --
        String inputStr = "* 1 moto essence arrivée à 19h30 et repartie à 00h37";
        // When --
        Input input = InputMapper.toInput(inputStr);
        // Then --
        assertThat(input).isNotNull();
        assertThat(input.getType()).isEqualTo("moto essence");
        assertThat(input.getArriving()).isEqualTo(LocalTime.of(19, 30));
        assertThat(input.getDeparture()).isEqualTo(LocalTime.of(0, 37));
    }

    @Test
    public void should_split_input_string_to_object_nominalCase3() throws InputException {
        // Given --
        String inputStr = "* 1 voiture GPL arrivée à  7h43 et repartie à 15h10";
        // When --
        Input input = InputMapper.toInput(inputStr);
        // Then --
        assertThat(input).isNotNull();
        assertThat(input.getType()).isEqualTo("voiture GPL");
        assertThat(input.getArriving()).isEqualTo(LocalTime.of(7, 43));
        assertThat(input.getDeparture()).isEqualTo(LocalTime.of(15, 10));
    }

    @Test
    public void should_throw_Exception_given_non_valid_input_string_Case_first_delim_not_found() throws InputException {
        // Given --
        String inputStr = "xxxxxxxxxxxxx";
        InputException inputException = Assertions.catchThrowableOfType(
                // When --
                () -> InputMapper.toInput(inputStr),
                // Then --
                InputException.class);
        assertThat(inputException).hasMessage("The delimiter 'arrivée à' is not found in the input");
    }

    @Test
    public void should_throw_Exception_given_non_valid_input_string_Case_second_delim_not_found() throws InputException {
        // Given --
        String inputStr = "xxxxxxxx arrivée à  xxxxx";
        InputException inputException = Assertions.catchThrowableOfType(
                // When --
                () -> InputMapper.toInput(inputStr),
                // Then --
                InputException.class);
        assertThat(inputException).hasMessage("The delimiter 'et repartie à' is not found in the input");
    }

}