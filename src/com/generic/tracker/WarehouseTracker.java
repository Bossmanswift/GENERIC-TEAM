package com.generic.tracker;

import java.util.HashMap;
import java.util.Map;

import com.generic.model.Shipment;
import com.generic.model.Warehouse;

/**
 * This class will be responsible for tracking a collection of warehouses and
 * the shipments in them. This will allow for consistency of objects as a
 * shipment can't exist without a warehouse. It would provide only the required
 * functionalities such as adding shipment, printing warehouse details, enabling
 * and disabled the freight receipt of a warehouse.
 * 
 * @author GENERIC TEAM
 *
 */
public final class WarehouseTracker {
	private static WarehouseTracker warehouseTracker;

	// Stores a collection of warehouses mapped by their id
	private Map<Integer, Warehouse> warehouses;

	// private constructor
	private WarehouseTracker() {
	}

	public static WarehouseTracker getInstance() {
		if (warehouseTracker == null) {
			synchronized (WarehouseTracker.class) {
				warehouseTracker = new WarehouseTracker();
				warehouseTracker.warehouses = new HashMap<>();
			}
		}
		return warehouseTracker;
	}

	// TODO: Create a method that checks if a particular warehouse has it's freight
	// enabled
	public boolean freightEnabled(int warehouseID) {
		Warehouse mWarehouse = warehouses.get(warehouseID);

		if (mWarehouse == null) {
			return false;
		}

		return mWarehouse.receivingFreight();
	}

	/**
	 * 
	 * Adds a new warehouse to the warehouse collection. If the warehouse already
	 * exists, we return false.
	 * 
	 * @param mWarehouse warehouse to add.
	 * @return true if add was successful, false if add wasn't.
	 */
	public boolean addWarehouse(Warehouse mWarehouse) {
		if (warehouses.get(mWarehouse.getWarehouseID()) == null) {
			warehouses.put(mWarehouse.getWarehouseID(), mWarehouse);
			return true;
		}
		return false;
	}
<<<<<<< HEAD

	public boolean addShipment(int warehouseID, Shipment mShipment) {
=======
	
	/**
	 * Adds a shipment to a warehouse, if the warehouseID
	 * does not exist, it returns -1, if
	 * the shipment could not be added because it's 
	 * freight receipt has ended, it returns 0,
	 * and if the shipment was successfully added, it 
	 * returns 1.
	 * @param warehouseID the warehouseID
	 * @param shipment the shipment to add to this warehouse
	 * @return -1 if ID does not exist
	 * 		  , 0 if freight receipt is disabled,
	 * 		  , 1 if successfully added.
	 * 			
	 */
	public int addShipment(int warehouseID, Shipment mShipment)
	{
>>>>>>> f7b33e42a3cbc268495643d0d393f6de2da4554c
		Warehouse theWarehouse = warehouses.get(warehouseID);
		if (theWarehouse != null && theWarehouse.receivingFreight()) {
			theWarehouse.addShipment(mShipment);
			return true;
		}
<<<<<<< HEAD
		return false;
=======
		
		return logCode;
>>>>>>> f7b33e42a3cbc268495643d0d393f6de2da4554c
	}

	public boolean addShipment(Warehouse theWarehouse, Shipment mShipment) {
		return addShipment(theWarehouse.getWarehouseID(), mShipment);
	}

	/**
	 * Enables a freight receipt in a Warehouse.
	 * 
	 * @param warehouseID warehouse id
	 * @return true if freight was successfully enabled, false if not.
	 */
	public boolean enableFreight(int warehouseID) {
		Warehouse theWarehouse = warehouses.get(warehouseID);
		if (theWarehouse != null) {
			theWarehouse.enableFreight();
			return true;
		}
<<<<<<< HEAD
		return false;
	}

=======
		
		return logCode;
	 }
	 
>>>>>>> f7b33e42a3cbc268495643d0d393f6de2da4554c
	/**
	 * Disables freight receipt in a warehouse
	 * 
	 * @param warehouseID warehouse id
	 * @return true if freight was successfully disabled, false if not.
	 */
	public boolean endFreight(int warehouseID) {
		Warehouse theWarehouse = warehouses.get(warehouseID);
		if (theWarehouse != null) {
			theWarehouse.disableFreight();
			return true;
		}
		return false;
	}

	/**
	 * Checks if we have an empty collection of warehouses.
	 * 
	 * @return true if empty, false if not.
	 */
	public boolean isEmpty() {
		return warehouses.size() == 0;
	}

	/**
	 * Getter for shipments size for a specified warehouse
	 * 
	 * @return the size of the warehouse, -1 if warehouse doesn't exist
	 */
	public int getWarehouseShipmentsSize(int warehouseID) {
		Warehouse theWarehouse = warehouses.get(warehouseID);
		return (theWarehouse != null ? theWarehouse.getShipmentSize() : -1);
	}

	/**
	 * Exports a warehouse object to JSON file
	 * 
	 * @param warehouseID warehouse id
	 * @param destPath    path to write file to
	 * @return true if warehouse exists, false if not
	 */
	public boolean exportWarehouseToJSON(int warehouseID) {
		Warehouse theWarehouse = warehouses.get(warehouseID);
		if (theWarehouse != null) {
			theWarehouse.exportToJSON();
			return true;
		}
		return false;
	}

	/**
	 * Checks if a warehouse exists
	 * 
	 * @param warehouseID warehouse id
	 * @return true if it does, false if not.
	 */
	public boolean warehouseExists(int warehouseID) {
		return (warehouses.get(warehouseID) != null);
	}

	/**
	 * Prints information about a warehouse for user to see.
	 * 
	 * @param warehouseID the warehouse id number.
	 */
	public void printWarehouseDetails(int warehouseID) {
		Warehouse theWarehouse = warehouses.get(warehouseID);
		if (theWarehouse == null) {
			System.out.println("Warehouse cannot be found!");
			return;
		}
		System.out.println(theWarehouse.toString());
	}

	public void printAll() {
		warehouses.forEach((k, v) -> printWarehouseDetails(k));
	}
}
