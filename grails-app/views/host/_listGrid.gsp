<script type="text/javascript">

$(document).ready(function() {


// Getting frozen columns to line up correctly with wordwrap enabled for jqGrid.
// Resource: http://stackoverflow.com/questions/8686616/how-can-i-get-jqgrid-frozen-columns-to-work-with-word-wrap-on
        var fixPositionsOfFrozenDivs = function () {
        var grid = this.grid || this;
        var $rows;
        if (typeof grid.fbDiv !== "undefined") {
            $rows = $('>div>table.ui-jqgrid-btable>tbody>tr', grid.bDiv);
            $('>table.ui-jqgrid-btable>tbody>tr', grid.fbDiv).each(function (i) {
                var rowHight = $($rows[i]).height(), rowHightFrozen = $(this).height();
                if ($(this).hasClass("jqgrow")) {
                    $(this).height(rowHight);
                    rowHightFrozen = $(this).height()+0.2;
                    if (rowHight !== rowHightFrozen) {
                        $(this).height(rowHight + (rowHight - rowHightFrozen));
                    }
                }
            });
            $(grid.fbDiv).height(grid.bDiv.clientHeight);
            $(grid.fbDiv).css($(grid.bDiv).position());
        }
        if (typeof grid.fhDiv !== "undefined") {
            $rows = $('>div>table.ui-jqgrid-htable>thead>tr', grid.hDiv);
            $('>table.ui-jqgrid-htable>thead>tr', grid.fhDiv).each(function (i) {
                var rowHight = $($rows[i]).height(), rowHightFrozen = $(this).height();
                $(this).height(rowHight);
                rowHightFrozen = $(this).height();
                if (rowHight !== rowHightFrozen) {
                    $(this).height(rowHight + (rowHight - rowHightFrozen));
                }
            });
            $(grid.fhDiv).height(grid.hDiv.clientHeight);
            $(grid.fhDiv).css($(grid.hDiv).position());
        }
    };

jQuery("#allHosts").jqGrid({

height:'auto',
//caption:'Host List',
url:'listAll',
            datatype: "json",
            colNames:['Hostname', 'Physical Server', 'Host Type', 'VM State', 'Environment', 'Primary Host SA', 'Host OS', 'Application(s)',
                'Service(s)', 'Service Primary SA(s)','Max. Memory (GB)', 'Max. CPU (MHz)','IP Address', 'General Notes', 'id'],
            colModel:[
                {name:'hostname', width: 100, formatter: 'showlink', formatoptions: {showAction:'show'}, frozen:true, title:false},
                {name:'asset', width:120, frozen:true, title:false},
                {name:'type', width:150,title:false},
                {name:'vCenterState', width:100,title:false},
                {name:'env', width:100,title:false},
                {name:'primarySA', width:120, sortable:false,title:false},
                {name:'os', width:250,title:false},
                {name:'appList', width:200,title:false, sortable:false},
                {name:'serviceList', width:250, title:false, sortable:false},
                {name:'serviceAdmin', width: 200, sortable:false, search:false, title:false},
                {name:'maxMemory', width: 120, title:false, search:false},
                {name:'maxCpu', width: 100, title:false, search:false},
                {name:'ip', width:150, title:false, sortable:false},
                {name:'generalNote', width:'400',title:false},
                {name:'id', hidden:true}
            ],

    rowNum: 50,
    rowList: [10, 20, 50, 100, 200],
    gridview: true,
    viewrecords: true,
    sortname: 'hostname',
    sortorder: 'asc',
    autowidth:false,
    forceFit:true,
    shrinkToFit: false,
    searchOnEnter:true,
    pager: '#hostAllPager',
    headertitles: true,
    scrollOffset:0,
    toolbar:[true, 'top'],
    gridComplete: function() {
        dynamicListSize("#allHosts");
    },
    loadComplete: function() {
        fixPositionsOfFrozenDivs.call(this);
    },
    resizeStop: function() {
        fixPositionsOfFrozenDivs.call(this);
    }
});

    createTopToolbar("#allHosts");

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

    setTooltipsOnColumnHeader($("#allHosts"),0,"ITS name of the Host");
    setTooltipsOnColumnHeader($("#allHosts"),1,"The Physical Server Host is running on");
    setTooltipsOnColumnHeader($("#allHosts"),2,"Virtualizaiton Type (e.g., Global Zone, VMWare, Standalone, etc.)");
    setTooltipsOnColumnHeader($("#allHosts"),3,"State from VCenter sync (not found if not in VCenter, N/A of non-VMWare Host)");
    setTooltipsOnColumnHeader($("#allHosts"),4,"The Environment of the Host. e.g. Prod, Test, Dev, etc.");
    setTooltipsOnColumnHeader($("#allHosts"),5,"The Primary System Administrator for this Host");
    setTooltipsOnColumnHeader($("#allHosts"),6,"The Primary Operating System of this Host");
    setTooltipsOnColumnHeader($("#allHosts"),7,"The Applications depending on this Host");
    setTooltipsOnColumnHeader($("#allHosts"),8,"Services running on the Host");
    setTooltipsOnColumnHeader($("#allHosts"),9,"Primary SA for each running Service");
    setTooltipsOnColumnHeader($("#allHosts"),10,"Maximum Memory Assigned to this Virtual Host");
    setTooltipsOnColumnHeader($("#allHosts"),11,"Maximum CPU MHz Allocated to this Virtual Host");
    setTooltipsOnColumnHeader($("#allHosts"),12,"IP Address of the Virtual Host");
    setTooltipsOnColumnHeader($("#allHosts"),13,"General Notes about Host");


    jQuery("#allHosts").jqGrid('jqGridExport');
    jQuery('#allHosts').filterToolbar({id:'allHosts', searchOnEnter:true});
        jQuery('#allHosts').jqGrid('setFrozenColumns');
        $("#allHosts").jqGrid('navGrid','#hostAllPager',{
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
        dynamicListSize('#allHosts');
    }).trigger('resize');

    });

</script>

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

<g:render template="../advancedOptions" model="[pageType:'host', gridId:'#allHosts', export:true, exportAction:'exportListAll', hostFilter:true]" />



<table id="allHosts"></table>
<div id="hostAllPager"></div>
