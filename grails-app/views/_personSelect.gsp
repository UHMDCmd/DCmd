<%@  page import="edu.hawaii.its.dcmd.inf.PersonService" %>
<%
    def personService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.PersonService').newInstance()
%>

<r:require modules='select2' />

<script>
    $(document).ready(function() { $("#personSelect${idNum}").select2({
        placeholder: 'Please Select...',
        maximumInputLength: 20,
        width:150,
        initSelection: function(element, callback) {
            var data = {id: "${personService.getAdmin(objectInstance)?.id}", text: "${personService.getAdmin(objectInstance).toString()}"};
                callback(data);
        },
        data: [${personService.listPersonAsSelect()}]
    }).select2('val', '0');

});

</script>


        <input type="hidden" name="personSelect${idNum}" id="personSelect${idNum}" />
