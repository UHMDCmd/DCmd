

var labelType, useGradients, nativeTextSupport, animate;

(function() {
    var ua = navigator.userAgent,
        iStuff = ua.match(/iPhone/i) || ua.match(/iPad/i),
        typeOfCanvas = typeof HTMLCanvasElement,
        nativeCanvasSupport = (typeOfCanvas == 'object' || typeOfCanvas == 'function'),
        textSupport = nativeCanvasSupport
            && (typeof document.createElement('canvas').getContext('2d').fillText == 'function');
    //I'm setting this based on the fact that ExCanvas provides text support for IE
    //and that as of today iPhone/iPad current text support is lame
    labelType = (!nativeCanvasSupport || (textSupport && !iStuff))? 'Native' : 'HTML';
    nativeTextSupport = labelType == 'Native';
    useGradients = nativeCanvasSupport;
    animate = !(iStuff || !nativeCanvasSupport);
})();

var Log = {
    elem: false,
    write: function(text){
        if (!this.elem)
            this.elem = document.getElementById('log');
        this.elem.innerHTML = text;
        this.elem.style.left = (500 - this.elem.offsetWidth / 2) + 'px';
    }
};


var icicle;

function init(assetId){
    //left panel controls
    controls();
    labelType = 'HTML';

    var json;

    jQuery.ajax({
        async:false,
        url: 'getFullTree',
        datatype:'json',
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
            json = data;
        },
        error: function () { console.log('Error performing search'); }
    });

    // end
    // init Icicle
    icicle = new $jit.Icicle({
        // id of the visualization container
        injectInto: 'infovis',
        // whether to add transition animations
        animate: animate,
        // nodes offset
        offset: 1,
        // whether to add cushion type nodes
        cushion: false,
        //show only three levels at a time
        constrained: false,

        orientation:'v',
        //levelsToShow: 'all',

    // enable tips
        Tips: {
            enable: true,
            type: 'Native',
            // add positioning offsets
            offsetX: 20,
            offsetY: 20,
            // implement the onShow method to
            // add content to the tooltip when a node
            // is hovered
            onShow: function(tip, node){
                // count children
                var count = 0;
                node.eachSubnode(function(){
                    count++;
                });
               // console.log(node);
                // add tooltip info
         //       if(node.data.type != 'label') {
                    tip.innerHTML = "<div class=\"tip-title\"><b>Name:</b> " + node.name
                        + "</div><div class=\"tip-text\">" + count + " children</div>"
                        + "<div class=\"tip-text\"><b>Capacity:</b> " + node.data.capacity + "</div>"
                        + "<div class=\"tip-text\"><b>Draw:</b>    " + node.data.draw + "</div>";
         //       }

            }
        },
        // Add events to nodes
        Events: {
            type:'Native',
            enable: true,
            onMouseEnter: function(node) {
                if(node.data.type != 'label') {
                //add border and replot node
                    node.setData('border', '#33dddd');
                    icicle.fx.plotNode(node, icicle.canvas);
                    icicle.labels.plotLabel(icicle.canvas, node, icicle.controller);
                }
                else
                    icicle.tips.hide();
            },
            onMouseLeave: function(node) {
                node.removeData('border');
                icicle.fx.plot();
            },
            /*
            onClick: function(node){
                if (node) {
                  //  console.log(node);
                    //hide tips and selections
                    icicle.tips.hide();
                    if(icicle.events.hovered)
                        this.onMouseLeave(icicle.events.hovered);
                    //perform the enter animation
                    icicle.enter(node);
                }
            },
            */
            onRightClick: function(){
                //hide tips and selections
                icicle.tips.hide();
                if(icicle.events.hovered)
                    this.onMouseLeave(icicle.events.hovered);
                //perform the out animation
                icicle.out();
            }

        },
        // Add canvas label styling
        Label: {
            type: labelType // "Native" or "HTML"
        },
        // Add the name of the node in the corresponding label
        // This method is called once, on label creation and only for DOM and not
        // Native labels.
        onCreateLabel: function(domElement, node){
            domElement.ondblclick = function(){
                if (node && node.data.type != 'label') {
                    //  console.log(node);
                    //hide tips and selections
                    //icicle.tips.hide();
                    //if(icicle.events.hovered)
                    //    domElement.onMouseLeave(icicle.events.hovered);
                    //perform the enter animation
                    icicle.enter(node);

                }
            };
            if(node.data.type == 'label') {
                domElement.className='labelNode';
                domElement.style.width='40px';
                domElement.style.height='160px';
                var rotateDiv = document.createElement("DIV");
                rotateDiv.className = 'rotateDiv';
                rotateDiv.appendChild(document.createTextNode(node.name));
                domElement.appendChild(rotateDiv);
            }
            else {
                domElement.appendChild(renderButton(node));
                var nodeName = document.createElement("b");
                nodeName.appendChild(document.createTextNode(node.name));
                domElement.appendChild(nodeName);
                //domElement.innerHTML = node.name;
                var style = domElement.style;
                style.fontSize = '1.2em';
                style.display = '';
                style.cursor = 'pointer';
                style.color = '#fff';
                style.overflow = 'hidden';
                var detailsDiv = document.createElement("div");
                detailsDiv.className = "detailsDiv";
                detailsDiv.visibility = 'hidden';

                // Detailed information for each type of entity

                if(node.data.type != 'root') {
                    detailsDiv.appendChild(document.createTextNode("Type: " + node.data.type));
                    detailsDiv.appendChild(document.createElement("br"));
                    detailsDiv.appendChild(document.createTextNode("Cap: " + node.data.capacity));
                    detailsDiv.appendChild(document.createElement("br"));
                    detailsDiv.appendChild(document.createTextNode("Draw: " + node.data.draw));
                }
                else {
                    var detailTable = document.createElement("table");
                    detailTable.style.margin = 0;
                    var TR1 = document.createElement("tr");
                    var TR2 = document.createElement("tr");
                    var TH1 = document.createElement("th");
                    TH1.className = 'detailsTH';
                    var TH2 = document.createElement("th");
                    TH2.className = 'detailsTH';
                    var TH3 = document.createElement("th");
                    TH3.className = 'detailsTH';
                    var TH4 = document.createElement("th");
                    TH4.className = 'detailsTH';
                    var TH5 = document.createElement("th");
                    TH5.className = 'detailsTH';
                    var TD1 = document.createElement("td");
                    TD1.className = 'detailsTD';
                    var TD2 = document.createElement("td");
                    TD2.className = 'detailsTD';
                    var TD3 = document.createElement("td");
                    TD3.className = 'detailsTD';
                    var TD4 = document.createElement("td");
                    TD4.className = 'detailsTD';
                    var TD5 = document.createElement("td");
                    TD5.className = 'detailsTD';

                    TH1.appendChild(document.createTextNode("Totals"));
                   // TD1.appendChild(document.createElement("br"));
                    TD1.appendChild(document.createTextNode("Total Draw: " + node.data.totalDraw));
                    TD1.appendChild(document.createElement("br"));
                    TD1.appendChild(document.createTextNode("Average Util: " + node.data.avgUtil + "%"));
                    TD1.appendChild(document.createElement("br"));
                    TD1.appendChild(document.createTextNode("Total Elements: " + node.data.totalElements));

                    TH2.appendChild(document.createTextNode("Sources"));
                   // TD2.appendChild(document.createElement("br"));
                    TD2.appendChild(document.createTextNode("Total Capacity: " + node.data.sourceCap));
                    TD2.appendChild(document.createElement("br"));
                    TD2.appendChild(document.createTextNode("Average Util: " + node.data.sourceUtil + "%"));
                    TD2.appendChild(document.createElement("br"));
                    TD2.appendChild(document.createTextNode("Total Sources: " + node.data.sourceElements));

                    TH3.appendChild(document.createTextNode("Panels"));
                  //  TD3.appendChild(document.createElement("br"));
                    TD3.appendChild(document.createTextNode("Total Capacity: " + node.data.panelCap));
                    TD3.appendChild(document.createElement("br"));
                    TD3.appendChild(document.createTextNode("Average Util: " + node.data.panelUtil + "%"));
                    TD3.appendChild(document.createElement("br"));
                    TD3.appendChild(document.createTextNode("Total Panels: " + node.data.panelElements));

                    TH4.appendChild(document.createTextNode("Breakers"));
                   // TD4.appendChild(document.createElement("br"));
                    TD4.appendChild(document.createTextNode("Total Capacity: " + node.data.breakerCap));
                    TD4.appendChild(document.createElement("br"));
                    TD4.appendChild(document.createTextNode("Average Util: " + node.data.breakerUtil + "%"));
                    TD4.appendChild(document.createElement("br"));
                    TD4.appendChild(document.createTextNode("Total Breakers: " + node.data.breakerElements));

                    TH5.appendChild(document.createTextNode("Strips"));
                   // TD5.appendChild(document.createElement("br"));
                    TD5.appendChild(document.createTextNode("Total Capacity: " + node.data.stripCap));
                    TD5.appendChild(document.createElement("br"));
                    TD5.appendChild(document.createTextNode("Average Util: " + node.data.stripUtil + "%"));
                    TD5.appendChild(document.createElement("br"));
                    TD5.appendChild(document.createTextNode("Total Strips: " + node.data.stripElements));

                    TR1.appendChild(TH1);
                    TR1.appendChild(TH2);
                    TR1.appendChild(TH3);
                    TR1.appendChild(TH4);
                    TR1.appendChild(TH5);
                    detailTable.appendChild(TR1);

                    TR2.appendChild(TD1);
                    TR2.appendChild(TD2);
                    TR2.appendChild(TD3);
                    TR2.appendChild(TD4);
                    TR2.appendChild(TD5);
                    detailTable.appendChild(TR2);
                    detailsDiv.appendChild(detailTable);
                }
                domElement.appendChild(detailsDiv);
            }
        },
        // Change some label dom properties.
        // This method is called each time a label is plotted.
        onPlaceLabel: function(domElement, node){
//            console.log(node.getData('border'));
            if(node.data.type != 'label') {
                if(node.getData('width') > 50) {
                    domElement.childNodes.item(1).textContent = node.name;
                    if(node.getData('border') == 0) {
                        domElement.childNodes.item(0).style.visibility = 'hidden';
                        domElement.childNodes.item(2).style.visibility = 'hidden';
                    }
                    else {
                        domElement.childNodes.item(0).style.visibility = 'visible';
                        domElement.childNodes.item(2).style.visibility = 'visible';
                    }
                }
                else {
                    domElement.childNodes.item(0).style.visibility = 'hidden';
                    domElement.childNodes.item(1).textContent = "";
                    domElement.childNodes.item(2).style.visibility = 'hidden';
                }


                var style = domElement.style,
                    width = node.getData('width'),
                    height = node.getData('height');
                if(node.data.type != 'label') {
                    if(width < 7 || height < 7) {
                        style.display = 'none';
                    } else {
                        style.display = '';
                        style.width = width + 'px';
                        style.height = height + 'px';
                    }
                }
            }
            //console.log(labelType);
            //console.log(domElement);
        }
    });

    // load data
    icicle.loadJSON(json);

    console.log(icicle);
    // compute positions and plot
    icicle.refresh();

    var newRoot = icicle.graph.getNode(assetId);
    if(newRoot) {
        icicle.enter(newRoot);
    }
    //end
}

function renderButton(node) {

    var div = document.createElement("DIV");
    div.className='innerDiv';

    if(node.data.type != 'root') {

        if(node.data.type != 'strip') {
            var btnDel = document.createElement("span");
            btnDel.className='treeButton';
            var delImg = document.createElement("img");
            delImg.src = "../images/trashcan.png";
            delImg.alt = "Delete";
            delImg.style.width = '20px';
            btnDel.onclick=function(){deleteDialog(node)};

            btnDel.appendChild(delImg);
            div.appendChild(btnDel);
        }
        /*
        var btnForm = document.createElement("span");
        btnForm.className='treeButton';
        var formImg = document.createElement("img");
        formImg.src = "../images/formLink.png";
        formImg.alt = "Go to Form";
        formImg.style.width = '20px';

        btnForm.onclick=function(){openEditDialog(node)};
        btnForm.appendChild(formImg);
        div.appendChild(btnForm);
        */

        var btnEdit = document.createElement("span");
        btnEdit.className='treeButton';
        var editImg = document.createElement("img");
        editImg.src = "../images/editIcon.png";
        editImg.alt = "Edit Element";
        editImg.style.width = '20px';

        btnEdit.onclick=function(){openEditDialog(node)};
        btnEdit.appendChild(editImg);
        div.appendChild(btnEdit);
    }

    if(node.data.type != 'strip' && node.data.type != 'CDU') {
        var btnCreate = document.createElement("span");
        btnCreate.className='treeButton';
        var createImg;
        createImg = document.createElement("img");
        createImg.src = "../images/addChildren.png";
        createImg.alt = "Add Children";
        createImg.style.width = '20px';
        //createText = document.createTextData("+");

        if(node.data.type == 'root') {
            btnCreate.onclick=function(){openCreateItemsDialog(node, 'PSU')};
        }
        if(node.data.type == 'PSU') {
            btnCreate.onclick=function(){openCreateItemsDialog(node, 'Bus')};
        }
        else if(node.data.type == 'Bus') {
            btnCreate.onclick=function(){openCreateCDUsDialog(node)};
        }
//        else if(node.data.type == 'CDU') {
//            btnCreate.onclick=function(){openCreateCDUsDialog(node)};
//        }
        btnCreate.appendChild(createImg);
        div.appendChild(btnCreate);
    }

    return div;
}
function deleteDialog(node) {
    var deleteMessage = "";
    // If trying to delete an object with Children
    if(node.getSubnodes()[1] && node.data.type != 'CDU') {
        var dialog = $("<p>Delete Children before attempting to delete \'" + node.name + "\'</p>").dialog({
            title:"Cannot Delete...",
            id:"deleteDialog",
            name:"deleteDialog",
            buttons: {
                "OK": function() {dialog.dialog('close');}
            },
            width:400
        })
    }
    else {
        switch(node.data.type) {
            case 'CDU':
                deleteMessage = "<p>Unplug all Devices and delete CDU \'" + node.name + "\'?</p>";
                break;
            case 'Bus':
                deleteMessage ="<p>Delete Bus \'" + node.name + "\'?</p>";
                break;
            case 'PSU':
                deleteMessage ="<p>Delete PSU \'" + node.name + "\'?</p>";
                break;
        }
        var dialog = $(deleteMessage).dialog({
            title:"Are you sure?",
            id:"deleteDialog",
            name:"deleteDialog",
            buttons: {
                /*
                 "Move to Storage": function() {
                 document.getElementById("nodeID").value = node.id;
                 $("#locationDialog").dialog("open");
                 dialog.dialog("close");
                 },
                 */
                "Delete": function() {deleteObject(node.id, node.data.type, dialog);},
                "Cancel": function() {dialog.dialog('close');}
            },
            width: 400
        });
    }
    /*
    else if(node.getSubnodes()[1]) {
        console.log("true");
    }
    else {
        console.log("false");
    }
    */
}

function deleteObject(nodeId, nodeType, dialog) {
    console.log(dialog);
    jQuery.ajax({
        async:false,
        url: 'deleteObject',
        data:{id:nodeId, type:nodeType},
        datatype:'json',
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
            console.log(data);
            dialog.dialog("close");
            reloadTree();
        },
        error: function () { console.log('Error performing search'); }
    });
}



function unplugStrip(option,nodeId,dialog) {
    console.log(dialog);
    jQuery.ajax({
        async:false,
        url: 'unplugPowerStrip',
        data:{id:nodeId, storageOption:option},
        datatype:'json',
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
            console.log(data);
            dialog.dialog("close");
            reloadTree();
        },
        error: function () { console.log('Error performing search'); }
    });
}


function openEditDialog(node){
    var nodeType = node.data.type;

    $( "#dialog-form" ).dialog({
        title:"Edit " + nodeType
    });


    switch(nodeType) {
        case 'PSU':
            var dialog = document.getElementById('dialog-form');
            dialog.innerHTML = "<div class='edit_dialog_box'>" +
                "<label class='dialogLabel' for='dialogName' id='dialogNameLabel'>Name</label>" +
                "<input type='text' name='dialogName' id='dialogName' class='text ui-widget-content ui-corner-all' /><br>" +
                "<label class='dialogLabel' for='dialogCapacity' id='dialogCapacityLabel'>Capacity</label>" +
                "<input type='text' name='dialogCapacity' id='dialogCapacity' class='text ui-widget-content ui-corner-all' /><br>" +
                "<label class='dialogLabel' for='dialogNotes' id='dialogNotesLabel'>Notes</label>" +
                "<input type='text' name='dialogNotes' id='dialogNotes' class='text ui-widget-content ui-corner-all' /><br>" +
                "<input class='ui-corner-all' id='btnSave' type='button' value='Save'/>" +
                "<input class='ui-corner-all' id='btnCancel' type='button' value='Cancel'/>" +
                "</div>";
            $("#dialog-form").dialog("open");
            loadEditFields(node);
            break;
        case 'Bus':
            var dialog = document.getElementById('dialog-form');
            dialog.innerHTML = "<div class='edit_dialog_box'>" +
            "<label class='dialogLabel' for='dialogName' id='dialogNameLabel'>Name</label>" +
                "<input type='text' name='dialogName' id='dialogName' class='text ui-widget-content ui-corner-all' /><br>" +
            "<label class='dialogLabel' for='dialogPSU' id='dialogPSULabel'>Parent PSU</label>" +
                "<input type='hidden' name='dialogPSU' id='dialogPSU' class='text ui-widget-content ui-corner-all'/><br>" +
            "<label class='dialogLabel' for='dialogCapacity' id='dialogCapacityLabel'>Capacity</label>" +
                "<input type='text' name='dialogCapacity' id='dialogCapacity' class='text ui-widget-content ui-corner-all' /><br>" +
            "<label class='dialogLabel' for='dialogNotes' id='dialogNotesLabel'>Notes</label>" +
                "<input type='text' name='dialogNotes' id='dialogNotes' class='text ui-widget-content ui-corner-all' /><br>" +
                "<input class='ui-corner-all' id='btnSave' type='button' value='Save'/>" +
                "<input class='ui-corner-all' id='btnCancel' type='button' value='Cancel'/>" +
                "</div>";
            $("#dialog-form").dialog("open");
            loadEditFields(node);
            break;
        case 'CDU':
            var dialog = document.getElementById('dialog-form');
            dialog.innerHTML = "<div class='edit_dialog_box'>" +
            "<label class='dialogLabel' for='dialogName' id='dialogNameLabel'>Name</label>" +
                "<input type='text' name='dialogName' id='dialogName' class='text ui-widget-content ui-corner-all' /><br>" +
            "<label class='dialogLabel' for='dialogBus' id='dialogBusLabel'>Parent Bus</label>" +
                "<input type='hidden' name='dialogBus' id='dialogBus' class='text ui-widget-content ui-corner-all'/><br>" +
            "<label class='dialogLabel' for='dialogCapacity' id='dialogCapacityLabel'>Capacity</label>" +
                "<input type='text' name='dialogCapacity' id='dialogCapacity' class='text ui-widget-content ui-corner-all' /><br>" +
            "<label class='dialogLabel' for='dialogRack' id='dialogRackLabel'>Rack</label>" +
                "<input type='hidden' name='dialogRack' id='dialogRack' class='text ui-widget-content ui-corner-all'/><br>" +
            "<label class='dialogLabel' for='dialogNotes' id='dialogNotesLabel'>Notes</label>" +
                "<input type='text' name='dialogNotes' id='dialogNotes' class='text ui-widget-content ui-corner-all' /><br>" +
                "<input class='ui-corner-all' id='btnSave' type='button' value='Save'/>" +
                "<input class='ui-corner-all' id='btnCancel' type='button' value='Cancel'/>" +
                "</div>";
            $("#dialog-form").dialog("open");
            loadEditFields(node);
            break;
        case 'strip':
            var dialog = document.getElementById('edit-strip-dialog');
//            var stripId = document.createElement("INPUT");
//            stripId.type = "hidden";
//            stripId.value = node;
//            dialog.appendChild(stripId);

            loadStripFields(node);
            $("#edit-strip-dialog").dialog("open");

            break;

    }
    $('#btnSave').bind('click', function() {
        editNode(node);
        $("#dialog-form").dialog("close");
    });
    $('#btnCancel').bind('click', function() {

        $("#dialog-form").dialog("close");
    });
}


function editNode(node) {
    var params = new Object();
    params.id = node.id;
    params.type = node.data.type;
    params.itsId = document.getElementById('dialogName').value;
    params.capacity = document.getElementById('dialogCapacity').value;
    params.notes = document.getElementById('dialogNotes').value;

    switch(node.data.type) {
        case 'Bus':
            params.parent = document.getElementById("dialogPSU").value;
            break;
        case 'CDU':
            params.rackId = document.getElementById("dialogRack").value;
            params.parent = document.getElementById("dialogBus").value;
            break;
    }

    $.ajax({
        async: false,
        url: '../power/editNode',
        data:$.param(params),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
            $('#dialog-form').dialog("close");
            reloadTree();
        },
        error: function(){
            alert('An error occurred, child was not created.');
        }
    });
}


function openCreateSourceDialog(node){}
function openCreatePanelDialog(node){
    $( "#dialog-form" ).dialog({
        title:"Create PowerPanel"
    });


    var dialog = document.getElementById('dialog-form');
    dialog.innerHTML = "<div class='edit_dialog_box'>" +
        "<label class='dialogLabel' for='dialogName' id='dialogNameLabel'>Name</label>" +
        "<input type='text' name='dialogName' id='dialogName' class='text ui-widget-content ui-corner-all' /><br>" +
        "<label class='dialogLabel' for='dialogCapacity' id='dialogCapacityLabel'>Capacity</label>" +
       "<input type='text' name='dialogCapacity' id='dialogCapacity' class='text ui-widget-content ui-corner-all' /><br>" +

        "<label class='dialogLabel' for='dialogPoles' id='dialogPolesLabel'>Poles</label>" +
         //select input field

        "<select id='dialogPoles' name='dialogPoles'  class='text ui-widget-content ui-corner-all'>" +
        "<option value='6'>6</option>" +
        "<option value='8'>8</option>" +
        "<option value='10'>10</option>" +
        "<option value='12'>12</option>" +
        "<option value='14'>14</option>" +
        "<option value='16'>16</option>" +
        "<option value='18'>18</option>" +
        "<option value='20'>20</option>" +
        "<option value='22'>22</option>" +
        "<option value='24'>24</option>" +
        "</select></br>" +

     //   "<input type='text' name='dialogPoles' id='dialogPoles' class='text ui-widget-content ui-corner-all' /><br>" +
        "<label class='dialogLabel' for='dialogNotes' id='dialogNotesLabel'>Notes</label>" +
        "<input type='text' name='dialogNotes' id='dialogNotes' class='text ui-widget-content ui-corner-all' /><br>" +
//        "<input class='ui-corner-all' id='btnSave' type='button' onclick='createPanel(node)' value='Create New Panel'/>" +
        "</div>";

    var btnSave = document.createElement("INPUT");
    btnSave.typeName='button';
    btnSave.className='ui-corner-all';
    btnSave.value = 'Create';
    btnSave.onclick = function() {createPanel(node);};
    dialog.appendChild(btnSave);

    $("#dialog-form").dialog("open");

}

/*function openCreateBreakersDialog(node){
    var childType;

    $( "#dialog-form" ).dialog({
        title:"Create Breakers"
    });
    var i=0;
    var tempString = "";
    for(i=0; i<node.data.poles; i++) {
        tempString += i + "<br>";
    }
    var dialog = document.getElementById('dialog-form');
    dialog.innerHTML = "<div class='innerDiv'>" + tempString +
        "<label class='dialogLabel' for='dialogName' id='dialogNameLabel'>Name</label>" +
        "<input type='text' name='dialogName' id='dialogName' class='text ui-widget-content ui-corner-all' /><br>" +
        "<label class='dialogLabel' for='dialogCapacity' id='dialogCapacityLabel'>Capacity</label>" +
        "<input type='text' name='dialogCapacity' id='dialogCapacity' class='text ui-widget-content ui-corner-all' /><br>" +
        "<label class='dialogLabel' for='dialogNotes' id='dialogNotesLabel'>Notes</label>" +
        "<input type='text' name='dialogNotes' id='dialogNotes' class='text ui-widget-content ui-corner-all' /><br>" +
        "<input class='ui-corner-all' id='btnSave' type='button' value='Create'/>" +
        "</div>";

    *//*
    document.getElementById('dialogNameLabel').innerHTML = childType + " Name";
    document.getElementById('dialogCapacityLabel').innerHTML = childType + ' Capacity';
    document.getElementById('dialogNotesLabel').innerHTML = childType + ' Notes';
*//*

    $("#dialog-form").dialog("open");


}*/


function openCreateBreakersDialog(node){
    var childType;

    var panelId = node.id;
    var sourceId = null;
    var panelLabel = null;

    var  totalPoles = null;
    var  breakersInUse = 0;

    var activePoles = new Array();
    var preLoad = null;
    var responseData = null;
    var form_table = '<div id="dialog-form" title="Add Breakers for Panel"><form></form><Style>h3{color: #3C4F5D;}</Style><table id=selectionTable></table></form></div>';

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
            title:"Assign Breakers for Panel",
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

    });

    function getPanelData(node){

        var params = new Object();
        var resp = null;

        params.panelId = node.id;

        $.ajax({
            async:false,
            url: '../SourceFeed/getPanelDataForBreakerForm',
            type:'GET',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: params,
            cache: false,

            success: function(response){
                if(response != null){
                    panelLabel= response[0];
                    totalPoles = response[1];
                    breakersInUse = response[2];
                    sourceId = response[3];
                    resp = true;
                    alert("current panel set to: " + panelLabel + "\npoles set to: " + totalPoles + "breakers in use: " + breakersInUse + "sourceId: " + sourceId);
                }
            },

            fail: function(){
                alert('Could not load breakers')
            }
        });
        if(resp == true){
            $('#dialog-form').html(form_table);
            createSelectors();  //dynamically create correct number of selector options

            if(breakersInUse != 0){
                responseData = getSelected();  //calls breaker controller action

                if(responseData != null){
                    loadCSS(responseData);  //filters data from previous controller action and applies to elements
                }
            }
        }

    }

    function createSelectors(){
        $("#selectionTable").empty(); //clean out modal before repopulate
        alert("There are " + totalPoles + " Poles to Assign Breakers to panel: " + panelLabel + "\n id: " + sourceId);

        var num = 1;
        while(num < totalPoles){
            /*unique id must be added for selectors*/
            var pduSelector = '<label>Associated PDUs</label><input type="number" name="pdus" min="1" max="10">';

            //limit options on last two poles
            if (num == (totalPoles - 1)){
                /*Odd pole********************/
                var voltageSelector = '<label>Voltage</label><select id="'+ 'vselectp' + num +'" class = "select_target" valign="top" style="width: 150px"><option value="0"> -- Not Enabled -- </option><option value="110">110V -- Single PowerBreaker</option></select>';
                var odd = '<fieldSet id="'+ 'fieldset' + num +'" class="fieldSetA"  style=" width:440px; text-align:center; margin: 20px"><h2 class ="ph">Pole '+ num +'</h2><br>' + voltageSelector +'<br><br>' + pduSelector + '</fieldSet>';
                /*********************************/

                /*Even pole*****************/
                voltageSelector = '<label>Voltage</label><select id="'+ 'vselectp' + (num+1) +'" class = "select_target" valign="top" style="width: 150px"><option value="0"> -- Not Enabled -- </option><option value="110">110V -- Single PowerBreaker</option></select>';
                var even = '<fieldSet id="' + 'fieldset' + (num+1) +'" class="fieldSetB" style="width:440px; text-align:center; margin: 20px"><h2 class ="ph">Pole '+(num+1) +'</h2><br>' + voltageSelector +'<br><br>' + pduSelector + '</fieldSet>';
                /**********************************/

            }
            else{

                /*Odd pole********************/
                var voltageSelector = '<label>Voltage</label><select id="'+ 'vselectp' + num +'" class = "select_target"  valign="top" style="width: 150px"><option value="0"> -- Not Enabled -- </option><option value="110">110V -- Single PowerBreaker</option><option value="220">220V -- Dual PowerBreaker</option></select>';
                var odd = '<fieldSet id="'+ 'fieldset' + num +'" class="fieldSetA"  style=" width:440px; text-align:center; margin: 20px"><h2 class ="ph">Pole '+ num +'</h2><br>' + voltageSelector +'<br><br>' + pduSelector + '</fieldSet>';
                /*********************************/

                /*Even pole*****************/
                voltageSelector = '<label>Voltage</label><select id="'+ 'vselectp' + (num+1) +'"  class = "select_target" valign="top" style="width: 150px"><option value="0"> -- Not Enabled -- </option><option value="110">110V -- Single PowerBreaker</option><option value="220">220V -- Dual PowerBreaker</option></select>';
                var even = '<fieldSet id="' + 'fieldset' + (num+1) +'" class="fieldSetB" style="width:440px; text-align:center; margin: 20px"><h2 class ="ph">Pole '+(num+1) +'</h2><br>' + voltageSelector +'<br><br>' + pduSelector + '</fieldSet>';
                /**********************************/
            }

            var oddPole = '<td  id="td'+num+'" style="border:2px; border-radius:25px;">' + odd+ '</td>';
            var evenPole = '<td id="td'+(num+1)+'" style="border:2px; border-radius:25px;">' + even+ '</td>';


            var row = '<tr>' + oddPole + evenPole + '</tr>';

            $('#selectionTable').append(row);

            num += 2;
        }

        $(".select_target").change(function(event){
            //alert("handler for vselect: " + event.target.id);
            var select_name = event.target.id;
            var num = parseInt(select_name.slice(8,select_name.length));

             changeBreaker(select_name,num);

        })

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



    function changeBreaker(id,num){
        /***************************************************************************/
        //special Case: end poles need to be done seperately
        // alert("change breaker called, id: " + id + " number: " + num);
        //last pole
        if (num == totalPoles){
            var td = "td" + num;
            var select = id;

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
            var select = id;
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
                addStyle("#td" + num + '{background:' + metal1 +'}');
                addStyle("#fieldset" + num + '{background-color: #58858d}');
                (document.getElementById(select)).selectedIndex = 1;
            }
            else if(val == 0){
                addStyle("#td" + num + '{background: #3C4F5D}');
                addStyle("#fieldset" + num + '{background-color:  #4a4d4a}');
            }
        }
        //second to last pole, has different color bg
        else if (num == (totalPoles-1)){
            var td = "td" + num;
            var select = "vselectp" + num;

            if(val == 110){
                addStyle("#td" + num + '{background' + metal1 +'}');
                addStyle("#fieldset" + num + '{background-color: #91b3b5}');
                (document.getElementById(select)).selectedIndex = 1;
            }

            else if(val == 0){
                addStyle("#td"+ num + '{background: #3C4F5D}');
                addStyle("#fieldset" + num + '{background-color:  #4a4d4a}');
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


    $("#dialog-form").dialog("open");
   // $("#breaker-dialog-form").innerHTML(form_table);
//   $('#breaker-dialog-form').createElement('#selectionTable');
    getPanelData(node);


}


function createPanel(node) {
    var params = new Object();
    params.id = node.id;
    params.type = node.data.type;
    params.name = document.getElementById('dialogName').value;
    params.capacity = document.getElementById('dialogCapacity').value;
    params.poles = document.getElementById('dialogPoles').value;
    params.notes = document.getElementById('dialogNotes').value;

    $.ajax({
        async: false,
        url: '../power/createChild',
        data:$.param(params),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
            $('#dialog-form').dialog("close");
            reloadTree();
        },
        error: function(){
            alert('An error occurred, child was not created.');
        }
    });
}


function createChild(node) {
    var params = new Object();
    params.id = node.id;
    params.type = node.data.type;
    var i=1;


    console.log(params)
    switch (params.type) {
        case 'root':
            params.numItems = document.getElementById('numItems').value;
            params.name = new Array();
            params.capacity = new Array();
            params.notes = new Array();
            for(i=1; i<=params.numItems; i++) {
                params.name[i] = document.getElementById("dialogName"+i).value;
                params.capacity[i] = document.getElementById("dialogCapacity"+i).value;
                params.notes[i] = document.getElementById("dialogNotes"+i).value;
            }
            break;
        case 'PSU':
            params.numItems = document.getElementById('numItems').value;
            params.name = new Array();
            params.capacity = new Array();
            params.notes = new Array();
            for(i=1; i<=params.numItems; i++) {
                params.name[i] = document.getElementById("dialogName"+i).value;
                params.capacity[i] = document.getElementById("dialogCapacity"+i).value;
                params.notes[i] = document.getElementById("dialogNotes"+i).value;
            }
            break;
        case 'Bus':
            params.numCDUs = document.getElementById('numCDUs').value;
            params.name = new Array();
            params.capacity = new Array();
            params.IP = new Array();
            params.rack = new Array();
            params.notes = new Array();
            for(i=1; i<=params.numCDUs; i++) {
                params.name[i] = document.getElementById("dialogName"+i).value;
                params.capacity[i] = document.getElementById("dialogCapacity"+i).value;
                params.IP[i] = document.getElementById("dialogIP"+i).value;
                params.rack[i] = document.getElementById("dialogRack"+i).value;
                params.notes[i] = document.getElementById("dialogNotes"+i).value;
            }
            break;


    }

    console.log(params);
    $.ajax({
        async: false,
        url: '../power/createChild',
        data:$.param(params),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
            $('#dialog-form').dialog("close");
            reloadTree();
            clearDialog();
        },
        error: function(){
            alert('An error occurred, child was not created.');
        }
    });
}

function reloadTree() {
    var json;

    jQuery.ajax({
        async:false,
        url: 'getFullTree',
        datatype:'json',
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
            json = data;
        },
        error: function () { console.log('Error performing search'); }
    });
    icicle.loadJSON(json);
    icicle.refresh();
}

function clearDialog() {
    document.getElementById("dialog-form").innerHTML = '';
}

//init controls
function controls() {
    var jit = $jit;
    /*
    var gotoparent = jit.id('update');
    jit.util.addEvent(gotoparent, 'click', function() {
        icicle.out();
    });
    /*
    var select = jit.id('s-orientation');
    jit.util.addEvent(select, 'change', function () {
        icicle.layout.orientation = select[select.selectedIndex].value;
        icicle.refresh();
    });
    var levelsToShowSelect = jit.id('i-levels-to-show');
//    var levelsToShowSelect = 'all';
    jit.util.addEvent(levelsToShowSelect, 'change', function () {
        var index = levelsToShowSelect.selectedIndex;
        if(index == 0) {
            icicle.config.constrained = false;
        } else {
            icicle.config.constrained = true;
            icicle.config.levelsToShow = index;
        }
        icicle.refresh();

    });
     */
}
//end
