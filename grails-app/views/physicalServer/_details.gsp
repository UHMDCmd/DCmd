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
def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()

<script language="javascript" type="text/javascript" src="../js/isotope.pkgd.min.js"></script>
<script language="javascript" type="text/javascript">
    function setup(physicalServerInstance) {

        var $container = $('#container');
        $container.isotope({
            itemSelector: '.item',
            layoutMode: 'fitRows'
        });
    }
    %{--

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

<script type="text/javascript">

</script>


<style>
.value input[type="text"] {
    background:transparent;
    border: 0px;
    color: white;
}

.editing input[type="text"] {
    background:white;
    border: 1px black;
    color: black;
}
</style>


<div class="dialog">
<script type="text/x-handlebars-template" id="server_template">

<div id="container" class="js-isotope" data-isotope-options='{ "columnWidth":400}' style="min-height:275px">

<input type="button" id="unlock" value="Unlock" /><input type="button" id="lock" value="Lock" />

<div class="item">
    <table class="floatTables" style="border:1px solid #CCCCCC;">
        <tr><td colspan="2"><center><b>General Information</b></center></td></tr>
        <tr>
            <td valign="top" class="name">itsId</td>
            <td valign="top" class="value">
                <input type='text' value="{{server.itsId}}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name">Status</td>
            <td valign="top"class="value">
                <input type='text' value="{{server.status}}" />
            </td>
        </tr>

        <tr>
            <td valign="top" class="name">Server Type</td>
            <td valign="top" class="value">
                <select id="serverType">
                    {{#each sTypes}}
                    <option value="{{this.id}}"}>{{this.text}}</option>
                    {{/each}}
                </select></td>
        </tr>
    <tr>
        <td valign="top" class="name">Clusters</td>
        <td valign="top" class="value">
            <select id="cluster">
                {{#each clusterList}}
                <option value="{{this.id}}">{{this.text}}</option>
                {{/each}}
            </select></td>
    </tr>
    </table>
</div>
</div>
<br />

</script>



</div>