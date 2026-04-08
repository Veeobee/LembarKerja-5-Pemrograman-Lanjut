package mediaguard.interfaces;

// interface ini adalah interface paling dasar yang harus dimiliki semua data medis
// setiap data medis wajib punya patientId dan nama pasien

public interface MedicalRecord {
    String getPatientId();
    String getName();
}
