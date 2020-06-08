package com.jupitertools.datasetroll;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Foo {

	private String id;
	private Date time;
	private int counter;
}