/**
 * Created by damyant on 6/10/14.
 */
$(document).ready(function () {


});

function updateBankType(id){
    var bankId = id
    window.location.href = '/UniversityProject/bank/editBank?bankId=' + bankId;
}
function deleteBankType(id){
    var bankId = id
    alert(bankId)
    window.location.href = '/UniversityProject/bank/deleteBank?bankId=' + bankId;
}

function updateBranch(id){
    var branchId = id
    alert(branchId)
    window.location.href = '/UniversityProject/branch/editBranch?branchId=' + branchId;
}

function deleteBranch(id){
    var branchId = id
    window.location.href = '/UniversityProject/branch/deleteBranch?branchId=' + branchId;
}
function loadBranch(t){
    var bank=$(t).val();
//    alert(bank)
    if(bank){
        $.ajax({
            type: "post",
            url: url('branch', 'getBranchList', ''),
            data: {bank: bank},
            success: function (data) {
                //document.location.reload();
                $("#branchList").empty().append('');
                $("#branchList").append('<tr><th>Branch Name</th><th></th></tr>')
               // alert(data.length)
                for (var i = 0; i < data.length; i++) {
                    $("#branchList tbody").append('<tr><td>' + data[i].branchLocation +'</td><td><input type="button" value="Update" onclick="updateBranch(' + data[i].id + ')"/><input type="button" value="Delete"  onclick="deleteBranch(' + data[i].id + ')"/> </td></tr>')
                }
            }
        });
    }
}