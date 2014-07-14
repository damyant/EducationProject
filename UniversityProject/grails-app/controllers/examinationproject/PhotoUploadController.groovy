package examinationproject

class PhotoUploadController {
    def photoUploadService
    def photoUpload() {
        def appNo=[]
        def studentListInst=Student.findAllByStudentImageIsNull()
        def sessionList = Student.createCriteria().list {
            projections {
                distinct("registrationYear")
            }
        }

        def year = new Date().format("yyyy")
        if (sessionList.size() == 0) {
            sessionList << Integer.parseInt(year)
        }
        sessionList << Integer.parseInt(year) + 1
        [studentListInst:studentListInst,sessionList:sessionList]
    }
    def uploadPhoto(){
        println(params)
        def result=photoUploadService.saveBulkPhoto(params)
        if(result.status){
            if(result.dirStatus){
                if(result.rollNo){
                    flash.message = "Uploaded for Roll Number "+result.rollNo
                }
                else{
                    flash.message = "Unable to find any new Image to Upload."
                }
            }
            else{
                flash.message = "Unable to find Session Directory in the Provided Path."
            }
        }
        else{
            flash.message = "You have Selected wrong Session or Photos belongs to another Session."
        }
        redirect(controller: 'photoUpload', action: 'photoUpload')
    }
}
