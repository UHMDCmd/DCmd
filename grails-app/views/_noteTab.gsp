<script>
    $(document).ready(function() {

        //Bind the click event here
        $("#save${noteType}").bind('click', function() {
            //set up the ajax call
            $.ajax({
                async: false,
                url: "/its/dcmd/asset/saveNote",
                data: getNewNotes(),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function(data) {alert("Note Saved.")},
                error: function(){
                    alert('An error occurred, the note was not saved.');
                }
            });
        });

        function getNewNotes(){

            var params = {pageType: "${pageType}", noteType: "${noteType}", noteData:document.getElementById("${noteType}Field").value, id: ${objectId}};
            return $.param(params);
        }

    });
</script>
<table>
    <thead></thead>
    <tbody>
    <g:if test="${action == 'edit'}">
        <tr class="prop">
            <td valign="top" class="value ${hasErrors(bean: objectInstance, field: noteType, 'errors')}">
                <g:textArea rows="20" cols="50" style="width:650px" name="${noteType}Field" value="${fieldValue(bean: objectInstance, field: noteType)}" />
            </td>
        </tr>
        <tr class="prop">
            <td>
                <div>
                    <button id="save${noteType}"
                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                            role="button" aria-disabled="false">
                        <span class="ui-button-text">Save Note</span>
                    </button>
                </div>
            </td>
        </tr>
    </g:if>
    <g:if test="${action != 'edit'}">
        <tr class="prop">
            <td>
                <g:textArea rows="20" cols="50" style="width:650px" name="textField" value="${fieldValue(bean: objectInstance, field: noteType)}" readonly='yes' />
            </td>
        </tr>
    </g:if>
    </tbody>
</table>

