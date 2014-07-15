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

<%@  page import="edu.hawaii.its.dcmd.inf.HostService" %>
<%
    def hostService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.HostService').newInstance()
%>


<r:require modules='select2' />

<script type="text/javascript">



    function getPerson(e, roleName) {
        var rowId= e.id.split("_")[0];
        jQuery.ajax({
            async:false,
            url: '../person/getPersonIdFromRoleName',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: {objectId: rowId, roleName: roleName},
            success: function(data) {
                $(e).select2({
                    width:200
                }).select2('val', data.personId);
            },
            error: function () { console.log('Error updating list'); }
        });
    }

    function getStatus(e) {
        var rowId= e.id.split("_")[0];
        jQuery.ajax({
            async:false,
            url: 'getStatus',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: {hostId: rowId},
            success: function(data) {
                $(e).select2({
                    width:200
                }).select2('val', data.statusId);
            },
            error: function () { console.log('Error updating list'); }
        });
    }



$(document).ready(function() {

    jQuery("#allSupportedHosts").jqGrid({

height:'auto',
//caption:'Host List',
url:"listAllSupportList",
editurl:'editAllSupportList',
            datatype: "json",
            colNames:['', 'Host', 'Status', 'Primary SA', 'Secondary SA', 'Tertiary SA', 'Service Lead',
                 'General Notes','id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, search:false, width:40,
                    formatter: 'actions', formatoptions: {
                    keys: true, editbutton: true, delbutton:false }
                },
                {name:'hostname', width: 100, formatter: 'showlink', formatoptions: {showAction:'show'}, title:false, editable:true},
                {name:"status", width:100, editable:true, sortable:false,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"status",action:"listStatusAsSelect")}',
                    dataInit:function(e){
                        getStatus(e);
                    }
                }},
                {name:"primarySA", width:100, editable:true, sortable:false,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"person",action:"listAsSelectWithNull")}',
                    dataInit:function(e){
                        getPerson(e, 'Primary SA');
                    }
                }},
                {name:"secondSA", width:100, editable:true, sortable:false,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"person",action:"listAsSelectWithNull")}',
                    dataInit:function(e){
                        getPerson(e, 'Secondary SA');
                    }
                }},
                {name:"thirdSA", width:100, editable:true, sortable:false,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"person",action:"listAsSelectWithNull")}',
                    dataInit:function(e){
                        getPerson(e, 'Tertiary SA');
                    }
                }},
                {name:"servLead", width:100, editable:true, sortable:false,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"person",action:"listAsSelectWithNull")}',
                    dataInit:function(e){
                        getPerson(e, 'Service Lead');
                    }
                }},
                {name:'generalNote', width:200, editable:true},
                {name:'id', hidden:true}
            ],

    rowNum: 50,
    rowList: [10, 20, 50, 100, 200],
    shrinkToFit: true,
    gridview: true,
    viewrecords: true,
    sortname: 'hostname',
    sortorder: 'asc',
    autowidth:true,
    searchOnEnter:true,
    pager: '#supportedHostAllPager',
    navigator:true,
    headertitles: true,
    scrollOffset:0,
    gridComplete: function() {
        dynamicListSize("#allSupportedHosts");
    },
    exportfile: 'Report.xls',
    csvsep:","

});

//    $('#export-button').click(function(){
//        var postData = $("#allHosts").jqGrid('getGridParam','postData');
//        var str='';
//        for(i in postData)
//            str+=i+"="+postData[i]+"&";
//        window.location.href=("<?php echo $this->baseUrl() ?>/user/listpayments?export=excel&"+
//                str + new Date().getTime());
//    });

   var setTooltipsOnColumnHeader = function (grid, iColumn, text) {
        var thd = jQuery("thead:first", grid[0].grid.hDiv)[0];
        jQuery("tr.ui-jqgrid-labels th:eq(" + iColumn + ")", thd).attr("title", text);
    };


    jQuery('#allSupportedHosts').filterToolbar({id:'allHosts', searchOnEnter:true});
        $("#allSupportedHosts").jqGrid('navGrid','#supportedHostAllPager',{
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


    jQuery(window).bind('resize', function() {
        dynamicListSize('#allSupportedHosts');
    }).trigger('resize');

    });

</script>

<g:render template="../advancedOptions" model="[pageType:'host', gridId:'#allSupportedHosts', export:true, exportAction:'exportSupportList', hostFilter:true]" />
<table id="allSupportedHosts"></table>
<div id="supportedHostAllPager"></div>
