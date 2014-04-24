package edu.hawaii.its.dcmd.inf

class AssetMaintenanceContract extends Contract {

	static belongsTo = [ asset : Asset ]
	
    static constraints = {
    }
}
