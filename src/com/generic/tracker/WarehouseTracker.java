package com.generic.tracker;

import java.util.HashMap;
import java.util.Map;
import com.generic.model.Shipment;
import com.generic.model.Warehouse;
/**
 * This class will be responsible for tracking
 * a collection of warehouses and the shipments
 * in them. This will allow for consistency
 * of objects as a shipment can't exist without
 * a warehouse. It would provide only the
 * required functionalities such as
 * adding shipment, printing warehouse details,
 * enabling and disabled the freight receipt of a warehouse.
 * 
 * @author Seyi Ola
 *
 */
public class WarehouseTracker {
	
	
	private static WarehouseTracker warehouseTracker = new WarehouseTracker();
	private Map<Integer, Warehouse> warehouses; // Stores a collection of warehouses
											    // mapped by their id 
	
	private WarehouseTracker()
	{
		// Store the warehouse
		warehouses = new HashMap<>();	
	}
	
	public static WarehouseTracker getInstance()
	{
		return warehouseTracker;
	}
	
	/**
	 * 
	 * Adds a new warehouse to the warehouse collection.
	 * If the warehouse already exists, we return false.
	 * @param  mWarehouse warehouse to add.
	 * @return true if add was successful,
	 * 		   false if add wasn't.
	 */
	public boolean addWarehouse(Warehouse mWarehouse) 
	{
		boolean added = false;
		if(warehouses.get(mWarehouse.getWarehouseID()) == null)
		{
			warehouses.put(mWarehouse.getWarehouseID(), mWarehouse);
			added = true;
		}
		return added;
	}
	
	
	public boolean addShipment(int warehouseID, Shipment mShipment)
	{
		Warehouse theWarehouse = warehouses.get(warehouseID);
		
		boolean add = (theWarehouse != null && theWarehouse.receivingFreight());
		if (add) {theWarehouse.addShipment(mShipment);}
		
		return add;
	}
	
	
	/**
	 * Enables a freight receipt in 
	 * a Warehouse.
	 * @param warehouseID warehouse id
	 * @return true if freight was successfully
	 *         enabled, false if not.
	 */
	
	 public boolean enableFreight(int warehouseID)
	 {
		 
		 Warehouse theWarehouse = warehouses.get(warehouseID);

		 boolean enabled = (theWarehouse != null);
		 
		 if (enabled) {theWarehouse.enableFreight();}
			
		 return enabled;
	 }
	 

	 /**
	  * Disables freight receipt in a warehouse
	  * @param warehouseID warehouse id
	  * @return true if freight was successfully
	  *         disabled, false if not.
	  */
	
	public boolean endFreight(int warehouseID) {
		Warehouse theWarehouse = warehouses.get(warehouseID);

		boolean disabled = (theWarehouse != null);
		if (disabled) {theWarehouse.disableFreight();}
		
		return disabled;
	}
	
	/**
	 * Checks if we have an empty collection of warehouses.
	 * @return true if empty, false if not.
	 */
	public boolean isEmpty()
	{
		return warehouses.size() == 0;
	}
	
	
	/**
	 * Getter for shipments size for a specified warehouse
	 * @return the size of the warehouse, -1 if
	 * 		   warehouse doesn't exist
	 */
	public int getWarehouseShipmentsSize(int warehouseID)
	{
		Warehouse theWarehouse = warehouses.get(warehouseID);
		return (theWarehouse != null ? theWarehouse.getShipmentSize() : -1);
		
	}
	
	
	
	/**
	 * Exports a warehouse object to JSON file
	 * @param warehouseID warehouse id
	 * @param destPath path to write file to
	 * @return true if warehouse exists, false if not
	 */
	public boolean exportWarehouseToJSON (int warehouseID) // What if the warehouse is empty?
	{
		
		Warehouse theWarehouse = warehouses.get(warehouseID);
		
		boolean exists = (theWarehouse != null);
		
		if (exists){ theWarehouse.exportToJSON(); }
		
		return exists;
	}
	
	/**
	 * Prints information about a warehouse for user to see.
	 * @param warehouseID the warehouse id number.
	 */
	public void printWarehouseDetails(int warehouseID) 
	{
		Warehouse theWarehouse = warehouses.get(warehouseID);
		
		if (theWarehouse == null) 
		{
			System.out.println("Warehouse with ID: " + warehouseID + " doesn't exist");
		}else 
		{
			System.out.println(theWarehouse.toString());
		}

	}
	
	
	

}
