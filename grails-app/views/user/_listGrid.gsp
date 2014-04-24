<script type="text/javascript">

    $(document).ready(function() {

        $("#btnAdd").click(function(){
            $("#user_list").jqGrid("editGridRow","new",
                    {addCaption:'Add new User Access',
                        closeAfterAdd: true,
                        savekey:[true,13]}
//                                closeModal()
            );
        });

        jQuery("#user_list").jqGrid({

            height:'240',
            width:'400',
            caption:'User Access List',
            showPager:'false',
            url:'listUsers',
            editurl:'editUsers',

            datatype: "json",
            colNames:['', 'Username', 'Role', 'id'],
            colModel:[
                {name:'actions', index:'actions', search:false, sortable:false, title:false, editable:false, required:false, sortable:false, width:"20",
                    formatter: 'actions', hidden:false, formatoptions: {
                        keys: true, editbutton: false, delbutton: true}
                    },
                {name:'username', width:120, editable:true,title:false},
                {name:'role', width:100, editable:true, search:false, sortable:false, title:false, edittype:'select', editoptions: {dataUrl:'${createLink(controller:"user",action:"listRolesAsSelect")}'}},
                {name:'id', hidden:true}
            ],

            rowNum:1000,
            viewrecords: true,
            gridview: true,
            cellEdit:true,
            cellsubmit: 'remote',
//            afterSaveCell: afterSubmitHostEvent,
            cellurl:'editUsers',
            scrollOffset:20
        });

        var setTooltipsOnColumnHeader = function (grid, iColumn, text) {
            var thd = jQuery("thead:first", grid[0].grid.hDiv)[0];
            jQuery("tr.ui-jqgrid-labels th:eq(" + iColumn + ")", thd).attr("title", text);
        };

        setTooltipsOnColumnHeader($("#user_list"),0,"Remove User's access to DCmd");
        setTooltipsOnColumnHeader($("#user_list"),1,"UH Username of permitted User");
        setTooltipsOnColumnHeader($("#user_list"),2,"Permissions granted to user (Read, Read/Write, or Admin");

        jQuery('#user_list').filterToolbar({id:'user_list', searchOnEnter:true});

    });


</script>

<table id="user_list"></table>

    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAdd" type="button" value="Add New User Access"/>
    </div>
<div id='message' class="message" style="display:none;"></div>