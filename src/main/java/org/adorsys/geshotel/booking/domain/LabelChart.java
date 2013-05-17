package org.adorsys.geshotel.booking.domain;

import java.util.Date;

import org.springframework.roo.addon.json.RooJson;


@RooJson
public class LabelChart {

	public LabelChart(String text, int value) {
		super();
		this.text = text;
		this.value = value;
	}

	private int value;
	
	private String text;
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
