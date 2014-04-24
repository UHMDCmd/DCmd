package edu.hawaii.its.dcmd.inf

import org.apache.commons.collections.list.LazyList
import org.apache.commons.collections.FactoryUtils

class Contractmin {

    static hasMany = [ requiredRenewForms:ContractminFormType ]

    List requiredRenewalForms = new ArrayList()

    String uhContractNo

    static constraints = {
        requiredRenewalForms(nullable:true)
    }

	def getRequiredRenewalFormsList() {
		return LazyList.decorate(
		requiredRenewalForms,
		FactoryUtils.instantiateFactory(ContractminFormType.class)
		)
	}

	String toString(){
		"${uhContractNo}"
	}
}
