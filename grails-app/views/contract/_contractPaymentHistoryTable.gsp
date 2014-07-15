<table id="contractPaymentHistoryTable" class="jquiTable">
	<thead>
	  <tr>                        
	      <th>Period Begin Date</th>  
	      <th>Period End Date</th>                      
	      <th>Amount Encumbered</th>
	       <th>Amount Paid</th>
	  </tr>
	</thead>
	<tbody>
		%{--
  - Copyright (c) 2014 University of Hawaii
  -
  - This file is part of DataCenter metadata (DCmd) project.
  -
  - DCmd is free software: you can redistribute it and/or modify
  - it under the terms of the GNU General Public License as published by
  - the Free Software Foundation, either version 3 of the License, or
  - (at your option) any later version.
  -
  - DCmd is distributed in the hope that it will be useful,
  - but WITHOUT ANY WARRANTY; without even the implied warranty of
  - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  - GNU General Public License for more details.
  -
  - You should have received a copy of the GNU General Public License
  - along with DCmd.  It is contained in the DCmd release as LICENSE.txt
  - If not, see <http://www.gnu.org/licenses/>.
  --}%

<g:each in="${contractInstance.contractPaymentHistory.sort{sp->sp.toString()}}" status="i" var="contractPaymentHistoryInstance">
    	<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">                        
	      <td><g:link class="white-link" controller="contractPaymentHistory" action="show" id="${contractPaymentHistoryInstance.id}">${fieldValue(bean: contractPaymentHistoryInstance, field: "id")}</g:link></td>                        
	      <td>${fieldValue(bean: contractPaymentHistoryInstance, field: "periodBeginDate")}</td>                        
	    	      <td>${fieldValue(bean: contractPaymentHistoryInstance, field: "periodEndDate")}</td>                        
				  <td>${fieldValue(bean: contractPaymentHistoryInstance, field: "amountEncumbered")}</td>                        
	    	      <td>${fieldValue(bean: contractPaymentHistoryInstance, field: "amountPaid")}</td>                        
	    	    
	
			</tr>
		</g:each>
	</tbody>
</table>