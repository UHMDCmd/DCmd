/*
 * Copyright (c) 2014 University of Hawaii
 *
 * This file is part of DataCenter metadata (DCmd) project.
 *
 * DCmd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DCmd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DCmd.  It is contained in the DCmd release as LICENSE.txt
 * If not, see <http://www.gnu.org/licenses/>.
 */

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
