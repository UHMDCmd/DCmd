package edu.hawaii.its.dcmd.inf

class Costs {
	
	Integer renewalFiscalYear
	Float renewalCost
	Float listPrice
	Float uhDiscount
	Boolean isProrated=false
	Boolean includesTax=false
	Float taxRate
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	
	static belongsTo = [asset:Asset]

    static constraints = { 
		renewalFiscalYear(blank:false, range:10001231..30001231) 
		renewalCost(blank:false, min:0F)
		uhDiscount(blank:false, range:0F..0.75F)
		isProrated(blank:false)
		includesTax(blank:false)
		taxRate(blank:false, range:0F..0.1500F) 
    } 
}
