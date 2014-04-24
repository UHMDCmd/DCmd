//////////////////////////////
/*Physical Server Alerts*/
/////////////////////////////

/*
* view: asset/_dialog
* function: alerts user on rack change
* element/line: #currentRack, line 293*
*/
$(document.body).on("change","#currentRack",function(){
    var size = document.getElementById('RU_size').value;
     var ru_size = parseInt(size);

    if(ru_size == 0){
     warnRackSize();
    }
   });

//elevated warning
function warnRackSize(){
    $.notify({title:'Rack Unit Size', text:'Please Assign a Unit Size Before Proceeding or an Error will occur.', icon:'../css/flash_messages/images/icon_s.png'});
}


function warnRackPosition(){
    $.notify({title:'Rack Position', text:'Please Specify the Rack Position for this Physical Server.', icon:'../css/flash_messages/images/icon_i.png'});
}

//$(document.body).on("focus","#RU_begin",function() {
//        console.log('in');
//    });
$(document.body).on("focusout","#RU_begin",function() {
          //  console.log('out');
    var size = document.getElementById('RU_size').value;
    var ru_size = parseInt(size);
    if(ru_size == 0){
        warnRackSize();
    }
    else{
        $.notify({title:'Postion in Rack', text:'Please Make Sure Position is Within Range: 1-45.', icon:'../css/flash_messages/images/icon_i.png'});
    }
    });

$(document.body).on("focusout","#RU_size",function() {
    console.log('out');
    var size = document.getElementById('RU_size').value;
    var ru_size = parseInt(size);
    if(ru_size == 0){
    $.notify({title:'Rack Unit Size', text:'RU Size must be greater than 0.', icon:'../css/flash_messages/images/icon_ex.png'});
    }
    else{
        warnRackPosition();
    }
    });


