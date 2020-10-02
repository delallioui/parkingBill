package com.financeactive.test;

import com.financeactive.test.parkingBill.Input;
import com.financeactive.test.parkingBill.InputMapper;
import com.financeactive.test.parkingBill.ParkingBill;
import com.financeactive.test.parkingBill.exception.InputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        while (true) {
            System.out.println("Veuillez saisir un input ou 'q' pour quitter");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String inputStr = reader.readLine();
            if ("q".equalsIgnoreCase(inputStr)) {
                break;
            }

            try {
                Input input = InputMapper.toInput(inputStr);
                String bill = ParkingBill.generateBill(input);
                System.out.println(bill);
            } catch (InputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
