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
            <a href="#tabs-items">Purchase Items</a>
        </li>
        %{--
        <li>
            <a href="#tabs-replacements">Replacements</a>
        </li> --}%
        <li>
            <a href="#tabs-support-staff" onclick="resizeSupportGrid()"><g:message code="purchase.supportStaff.label" default="Support Staff" /></a>
        </li>
    </ul>
    <div id="tabs-items">
        <table id="itemGrid"></table>
        <div id="itemGridPager"></div>
    </div>
    <div id="tabs-support-staff">
        <table id="supportGrid"></table>
        <div id="supportPager"></div>
    </div>
</div>