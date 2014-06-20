/**
 * Created by damyant on 6/10/14.
 */

var districtId =0;
var bankId=0;
var branchId=0;
$(document).ready(function () {


});

function updateBankType(id){
    bankId = id
    window.location.href = '/UniversityProject/bank/editBank?bankId=' + bankId;
}
function deleteBankType(id){
    bankId = id
//    alert(bankId)
    window.location.href = '/UniversityProject/bank/deleteBank?bankId=' + bankId;
}

function updateBranch(id){
    branchId = id
//    alert(branchId)
    window.location.href = '/UniversityProject/branch/editBranch?branchId=' + branchId;
}

function deleteBranch(id){
    branchId = id
    window.location.href = '/UniversityProject/branch/deleteBranch?branchId=' + branchId;
}

function updateDistrict(id){
    districtId = id
    window.location.href = '/UniversityProject/district/editDistrict?districtId=' + districtId;
}
function deleteDistrict(id){
    districtId = id
//    alert(bankId)
    window.location.href = '/UniversityProject/district/deleteDistrict?districtId=' + districtId;
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