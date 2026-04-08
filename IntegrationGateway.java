package mediaguard.gateway;

import mediaguard.interfaces.Confidential;
import mediaguard.interfaces.MedicalRecord;
import mediaguard.interfaces.Versioned;
import mediaguard.response.SecureResponse;


public class IntegrationGateway<T extends MedicalRecord & Versioned & Confidential> {
    private final T  mockDatabaseRecord;

    public IntegrationGateway(T record){
        this.mockDatabaseRecord= record;
    }
    public SecureResponse<T> fetchData(String patientId, int clearanceLevel){
        if(!mockDatabaseRecord.getPatientId().equals(patientId)) {
            return new SecureResponse<>(false, mockDatabaseRecord, "ERROR: Patient ID '" + patientId + "' tidak ditemukan.", clearanceLevel);
        }
        int dataSecurityLevel = mockDatabaseRecord.getSecurityLevel();
        String warningMsg = null;

        if(clearanceLevel < dataSecurityLevel){
            mockDatabaseRecord.maskSensitiveData();
            warningMsg = String.format("PERINGATAN: Akses level %d < level data %s (%d). " +
                            "Data sensitif telah disembunyikan secara otomatis.",
                    clearanceLevel, mockDatabaseRecord.getSecurityLevelLabel(), dataSecurityLevel);
        }
        return new SecureResponse<>(true, mockDatabaseRecord,warningMsg,clearanceLevel);
    }
}

