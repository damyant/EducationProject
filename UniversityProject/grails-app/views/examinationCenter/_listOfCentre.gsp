<table id="centreListTable" class="tableClass">

                    <thead class='thclass'>
                    <tr>
                        <th>Name</th>
                        <th>Address</th>
                        %{--<th>Total Rooms</th>--}%
                        <th>Contact No</th>
                        <th>Capacity</th>
                        <g:if test="${edit||delete}">
                        <th> </th>
                            </g:if>
                    </tr>
                    </thead>
                    <tbody class='tdclass'>
<g:each in="${centreList}" status="i" var="examCentreListInstance">
    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}"    style ="cursor: pointer;">

        <td>${fieldValue(bean: examCentreListInstance, field: "name")}</td>

        <td>${fieldValue(bean: examCentreListInstance, field: "address")}</td>

        %{--<td>${fieldValue(bean: examCentreListInstance, field: "rooms")}</td>--}%

        <td>${fieldValue(bean: examCentreListInstance, field: "contactNo")}</td>

        <td>${fieldValue(bean: examCentreListInstance, field: "capacity")}</td>
        <g:if test="${edit}">
        <td class='buttonCenter'>
            <g:link controller="examinationCenter" action="editExaminationCentre" params="[id: examCentreListInstance.id]"><button value='Edit' class="actionButton">Edit</button></g:link>
        </td>
            </g:if>
        <g:if test="${delete}">
            <td class='buttonCenter'>
                <g:link controller="examinationCenter" action="deleteCentre" params="[id: examCentreListInstance.id]"><button value='Edit' class="actionButton" onclick="return confirm('Are you sure you would like to delete this User?')">Delete</button></g:link>
            </td>
        </g:if>
    </tr>
</g:each>
</tbody>
</table>
