package examinationproject

class AdmitCard {
    ExaminationVenue examVenue
    byte[] signatureImg

    static constraints = {
    }
    static mapping ={
        signatureImg column: "signatureImg", sqlType: "blob"
        examVenue column: "examVenue"
    }
}
