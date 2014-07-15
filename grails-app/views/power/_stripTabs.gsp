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

<g:set var="action" value="${actionName != 'show' ? 'edit' : 'show'}"/>
<g:set var="assetId" value="${assetId}" />

<r:require module="select2" />

<script type="text/javascript">

    function unplugDevice(plugId) {
        var conBox = confirm("Unplug Device?");
        if(conBox){
            jQuery.ajax({
                async:false,
                url: 'unplugDevice',
                datatype:'json',
                contentType: 'application/json; charset=utf-8',
                data:{plugId:plugId},
                success: function(data) {
                    $("#device_list").trigger("reloadGrid");
                },
                error: function () { console.log('Error unplugging device.'); }
            });
        }
    }

    $("#btnPlugDevice").click(function(){
        jQuery("#device_list").setColProp('device', {editOptions:{dataUrl:'${createLink(controller:"power", action:"listDevicesAsSelect", id:"BLAH!")}'}});

        jQuery("#device_list").trigger("reloadGrid");

        $("#device_list").jqGrid("editGridRow","new",
                {addCaption:'Plug in a Device',
                    width:500,
                    height:200,
                    recreateForm:true,
                    //editData:{service:function() {return document.getElementById('service').value}, application: function() {return document.getElementById('application').value}},
                    closeAfterAdd: true,
                    params:{stripId:assetId},
                    savekey:[true,13],
                    afterComplete: function() {
                    //    $("#technicalSupportStaff_list").trigger("reloadGrid");
                    //    $("#functionalSupportStaff_list").trigger("reloadGrid");
                        $("#device_list").trigger("reloadGrid");
                    },
                    afterShowForm: function() {
                        var test = document.getElementById("TblGrid_device_list");
                        test.style.marginTop = "0.5em";
                        test.style.marginBottom = "0.8em";
                        test.style.width= "90%";
                    }
                }
        );
    });



    function loadDeviceGrid(stripId) {
        console.log("TEST" + assetId);
        document.getElementById("tabs").style.visibility='visible';
        jQuery("#device_list").setGridParam({url:'listDevices?stripId='+assetId});
        jQuery("#device_list").setGridParam({editurl:'editDevices?stripId='+assetId});

//        jQuery("#device_list").setColProp('device', {editOptions:{dataUrl:'${createLink(controller:"power", action:"listDevicesAsSelect", id:"BLAH!")}'}});

        jQuery("#device_list").trigger("reloadGrid");
        var editOption = true;
        jQuery("#device_list").jqGrid({

            height:'auto',
            width:'1000',
            showPager:'true',
            url:'listDevices?stripId='+assetId,
            editurl:'editDevices?stripId=' + assetId,
            datatype: "json",
            colNames:['Device', 'Connector Used','', 'id'],
            colModel:[
                {name:'device', width:160, editable:true, edittype:'select', editoptions: {dataUrl:'${createLink(controller:"power",action:"listDevicesAsSelect",id:"TEST")}',
                    dataInit:function(e){$(e).select2({
                        width: 150
                    })}
                }},
                {name:'connector', width:160, editable:false},
                {name:'', width:80, editable:false, sortable:false, search:false},
                {name:'id', hidden:true}
            ],

            rowNum:1000,
            //  pager: jQuery('#application_list_pager'),
            viewrecords: true,
            gridview: true,
            cellEdit:false,
            cellsubmit: 'remote',
            shrinkToFit: true,
            autowidth: true,
            jqModal:false,
            modal:true
        });

        //    dynamicGridSize('#device_list');

        //    jQuery(window).bind('resize', function() {
        //        dynamicGridSize('#device_list');
        //    }).trigger('resize');
    }

</script>


<div id="tabs" style="margin-top: 5px; visibility:hidden;">
    <ul>
        <li>
            <a href="#tabs-devices">Devices Connected
            </a>
        </li>
            <li>
                <a href="#tabs-notes"><g:message code="notes.label" default="Notes" /></a>
            </li>
        </ul>
    <div id="tabs-devices">
        <table id="device_list" class="device_list"></table>
        <div style="margin-top:5px; text-align: left;">
            <input class="ui-corner-all" id="btnPlugDevice" type="button" value="Plug in a Device"/>
        </div>
    </div>

    <div id="tabs-notes">
        <ul>

            <li>
                <a href="#notes-general"><g:message code="notes-general.label" default="General" /></a>
            </li>
            <li>
                <a href="#notes-change"><g:message code="notes-change.label" default="Change" /></a>
            </li>
            <li>
                <a href="#notes-planning"><g:message code="notes-planning.label" default="Planning" /></a>
            </li>
        </ul>
        <div id="notes-general">
            <g:render template='../noteTab' model="[action:action, pageType:'asset', noteType:'generalNote', objectId: assetId]"/>
        </div>
        <div id="notes-change">
            <g:render template='../noteTab' model="[action:action, pageType:'asset', noteType:'changeNote', objectId: assetId]"/>
        </div>
        <div id="notes-planning">
            <g:render template='../noteTab' model="[action:action, pageType:'asset', noteType:'planningNote', objectId: assetId]"/>
        </div>
    </div>
</div>
<br>
<div>
    <input class="ui-corner-all" id="btnSubmitStripChanges" type="button" value="Save Changes"/>
    <input class="ui-corner-all" id="btnCancelStripChanges" type="button" value="Cancel"/>
</div>