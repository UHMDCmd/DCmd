
<script type="text/javascript">

    $(document).ready(function() {

        var mydata = [
            {id:"1",invdate:"2010-05-24",name:"test",note:"note",tax:"10.00",total:"2111.00"} ,
            {id:"2",invdate:"2010-05-25",name:"test2",note:"note2",tax:"20.00",total:"320.00"},
            {id:"3",invdate:"2007-09-01",name:"test3",note:"note3",tax:"30.00",total:"430.00"},
            {id:"4",invdate:"2007-10-04",name:"test",note:"note",tax:"10.00",total:"210.00"},
            {id:"5",invdate:"2007-10-05",name:"test2",note:"note2",tax:"20.00",total:"320.00"},
            {id:"6",invdate:"2007-09-06",name:"test3",note:"note3",tax:"30.00",total:"430.00"},
            {id:"7",invdate:"2007-10-04",name:"test",note:"note",tax:"10.00",total:"210.00"},
            {id:"8",invdate:"2007-10-03",name:"test2",note:"note2",amount:"300.00",tax:"21.00",total:"320.00"},
            {id:"9",invdate:"2007-09-01",name:"test3",note:"note3",amount:"400.00",tax:"30.00",total:"430.00"},
            {id:"11",invdate:"2007-10-01",name:"test",note:"note",amount:"200.00",tax:"10.00",total:"210.00"},
            {id:"12",invdate:"2007-10-02",name:"test2",note:"note2",amount:"300.00",tax:"20.00",total:"320.00"},
            {id:"13",invdate:"2007-09-01",name:"test3",note:"note3",amount:"400.00",tax:"30.00",total:"430.00"},
            {id:"14",invdate:"2007-10-04",name:"test",note:"note",amount:"200.00",tax:"10.00",total:"210.00"},
            {id:"15",invdate:"2007-10-05",name:"test2",note:"note2",amount:"300.00",tax:"20.00",total:"320.00"}
        ];

        jQuery("#gridPreview").jqGrid({
            data: mydata,
            datatype: "local",
            height: 'auto',
            rowNum: 30,
            rowList: [10,20,30],
            colNames:['Inv No','Date', 'Client', 'Amount','Tax','Total','Notes'],
            colModel:[
                {name:'id',index:'id', width:60, sorttype:"int"},
                {name:'invdate',index:'invdate', width:90, sorttype:"date", formatter:"date"},
                {name:'name',index:'name', width:100, editable:true},
                {name:'amount',index:'amount', width:80, align:"right",sorttype:"float", formatter:"number", editable:true},
                {name:'tax',index:'tax', width:80, align:"right",sorttype:"float", editable:true},
                {name:'total',index:'total', width:80,align:"right",sorttype:"float"},
                {name:'note',index:'note', width:150, sortable:false}
            ],
            pager: "#gridPreview",
            viewrecords: true,
            sortname: 'name',
            grouping:true,
            groupingView : {
                groupField : ['name']
            },
            caption: "Preview Grid"
        });


        jQuery(window).bind('resize', function() {
            dynamicListSize('#gridPreview');
        }).trigger('resize');



    });

</script>
<div id="preview" style="position:absolute; display: block" align="center">
<table>
    <td>
    <table id="gridPreview"></table>
    </td>
    <td>
<div id="tabs">
<div id="tabs-notes">
    <ul>

        <li>
            <a href="#notes-general">%{--
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

<g:message code="notes-general.label" default="General" /></a>
        </li>
        <li>
            <a href="#notes-change"><g:message code="notes-change.label" default="Change" /></a>
        </li>
        <li>
            <a href="#notes-planning"><g:message code="notes-planning.label" default="Planning" /></a>
        </li>
    </ul>
    <div id="notes-general">
        <g:render template='previewtabs'/>
    </div>
    <div id="notes-change">
        <g:render template='previewtabs'/>
    </div>
    <div id="notes-planning">
        <g:render template='previewtabs'/>
    </div>
</div>
</div>
</table>
</div>



