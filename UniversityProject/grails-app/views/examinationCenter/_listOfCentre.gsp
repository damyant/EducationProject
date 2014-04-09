<g:if test="${edit}">
    <table id="centreListTable" class="inner university-table-1-7" style="width: 98%;margin: 3px auto;">
</g:if>
<g:else>
    <table id="centreListTable" class="inner university-table-1-6" style="width: 98%;margin: 3px auto;">
</g:else>
<thead class='thclass'>
<tr>
    <th>Name</th>
    <th>Address</th>
    <th>City</th>
    %{--<th>Total Rooms</th>--}%
    <th>Contact No</th>
    <th>Center Code</th>
    <th>Capacity</th>
    <g:if test="${edit}">
        <th></th>
    </g:if>
</tr>
</thead>
<tbody class='tdclass'>
<g:each in="${centreList}" status="i" var="examCentreListInstance">
    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}" style="cursor: pointer;">

        <td>${fieldValue(bean: examCentreListInstance, field: "name")}</td>

        <td>${fieldValue(bean: examCentreListInstance, field: "address")}</td>

        <td>${fieldValue(bean: examCentreListInstance, field: "city.cityName")}</td>
        %{--<td>${fieldValue(bean: examCentreListInstance, field: "rooms")}</td>--}%

        <td>${fieldValue(bean: examCentreListInstance, field: "contactNo")}</td>

        <td>${fieldValue(bean: examCentreListInstance, field: "centreCode")}</td>
        <td>${fieldValue(bean: examCentreListInstance, field: "capacity")}</td>
        <g:if test="${edit}">
            <td class='buttonCenter'>

                <g:link controller="examinationCenter" action="editExaminationCentre"
                        params="[id: examCentreListInstance.id]"><button value='update'
                                                                         class="university-button-small actionButton">Update</button></g:link>
                <g:link controller="examinationCenter" action="deleteCentre"
                        params="[id: examCentreListInstance.id]"><button value='Edit'
                                                                         class="university-button-small actionButton"
                                                                         onclick="return confirm('Are you sure you would like to delete this User?')">Delete</button></g:link>

            </td>
        </g:if>
    </tr>
</g:each>
</tbody>

</table>
