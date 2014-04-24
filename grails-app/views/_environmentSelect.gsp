<%@  page import="edu.hawaii.its.dcmd.inf.GeneralService" %>
<%
    def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()
%>

<r:require modules='select2' />

<script>
    $(document).ready(function() { $("#environmentSelect").select2({
        placeholder: 'Please Select...',
        maximumInputLength: 20,
        width:150,
        createSearchChoice:function(term, data) { if ($(data).filter(function() { return this.text.localeCompare(term)===0; }).length===0) {return {id:term, text:term};} },
        initSelection: function(element, callback) {
            var data = {id: "${objectInstance?.env?.id}", text: "${objectInstance?.env.toString()}"};
                callback(data);
        },
        data: [${genService.listEnvsAsSelect()}]
    }).select2('val', '0');

});

</script>


        <input type="hidden" name="environmentSelect" id="environmentSelect" />
