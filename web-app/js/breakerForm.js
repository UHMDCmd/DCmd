

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

