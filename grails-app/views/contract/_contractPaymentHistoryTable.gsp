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