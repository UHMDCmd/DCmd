package edu.hawaii.its.dcmd.inf

/**
 * Forms and documents are required for submission along with a contract's renewal.
 * A contract may have zero, one or more required forms for an annual renewals
 */
class ContractFormType {

    boolean deleted
    static transients = ['deleted']
	static hasMany = [contracts:Contract]
	static belongsTo = Contract

	String form                                  // Form type: memo, Form 95, ...
	String description                           // Form/doc title or description.
	String formUrl                               // URL to the form/doc PDF template.
	Date dateCreated = new Date()
	Date lastUpdated = new Date()

	static constraints = {
		form(nullable:false, unique:true, blank:false)
		description(nullable:false, blank:false)
		formUrl(nullable:true, blank:true)
	}

	String toString() {
		"${form} - ${description}"
	}
}
