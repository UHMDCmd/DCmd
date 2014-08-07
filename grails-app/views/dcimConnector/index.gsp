<%--
  Created by IntelliJ IDEA.
  User: Jesse
  Date: 7/18/2014
  Time: 12:15 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>

<g:form method="post">
<g:actionSubmit value="run Query" action="runQuery" />
</g:form>

<g:form method="post">
<g:actionSubmit value="run DCIM Update for Devices" action="updatePhysicalDevicesWithDCIM"/>
</g:form>

<g:form method="post">
<g:actionSubmit value="run DCIM Update for DataCenters" action="updateDataCentersWithDCIM"/>
</g:form>

<g:form method="post">
    <g:actionSubmit value="run DCIM Update for Racks" action="updateRackAttributesWithDCIM"/>
</g:form>

<g:form method="post">
    <g:actionSubmit value="import Physical Servers from production" action="importServersFromProduction"/>
</g:form>

<g:form method="post">
    <g:actionSubmit value="fill slots" action="updateOccupiedRackSlots"/>
</g:form>

<div>
<h1>SSH Config:</h1>
<h2>${ak}</h2>

<h1>Returned:</h1>
    <h3>
    datacenters: ${datacenters}<br>
    cabinets: ${cabinets}<br>
    devices: ${devices}
    </h3>
    <br>

</div>



</body>
</html>