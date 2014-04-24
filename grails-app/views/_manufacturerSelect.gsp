<%@  page import="edu.hawaii.its.dcmd.inf.GeneralService" %>
<%
    def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()
%>

<r:require modules='select2' />

<script>
    $(document).ready(function() { $("#manufacturerSelect").select2({
        placeholder: 'Please Select...',
        maximumInputLength: 20,
        width:150,
        createSearchChoice:function(term, data) { if ($(data).filter(function() { return this.text.localeCompare(term)===0; }).length===0) {return {id:term, text:term};} },
        initSelection: function(element, callback) {
            var data = {id: "${objectInstance?.manufacturer?.id}", text: "${objectInstance?.manufacturer.toString()}"};
                callback(data);
        },
        data: [${genService.listManufacturersAsSelect()}]
    }).select2('val', '0');

});

</script>


        <input type="hidden" name="manufacturerSelect" id="manufacturerSelect" />
