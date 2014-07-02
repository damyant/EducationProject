package examinationproject

class AdmitCard {
    byte[] signatureImg

    static constraints = {
    }
    static mapping ={
        signatureImg column: "signatureImg", sqlType: "blob"
    }
}
