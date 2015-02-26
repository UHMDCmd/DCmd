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

            height:'auto',
            width:'1000',
            caption:'User Access List',
            url:'listUsers',
            editurl:'editUsers',
            datatype: "json",
            colNames:['', 'Username', 'Role', 'Account Locked?', 'id'],
            colModel:[
                {name:'actions', index:'actions', search:false, sortable:false, title:false, editable:false, required:false, sortable:false, width:"20",
                    formatter: 'actions', hidden:false, formatoptions: {
                    keys: true, editbutton: false, delbutton: true}
                },
                {name:'username', width:120, editable:true,title:false},
                {name:'role', width:100, editable:true, search:false, sortable:false, title:false, edittype:'select', editoptions: {dataUrl:'${createLink(controller:"user",action:"listRolesAsSelect")}'}},
                {name:'locked', width:100, editable:true, edittype:'checkbox', editoptions:{value:"true:false"}, sortable:false, search:false},
                {name:'id', hidden:true}
            ],

            sortname: 'username',
            sortorder: 'asc',
            rowNum: 100,
            rowList: [10, 20, 50, 100],

            gridview: true,
            viewrecords: true,
            autowidth:true,
            shrinkToFit: true,
            searchOnEnter:true,
            cellEdit:true,
            cellsubmit: 'remote',
//            afterSaveCell: afterSubmitHostEvent,
            cellurl:'editUsers',
            pager: '#allUserPager',
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
<div id="allUserPager"></div>

    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAdd" type="button" value="Add New User Access"/>
    </div>
<div id='message' class="message" style="display:none;"></div>