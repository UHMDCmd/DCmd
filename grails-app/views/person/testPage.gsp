<html>
<head>
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

<r:require modules='footer, tabletools' />

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'person.label', default: 'Staff')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <jqDT:resources jqueryUi="true" type="js" />

    %{--pop up for row select--}%
    <style>
    .toggler { width: 500px; height: 200px; position: absolute}
    #button { padding: .5em 1em; text-decoration: none; }
    #effect { width: 240px; height: 135px; padding: 0.4em; position: absolute; float: left }
    #effect h3 { margin: 0; padding: 0.4em; text-align: center; }
    </style>

    <title>jsplumb example</title>
    <style type="text/css">
    .nodes {
        border: 2px solid steelblue;
        width: 100px;
        height: 100px;
    }
    .div1 {
        position: absolute;
        top: 10%;
        left: 10%;
        background-color: #f5f5f5;
    }
    .div2 {
        position: absolute;
        top: 20%;
        left: 40%;
        background-color: #ffebcd;
    }
    /*.containerdiv {*/
        /*border: 2px solid black;*/
       /*position: relative;*/
        /*width: 500px;*/
        /*height: 500px;*/
    /*}*/
    </style>


    <script>
        $(function() {
            $( "#dialog" ).dialog();
            $( "#dialog" ).dialog({ height: 300 });
            $( "#dialog" ).dialog({ width: 200 });
            $( "#dialog" ).dialog({ position: { my: "right bottom", at:"right bottom"} });

        });
    </script>

</head>

<body>
 <div id="container" >
    <g:render template="../show_secondary" model="[pageType:'person', objectId:0, action:'list']" />
</div>
 <div class="pageBody" style="position: relative">

<div class='containerdiv'>
    <div class="nodes div1" id="inner1">Inner 1</div>
    <div class="nodes div2" id="inner2">Inner 2</div>
</div>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.13/jquery-ui.min.js"></script>
<script type="text/javascript" src="../js/jquery.jsPlumb-1.4.1-all.js"></script>

<script type="text/javascript">
    jsPlumb.bind("ready", function() {
        jsPlumb.connect({
            source:"inner1", target:"inner2",
            paintStyle: {strokeStyle: 'rgb(120,120,240)', lineWidth: 6}
        });
        jsPlumb.draggable('inner1');
        jsPlumb.draggable('inner2');
    });

</script>



<div id="dialog" title="Place Holder">
 %{--<p>This is the default dialog which is useful for displaying information. The dialog window can be moved, resized and closed with the 'x' icon.</p>--}%

 <script type='text/javascript' src='https://www.google.com/jsapi'></script>
 <script type='text/javascript'>
google.load('visualization', '1', {packages:['orgchart']});
google.setOnLoadCallback(drawChart);
function drawChart() {
var data = new google.visualization.DataTable();
data.addColumn('string', 'Name');
data.addColumn('string', 'Manager');
data.addColumn('string', 'ToolTip');
data.addRows([
[{v:'Mike', f:'Mike<div style="color:red; font-style:italic">President</div>'}, '', 'The President'],
[{v:'Jim', f:'Jim<div style="color:red; font-style:italic">Vice President</div>'}, 'Mike', 'VP'],
['Alice', 'Mike', ''],
['Bob', 'Jim', 'Bob Sponge'],
['Carol', 'Bob', '']
]);
var chart = new google.visualization.OrgChart(document.getElementById('chart_div'));
chart.draw(data, {allowHtml:true});
}
</script>

<div id='chart_div'></div>

 </div>

</body>
</html>

