package com.generic.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.JSONObject;

/**
 * @author GENERIC TEAM
 * This models a Shipment
 */

public class Shipment {

	private static final String SHIPMENT_DETAIl_FORMAT_STRING = "Shipment_Id: %s\n  Weight: %.1f\n  Freight_Type: %s\n  Receipt_Date: %s";

	private String shipmentID; //shipment identification number
	private FreightType freight; //freight type
	private double weight; // shipment weight
	private long receiptDate; //Need to figure out the date format
	
	/**
	 * Constructs a new Shipment
	 * @param shipmentID shipment identification number
	 * @param freight freight type
	 * @param weight shipment weight
	 * @param receiptDate shipment receipt
	 */
	private Shipment(String shipmentID, FreightType freight, double weight, long receiptDate) {
		this.shipmentID = shipmentID;
		this.freight = freight;
		this.weight = weight;
		this.receiptDate = receiptDate;
	}
	
	public String getShipmentID() {
		return shipmentID;
	}

	public FreightType getFreight() {
		return freight;
	}

	public double getWeight() {
		return weight;
	}

	public long getReceiptDate() {
		return receiptDate;
	}
	
	/**
	 * Converts milliseconds to a date (STATIC)
	 * @param milliDate date in milliseconds
	 * @return date in simple format
	 */
	private static String milliToDate(long milliDate) {
		DateFormat simple = new SimpleDateFormat("dd MMMMM yyyy HH:mm:ss");
		Date result = new Date(milliDate);
		return simple.format(result);
	}
	
	public String toString() {
		return String.format(SHIPMENT_DETAIl_FORMAT_STRING, shipmentID, weight, freight.toString().toLowerCase(), milliToDate(receiptDate));
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject shipmentJSON = new JSONObject();
		shipmentJSON.put("shipment_id", shipmentID);
		shipmentJSON.put("shipment_method", freight.toString().toLowerCase());
		shipmentJSON.put("weight", weight);
		shipmentJSON.put("receipt_date", receiptDate);
		
		return shipmentJSON;
	}

	public static final class Builder {
		private String shipmentID;
		private FreightType freight;
		private double weight;
		private long receiptDate;
		
		public Builder() {}
		
		public Builder id(String shipmentID) {
			this.shipmentID = shipmentID;
			return this;
		}
		
		public Builder type(FreightType type) {
			this.freight = type;
			return this;
		}
		
		public Builder weight(double weight) {
			this.weight = weight;
			return this;
		}
		
		public Builder date(long receiptDate) {
			this.receiptDate = receiptDate;
			return this;
		}
		
		public Shipment build() {
			return new Shipment(shipmentID, freight, weight, receiptDate);
		}
	}
}
