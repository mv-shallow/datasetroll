package com.jupitertools.datasetroll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FooBar {

	private String id;
	private String data;
	private Bar bar;
}