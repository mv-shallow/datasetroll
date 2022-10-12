package com.jupitertools.datasetroll;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Foo {

    private String id;
    private Date time;
    private int counter;
}