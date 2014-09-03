<script type="text/javascript">

    listPanelUrl = '../SourceFeed/listSourcePanels?sourceId=%{--
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

${sourceInstance.id}';
    editOption = true;
    var panelLabel = "";
    var totalPoles = 0;
    var id = ${sourceInstance.id};
    var panelId = 0;
    var sourceId = 0;
    var breakersInUse = 0;

    var lastID = sessionStorage.getItem("lastEdited");

    $(document).ready(function() {

        $("#btnAddPanel").click(function(){
            $("#allSourcePanels").jqGrid("editGridRow","new",
                    {addCaption:'Create new Panel for Source',
                        width:500, height:300,
                        closeAfterAdd: true,
                        params:{id:${sourceInstance.id}},
                        savekey:[true,13]}
//                                closeModal()
            );

        });

        jQuery("#allSourcePanels").jqGrid({

            height:'auto',
            width:'1000',
            caption:'Panel List',
            url:listPanelUrl,
            editurl:'editSourcePanels?sourceId=${sourceInstance.id}',
            datatype: "json",
            colNames:['','itsId', 'Breaker Poles','breakers In Use','Main Breaker Amp', 'Panel Voltage', 'Numbering Scheme', 'id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, search:false , sortable:false, width:10,
                    formatter: 'actions', formatoptions: {
                    keys: true, editbutton: true, delbutton:true}
                },

                // {name:'panel_id', width:40, editable:false ,title:false, search:false, sortable:false},
                {name:"itsId", width:30, editable:true,  editrules:{required:true},search:false,title: false, edittype:'text', editoptions:{size:10,maxlength:16}},
                {name:'breaker_poles', width:20, editable: true, edittype: "select", title:false, search:false, sortable:false,
                    editoptions:{value:"6:6;8:8;10:10;12:12;14:14;16:16;18:18;20:20;22:22;24:24"}},
                {name: 'breakersInUse', width: 20},
                {name:'mainBreakerAmperage', width:20, editable:true,title:false, search:false, sortable:false},
                {name:'panelVoltage', width:20, editable:true,title:false, search:false, sortable:false},
                {name:'numberingScheme', width:20, editable:true, edittype:'select', editoptions:{value:"Even:Even;Odd:Odd"},title:false, search:false, sortable:false},

                {name:'id', width:10}
                // {name:'id', hidden: true}
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
            pager: '#allSourcePanelPager',
            scrollOffset:0,
            onSelectRow: function(rowId){
                loadBreakerData(rowId);
            },
           //load last edited panel
            gridComplete:function(){
                if(lastID != 0){

                       var ids = $('#allSourcePanels').getDataIDs();

                    if(($.inArray(lastID,ids)) != -1){ //returns -1 if id is not in table
                  //  alert("found id, loading previous data from id: " + lastID);
                    loadBreakerData(lastID);
                    }
                    else{
                    //    alert("id not found, array:" + ids.toString());
                        lastID = 0;
                    }
                }
            }
        });

        jQuery('#allSourcePanels').filterToolbar({id:'allSourcePanels', searchOnEnter:true});
        $("#allSourcePanels").jqGrid('navGrid','#allSourcePanelPager',{
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


        jQuery(window).bind('resize', function() {
            dynamicListSize('#allSourcePanels');
        }).trigger('resize');

    });

    /**************************************
     Breaker widget Display
     **************************************/

        //includes ajax call to breaker controller
    function loadBreakerData(rowId){
        $("#breakerTable").empty();

        var rowData = $('#allSourcePanels').getRowData(rowId);
        panelLabel = rowData['itsId'];
        totalPoles = rowData['breaker_poles'];
        panelId = rowData['id'];
        sourceId = id;

        breakersInUse = rowData['breakersInUse'];

        //set current panel label
        setLabels();

        //parameters for ajax call
        var params = new Object();
        var responseData = null;

        params.itsId = panelLabel;
        params.sourceId = sourceId;
        params.panelId = panelId;


        //draw vacant slots if there are no breakers in use
        if(breakersInUse == 0){
            drawBreakers(totalPoles, null);
            /* alert("itsId " + params.itsId
             + "\npanel id " + params.panelId
             + "\nsource id: " + params.sourceId);*/
        }

        if(breakersInUse != 0){
            /* alert("itsId " + params.itsId
             + "\npanel id " + params.panelId
             + "\nsource id: " + params.sourceId);*/

            //ajax call to get pole information
            $.ajax({
                async:false,
                url: '../Breaker/getBreakers',
                type:'GET',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: params,
                cache: false,

                success: function(response){
                    responseData = response;  //set global variable to recieved data
                    //   alert("controller data retrieved: " + responseData);
                },

                fail: function(){
                    alert('Could not load breakers')
                }
            });
            //call method to populate css with pole information
            drawBreakers(totalPoles, responseData);
        }
    }


    function drawBreakers(poles, data){

        $("#breakerTable").empty();

        var panelA = '<td><div id="panelA" class="panelA">';
        var panelB = '<td><div id="panelB" class="panelB">';

        var leftPoles = new Array();
        var rightPoles = new Array();

        //if there are currently no breakers assigned, show total vacant slots of panel
        if(data == null){
            var sideA = 1;
            var sideB = 2;
            var count1 = 0;
            var count2 = 0;
            for(count1; count1 < poles/2; count1++){
                panelA += '<h3 id="vacant_slot"><div class=nums><p>'+ sideA + '</p></h3><div><p class=stats>No Breaker Assigned</div>';
                sideA += 2;
            }
            for(count2; count2 < poles/2; count2++){
                panelB += '<h3 id="vacant_slot"><div class=nums><p>'+ sideB + '</p></h3><div><p class=stats>No Breaker Assigned</div>';
                sideB += 2;
            }
        }

        //else if panel contains active poles
        else{

            //separate left and right poles
            for(var c = 0; c < data.length; c++){
                var current = data[c];
                var pole = current[2];
                if(pole%2 == 0){
                    rightPoles.push(current);
                }
                else{
                    leftPoles.push(current);
                }
            }

            //sort data of left poles with bubble sort
            for(var i = 0; i < leftPoles.length ; i++){
                for(var j = 1; j < leftPoles.length - i; j++){
                    var first_L = leftPoles[j-1];
                    var second_L = leftPoles[j];
                    if (first_L[2] > second_L[2]){ //if first pole retrieved is larger, behind second
                        leftPoles[j -1] = second_L;
                        leftPoles[j] = first_L;
                    }
                }
            }

            //sort data of right poles with bubble sort
            for(var k = 0; k < rightPoles.length ; k++){
                for(var y = 1; y < rightPoles.length - k; y++){
                    var first_R = rightPoles[y-1];
                    var second_R = rightPoles[y];
                    if (first_R[2] > second_R[2]){ //if first pole retrieved is larger, behind second
                        rightPoles[y -1] = second_R;
                        rightPoles[y] = first_R;
                    }
                }
            }

            // alert("sorted left poles: " + leftPoles.length + "\n" + leftPoles.toString());
//        alert("sorted right poles: " + rightPoles.length + "\n" + rightPoles.toString());
            /*
              Left side breaker graphics
             */
            var poleCount = 1;
            var itemCount = 0;

            while(poleCount < poles){
                try{
                    if(itemCount < leftPoles.length){
                        var item_L = leftPoles[itemCount];
                        var label_L = item_L[0];
                        var volts_L = item_L[1];
                        var pole1_L = item_L[2];
                        var pole2_L = item_L[3];
                        var pdus_L = item_L[4].toString();

                        //  alert("current item:" + item_L);

                        if(pole1_L == poleCount.toString()){ //if match of pole labels create graphic
                            //  alert("found match for pole: " + pole1_L + " : " + poleCount);

                            if(volts_L == "220"){
                                panelA += '<h3 id="dual_breaker"> <div class="nums"><p>' +  +pole1_L + '/' + pole2_L + '</p></div></h3><div><div><p class=stats>Label: ' + label_L
                                        + '</div>' + '<div><p class=stats>' + 'Voltage: ' + volts_L + '</div>' + '<div><p class=stats>'
                                        + 'Pole Assignment: ' + pole1_L +','+ pole2_L +'</div><div><p class=stats>PDUS: ' + pdus_L+'</div></div>';
                                poleCount += 4;
                                itemCount++;
                                //  alert("added 220 breaker, incremented");
                            }
                            else if(volts_L== "110"){
                                panelA += '<h3 id="single_breaker"> <div class="nums"><p>' + pole1_L + '</p></div></h3><div><div><p class=stats>Label: ' + label_L
                                        + '</div>' + '<div><p class=stats>' + 'Voltage: ' + volts_L + '</div>' + '<div><p class=stats>'
                                        + 'Pole Assignment: ' + pole1_L +'</div><div><p class=stats>PDUS: ' + pdus_L+'</div></div>';
                                poleCount += 2;
                                itemCount++;
                                //  alert("added 110 breaker, incremented");
                            }
                        }
                        else{
                            //add vacant slot
                            //  alert("no match, adding vacant slot");
                            panelA += '<h3 id="vacant_slot"> <div class="nums"><p>'+ poleCount+ '</p></div></h3><div><p class=stats>No Breaker Assigned</div>';
                            //  alert("Pole Count:" + poleCount + "\n item Count: " + itemCount);
                            poleCount += 2;
                        }
                    }
                    //If there are no more items to add, insert blank slots
                    else{
                        //add vacant slot
                        //  alert("no match, adding vacant slot");
                        panelA += '<h3 id="vacant_slot"> <div class="nums"><p>'+ poleCount+ '</p></div></h3><div><p class=stats>No Breaker Assigned</div>';
                        //   alert("Pole Count:" + poleCount + "\n item Count: " + itemCount);
                        poleCount += 2;
                    }
                }
                catch (err){
                    alert("there was an error in creating graphics, " + err + "\n Pole Count:" + poleCount + "\n item Count: " + itemCount);
                }
            }

            /*
             Right side breaker graphics
             */
            poleCount = 2;
            itemCount = 0;

            while(poleCount <= poles){
                try{
                    if(itemCount < rightPoles.length){
                        var item_R = rightPoles[itemCount];
                        var label_R = item_R[0];
                        var volts_R = item_R[1];
                        var pole1_R = item_R[2];
                        var pole2_R = item_R[3];

                        var pdus_R = item_R[4].toString();

                        //  alert("current item:" + item_R);

                        if(pole1_R == poleCount.toString()){ //if match of pole labels create graphic
                            //      alert("found match for pole: " + pole1_R + " : " + poleCount);

                            if(volts_R == "220"){
                                panelB += '<h3 id="dual_breaker"> <div class=nums><p>' + pole1_R + '/' + pole2_R + '</p></h3><div><div><p class=stats>Label: ' + label_R
                                        + '</div>' + '<div><p class=stats>' + 'Voltage: ' + volts_R + '</div>' + '<div><p class=stats>'
                                        + 'Pole Assignment: ' + pole1_R +','+ pole2_R +'</div><div><p class=stats>PDUS: ' + pdus_R+'</div></div>';
                                poleCount += 4;
                                itemCount++;
                                //      alert("added 220 breaker, incremented");
                            }
                            else if(volts_R== "110"){
                                panelB += '<h3 id="single_breaker"> <div class=nums><p>' + pole1_R + '</p></h3><div><div><p class=stats>Label: ' + label_R
                                        + '</div>' + '<div><p class=stats>' + 'Voltage: ' + volts_R + '</div>' + '<div><p class=stats>'
                                        + 'Pole Assignment: ' + pole1_R +'</div><div><p class=stats>PDUS: ' + pdus_R +'</div></div>';
                                poleCount += 2;
                                itemCount++;
                                //    alert("added 110 breaker, incremented");
                            }
                        }
                        else{
                            //add vacant slot
                            //   alert("no match, adding vacant slot");
                            panelB += '<h3 id="vacant_slot"> <div class=nums><p>'+ poleCount+ '</p></h3><div><p class=stats>No Breaker Assigned</div>';
                            //    alert("Pole Count:" + poleCount + "\n item Count: " + itemCount);
                            poleCount += 2;
                        }
                    }
                    else{
                        //add vacant slot
                        //      alert("no match, adding vacant slot");
                        panelB += '<h3 id="vacant_slot"> <div class=nums><p>'+ poleCount+ '</p></h3><div><p class=stats>No Breaker Assigned</div>';
                        //     alert("Pole Count:" + poleCount + "\n item Count: " + itemCount);
                        poleCount += 2;
                    }
                }
                catch (err){
                    alert("there was an error in creating graphics, " + err + "\n Pole Count:" + poleCount + "\n item Count: " + itemCount);
                }
            }
        }
        //add end tags
        panelA = panelA + '</div></td>';
        panelB = panelB + '</div></td>';

        $('#breakerTable').append(panelA);
        $('#breakerTable').append(panelB);

        $( "#panelA" ).accordion({
            event: "click hoverintent"
        });

        $("#panelB").accordion({
            event: "click hoverintent"
        });


        $.event.special.hoverintent = {
            setup: function() {
                $( this ).bind( "mouseover", jQuery.event.special.hoverintent.handler );
            },
            teardown: function() {
                $( this ).unbind( "mouseover", jQuery.event.special.hoverintent.handler );
            },
            handler: function( event ) {
                var currentX, currentY, timeout,
                        args = arguments,
                        target = $( event.target ),
                        previousX = event.pageX,
                        previousY = event.pageY;

                function track( event ) {
                    currentX = event.pageX;
                    currentY = event.pageY;
                };

                function clear() {
                    target
                            .unbind( "mousemove", track )
                            .unbind( "mouseout", clear );
                    clearTimeout( timeout );
                }

                function handler() {
                    var prop,
                            orig = event;

                    if ( ( Math.abs( previousX - currentX ) +
                            Math.abs( previousY - currentY ) ) < 7 ) {
                        clear();

                        event = $.Event( "hoverintent" );
                        for ( prop in orig ) {
                            if ( !( prop in event ) ) {
                                event[ prop ] = orig[ prop ];
                            }
                        }
                        // Prevent accessing the original event since the new event
                        // is fired asynchronously and the old event is no longer
                        // usable (#6028)
                        delete event.originalEvent;

                        target.trigger( event );
                    } else {
                        previousX = currentX;
                        previousY = currentY;
                        timeout = setTimeout( handler, 100 );
                    }
                }

                timeout = setTimeout( handler, 100 );
                target.bind({
                    mousemove: track,
                    mouseout: clear
                });
            }
        };

        //setLabels();
    }


    function setLabels (){
        if((panelLabel).length < 30){   //on edit puts long string of characters, eliminate unecessary display of characters
            //$('#panelLabel').text(panelLabel.toString());
            document.getElementById('#panelLabel').innerHTML = panelLabel;
        }
    }

    /**********************************************
     HTML code
     *********************************************/


</script>

<p style="color:white">*Note: Once a Panel is created the number of Breaker Poles becomes fixed and cannot be edited.<br></p>

<div id = "addPanel" style="margin-top:5px">
    <input class="ui-corner-all" id="btnAddPanel" type="button" value="Add New Panel"/>
</div>

<table id="allSourcePanels"></table>
<div id="allSourcePanelPager"></div>


<style>
#feedback { font-size: 1.4em; }
#panelA .ui-selecting { background: #FECA40; }
#panelA .ui-selected { background: #F39814; color: white; }
#panelA { list-style-type: none; margin: 0; padding: 0; width: 99%; }
#panelA li { margin: 0; padding: 0.4em; font-size: 1.4em; height: 18px; max-width:440px}

#panelB .ui-selecting { background: #FECA40; }
#panelB .ui-selected { background: #F39814; color: white; }
#panelB { list-style-type: none; margin: 0; padding: 0; width: 99%; }
#panelB li { margin: 0; padding: 0.4em; font-size: 1.4em; height: 18px;max-width:440px }
#single_breaker{height: 95px; width:440px; background-image: url(../css/breaker/breaker_green.jpg) }
#dual_breaker{height: 190px; width:440px; background-image: url(../css/breaker/dualbreaker_blue.jpg) }
#vacant_slot{height: 95px; width:440px; background-image: url(../css/breaker/vacant_slot.jpg) }




/*
#breakerTable{ margin:auto; width:80%; border: ridge #929292; border-width: 30px ; background-color: #000}
*/
 #breakerTable{margin:auto ;width:80%; max-width:440px; border: 60px solid transparent; -moz-box-shadow: 0 0 5px 5px #000;
     -webkit-box-shadow: 0 0 5px 5px #000;
     box-shadow: 0 0 5px 5px #000;

     -moz-border-image:url("../css/breaker/metal_s.png") 20 20 round; /* Old firefox */
     -webkit-border-image:url("../css/breaker/metal_s.png") 20 20 round; /* Safari */
     -o-border-image:url("../css/breaker/metal_s.png") 20 20 round; /* Opera */
     border-image:url("../css/breaker/metal_s.png") 20 20 round;  background-color: #000}


#label{font-family:Verdana, Arial, Helvetica, sans-serif ; font-size: 250%; color: #c7dcff;
    text-shadow:
        -2px -2px 0 #000,
        2px -2px 0 #000,
        -2px 2px 0 #000,
        2px 2px 0 #000;}


.nums {
    /*internal text*/

     display:table;
    /*-moz-border-radius: 50px/50px;*/
    /*-webkit-border-radius: 50px 50px;*/
    /*border-radius: 50px/50px;;*/
    /*border:solid 21px #f00;*/
    width:50px;
    /*background-color: #363636;*/

    background: -moz-linear-gradient(top,  #545454, #252525);
    background: -webkit-linear-gradient(top,  #545454, #252525);
    background: -o-linear-gradient(top,  #545454,#252525);
    background: linear-gradient(top, #545454, #252525);

    border: 2px solid transparent; -moz-box-shadow: 0 0 5px 5px  #4e4e4e;
    -webkit-box-shadow: 0 0 5px 5px #4a5356;
    box-shadow: 0 0 1px 1px #4a5356;
    border-radius: 5px;
    vertical-align: middle;

}

.nums p {
    font-family:Veranda, Helvetica, sans-serif ; font-size: 250%; color: #f5f5f5;
    text-shadow:
        -2px -3px 0 #2B2B2B,
        2px -2px 0 #2B2B2B,
        -2px 2px 0 #2B2B2B,
        2px 3px 0 #2B2B2B;
    text-align: center;
    vertical-align: middle;
    /*background-color: #4c4c4c;*/
}

.stats{font-family:Arial, Helvetica, sans-serif ; font-size: 120%; color: #f5f5f5;}

.fieldSetA {background: #4a4d4a}
.fieldSetB {background: #4a4d4a;}

.ph {color: #e6e6e6;} /*poleheader*/


/*tab label*/
.panelLabel {
    -moz-box-shadow:inset 0px 1px 12px 0px #bee2f9;
    -webkit-box-shadow:inset 0px 1px 12px 0px #bee2f9;
    box-shadow:inset 0px 1px 12px 0px #bee2f9;
    background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #5da3d8), color-stop(1, #3b5f81) );
    background:-moz-linear-gradient( center top,  #5da3d8 5%, #3777b3 100% );
    filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#65bef5', endColorstr='#3b5f81');
    background-color: #3b5f81;
    background-color: #5da3d8;
    -webkit-border-top-left-radius:17px;
    -moz-border-radius-topleft:17px;
    border-top-left-radius:17px;
    -webkit-border-top-right-radius:17px;
    -moz-border-radius-topright:17px;
    border-top-right-radius:17px;
    -webkit-border-bottom-right-radius:0px;
    -moz-border-radius-bottomright:0px;
    border-bottom-right-radius:0px;
    -webkit-border-bottom-left-radius:0px;
    -moz-border-radius-bottomleft:0px;
    border-bottom-left-radius:0px;
    text-indent:0px;
    border:2px solid #235291;
    display:inline-block;
    font-family:Arial Black;
    font-size:250%;
    font-weight:bold;
    font-style:normal;
    height:56px;
    line-height:56px;
    width:400px;
    text-decoration:none;
    text-align:center;
    color: #aec2e4;
    text-shadow:
        -2px -2px 0 #000,
        2px -2px 0 #000,
        -2px 2px 0 #000,
        2px 2px 0 #000;
}
/*.panelLabel:hover {
    background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #3777b3), color-stop(1, #3b5f81) );
    background:-moz-linear-gradient( center top, #3777b3 5%, #65bef5 100% );
    filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#3777b3', endColorstr='#3b5f81');
    background-color:#3777b3;
}*//*.panelLabel:active {
     position:relative;
     top:1px;
 }*/




</style>

<br>
<button id="assignBreakers">Assign Breakers</button>
<br><br>
<g:javascript>

    var activePoles = new Array();

    // var slot = "background-image:url(/web-app/images/vacant_slot.jpg)";
    /* var selectedPanel = sessionStorage.getItem('selectedPanel');
     var pId = sessionStorage.getItem('panelId');
     var sourceId = sessionStorage.getItem('sourceId');*/

    var preLoad = null;

    var responseData = null;

    var metal1 =  "background-image: -webkit-gradient(linear,left top,right top,color-stop(0, #4BCD8D),color-stop(0.33, #5C9E26));" +
    "background-image: -o-linear-gradient(right, #4BCD8D 0%, #5C9E26 33%);"+
    "background-image: -moz-linear-gradient(right, #4BCD8D 0%, #5C9E26 33%);"+
    "background-image: -webkit-linear-gradient(right, #4BCD8D 0%, #5C9E26 33%);"+
    "background-image: -ms-linear-gradient(right, #4BCD8D 0%, #5C9E26 33%);"+
    "background-image: linear-gradient(to right, #4BCD8D 0%, #5C9E26 33%);";

    var metal2 = "background-image: -webkit-gradient(linear,left top,right top,color-stop(0.22, #3C50D6),color-stop(1, #0F1C7D));" +
    "background-image: -o-linear-gradient(right, #3C50D6 22%, #0F1C7D 100%);" +
    "background-image: -moz-linear-gradient(right, #3C50D6 22%, #0F1C7D 100%);" +
    "background-image: -webkit-linear-gradient(right, #3C50D6 22%, #0F1C7D 100%);" +
    "background-image: -ms-linear-gradient(right, #3C50D6 22%, #0F1C7D 100%);" +
    "background-image: linear-gradient(to right, #3C50D6 22%, #0F1C7D 100%)";



    $(function(){
        $("#dialog-form").dialog ({
            autoOpen:false,
            height:900,
            resizable:true,
            width:1000,
            modal:true,
            closeButton: false,
            buttons: {
                "Submit Breakers": function() {

                    var params = new Object();
                    var data = collectData();

                    //alert("data collected\n" + data.toString());
                    alert("breakers for " + panelLabel + " have been saved");

                    params.itsId = panelLabel;
                    params.sourceId = sourceId;
                    params.panelId = panelId;

                    params.poleData = new Array();
                    params.poleData = addData(params.poleData, data);

                    sessionStorage.setItem("lastEdited", panelId);


                    //submit data to breaker controller
                    $.ajax({
                        async:false,
                        url: '../Breaker/createBreakers',
                        //   type:'POST',
                        contentType: 'application/json; charset=utf-8',
                        dataType: 'json',
                        data:$.param(params),

                        success: function(){
                            //    alert('Breakers Have Been Saved.');
                            resetCache();
                        },

                        error: function () {
                            // alert('An Error Occurred while trying to save the Breakers.');
                            resetCache();
                        }
                    });
                },


                Cancel: function() {
                    //     removeStyles();
                    $(this).dialog("close");
                    location.reload();
                }
            }
        });


        $( "#assignBreakers" )
                .button()
                .click(function() {


                    var responseData = null;

                    if(totalPoles == 0){
                        alert("Please Select a panel to load the number of poles first.")
                    }
                    else{
                        $( "#dialog-form" ).dialog( "open" );
                        createSelectors();  //dynamically create correct number of selector options

                        if(breakersInUse != 0){
                            responseData = getSelected();  //calls breaker controller action

                            if(responseData != null){
                                loadCSS(responseData);  //filters data from previous controller action and applies to elements
                            }
                        }

                    }

                });

        function createSelectors(){

            alert("There are " + totalPoles + " Poles to Assign Breakers to panel: " + panelLabel + "\n id: " + sourceId);

            $("#selectionTable").empty(); //clean out modal before repopulate

            var num = 1;

            //var pole_color = "background-color: #4A4D4A";
            //var unassigned_color = "background-color: red";

            while(num < totalPoles){
                /*unique id must be added for selectors*/
                var pduSelector = '<label>Associated PDUs</label><input type="number" name="pdus" min="1" max="10">';

                //limit options on last two poles
                if (num == (totalPoles - 1)){
                    /*Odd pole********************/
                    var voltageSelector = '<label>Voltage</label><select id="'+ 'vselectp' + num +'" onchange= "changeBreaker('+ [num] +')"  valign="top" style="width: 150px"><option value="0"> -- Not Enabled -- </option><option value="110">110V -- Single PowerBreaker</option></select>';
                    var odd = '<fieldSet id="'+ 'fieldset' + num +'" class="fieldSetA"  style=" width:440px; text-align:center; margin: 20px"><h2 class ="ph">Pole '+ num +'</h2><br>' + voltageSelector +'<br><br>' + pduSelector + '</fieldSet>';
                    /*********************************/

                    /*Even pole*****************/
                    voltageSelector = '<label>Voltage</label><select id="'+ 'vselectp' + (num+1) +'" onchange= "changeBreaker('+ [(num+1)] +')"valign="top" style="width: 150px"><option value="0"> -- Not Enabled -- </option><option value="110">110V -- Single PowerBreaker</option></select>';
                    var even = '<fieldSet id="' + 'fieldset' + (num+1) +'" class="fieldSetB" style="width:440px; text-align:center; margin: 20px"><h2 class ="ph">Pole '+(num+1) +'</h2><br>' + voltageSelector +'<br><br>' + pduSelector + '</fieldSet>';
                    /**********************************/

                }
                else{

                    /*Odd pole********************/
                    var voltageSelector = '<label>Voltage</label><select id="'+ 'vselectp' + num +'" onchange= "changeBreaker('+ [num] +')"  valign="top" style="width: 150px"><option value="0"> -- Not Enabled -- </option><option value="110">110V -- Single PowerBreaker</option><option value="220">220V -- Dual PowerBreaker</option></select>';
                    var odd = '<fieldSet id="'+ 'fieldset' + num +'" class="fieldSetA"  style=" width:440px; text-align:center; margin: 20px"><h2 class ="ph">Pole '+ num +'</h2><br>' + voltageSelector +'<br><br>' + pduSelector + '</fieldSet>';
                    /*********************************/

                    /*Even pole*****************/
                    voltageSelector = '<label>Voltage</label><select id="'+ 'vselectp' + (num+1) +'" onchange= "changeBreaker('+ [(num+1)] +')"valign="top" style="width: 150px"><option value="0"> -- Not Enabled -- </option><option value="110">110V -- Single PowerBreaker</option><option value="220">220V -- Dual PowerBreaker</option></select>';
                    var even = '<fieldSet id="' + 'fieldset' + (num+1) +'" class="fieldSetB" style="width:440px; text-align:center; margin: 20px"><h2 class ="ph">Pole '+(num+1) +'</h2><br>' + voltageSelector +'<br><br>' + pduSelector + '</fieldSet>';
                    /**********************************/
                }

                var oddPole = '<td id="td'+num+'" style="border:2px; border-radius:25px;">' + odd+ '</td>';
                var evenPole = '<td id="td'+(num+1)+'" style="border:2px; border-radius:25px;">' + even+ '</td>';


                var row = '<tr>' + oddPole + evenPole + '</tr>';

                $('#selectionTable').append(row);

                num += 2;
            }

        }

        //retrieve saved data to show what has already been assigned
        function getSelected(){

            var params = new Object();
            var retrievedData = null;

            params.itsId = panelLabel;
            params.sourceId =  sourceId;
            params.panelId = panelId;

            $.ajax({
                async:false,
                url: '../Breaker/getBreakers',
                type:'GET',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: params,
                cache: false,

                success: function(response){

                    retrievedData = response;  //set global variable to recieved data

                    //  alert("controller data retrieved: " + retrievedData.toString());

                },

                fail: function(){
                    alert('Could not load breakers')
                }
            });
            return retrievedData;
        }

    });

    function loadCSS(responseData){
        var data = responseData;

        // var dataString = data[x];
        // alert("data items total: " + data.length);
        for(var x = 0; x < data.length; x++){

            var  item = data[x];

            //  alert("in load css, full item: " + item.toString() + "size of item: " + item.length);

            var voltage = parseInt((item)[1]);
            var pole = parseInt((item)[2]);
            try{
                putBreakerCss(pole,voltage);
            }
            catch(err){
                //   alert ('error in putting css for pole: ' + pole);
            }
        }
    }

    function resetCache(){
        $(this).dialog("close");
        location.reload();
    }
    function addData(params, data){
        for(var x = 0; x < data.length; x++){
            params[x] = data[x];
        }
        return params;
    }

    function collectData(){
        var activePoles = new Array();
        for (var x = 0; x < totalPoles; x++){

            var element = "vselectp" + (x+1);
            var val = (document.getElementById(element)).value;
            //   var num = val.toString(); //integer value as string
            activePoles.push(val);
        }
        return activePoles;
    }


    /********************************
     *
     * Change CSS functions
     **********************************/



    function changeBreaker(num){
        /***************************************************************************/
        //special Case: end poles need to be done seperately
         alert("change breaker called");
        //last pole
        if (num == totalPoles){
            var td = "td" + num;
            var select = "vselectp" + num;

            val = (document.getElementById(select)).value;

            if(val == 110){
                addStyle("#td" + num + '{background:' + metal1 +'}');
                addStyle("#fieldset" + num + '{background-color: #58858d}');}

            else if(val == 0){
                addStyle("#td" + num + '{background: #3C4F5D}');
                addStyle("#fieldset" + num + '{background-color:  #4a4d4a}');}

        }
        //second to last pole, has different color bg
        else if (num == (totalPoles-1)){
            var td = "td" + num;
            var select = "vselectp" + num;
            var val = (document.getElementById(select)).value;

            if(val == 110){
                addStyle("#td" + num + '{background' + metal1 +'}');
                addStyle("#fieldset" + num + '{background-color: #91b3b5}');}

            else if(val == 0){
                addStyle("#td"+ num + '{background: #3C4F5D}');
                addStyle("#fieldset" + num + '{background-color:  #4a4d4a}');}
        }
        /***************************************************************************/

        var p1Id = num;  //top pole id stem
        var vselectp1 = "vselectp" + p1Id;
        var topPole = "#fieldset" + p1Id;
        var td1 = "#td" + num;

        var s1Val = (document.getElementById(vselectp1)).value;

        var p2Id = num + 2;
        var bottomPole = "#fieldset" + p2Id;
        var vselectp2 = "vselectp" + p2Id;
        var td2 = "#td" + (num+2);

        var s2Val =(document.getElementById(vselectp2)).value;
        /*************************************
         220 volt assignment
         *************************************/

        if (s1Val == 220){
            if((s2Val == 220)||(s2Val == 110)){ //if bottom breaker is already assigned to 220v, prompt with message
                alert("There was a conflict with PowerBreaker Assignment.\n This Pole cannot be paired with the one below it.");
                (document.getElementById(vselectp1)).selectedIndex = 0;
                revertStyle(td1 + '{background: #3C4F5D}');
            }
            else{
                // alert( "Pole #" + p1Id + " has been changed to " + s1Val + " and now occupies the space of Pole #" + p2Id + ".");
                //disable bottom pole
                (document.getElementById(vselectp2)).selectedIndex = 0;
                document.getElementById(vselectp2).disabled = true;
                addStyle(bottomPole + '{background-color: grey; opacity:0.3}');
                addStyle(vselectp2+'{ background-color: grey; opacity:0.3}'); //grey out select

                addStyle(td1 + '{' + metal2 + '}');
                addStyle(td2 + '{' + metal2 + '}');
                if(num%2==0){
                    addStyle(topPole + '{background-color: #58858d}');
                }
                else if(num%2 == 1){
                    addStyle(topPole + '{background-color:#91b3b5}');
                }

            }
        }
        /*************************************
         110 volt assignment
         *************************************/
        else if (s1Val == 110) {
            //  alert( p1Id + " pole selector has changed to " + s1Val);
            //revertStyle(topPole+ '{border-style:none}');
            addStyle(td1 + '{' + metal1 + '}');
            if(num%2==0){
                addStyle(topPole + '{background-color: #58858d}');
            }
            else if(num%2 == 1){
                addStyle(topPole + '{background-color:#91b3b5}');
            }
            if(s2Val == 0){
                document.getElementById(vselectp2).disabled = false;

                if(num%2==0){
                    addStyle(topPole + '{background-color: #58858d}');
                    revertStyle(bottomPole + '{background-color: #4a4d4a; opacity:1}');
                }
                else if(num%2 == 1){
                    addStyle(topPole + '{background-color:#91b3b5}');
                    revertStyle(bottomPole + '{background-color:  #4a4d4a; opacity:1}');
                }
                revertStyle('#'+vselectp2+'{visible;background-color: white; opacity:1}');
                revertStyle(td2 + '{background: #3C4F5D}');
            }
        }
        /*************************************
         disable assignment
         *************************************/
        else if(s1Val == 0){
            revertStyle(td1 + '{background: #3C4F5D}');
            revertStyle(topPole + '{background-color: #4a4d4a}');
            if(s2Val == 0){
                document.getElementById(vselectp2).disabled = false;
                if(num%2==0){
                    addStyle(topPole + '{background-color: #4a4d4a}');
                    revertStyle(bottomPole + '{background-color: #4a4d4a; opacity:1}');
                }
                else if(num%2 == 1){
                    addStyle(topPole + '{background-color:#4a4d4a}');
                    revertStyle(bottomPole + '{background-color: #4a4d4a; opacity:1}');
                }
                revertStyle('#'+vselectp2+'{visible;background-color: white; opacity:1}');
                revertStyle(td2 + '{background: #3C4F5D}');
            }
        }
    }

    /************************************************
     Overloaded Method for preloaded css content
     **************************************************/

    function putBreakerCss(pole,voltage){
        /***************************************************************************/
        //special Case: end poles need to be done seperately
        var num = pole;
        var val = voltage;
        //last pole
        if (num == totalPoles){
            var td = "td" + num;
            var select = "vselectp" + num;
            // val = (document.getElementById(select)).value;

            if(val == 110){
                addStyle("#td" + num + '{background:' + metal1 +'}');   //add breaker highlight
                addStyle("#fieldset" + num + '{background-color: #58858d !important}');

                (document.getElementById(select)).selectedIndex = 1;
            }
            else if(val == 0){
                addStyle("#td" + num + '{background: #3C4F5D}');    //add breaker highlight
                addStyle("#fieldset" + num + '{background-color:  #4a4d4a !important}');
            }
        }
        //second to last pole, has different color bg
        else if (num == (totalPoles-1)){
            var td = "td" + num;
            var select = "vselectp" + num;

            if(val == 110){
                addStyle("#td" + num + '{background' + metal1 +'}');     //add breaker highlight

                addStyle("#fieldset" + num + '{background-color: #91b3b5 !important}');
                (document.getElementById(select)).selectedIndex = 1;
            }

            else if(val == 0){
                addStyle("#td"+ num + '{background: #3C4F5D}');       //add breaker highlight
                addStyle("#fieldset" + num + '{background-color:  #4a4d4a !important}');
            }
        }
        /***************************************************************************/

        var p1Id = num;  //top pole id stem
        var vselectp1 = "vselectp" + p1Id;
        var topPole = "#fieldset" + p1Id;
        var td1 = "#td" + num;

        //var s1Val = (document.getElementById(vselectp1)).value;
        var s1Val = voltage;

        var p2Id = num + 2;
        var bottomPole = "#fieldset" + p2Id;
        var vselectp2 = "vselectp" + p2Id;
        var td2 = "#td" + (num+2);

        var s2Val =(document.getElementById(vselectp2)).value;

        /*************************************
         220 volt assignment
         *************************************/

        if (s1Val == 220){
            if((s2Val == 220)||(s2Val == 110)){ //if bottom breaker is already assigned to 220v, prompt with message
                alert("There was a conflict with PowerBreaker Assignment.\n This Pole cannot be paired with the one below it.");
                (document.getElementById(vselectp1)).selectedIndex = 0;
                revertStyle(td1 + '{background: #3C4F5D}');
            }
            else{
                // alert( "Pole #" + p1Id + " has been changed to " + s1Val + " and now occupies the space of Pole #" + p2Id + ".");
                //disable bottom pole
                (document.getElementById(vselectp2)).selectedIndex = 0;
                document.getElementById(vselectp2).disabled = true;
                addStyle(bottomPole + '{background-color: grey; opacity:0.3}');
                addStyle(vselectp2+'{ background-color: grey; opacity:0.3}'); //grey out select

                addStyle(td1 + '{' + metal2 + '}');
                addStyle(td2 + '{' + metal2 + '}');
                if(num%2==0){
                    addStyle(topPole + '{background-color: #58858d}');
                    (document.getElementById(vselectp1)).selectedIndex = 2;
                }
                else if(num%2 == 1){
                    addStyle(topPole + '{background-color:#91b3b5}');
                    (document.getElementById(vselectp1)).selectedIndex = 2;
                }
            }
        }
        /*************************************
         110 volt assignment
         *************************************/
        else if (s1Val == 110) {
            //  alert( p1Id + " pole selector has changed to " + s1Val);
            //revertStyle(topPole+ '{border-style:none}');
            addStyle(td1 + '{' + metal1 + '}');
            if(num%2==0){
                addStyle(topPole + '{background-color: #58858d}');
                (document.getElementById(vselectp1)).selectedIndex = 1;
            }
            else if(num%2 == 1){
                addStyle(topPole + '{background-color:#91b3b5}');
                (document.getElementById(vselectp1)).selectedIndex = 1;
            }
            if(s2Val == 0){
                document.getElementById(vselectp2).disabled = false;

                if(num%2==0){
                    addStyle(topPole + '{background-color: #58858d}');
                    revertStyle(bottomPole + '{background-color: #4a4d4a; opacity:1}');
                }
                else if(num%2 == 1){
                    addStyle(topPole + '{background-color:#91b3b5}');
                    revertStyle(bottomPole + '{background-color:  #4a4d4a; opacity:1}');
                }
                revertStyle('#'+vselectp2+'{visible;background-color: white; opacity:1}');
                revertStyle(td2 + '{background: #3C4F5D}');
            }
        }
        /*************************************
         disable assignment
         *************************************/
        else if(s1Val == 0){
            revertStyle(td1 + '{background: #3C4F5D}');
            revertStyle(topPole + '{background-color: #4a4d4a}');
            if(s2Val == 0){
                document.getElementById(vselectp2).disabled = false;
                if(num%2==0){
                    addStyle(topPole + '{background-color: #4a4d4a}');
                    revertStyle(bottomPole + '{background-color: #4a4d4a; opacity:1}');
                }
                else if(num%2 == 1){
                    addStyle(topPole + '{background-color:#4a4d4a}');
                    revertStyle(bottomPole + '{background-color: #4a4d4a; opacity:1}');
                }
                revertStyle('#'+vselectp2+'{visible;background-color: white; opacity:1}');
                revertStyle(td2 + '{background: #3C4F5D}');
            }
        }
    }


    function addStyle(str) {
        var node = document.createElement('style');
        node.innerHTML = str;
        document.body.appendChild(node);
        //  return node;

    }

    function revertStyle(str){

        var node = document.createElement('style');
        node.innerHTML = str;
        document.body.appendChild(node);
    }


</g:javascript>

<div class="num_circle"></div>

<p align="center"><a id="#panelLabel" class="panelLabel"></a>
</p>
<table id="breakerTable">


    %{--panel A and B injected when add breakers called--}%

</table>


%{--Pop-up dialog--}%

<div id="dialog-form" title="Add Breakers for Panel">
    <form>
        <Style>
        h3{color: #3C4F5D;}
        </Style>

        <table id=selectionTable>
        </table>


    </form>
</div>


%{--<fieldSet><select><option value="120">120V</option><option value="220">220V</option></select></fieldSet>--}%