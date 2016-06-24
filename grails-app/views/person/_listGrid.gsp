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



    function getManager(e) {
        var rowId= e.id.split("_")[0];
        jQuery.ajax({
            async:false,
            url: '../person/getManagerId',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: {personId: rowId},
            success: function(data) {
                $(e).select2({
                    width:200
                }).select2('val', data.managerId);
            },
            error: function (e) { console.log(e); }
        });
    }

    function changeReport() {
        jQuery.ajax({
            async:false,
            url:  "../person/roleChangeReport",
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function(data) {
                console.log(data);
            },
            complete: function(data) {

            },
            error: function () { console.log('Error Adding Row'); }
        });
        jQuery.ajax({
            async:false,
            url:  "../person/staffChangeReport",
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function(data) {
                console.log(data);
            },
            complete: function(data) {

            },
            error: function () { console.log('Error Adding Row'); }
        });
    }

$(document).ready(function() {

jQuery("#allPerson").jqGrid({

height:'auto',
width:'1000',
caption:'Staff List',
url:'listAllPerson',
editurl:'editAllPerson',
        datatype: "json",
        colNames:['','UH Username', 'Last Name', 'First Name', 'MI', 'Title', 'Manager', 'Office Phone', 'Primary Phone', 'Secondary Phone', 'E-Mail', 'UH ID', 'General Notes', 'id'],
        colModel:[
            {name:'actions', index:'actions', editable:false, search:false, required:false, sortable:false, width:50, fixed:true,
                formatter: 'actions', hidden:false,formatoptions: {
                keys: true, editbutton: true, delbutton:false }},
            {name:'uhName', editable:false, formatter: 'showlink', formatoptions: {showAction:'show'},title:false},
            {name:'lastName', editable:false, formatter: 'showlink', formatoptions: {showAction:'show'},title:false},
            {name:'firstName', editable:false, formatter: 'showlink', formatoptions: {showAction:'show'},title:false},
            {name:'midInit', editable:false, width:45,title:false},
            {name:'title', editable:false,title:false},
            {name:'manager', editable:true, sortable:false,edittype:'select', editoptions: {
                dataUrl: '${createLink(controller:"person",action:"listAsSelectWithNull")}',
                dataInit: function (e) {
                    getManager(e);
                }
            }},
            {name:'telephone', editable:false,title:false},
            {name:'primaryPhone', editable:true,title:false},
            {name:'secondPhone', editable:true,title:false},
            {name:'primaryEmail', editable:false, width: 200,title:false},
            {name:'uhNumber', editable:false, formatter: 'showlink', formatoptions: {showAction:'show'}, width: 80,title:false},
            {name:'generalNote', width:'400', editable:true,title:false},
            {name:'id', hidden:true}
        ],
    rowNum: 50,
    rowList: [10, 20, 50, 100, 200],

    gridview: true,
    viewrecords: true,
    sortname: 'uhName',
    sortorder: 'asc',
    autowidth:true,
    shrinkToFit: true,
    searchOnEnter:true,
    headertitles:true,
    cmTemplate: { title: false },
    pager: '#allPersonPager',
    scrollOffset:0,
    gridComplete: function() {
        dynamicListSize("#allPerson");
    }
});
        jQuery('#allPerson').filterToolbar({id:'allPerson', searchOnEnter:true});
        $("#allPerson").jqGrid('navGrid','#allPersonPager',{
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

    setTooltipsOnColumnHeader($("#allPerson"),0,"Unique UH Username");
    setTooltipsOnColumnHeader($("#allPerson"),1,"Last Name");
    setTooltipsOnColumnHeader($("#allPerson"),2,"First Name");
    setTooltipsOnColumnHeader($("#allPerson"),3,"Middle Initial");
    setTooltipsOnColumnHeader($("#allPerson"),4,"Professional Title");
    setTooltipsOnColumnHeader($("#allPerson"),5,"Manager uhName");
    setTooltipsOnColumnHeader($("#allPerson"),6,"Primary Phone Contact");
    setTooltipsOnColumnHeader($("#allPerson"),7,"Primary Email Contact");
    setTooltipsOnColumnHeader($("#allPerson"),8,"Unique Campus Wide Id");



    jQuery(window).bind('resize', function() {
        dynamicListSize('#allPerson');
    }).trigger('resize');

    });

</script>
<table id="allPerson"></table>
<div id="allPersonPager"></div>

%{--<input type="button" id="changeReport" onclick="changeReport()" value="Report"/>
<g:form action="changeReport"><input type="submit"/></g:form>--}%