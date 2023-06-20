package com.gancidev.cardmanager.Enum;

public enum TypeEnum {

    PAYMENT("payment"),
	ACCREDIT("accredit");
	
	private String type;
	
	private TypeEnum(String type) {
		this.type = type;	
	}
	
	public String getType() {
		return this.type;
	}

}
