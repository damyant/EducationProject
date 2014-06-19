<div>
    <div style="width: 100%; margin: 6px auto; display: inline-block;text-align: center;">----------------------------------------BANK COPY----------------------------------------</div>

    <div style="border: 1px solid; padding: 10px;">
       <div class="university-size-full-1-1" style="margin-bottom: 25px;"> <div style="float: right"><lable>Challan. No. </lable><label >${student?.challanNo}</label></div>
       <div class="university-clear-both"></div>
       </div>
        <div style="clear: both; margin-bottom: 1px;width:100%;">&nbsp;</div>
        <p style="width:100%; margin-left: 3px; margin-top: 4px; text-align: center;text-transform: uppercase;font-size: 13px">
         <div  style="width:100%;">&nbsp;</div>
        <div style="width:100%;">State Bank of India</div>

        <div style="width:100%;">Gauhati University Branch (CODE-<g:message code="default.Bank.code"/>)</div>

        <div style="width:100%;">A/C No.  <g:message code="default.Bank.AcNo"/></div>

        <div style="width:100%;">INSTITUTE OF DISTANCE AND OPEN LEARNING</div>

        <div style="width:100%;">GAUHATI UNIVERSITY</div>
    </p>
        <div style="clear: both; margin-bottom: 10px;"></div>
        <table width="100%" class="university-table-1-2" style="font-size: 13px;">
            <tr><td><lable>Name:</lable></td><td><label>${student?.firstName} ${student?.middleName} ${student?.lastName} </label></td></tr>
            <tr><td><lable>Roll No:</lable></td><td><label>${student?.rollNo}</label></td></tr>
            <tr><td>Type Of Fee:</td><td>${programFee?.feeSession?.programDetailId?.courseName} ${feeType?.type}</td></tr>
            <tr><td><lable>Amount:</lable></td><td><label>${programFeeAmount}</label></td></tr>

            <tr><td style="vertical-align: bottom;">${new Date()}</td><td
                    style="vertical-align: bottom;height: 63px;"><div
                        style="width: 100%;text-align: right; bottom: 2px;">Cashier's Signature</div></td></tr>
        </table>
    </div>

    <div style="width: 100%; margin: 6px auto; display: inline-block;text-align: center;">-----------------------------------UNIVERSITY COPY-----------------------------------</div>

    <div style="border: 1px solid; padding: 10px;">
        <div class="university-size-full-1-1" style="margin-bottom: 25px;"> <div style="float: right"><lable>Challan. No. </lable><label >${student?.challanNo}</label></div>
            <div class="university-clear-both"></div>
        </div>

        <p style="width:100%; margin-left: 3px; margin-top: -6px; text-align: center;text-transform: uppercase;font-size: 13px">

        <div style="width:100%;">State Bank of India</div>

        <div style="width:100%;">Gauhati University Branch (CODE-<g:message code="default.Bank.code"/>)</div>

        <div style="width:100%;">A/C No.  <g:message code="default.Bank.AcNo"/></div>

        <div style="width:100%;">INSTITUTE OF DISTANCE AND OPEN LEARNING</div>

        <div style="width:100%;">GAUHATI UNIVERSITY</div>
    </p>
        <div style="clear: both; margin-bottom: 10px;"></div>
        <table width="100%" class="university-table-1-2" style="font-size: 13px;">
            <tr><td><lable>Name:</lable></td><td><label>${student?.firstName}  ${student?.middleName} ${student?.lastName}</label></td></tr>
            <tr><td><lable>Roll No:</lable></td><td><label>${student?.rollNo}</label></td></tr>
            <tr><td>Type Of Fee:</td><td>${programFee?.feeSession?.programDetailId?.courseName}${feeType?.type}</td></tr>
            <tr><td><lable>Amount:</lable></td><td><label>${programFeeAmount}</label>
            </td></tr>

            <tr><td style="vertical-align: bottom;">${new Date()}</td><td
                    style="vertical-align: bottom;height: 63px;"><div
                        style="width: 100%;text-align: right; bottom: 2px;">Cashier's Signature</div></td></tr>
        </table>
    </div>

    <div style="width: 100%; margin: 6px auto; display: inline-block;text-align: center;">--------------------------------------STUDENT COPY--------------------------------------</div>

    <div style="border: 1px solid; padding: 10px;">
        <div class="university-size-full-1-1" style="margin-bottom: 25px;"> <div style="float: right"><lable>Challan. No. </lable><label >${student?.challanNo}</label></div>
            <div class="university-clear-both"></div>
        </div>
        <p style="width:100%; margin-left: 3px; margin-top: -6px; text-align: center;text-transform: uppercase;font-size: 13px">

        <div style="width:100%;">State Bank of India</div>

        <div style="width:100%;">Gauhati University Branch (CODE-<g:message code="default.Bank.code"/>)</div>

        <div style="width:100%;">A/C No.  <g:message code="default.Bank.AcNo"/></div>

        <div style="width:100%;">INSTITUTE OF DISTANCE AND OPEN LEARNING</div>

        <div style="width:100%;">GAUHATI UNIVERSITY</div>
    </p>
        <div style="clear: both; margin-bottom: 10px;"></div>
        <table width="100%" class="university-table-1-2" style="font-size: 13px;">
            <tr><td><lable>Name:</lable></td><td><label>${student?.firstName}  ${student?.middleName} ${student?.lastName}</label></td></tr>
            <tr><td><lable>Roll No:</lable></td><td><label>${student?.rollNo}</label></td></tr>
            <tr><td>Type Of Fee:</td><td>${programFee?.feeSession?.programDetailId?.courseName} ${feeType?.type}</td></tr>
            <tr><td><lable>Amount:</lable></td><td><label>${programFeeAmount}</label></td></tr>

            <tr><td style="vertical-align: bottom;">${new Date()}</td><td
                    style="vertical-align: bottom;height: 63px;"><div
                        style="width: 100%;text-align: right; bottom: 2px;">Cashier's Signature</div></td></tr>
        </table>
    </div>
</div>