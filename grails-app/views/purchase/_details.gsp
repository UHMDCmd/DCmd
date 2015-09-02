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

<%@  page import="edu.hawaii.its.dcmd.inf.GeneralService" %>

<script language="javascript" type="text/javascript">
    %{--
    function setup(purchaseInstance) {

        var $container = $('#container');
        $container.isotope({
            itemSelector: '.item',
            layoutMode: 'fitRows'
        });
    }


    $("#serverType").on("change", function (e) {
        changeServerType(e);
    });
    changeServerType("${physicalServerInstance.serverType}");
    /*
     $("#currentRack").select2({
     placeholder: 'Please Select...',
     maximumInputLength: 20,
     width:150,
     initSelection: function(element, callback) {
     var data = {id: "
    ${assetInstance?.getRackAssignmentId()}", text: "
    ${assetInstance?.getRackAssignment()}"};
     callback(data);
     },
     data: [
    ${genService.listRacksAsSelect()}]
     }).select2('val', '0');
     */
}


function changeServerType(type) {
    var i;
    var myElements;
    if(type.val == "VMWare" || type == "VMWare") {
        myElements = document.querySelectorAll(".vmOption");

        for (i = 0; i < myElements.length; i++) {
            myElements[i].style.visibility='visible';
            myElements[i].style.position='relative';
        }
//            document.getElementById("otherOption").style.visibility = 'hidden';
//            document.getElementById("otherOption").style.position = 'absolute';
        document.getElementById("vmWarning").style.visibility='visible';
    }
    else {
        myElements = document.querySelectorAll(".vmOption");

        for (i = 0; i < myElements.length; i++) {
            myElements[i].style.visibility='hidden';
            myElements[i].style.position='absolute';
        }
//            document.getElementById("otherOption").style.visibility = 'visible';
//            document.getElementById("otherOption").style.position = 'relative';
        document.getElementById("vmWarning").style.visibility='hidden';
    }
//        var $container = $("#container");
//        $container.isotope('layout');
}
       --}%

</script>


<style>
.value input[type="text"] {
    background:transparent;
    border: 0px;
    color: white;
}
.value textarea {
    background:transparent;
    border: 0px;
    color: white;
}

.editing input[type="text"] {
    background: white;
    border: 1px black;
    color: black;
}

.editing textarea {
    background: white;
    border: 1px black;
    color: black;
}

.item {
    float:left;
    width:350px;
}
</style>


<div class="dialog">
<script type="text/x-handlebars-template" id="purchase_template">
<div class="lockBtns">
    <div class="dialog_buttons" id="editBtn">
    <input type="button" id="edit" class="dialog_edit_button" value="Edit"/>
    </div>
</div>
<div class="unlockBtns">
    <div class="dialog_buttons" id="saveBtn">
    <input type="button" id="save" value="Save" class="dialog_save_button" />
    </div>
    <div class="dialog_buttons" id="discardBtn">
        <input type="button" id="discard" value="Discard" class="dialog_discard_button" />
    </div>
</div>
<div id="purchase_container"  style="min-height:275px">



<div class="item">
    <table class="floatTables" style="border:1px solid #CCCCCC;">
        <tr><td colspan="2"><center><b>General Information</b></center></td></tr>
        <tr>
            <td valign="top" class="name">Fiscal Year Encumbered</td>
            <td valign="top"class="value">
                <input type='text' id='fiscalYear' value="{{purchase.fiscalYear}}" />
            </td>
        </tr>
%{--        <tr>
            <td valign="top" class="name">Contract Number</td>
            <td valign="top"class="value">
                <input type='text' id="uhContractNumber" value="{{purchase.uhContractNumber}}" />
            </td>
        </tr>
        --}%
        <tr>
            <td valign="top" class="name">Contract/ID</td>
            <td valign="top" class="value">
                <input type='text' id="uhContractTitle" value="{{purchase.uhContractTitle}}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name">Purchase Status</td>
            <td valign="top" class="value">
                <select id="purchaseStatus">
                    {{#each purchaseStatuses}}
                    <option value="{{this.id}}"}>{{this.text}}</option>
                    {{/each}}
                </select></td>
        </tr>
        %{--
        <tr>
            <td valign="top" class="name">Requested By</td>
            <td valign="top" class="value">
                <select id="requestedBy">
                    <option value=0>N/A</option>
                    {{#each personOptions}}
                    <option value="{{this.id}}"}>{{this.uhName}}</option>
                    {{/each}}
                </select></td>
        </tr>
        <tr>
            <td valign="top" class="name">Facilitated By</td>
            <td valign="top" class="value">
                <select id="facilitatedBy">
                    <option value=0>N/A</option>
                    {{#each personOptions}}
                    <option value="{{this.id}}"}>{{this.uhName}}</option>
                    {{/each}}
                </select></td>
        </tr>
        --}%
    </table>
</div>
<div class="item">
    <table class="floatTables" style="border:1px solid #CCCCCC;">
        <tr><td colspan="2"><center><b>Purchase Details</b></center></td></tr>
        <tr>
            <td valign="top" class="name">Purchase Type</td>
            <td valign="top" class="value">
                <select id="purchaseType">
                    {{#each purchaseTypes}}
                    <option value="{{this.id}}"}>{{this.abbreviation}}</option>
                    {{/each}}
                </select></td>
        </tr>
        <tr>
            <td valign="top" class="name">Payment Type</td>
            <td valign="top" class="value">
                <select id="paymentType">
                    {{#each paymentTypes}}
                    <option value="{{this.id}}"}>{{this.text}}</option>
                    {{/each}}
                </select></td>
        </tr>
        <tr>
            <td valign="top" class="name">Hawaii Tax Rate</td>
            <td valign="top"class="value">
                <input type='text' id="hawaiiTaxRate" value="{{purchase.hawaiiTaxRate}}" />
            </td>
        </tr>
        %{--
        <tr>
            <td valign="top" class="name">PCard Issued</td>
            <td valign="top"class="value">
                <input type='checkbox' id="pcard" {{#if purchase.pcard}}checked{{/if}} />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name">Requisitioned</td>
            <td valign="top"class="value">
                <input type='checkbox' id="requisition" {{#if purchase.requisition}}checked{{/if}} />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name">Has Support Renewal</td>
            <td valign="top"class="value">
                <input type='checkbox' id="hasSupportRenewal" {{#if purchase.hasSupportRenewal}}checked{{/if}} />
            </td>
        </tr>
            --}%
    </table>
    </div>
    <div class="item">
        <table class="floatTables" style="border:1px solid #CCCCCC;">
            <tr><td colspan="2"><center><b>Dates</b></center></td></tr>
            <tr>
                <td valign="top" class="name">Contract/Period Begin Date</td>
                <td valign="top"class="value">
                    <input type='text' id="uhContractBeginDate" class='datePicker' value="{{purchase.uhContractBeginDate}}" />
                </td>
            </tr>
            <tr>
                <td valign="top" class="name">Contract/Period End Date</td>
                <td valign="top"class="value">
                    <input type='text' id="uhContractEndDate" class='datePicker' value="{{purchase.uhContractEndDate}}" />
                </td>
            </tr>
            <tr>
                <td valign="top" class="name">Anniversary (mm/dd)</td>
                <td valign="top"class="value">
                    <input type='text' id="anniversary" class='anniversary' value="{{purchase.anniversary}}" />
                </td>
            </tr>
            <tr>
                <td valign="top" class="name">Multi-year</td>
                <td valign="top"class="value">
                    <input type='checkbox' id="multiyear" {{#if purchase.multiyear}}checked{{/if}} />
                </td>
            </tr>
            %{--
            <tr>
                <td valign="top" class="name">Period Begin Date</td>
                <td valign="top"class="value">
                    <input type='text' id="periodBeginDate" class='datePicker' value="{{purchase.periodBeginDate}}" />
                </td>
            </tr>
            <tr>
                <td valign="top" class="name">Period End Date</td>
                <td valign="top"class="value">
                    <input type='text' id="periodEndDate" class='datePicker' value="{{purchase.periodEndDate}}" />
                </td>
            </tr>
            --}%
    </table>
    </div>
    <div class="item">
    <table class="floatTables" style="border:1px solid #CCCCCC;">
        <tr><td colspan="2"><center><b>Vendor Information</b></center></td></tr>
        <tr>
            <td valign="top" class="name">Vendor Name</td>
            <td valign="top" class="value">
                <input type='text' id="vendorName" value="{{purchase.vendorName}}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name">Vendor Customer ID</td>
            <td valign="top"class="value">
                <input type='text' id="vendorCustomerId" value="{{purchase.vendorCustomerId}}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name">Vendor Contract ID</td>
            <td valign="top"class="value">
                <input type='text' id="vendorContractId" value="{{purchase.vendorContractId}}" />
            </td>
        </tr>
        %{--
        <tr>
            <td valign="top" class="name">SuperQuote Number</td>
            <td valign="top"class="value">
                <input type='text' id="superQuoteId" value="{{purchase.superQuoteId}}" />
            </td>
        </tr>
        --}%
    </table>
   </div>
    <div class="item">
        <table class="floatTables" style="border:1px solid #CCCCCC;">
            <tr><td colspan="2"><center><b>Notes</b></center></td></tr>
            <tr>
                <td valign="top" class="name">General Notes</td>
                <td valign="top" class="value">
                    <textarea style="height:50px;" id="generalNote">{{purchase.generalNote}}</textarea>
                </td>
            </tr>
        </table>
    </div>
</div>
<br />

</script>



</div>