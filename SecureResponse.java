package mediaguard.response;

import mediaguard.interfaces.Confidential;
import mediaguard.interfaces.MedicalRecord;

// kelas ini berfungsi sebagai pembungkus (wrapper) dari data yang dikembalikan oleh gateway
// menggunakan generic supaya bisa dipakai untuk semua jenis data medis
// constraint T extends MedicalRecord & Confidential artinya data yang dibungkus
// harus merupakan data medis sekaligus punya level keamanan
public class SecureResponse<T extends MedicalRecord & Confidential> {
    private final boolean success;
    private final T data;
    private final String warningMessage;
    private final int requestedClearance;

    public SecureResponse( boolean success, T data, String warningMessage, int requestedClearance){
        this.success = success;
        this.data = data;
        this.warningMessage = warningMessage;
        this.requestedClearance = requestedClearance;
    }
    public boolean isSuccess(){
        return success;
    }
    public T getData(){
        return data;
    }
    public String getWarningMessage(){
        return warningMessage;
    }
    public int getRequestedClearance(){
        return requestedClearance;
    }
    public void printResponse(){
        System.out.println(" Status : " + (success ? "BERHASIL" : "GAGAL"));
        System.out.println(" Warning : " + (warningMessage != null ? warningMessage : "-"));
        System.out.println(" Data : " + data.toString());
    }
}
