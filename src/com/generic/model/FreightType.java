package com.generic.model;

/**
 * Enum for FreightType
 * @author GENERIC TEAM
 */

public enum FreightType {
	AIR, TRUCK, SHIP, RAIL;
	
	public static FreightType getFreightFromString(String str) {
		//TODO: Return the value of the String str if it exists.
		// if it doesn't exist, throw an IllegalArgumentException
		return AIR;
	}

}
