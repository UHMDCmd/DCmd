<%--
  Created by IntelliJ IDEA.
  User: Ben
  Date: 2/27/2015
  Time: 2:00 PM
--%>
<div id="tabs" style="margin-top: 5px">

    <ul>
        %{--
        <li>
            <a href="#tabs-capacity">Capacities</a>
        </li>
        --}%
        <li>
            <a href="#tabs-hosts">Hosts</a>
        </li>
        %{--
        <li>
            <a href="#tabs-replacements">Replacements</a>
        </li> --}%
        <li>
            <a href="#tabs-support-staff"><g:message code="asset.supportStaff.label" default="Support Staff" /></a>
        </li>
        <li>
            <a href="#tabs-notes"><g:message code="notes.label" default="Notes" /></a>
        </li>
    </ul>
    <div id="tabs-hosts">
        <g:render template="hostGrid-local"/>
    </div>
    <div id="tabs-support-staff"></div>
    <div id="tabs-notes"></div>
</div>