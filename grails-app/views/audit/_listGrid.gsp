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

<script type="text/javascript">

$(document).ready(function() {

    $('#clearLog').bind('click', function() {
        //set up the ajax call
        $.ajax({
            url: 'clearLog',
            type: 'POST',
            success: function(data) {
                $("#auditLog").trigger("reloadGrid");
            },
            error: function(){
                alert('Could not clear the log');
            }
        });
    });

jQuery("#auditLog").jqGrid({

height:'auto',
width:'1000',
//caption:'Audit Log',
url:'listAuditLog',
            datatype: "json",
            colNames:['Action', 'Timestamp', 'User', 'Entity Type', 'Entity', 'Attribute', 'Old Value', 'New Value', 'id'],
            colModel:[
                {name:'eventName', width:50, editable:false,title:false},
                {name:'lastUpdated', width:50, search:false, editable:false,title:false},
                {name:'actor', width:50, editable:false,title:false},
                {name:'className', width:50, editable:false,title:false},
                {name:'persistedObjectId', width:50, editable:false,title:false},
                {name:'propertyName', width:50, editable:false,title:false},
                {name:'oldValue', width:50, editable:false,title:false},
                {name:'newValue', width:50, editable:false,title:false},
                {name:'id', hidden:true}
            ],

    rowNum: 20,
    rowList: [5, 10, 20, 50, 100],

    gridview: true,
    viewrecords: true,
    sortname: 'id',
    sortorder: 'desc',
    autowidth:true,
    shrinkToFit: true,
    searchOnEnter:true,
    pager: '#auditLogPager',
    scrollOffset:0,
    gridComplete: function() {
        dynamicListSize("#auditLog");
    }

});
        jQuery('#auditLog').filterToolbar({id:'auditLog', searchOnEnter:true});
        $("#auditLog").jqGrid('navGrid','#auditLogPager',{
                add:false,
                del:false,
                edit:false,
                refresh:false,
                refreshstate:"current",
                search:false
        },
        {},//edit options

        {recreateForm:true //since clearAfterAdd is trueby default, recreate the form so we re-establish value for parent id
        });


    var setTooltipsOnColumnHeader = function (grid, iColumn, text) {
        var thd = jQuery("thead:first", grid[0].grid.hDiv)[0];
        jQuery("tr.ui-jqgrid-labels th:eq(" + iColumn + ")", thd).attr("title", text);
    };

    setTooltipsOnColumnHeader($("#auditLog"),0,"The Action Executed");
    setTooltipsOnColumnHeader($("#auditLog"),1,"The Time The Action was Executed");
    setTooltipsOnColumnHeader($("#auditLog"),2,"UH Username of User who Executed the Action");
    setTooltipsOnColumnHeader($("#auditLog"),3,"Type of Entity the Action was performed on");
    setTooltipsOnColumnHeader($("#auditLog"),4,"DCmd ID of Entity the Action was performed on");
    setTooltipsOnColumnHeader($("#auditLog"),5,"Name of the Field/Attribute that was manipulated");
    setTooltipsOnColumnHeader($("#auditLog"),6,"Value of attribute before Action (Not available for Insert/Delete)");
    setTooltipsOnColumnHeader($("#auditLog"),7,"New Value of attribute (Not available for Insert/Delete)");
    
    jQuery(window).bind('resize', function() {
        dynamicListSize('#auditLog');
    }).trigger('resize');

    });

</script>

<table id="auditLog"></table>
<div id="auditLogPager"></div>

