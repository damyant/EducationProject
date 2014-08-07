<div>
    <div style="border: 1px solid; padding: 10px;">
       <div class="university-size-full-1-1" style="margin-bottom: 25px;"> <div style="float: right"><lable>Challan. No. </lable><label >${misFeeObject?.challanNo}</label></div>
       <div class="university-clear-both"></div>
       </div>
        <p style="width:100%; margin-left: 3px; margin-top: -6px; text-align: center;text-transform: uppercase;font-size: 14px">

        <div>State Bank of India</div>

        <div>Gauhati University Branch (CODE-4332)</div>

        <div>A/C No. 57846586846</div>

        <div>INSTITUTE OF DISTANCE AND OPEN LEARNING</div>

        <div>GAUHATI UNIVERSITY</div>
    </p>
        <div style="clear: both; margin-bottom: 10px;"></div>
        <table width="100%" class="university-table-1-2">
            <tr><td><lable>Name:</lable></td><td><label>${misFeeObject?.student?.firstName} ${misFeeObject?.student?.middleName} ${misFeeObject?.student?.lastName} </label></td></tr>
            <tr><td><lable>Roll No:</lable></td><td><label>${misFeeObject?.student?.rollNo}</label></td></tr>
            <tr><td>Type Of Fee:</td><td>${misFeeObject?.student?.programDetail[0]?.courseName} ${misFeeObject?.feeType?.type}</td></tr>
            <tr><td>Term/ Semester</td><td>${misFeeObject.semesterValue}</td></tr>
            <tr><td><lable>Amount:</lable></td><td><label>${amount}</label></td></tr>

            <tr><td style="vertical-align: bottom;">${new Date()}</td><td
                    style="vertical-align: bottom;height: 63px;"><div
                        style="width: 100%;text-align: right; bottom: 2px;">Cashier's Signature</div></td></tr>
        </table>
    </div>
</div>



