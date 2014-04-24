package edu.hawaii.its.dcmd.inf

class AssetPurchaseContract extends Contract {

	static belongsTo = [ asset : Asset ]
	
    static constraints = {
    }
}
