package universityproject

import examinationproject.Student
import grails.transaction.Transactional

@Transactional
class PhotoUploadService {

    def serviceMethod() {

    }

    def saveBulkPhoto(params) {
        println("@@@@@@@@@@@@@@@@@@@@@"+params.admissionYear)
        def resultMap = [:]
        def rollNo = []
        def studentListInst = Student.findAllByRegistrationYearAndStudentImageIsNullAndReferenceNumber(params.admissionYear,0)
        def directryName = (studentListInst[0].applicationNo).substring(0, 4)
        if (directryName == params.admissionYear) {
            def dirPath = params.filePath + directryName + "/"
            File f = new File(dirPath);
            if (f.exists() && f.isDirectory()) {
                resultMap.dirStatus = true
                studentListInst.each {
                    def filePathJpg = dirPath + it.applicationNo.substring(4) + ".jpg"
                    def filePathPng = dirPath + it.applicationNo.substring(4) + ".png"
                    def filePathJpeg = dirPath + it.applicationNo.substring(4) + ".jpeg"
                    File jpg = new File(filePathJpg);
                    File png = new File(filePathPng);
                    File jpeg = new File(filePathJpeg);
                    if ((jpg.exists() && !jpg.isDirectory())) {
                        println("--------------"+filePathJpg)
                        def byte[] studentPhoto = new File(filePathJpg).bytes
                        it.studentImage = studentPhoto
                        rollNo << it.rollNo
                    }
                    else if((png.exists() && !png.isDirectory())){
                        println("--------------"+filePathPng)
                        def byte[] studentPhoto = new File(filePathPng).bytes
                        it.studentImage = studentPhoto
                        rollNo << it.rollNo
                    }
                    else if((jpeg.exists() && !jpeg.isDirectory())){
                        println("--------------"+filePathJpeg)
                        def byte[] studentPhoto = new File(filePathJpeg).bytes
                        it.studentImage = studentPhoto
                        rollNo << it.rollNo
                    }
                }
            }
            else{
                resultMap.dirStatus = false
            }
            resultMap.rollNo = rollNo
            resultMap.status = true
        } else {
            resultMap.status = false
        }
        return resultMap
    }
}
