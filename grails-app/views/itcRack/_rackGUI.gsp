<script type="text/javascript">

    var rackLabel = "Sample ITC Rack";
    //var rackUnits = 45;

    var current_Rack = null;
    var current_RU = null;
    var next_RU = null;
    var selected_row = null;
    var slot_size = 1;
    var classHighLight = 'highlight';

    var slot_id = null;
    var plug_id = null;

    var image_Id = 0;
    var listItcRackUrl = '../ItcRackController/listAllRack';

    //js plumb variable for styling
    var common = {
        anchors:[[ 1, 0.5, 1, 0, 0, 0, "right" ],[ 0, 0.5, -1, 0, 0, 0, "left" ]],
        endpoint:["Dot", { radius:20 }],   //"Rectangle",{height:30,width:30}
        endpointStyle : { fillStyle: "#18bc23"},
        connector:[ "Bezier", { curviness:100 }],
        paintStyle:{
            lineWidth:10,
            strokeStyle:"#567567",
            outlineColor:"black",
            outlineWidth:3
        }
    };



    $(document).ready(function() {
        // setLabels();


        jQuery("#allRack").jqGrid({

            height:'auto',
            width:'1000',
            caption:'Rack List',
            url:'listAllRack',
            datatype: "json",
            colNames:['ITS Id', 'Location',
                'Avail. for parts', 'Serial #', 'Last Updated', 'General Notes', 'id'],
            colModel:[
                {name:'itsId', width:100, editable:false,title:false},
                {name:'location', width:120, editable:false,title:false},
                {name:'isAvailableForParts', width:120,title:false},
                {name:'serialNo', width:180, editable:false,title:false},
                {name:'lastUpdated', editable:false, width:120,title:false, search:false},
                {name:'generalNote', width:400,title:false},
                {name:'id', hidden:true}
            ],

            rowNum: 50,
            rowList: [10, 20, 50, 100, 200],

            gridview: true,
            viewrecords: true,
            sortname: 'itsId',
            sortorder: 'asc',
            autowidth:true,
            shrinkToFit: true,
            pager: '#allRackPager',
            scrollOffset:0,
            onSelectRow: function(rowId){
                selected_row = rowId;
                loadSelectedRack(selected_row);

            },
            gridComplete: function() {
                dynamicListSize("#allRack");
            }
        });
        jQuery('#allRack').filterToolbar({id:'allRack', searchOnEnter:true});
        $("#allRack").jqGrid('navGrid','#allRackPager',{
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
            dynamicListSize('#allRack');
        }).trigger('resize');

    });

    /**************************************
     rack widget Display
     **************************************/

    function loadSelectedRack(rowId){

        var rowData = $('#allRack').getRowData(rowId);
        var rackId = rowData['itsId'];
        var temp = rackId.slice(29,rackId.length);
        var id = temp.substr(0,temp.length-4);

        current_Rack = id;

        var params = new Object();
        var responseData = null;

        params.rackId = id;
        $.ajax({
            async:false,
            url: '../ItcRack/getRackData',
            type:'GET',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: params,
            cache: false,

            success: function(response){
                responseData = response;  //set global variable to recieved data

            },

            fail: function(){
                alert('Could not load the selected Rack')
            }
        });

//        alert("RACK ID: " +id);
        // alert("controller data retrieved: " + responseData[0].toString());
        drawRack(responseData);

//        alert("before plumb call");
//        var x = "9_plug";
//        var y = "45_slot";
//        drawPlumbConnection(x,y);
    }



    function drawRack(data){
        //alert("draw called");
        setLabels(data[0]);
        $("#rackTable").empty();

        var rackUnits = [];
        rackUnits = data[1];

        var imageType = [];
        imageType = data[2];

        var slotNum = [];
        slotNum = data[3]; //the slot number this unit lives at

        var slot_occ = [];  //number of rack spaces the unit occupies
        slot_occ = data[4];

//        var total = rackUnits.length;
        var total = 45;

//         alert("slot occ data: " + slot_occ);
//        alert("slot num data:" + slotNum);


        var deviceCollection = '<td><div id="deviceCollection" class="deviceCollection">';

        //if there are currently no breakers assigned, show total vacant slots of panel

        var x = 0;
        var num = 0;
        var count = 0;
        var unitCount = 0;
        var tag = null;
        for(x; x < total; x++){

            num = (slot_occ[unitCount]-1); //the total number of slots this particular unit occupies


            if(slotNum[unitCount] == (total-x)){
                if (imageType[unitCount] == 0){
                    deviceCollection += '<h3 id="slot_'+(total-x) +'" class="unit_empty"><div class=nums><p>'+ (total-x) + '</p></h3><div><p class=stats>No Device Assigned</div>';
                    tag = (total-x);
                    for(count = 0; count < num; count++){
                        x++;
                        unitCount++;
                        deviceCollection += '<h3 id="slot_'+(total-x) +'" class="unit_occupied"><div class=nums><p>'+ (total-x) + '</p></h3><div><p class=stats>This Slot is part of slot '+ tag +'</div>';
                    }
                }
                else if (imageType[unitCount] == 1){
                    deviceCollection += '<h3 id="slot_'+(total-x) +'" class="unit_type1"><div class=nums><p>'+ (total-x) + '</p></h3><div><p class=stats>No Device Assigned</div>';
                    tag = (total-x);
                    for(count = 0; count < num; count++){
                        x++;
                        unitCount++;
                        deviceCollection += '<h3 id="slot_'+(total-x) +'" class="unit_occupied"><div class=nums><p>'+ (total-x) + '</p></h3><div><p class=stats>This Slot is part of slot '+ tag +'</div>';
                    }
                }
                else if(imageType[unitCount] == 2){
                    deviceCollection += '<h3 id="slot_'+(total-x) +'" class="unit_type2"><div class=nums><p>'+ (total-x) + '</p></h3><div><p class=stats>No Device Assigned</div>';
                    tag = (total-x);
                    for(count = 0; count < num; count++){
                        x++;
                        unitCount++;
                        deviceCollection += '<h3 id="slot_'+(total-x) +'" class="unit_occupied"><div class=nums><p>'+ (total-x) + '</p></h3><div><p class=stats>This Slot is part of slot '+ tag +'</div>';
                    }
                }
                else if(imageType[unitCount] == 3){
                    deviceCollection += '<h3 id="slot_'+(total-x) +'" class="unit_type3"><div class=nums><p>'+ (total-x) + '</p></h3><div><p class=stats>No Device Assigned</div>';

                    tag = (total-x);
                    for(count = 0; count < num; count++){
                        x++;
                        unitCount++;
                        deviceCollection += '<h3 id="slot_'+(total-x) +'" class="unit_occupied"><div class=nums><p>'+ (total-x) + '</p></h3><div><p class=stats>This Slot is part of slot '+ tag +'</div>';
                    }
                }
                else if(imageType[unitCount] == 4){
                    deviceCollection += '<h3 id="slot_'+(total-x) +'" class="unit_type4"><div class=nums><p>'+ (total-x) + '</p></h3><div><p class=stats>No Device Assigned</div>';

                    tag = (total-x);
                    for(count = 0; count < num; count++){
                        x++;
                        unitCount++;
                        deviceCollection += '<h3 id="slot_'+(total-x) +'" class="unit_occupied"><div class=nums><p>'+ (total-x) + '</p></h3><div><p class=stats>This Slot is part of slot '+ tag +'</div>';
                    }
                }
                else if(imageType[unitCount] == 5){
                    deviceCollection += '<h3 id="slot_'+(total-x) +'" class="unit_type5"><div class=nums><p>'+ (total-x) + '</p></h3><div><p class=stats>No Device Assigned</div>';

                    tag = (total-x);
                    for(count = 0; count < num; count++){
                        x++;
                        unitCount++;
                        deviceCollection += '<h3 id="slot_'+(total-x) +'" class="unit_occupied"><div class=nums><p>'+ (total-x) + '</p></h3><div><p class=stats>This Slot is part of slot '+ tag +'</div>';
                    }
                }

                unitCount++;
            }

            else{
                deviceCollection += '<h3 id="slot_'+(total-x) +'" class="unit_empty"><div class=nums><p>'+ (total-x) + '</p></h3><div><p class=stats>No Device Assigned</div>';
            }

        }

        //add end tags
        deviceCollection = deviceCollection + '</div></td>';

        $('#rackTable').append(deviceCollection);

        $( "#deviceCollection" ).accordion({
            event: "click hoverintent"
        });


        //handlers for clicking on slot
        $(".unit_empty").dblclick(function(event){
            var ru_id = event.target.id;
            current_RU = ru_id;
            $('#dialog_header').html('Editing Device for Rack Unit: ' + current_RU);
            addDeviceToRackUnit(ru_id);
        });

        $(".unit_type1").dblclick(function(event){
            var ru_id = event.target.id;
            current_RU = ru_id;
            $('#dialog_header').html('Editing Device for Rack Unit: ' + current_RU);
            addDeviceToRackUnit(ru_id);
        });
        $(".unit_type2").dblclick(function(event){
            var ru_id = event.target.id;
            current_RU = ru_id;
            $('#dialog_header').html('Editing Device for Rack Unit: ' + current_RU);
            addDeviceToRackUnit(ru_id);
        });

        $(".unit_type3").dblclick(function(event){
            var ru_id = event.target.id;
            current_RU = ru_id;
            $('#dialog_header').html('Editing Device for Rack Unit: ' + current_RU);
            addDeviceToRackUnit(ru_id);
        });

        $(".unit_type4").dblclick(function(event){
            var ru_id = event.target.id;
            current_RU = ru_id;
            $('#dialog_header').html('Editing Device for Rack Unit: ' + current_RU);
            addDeviceToRackUnit(ru_id);
        });

        $(".unit_type5").dblclick(function(event){
            var ru_id = event.target.id;
            current_RU = ru_id;
            $('#dialog_header').html('Editing Device for Rack Unit: ' + current_RU);
            addDeviceToRackUnit(ru_id);
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
        drawPDU();
    }


    function setLabels (label){
        if((label).length < 30){   //on edit puts long string of characters, eliminate unecessary display of characters
            //$('#rackLabel').text(rackLabel.toString());
            document.getElementById('#rackLabel').innerHTML = label;
        }
    }

    function addDeviceToRackUnit(ru_id){

        $(function(){
            $("#dialog-form").dialog ({
                autoOpen:false,
                height:500,
                resizable:true,
                width:1000,
                modal:true,
                closeButton: false,
                buttons: {
                    "Save": function() {
                        var value = $('#device_select').val();

                        if(value == 0){
                            $(function() {
                                $( "#dialog-confirm" ).dialog({
                                    resizable: false,
                                    height:200,
                                    width:400,
                                    modal: true,
                                    buttons: {
                                        "Remove Device": function() {
                                            saveRackUnitDevice(value); //image id
                                            $( this ).dialog( "close" );
                                        },
                                        Cancel: function() {
                                            $( this ).dialog( "close" );
                                        }
                                    }
                                });
                            });
                            $( "#dialog-confirm" ).dialog( "open" );
                        }

                        else if (value != 0){
                            saveRackUnitDevice(value); //image id
                        }

                        //location.reload();
                    },
                    Cancel: function() {
                        //     removeStyles();
                        $(this).dialog("close");
                        //location.reload();
                    }
                }

            });

        });

        $( "#dialog-form" ).dialog( "open" );
    }


    function saveRackUnitDevice(imageId){

        current_RU = current_RU.substr(5,current_RU.length);

        var params = new Object();
        var responseData = null;
        params.itsId = current_Rack;
        params.ruId = current_RU;
        params.imageId = image_Id;
        params.slot_size = slot_size;


        $.ajax({
            async:false,
            url: '../ItcRack/saveRackUnit',
            type:'GET',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: params,
            cache: false,

            success: function(){
                // responseData = response;  //set global variable to recieved data

            },

            fail: function(){
                alert('Could not load the selected Rack')
            }
        });

        $('#dialog-form').dialog("close");
        $("#slot_occupancy").val('1'); //reset slot size selector
        $('#alert_message').html('Successfully Saved Rack Unit: ' + current_RU);
        $("#alert_message").fadeIn("slow", function(){
            $("#alert_message").fadeOut(6000);
        });


        loadSelectedRack(selected_row);//refresh rack selected row is set on click
        slot_size = 1;
    }
    /*************************
     *  PDU display
     ***************************/
    function drawPDU(){
        $("#pduTable_1").empty();
        var total = 10;
        var plugCollection = '<td><div id="plugCollection" class="plugCollection">';

        //if there are currently no breakers assigned, show total vacant slots of panel

        var x = 0;
        var unitCount = 0;
        for(x; x < total; x++){
            plugCollection += '<h3 id="plug_'+(total-x) +'" class="plug_type_1"><div class=nums><p>'+ (total-x) + '</p></h3><div><p class=stats>No RU Assigned</div>';

        }

        plugCollection = plugCollection + '</div></td>';

        $('#pduTable_1').append(plugCollection);

        //event handler for clicking connector
        $(".plug_type_1").click(function(event){

            plug_id = event.target.id;
            $('#connector_message').html('Select a Device to complete the connection for Plug: ' + plug_id);


            $("#connector_message").fadeIn(function(){
                $(".unit_type1").click(function(event){
                    connectionHandler(event);
                });
                $(".unit_type2").click(function(event){
                    connectionHandler(event);
                });
                $(".unit_type3").click(function(event){
                    connectionHandler(event);
                });
                $(".unit_type4").click(function(event){
                    connectionHandler(event);
                });
                $(".unit_type5").click(function(event){
                    connectionHandler(event);
                });
            });
            $("#connector_message").removeAttr("style");
        });

        function connectionHandler(event){
            slot_id = event.target.id;
            //  alert('slot id : ' + slot_id);
            drawPlumbConnection(plug_id,slot_id);
            slot_id = null;
            plug_id = null;
            $("#connector_message").html("Successfully Added Connector!");
            $("#connector_message").css("background","green");
            $("#connector_message").fadeOut(3000);

        }

        $( "#plugCollection" ).accordion({
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



    }

    /**************************************************
     Js Plumb - Library for connecting plugs to devices
     http://jsplumbtoolkit.com/doc/home
     *************************************************/
    function drawPlumbConnection(x,y){

        jsPlumb.ready(function(){
            jsPlumb.connect({source:x, target:y, detachable:true}, common);
        });

    }


    /**********************************************
     HTML code
     *********************************************/


</script>


<style>
#feedback { font-size: 1.4em; }
#deviceCollection .ui-selecting { background: #FECA40; }
#deviceCollection .ui-selected { background: #F39814; color: white; }
#deviceCollection { list-style-type: none; margin: 0; padding: 0; width: 99%; }
#deviceCollection li { margin: 0; padding: 0.4em; font-size: 1.4em; height: 18px; max-width:880px}

#plugCollection .ui-selecting { background: #FECA40; }
#plugCollection .ui-selected { background: #F39814; color: white; }
#plugCollection { list-style-type: none; margin: 0; padding: 0; width: 98%; }
#plugCollection li { margin: 0; padding: 0.4em; font-size: 1.4em; height: 140px; max-width:145px}

#imagePreview{height: 150px; width:660px;}
#dialog_header{font-family:Veranda, Helvetica,bold sans-serif ; font-size: 150%; color: #ddf5c9;}
    /*.unit_empty{height: 200px; width:880px; background-image: url(../css/ITC_Rack/device_images/vacant_ru.jpg) }*/
.unit_empty{height: 85px; width:880px;background-image: url(../css/ITC_Rack/device_images/vacant_ru_skinny.png) }
.unit_occupied{height: 184px; width:880px;background-image: url(../css/ITC_Rack/device_images/metal_grate2.png)}
.unit_type1{height: 200px; width:880px;background-image: url(../css/ITC_Rack/device_images/dellR710_scaled.png) }
.unit_type2{height: 202px; width:880px;background-image: url(../css/ITC_Rack/device_images/dellR720_scaled.png) }
.unit_type3{height: 220px; width:880px;background-image: url(../css/ITC_Rack/device_images/dellR910_scaled.png) }
.unit_type4{height: 187px; width:880px;background-image: url(../css/ITC_Rack/device_images/ciscoC250_scaled.png) }
.unit_type5{height: 211px; width:880px;background-image: url(../css/ITC_Rack/device_images/ciscoC260_scaled.png) }


    /*connectors*/
.plug_type_1{height: 140px; width:140px; background-image: url(../css/ITC_Rack/device_images/receptacle.png)}

#alert_message{height: 80px; width: 400px;
    font-family:Veranda, Helvetica, sans-serif ; font-size: 24px; text-align: center; padding-top: 50px;
    color: #f5f5f5;
    background: #518e48;
    box-shadow: 10px 10px 5px #888888;
    border:2px solid;
    border-radius:15px;
    position: fixed;
    top: 50%;
    left: 40%;
    z-index:99;}

#connector_message{height: 80px; width: 400px;
    font-family:Veranda, Helvetica, sans-serif ; font-size: 24px; text-align: center; padding-top: 50px;
    color: #f5f5f5;
    background: red;
    box-shadow: 10px 10px 5px #888888;
    border:2px solid;
    border-radius:15px;
    position: fixed;
    top: 50%;
    left: 40%;
    z-index:99;}

    /*width: 655px*/
#rackTable{margin:auto ;width:80%; max-width:880px; border-radius:10px;
    border-top:80px solid transparent;
    border-bottom: 60px solid transparent;
    border-left: 30px solid transparent;
    border-right: 30px solid transparent;
    -moz-box-shadow: 0 0 5px 5px #000;
    -webkit-box-shadow: 0 0 5px 5px #000;
    box-shadow: 0 0 5px 5px #000;
    /*-moz-border-image:url("../css/breaker/metal_s.png") 20 20 round; *//* Old firefox */
    /*-webkit-border-image:url("../css/breaker/metal_s.png") 20 20 round; *//* Safari */
    /*-o-border-image:url("../css/breaker/metal_s.png") 20 20 round; *//* Opera */
    /*border-image:url("../css/breaker/metal_s.png") 20 20 round;*/
    background-color: #585f62
}

#pduTable_1, #pduTable_2{margin:auto ;width:90%; max-width:200px; height:4600px ;max-height:4600px ;
    margin-top: 50px;
    border-radius: 15px;border: 20px solid transparent; -moz-box-shadow: 0 0 5px 5px #000;
    -webkit-box-shadow: 0 0 5px 5px #000;
    box-shadow: 0 0 5px 5px #000;
    /*-moz-border-image:url("../css/breaker/metal_s.png") 20 20 round; *//* Old firefox */
    /*-webkit-border-image:url("../css/breaker/metal_s.png") 20 20 round; *//* Safari */
    /*-o-border-image:url("../css/breaker/metal_s.png") 20 20 round; *//* Opera */
    /*border-image:url("../css/breaker/metal_s.png") 20 20 round;*/
    background-color: #474747
}

#dialog-confirm{font-size: 150%; width:400px; background-color: #561414}

.nums {
    /*internal text*/
    display:table;
    width:50px;
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

    /*tab label*/
.rackLabel {
    -moz-box-shadow:inset 0px 1px 12px 0px #3a3a3a;
    -webkit-box-shadow:inset 0px 1px 12px 0px #3a3a3a;
    box-shadow:inset 0px 1px 12px 0px #3a3a3a;
    background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #ebddaf), color-stop(1, #89836f) );
    background:-moz-linear-gradient( center top,  #ebddaf 5%, #3777b3 100% );
    filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#65bef5', endColorstr='#89836f');
    background-color: #89836f;
    background-color: #ebddaf;    /*#3b5f81 #5da3d8*/
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
    border:2px solid #a19678;   /*outline border*/
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
    color: #3a3a3a;
    text-shadow:
        -1px -1px 0 #727272,
        1px -1px 0 #727272,
        -1px 1px 0 #727272,
        1px 1px 0 #727272;
}

.highlight{
    background-color: cyan;
}


</style>
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

%{--<g:form controller="ItcRack">
   <g:actionSubmit value="submit" action="listItcRacks"/>
</g:form>--}%

%{--alert messages--}%
<div id="alert_message" style="display: none"></div>
<div id="connector_message" style="display: none;"></div>

%{--alert messages--}%



<br><br>


%{--
<div id="container0" style="width:100px;height:100px; background-color: red"></div>
<br><br>
<div id="container1" style="width:100px;height:100px; background-color: blue"></div>
--}%



<table id="allRack"></table>
<div id="allRackPager"></div>
<br>


%{--/********************
PDU Rendering
*********************/--}%

<div id='pdu_primary' style="float:left; margin-left:40px; max-width:200px">
    <dl>
        <dt><label for="pdu_primary_select" style="width:100px; font-size: 20pt; color: #f5f5f5;">Primary PDU</label></dt>
        <dd><select id="pdu_primary_select" style="width:200px; font-size: 14pt">
            <option value="0" selected="selected">-- None Selected --</option>
            <option value="1">PDU_Type_1</option>
            <option value="2">PDU_Type_2</option>
        </select></dd>
    </dl>

    <table id="pduTable_1">
        %{--drawing of rack--}%
    </table>

</div>

<div id='pdu_secondary' style="float:right; margin-right:40px">
    <dl>
        <dt><label for="pdu_secondary_select" style="width:100px; font-size: 20pt; color: #f5f5f5;">Secondary PDU</label></dt>
        <dd><select id="pdu_secondary_select" style="width:200px; font-size: 14pt">
            <option value="0" selected="selected">-- None Selected --</option>
            <option value="1">PDU_Type_1</option>
            <option value="2">PDU_Type_2</option>
        </select></dd>
    </dl>
    <table id="pduTable_2">
        %{--drawing of rack--}%
    </table>
</div>



%{--/**************************--}%
%{--Rack Table Rendering--}%
%{--***************************/--}%

<div>

    <p align="center"><a id="#rackLabel" class="rackLabel"></a></p>

    <table id="rackTable">

        %{--drawing of rack--}%
    </table>


</div>



%{--Pop-up dialog--}%

<div id="dialog-form" title="Device Selection" style="font-size:20px; display:none">
    <div id="dialog_header"></div>
    <p>*Note: To remove a Device, Select None</p>
    <br>

    <div style="float:left;margin_right:20px">
        <label for="device_select">Device Select</label>
        <select id="device_select" title="Device Select" style="width:200px; font-size: 14pt; margin-left:17px">
            <option value="0" selected="selected">-- None Selected --</option>
            <option value="1">Dell R710</option>
            <option value="2">Dell R720</option>
            <option value="3">Dell R910</option>
            <option value="4">Cisco C250</option>
            <option value="5">Cisco C260</option>
        </select>
    </div>

    <div style="position:relative; float:right">
        Upload Image: <br />
    <g:uploadForm action="upload" method="post" enctype="multipart/form-data">
        <button><input type="file" name="imageFile"/></button>
       <input type="submit" />
    </g:uploadForm>
    </div>

    <br>

    <div style="float:left;margin_right:20px; display: block">
        <label for="slot_occupancy">Slot Occupancy</label>
        <select id="slot_occupancy" style="width:200px; font-size: 14pt;">
            %{--<option value="0" selected="selected">-- None Selected --</option>--}%
            <option value="1" selected="selected">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
        </select>
    </div>


    <script>
        /*Image preview handler*/
        $('#device_select').change(function(event){

            var value = parseInt($('#device_select').val());   //value from selector
            var image_url = "";
            var height = "";
            var width = "";

            switch(value){
                case 0:
                    break;
                case 1:
                    image_url = "url(../css/ITC_Rack/device_images/dellR710.png)";
                    height = "149px";
                    width = "655px";
                    break;
                case 2:
                    image_url = "url(../css/ITC_Rack/device_images/dellR720.png)";
                    height = "154px";
                    width = "670px";
                    break;
                case 3:
                    image_url = "url(../css/ITC_Rack/device_images/dellR910_preview.png)";
                    height = "154px";
                    width = "427px";
                    break;
                case 4:
                    image_url = "url(../css/ITC_Rack/device_images/ciscoC250.png)";
                    height = "140px";
                    width = "648px";
                    break;
                case 5:
                    image_url = "url(../css/ITC_Rack/device_images/ciscoC260.png)";
                    height = "154px";
                    width = "641px";
                    break;
            }
            //alert(image);
            $("#imagePreview").css("height",height);
            $("#imagePreview").css("width",width);
            $("#imagePreview").css("background-image",image_url);

            image_Id = value;

        });

        $('#slot_occupancy').change(function(){
            var size = parseInt($('#slot_occupancy').val());
            var ru_val = parseInt(current_RU.substr(5,current_RU.length));
            var next_ru = (ru_val - 1);
            var next_type = ($('#slot_' + next_ru).attr('class')).substr(0,10);
             var x = 0;
           while(x < size-1){
                //  alert("next_ru: " + next_ru);
              //  alert("type of next: " + next_type);
              next_type = ($('#slot_' + next_ru).attr('class')).substr(0,10);

            if(!(next_type == "unit_empty")){
                alert('One of the slots you have selected is already being occupied.  Please Select a different Slot Size.');
                $("#slot_occupancy").val('1');
                 break;
                }
                next_ru -= 1;
               x++;
            }

            if((current_RU == "slot_1")&&(size > 1)){
                 alert('Number of Slots Exceeds Rack Space. \n Slot Size has been reset to 1.');
                $("#slot_occupancy").val('1');
            }

            else{slot_size = size}
        });
    </script>


    <br><br>

    <h2>Image Preview</h2>
    <div id="imagePreview"></div>


</div>

<div id="dialog-upload-image" title="Add New Device Image" style="display: none">
<g:uploadForm action="upload">
    <input type="file" name="myFile" />
    <input type="submit" />
</g:uploadForm>
</div>

<div id="dialog-confirm" title="Remove this Device from the Rack?" style="display: none">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>You are about to permanently remove this device and its connectors. Are you sure?</p>
</div>


%{--<fieldSet><select><option value="120">120V</option><option value="220">220V</option></select></fieldSet>--}%