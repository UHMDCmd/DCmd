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
    if('${action}'=='edit')
    {
        editOption = true
    }
    else {
        editOption = false
    }
    listReplacementUrl = 'listReplacements?assetId=${assetInstance.id}'

    $(document).ready(function() {

        $("#btnAdd").click(function(){
            $("#replacement_list").jqGrid("editGridRow","new",
                    {addCaption:'Create New Replacement Asset',
                        afterSubmit:afterSubmitEvent,
                        closeAfterAdd: true,
                        params:{main_asset:${assetInstance.id}},
                        savekey:[true,13]}
//                                closeModal()
            );
        });

        jQuery("#replacement_list").jqGrid({

            height:'240',
//            width:'1000',
            caption:'Replacement List',
            showPager:'true',
            url:listReplacementUrl,
            editurl:'editReplacements?assetId=${assetInstance.id}',
            datatype: "json",
            colNames:['', 'Replacement', 'Priority', 'Ready Date', 'Notes', 'id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:"20",
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                        keys: true, editbutton: false, delbutton: true}
                    },
                {name:'replacement', width:100, editable:true, edittype:'select', editoptions: {dataUrl:'${createLink(controller:"asset",action:"listAsSelect")}'}},
                {name:'priority', width:70, editable:true, edittype:'select',editoptions:{value:{1:'1', 2:'2', 3:'3', 4:'4', 5:'5'}}},

                {name:'ready_date', editable: true,
                    editoptions:{size:12, dataInit:function(el)
                    {
                        $(el).datepicker({dateFormat:'yy-mm-dd'}); },

                        defaultValue: function()
                        {
                            var currentTime = new Date();
                            var month = parseInt(currentTime.getMonth() + 1);
                            month = month <= 9 ? "0"+month : month;
                            var day = currentTime.getDate();
                            day = day <= 9 ? "0"+day : day;
                            var year = currentTime.getFullYear();
                            return year+"-"+month + "-"+day; }
                    }
                },

               {name:'replacement_notes',editable:true,editrules:{required:false}},
               {name:'id', hidden:true}
            ],

            rowNum:10,
            rowList:[1,2,3,4,5,6,7,8,9,10],
            pager: jQuery('#task_list_pager'),
            viewrecords: true,
            gridview: true,
            cellEdit:editOption,
            cellsubmit: 'remote',
            cellurl:'editReplacements?assetId=${assetInstance.id}',
            keys: true
        })


        jQuery(window).bind('resize', function() {

            // Get width of parent container
//            var width = jQuery('#gridContainer').attr('clientWidth');
            var width = document.getElementById("tabs").clientWidth;

//            var width = $("#gridStyle").width();
            if (width == null || width < 1){
                // For IE, revert to offsetWidth if necessary
                width = document.getElementById("tabs").offsetWidth;
            }
//            alert(width);
            width = width - 40; // Fudge factor to prevent horizontal scrollbars
            if (width > 0 &&
                // Only resize if new width exceeds a minimal threshold
                // Fixes IE issue with in-place resizing when mousing-over frame bars
                    Math.abs(width - jQuery("#replacement_list").width()) > 5)
            {
                jQuery("#replacement_list").setGridWidth(width);
            }

        }).trigger('resize');

    });


    function afterSubmitEvent(response, postdata) {
        var success = true;

        var json = eval('(' + response.responseText + ')');
        var message = json.message;

        if(json.state == 'FAIL') {
            success = false;
        } else {
            $('#message').html(message);
            $('#message').show().fadeOut(10000);
        }

        var new_id = json.id
        return [success,message,new_id];
    }

</script>

<table id="replacement_list"></table>






<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAdd" type="button" value="Add Replacement"/>
    </div>
</g:if>
<div id='message' class="message" style="display:none;"></div>