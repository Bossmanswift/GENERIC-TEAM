package com.generic.model;

/**
 * @author GENERIC TEAM
 * This models a Shipment
 */

public final class Shipment {

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
	
	public String toString() {
		return  "shipmentID = "+shipmentID+"\n" +
				"freight = "+freight+"\n" +
				"weight = "+weight+"\n" +
				"receiptDate = "+receiptDate+"\n";
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
