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
<%
    def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()
%>

<r:require modules='select2' />

<script>
    $(document).ready(function() {

        var colorVar = 'FF0000';

        $('#assetSelect').select2({
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            width:200,
            data: [${genService.listRackableAssetsAsSelect()}]
        }).select2('val', '0');

        $('#assetSelect').on("change", function() {
            getNumPositions();
        });

        //Bind the click event here
        $('#addAsset').bind('click', function() {
            if(validateAssetSelection()){

                //set up the ajax call
                $.ajax({
                    url: '/its/dcmd/rack/addAssetToRack',
                    data: getAssetParamsForAdd(),
                    type: 'POST',
                    success: function(data) {renderRack(data); getNumPositions();},
                    error: function(){
                        alert('The addition of the Asset was not successful.');
                    }
                });
            }
        });

        $('#addPlan').bind('click', function() {
            if(validateAssetSelection()){

                //set up the ajax call
                $.ajax({
                    url: '/its/dcmd/rack/addPlannedAssetToRack',
                    data: getAssetParamsForAdd(),
                    type: 'POST',
                    success: function(data) {renderRack(data); getNumPositions();},
                    error: function(){
                        alert('The addition of the Asset was not successful.');
                    }
                });
            }
        });

        // Reserve button
        $('#reserveSelected').bind('click', function() {
            jQuery.ajax({
                async: false,
                url: '/its/dcmd/rack/reserveSelected',
                data: getSelected(),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                cache: false,
                success: function(data) {renderRack(data);},
                //error: function cleanData() function ()  {alert('Can only Reserve Open slots'); }
                error: function ()  {alert('Can only Reserve Open slots'); renderRack(data)}

            });


        });

        // Open button
        $('#openSelected').bind('click', function() {

            jQuery.ajax({
                async: false,
                url: '/its/dcmd/rack/openSelected',
                data: getSelected(),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function(data) {renderRack(data); getNumPositions();},
                error: function () { alert('Error trying to validate'); }
            });

        });


    });

    /*
     * Get the value of the asset from dropdown
     */
    function validateAssetSelection(){
        var msg='';
        if($('#asset_Id').val() == ''){
            msg += 'You must select an Asset from the dropdown.';
        }
        if($('#asset_Id').val() == '-Choose an Asset to Add-'){
            msg += 'You must select an Asset from the dropdown.';
        }
        if(msg != ''){
            alert(msg);
            return false;
        }else{
            return true;
        }
    }

    function getAssetParamsForAdd(){

        var params = { asset_Id:$('#assetSelect').val(), addPosition:document.getElementById('select-result').innerHTML, id:$('#id').val()};
        return $.param(params);
    }

    function getNumPositions() {
//        alert("TEST");
//          alert ($('#asset_Id').val());

        jQuery.ajax({
            async: false,
            url: '/its/dcmd/rack/numPositions',
            data: getAssetParamsForAdd(),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function(data) {
                document.getElementById('numPositions').innerHTML = data.positions;
                document.getElementById('onCurrentRack').innerHTML = data.currentRack;
                document.getElementById('onPlannedRack').innerHTML = data.plannedRack;
            },
            error: function () { alert('Error with Asset selection'); }
        });

    }

    function getAssetsParamsForRemove(){
        var params = {assetsToRemove:assetsToRemove, id:$('#id').val()};
        return $.param(params);
    }

    function getSelected(){
        //  alert(document.getElementById('select-result').innerHTML);
        var params = { selected:document.getElementById('select-result').innerHTML, id:$('#id').val()};
        return $.param(params);
    }

    function renderRack(data){
        var newTable = "";

        if(data.data == -1) {
            alert("Asset is already assigned to a Rack.");

        }
        else if(data.data == -2) {
            alert("Can only place Asset in \'Open\' RUs.");
        }
        else {
            for  (var i=44; i>=0; i=i-1) {
                // newTable = newTable + "<li id='ru" + data.data[i].RUstatus + "' class='ui-state-default'>" + data.data[i].label + "</li>";
                newTable = newTable + "<li id='ru" + data.data[i].RUstatus + "' class='ui-state-default'>" +


                        "<table id ='ru_element'>" +
                        "<tr>" +
                        "<td class='ends' width='5%'>"+
                        "<div class='nums'><p>" + (i+1) + "</p></div>" +
                        "</td>" +
                        "<td  class='label' width='90%'>" +
                        "<p>" + data.data[i].label + "<p>" + "</td>" +
                        "<td class='ends' width='5%'>" +
                        "<div class='nums'><p>" + (i+1) + "</p></div>" +
                        "</td>" +
                        "</tr>" +
                        "</table>" +
                        "</li>";


            }
            document.getElementById('rackItem').innerHTML = newTable;
            document.getElementById('select-result').innerHTML = "";

            //  data.data.removeData();
        }
    }

</script>

<div>
    <table>
        <thead>
        <g:if test="${action == 'edit'}">
            <th id="stickyDiv">
                <label for="asset_Id"><g:message code = "rack_asset.code.label" default="Select Rack Asset" /></label>
            </th>
        </g:if>
        <th >
            <label for="section1"><g:message code = "section1.code.label" default="Rack Collection" /></label>
        </th>

        %{--<th>
            &nbsp;
        </th>--}%
        </thead>
        <tbody>


        <g:if test="${action == 'edit'}">
            <tr>
            <g:render template="../rack/sticky_bar" />
            <td  id="rackButtons">
                <div>
                    <input type="hidden" id="assetSelect" name="assetSelect"/>


                    <br> <br>
                    RUs required by Asset: <span id="numPositions"></span>
                    <br> <br>
                    Current Rack placement: <span id="onCurrentRack"></span>
                    <br> <br>
                    Planned Rack placement: <span id="onPlannedRack"></span>
                    <br><br>

                    RUs currently selected:<span name="select-result" id="select-result" class="srs"></span>
                    <br><br>
                    <button id="addAsset"
                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                            role="button" aria-disabled="false">
                        <span class="ui-button-text">Make <b>current</b> Asset position</span>
                    </button><br>
                    <button id="addPlan"
                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                            role="button" aria-disabled="false">
                        <span class="ui-button-text">Make <b>planned</b> Asset position</span>
                    </button><br>
                    <button id="reserveSelected"
                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                            role="button" aria-disabled="false">
                        <span class="ui-button-text">Reserve Selected RUs</span>
                    </button>
                    <br>
                    <button id="openSelected"
                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                            role="button" aria-disabled="false">
                        <span class="ui-button-text">Open Selected RUs</span>
                    </button>
                    <br><br>
                    <h3>Rack Operations:</h3><br>
                    <ul>
                        <li> <b>Add current:</b>  Select An asset from the drop-down list, click a start position on the right, and
                        click "Make current Asset position".  Asset will occupy RUs started at selected position, going down.</li><br>
                        <li><b>Add planned:</b> The same as above, except is the <b>planned</b> position of the Asset.  Note that
                        RUs can have both a current <b>and</b> Asset in them.</li><br>
                        <li><b>Reserve Slots:</b>  Select any number of Open slots and click "Reserve" to reserve RU(s).</li><br>
                        <li><b>Open Slots:</b>  Select any number of slots and click "Open" to clear RU(s).  Note any Assets
                        occupying selected RUs will be completely removed from Rack.</li>
                    </ul>
                </div>
            </td>
        </g:if>
        <g:hiddenField name="id" value="${rackId}" />

        <script>
            $(function() {
                $( "#rackItem").selectable({
                    cancel: 'a',
                    stop: function() {
                        var result = $( "#select-result").empty();

                        $(".ui-selected", this).each(function() {
                            var index = 45-$( "#rackItem li").index(this);
                            result.append(" " + (index));
                        });
                      
                    }

                });


            });

        </script>

        <td style="width:75%">
            <div id="rackDisplay">
                <table>
                    <tr>
                        %{--<td style="width:4%">--}%
                        %{--<ul name= "rackIndex" id="rackIndex" class='droptrue'>--}%
                        %{--<g:each in="${edu.hawaii.its.dcmd.inf.Rack.get(rackId.toLong()).RUs.reverse()}" var="RU" status = "i">--}%
                        %{--<li>${45-i}</li>--}%
                        %{--</g:each>--}%
                        %{--</ul>--}%
                        %{--</td>--}%
                        <td>
                            <ul name = "rackItem" id="rackItem" class='droptrue'>
                                <g:each in="${edu.hawaii.its.dcmd.inf.Rack.get(rackId.toLong()).RUs.reverse()}" var="RU" status = "i">
                                    <li id="ru${RU.RUstatus}" class="ui-state-default">
                                        <table id ="ru_element">
                                            <tr>
                                                <td class="ends" width="5%">
                                                    <div class="nums"><p>${45-i}</p></div>
                                                </td>
                                                <td  class="label" width="90%">
                                                    ${RU.toString()}
                                                </td>
                                                <td class="ends" width="5%">
                                                    <div class="nums"><p>${45-i}</p></div>
                                                </td>
                                            </tr>
                                        </table>
                                    </li>
                                </g:each>
                            </ul>
                        </td>
                    </tr>
                </table>
            </div>

        </td>
        </tr>
        </tbody>
    </table>


</div>
<style type="text/css">
.nums {
    /*internal text*/
    display:table;
    /*width:50px;*/
    /*height:30px;*/
    /*background: -moz-linear-gradient(top, #8f8f8f, #252525);*/
    /*background: -webkit-linear-gradient(top, #8f8f8f, #252525);*/
    /*background: -o-linear-gradient(top, #8f8f8f, #252525);*/
    /*background: linear-gradient(top, #8f8f8f, #252525);*/

    /*border: 2px solid transparent; -moz-box-shadow: 0 0 5px 5px #4e4c22;*/
    /*-webkit-box-shadow: 0 0 5px 5px #4e4c22;*/
    /*box-shadow: 0 0 1px 1px #4e4c22;*/
    /*border-radius: 5px;*/
    vertical-align: middle;
    text-align: center;
    height:20px;
    border-radius: 5px;

}

.nums p {
    font-family:Veranda, Helvetica, sans-serif ; font-size: 140%; color: #c5d2d3;
    text-shadow:
    -2px -1px 0 #343434,
    2px -1px 0 #343434,
    -2px 1px 0 #343434,
    2px 1px 0 #343434;
    text-align: justify;
    vertical-align: middle;
    padding-top: 8px;
    padding-left: 2px;


    /*background-color: #4c4c4c;*/
}
</style>