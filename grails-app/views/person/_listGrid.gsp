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

jQuery("#allPerson").jqGrid({

height:'auto',
width:'1000',
caption:'Staff List',
url:'listAllPerson',
            datatype: "json",
            colNames:['UH Username', 'Last Name', 'First Name', 'MI', 'Title', 'Phone #', 'E-Mail', 'UH ID', 'General Notes', 'id'],
            colModel:[
                {name:'uhName', editable:false, formatter: 'showlink', formatoptions: {showAction:'show'},title:false},
                {name:'lastName', editable:false, formatter: 'showlink', formatoptions: {showAction:'show'},title:false},
                {name:'firstName', editable:false, formatter: 'showlink', formatoptions: {showAction:'show'},title:false},
                {name:'midInit', editable:false, width:25,title:false},
                {name:'title', editable:false,title:false},
                {name:'telephone', editable:false,title:false},
                {name:'primaryEmail', editable:false, width: 200,title:false},
                {name:'uhNumber', editable:false, formatter: 'showlink', formatoptions: {showAction:'show'}, width: 80,title:false},
                {name:'generalNote', width:'400', editable:false,title:false},
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
    setTooltipsOnColumnHeader($("#allPerson"),5,"Primary Phone Contact");
    setTooltipsOnColumnHeader($("#allPerson"),6,"Primary Email Contact");
    setTooltipsOnColumnHeader($("#allPerson"),7,"Unique Campus Wide Id");



    jQuery(window).bind('resize', function() {
        dynamicListSize('#allPerson');
    }).trigger('resize');

    });

</script>
<table id="allPerson"></table>
<div id="allPersonPager"></div>