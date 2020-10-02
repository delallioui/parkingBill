package com.financeactive.test.parkingBill;


import lombok.Data;

import java.time.LocalTime;

@Data
public class Input {
    private String type;
    private LocalTime arriving;
    private LocalTime departure;
}
