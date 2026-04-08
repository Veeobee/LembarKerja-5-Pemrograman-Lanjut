package mediaguard.model;

import mediaguard.interfaces.Confidential;
import mediaguard.interfaces.MedicalRecord;
import mediaguard.interfaces.Versioned;

// kelas ini merepresentasikan data pasien versi lama (V1)
// field yang ada: id pasien, nama, NIK, diagnosis, golongan darah, dan level keamanan
// NIK dan diagnosis adalah data sensitif yang harus disembunyikan jika akses tidak cukup

public class PatientProfileV1 implements MedicalRecord, Versioned, Confidential {
    private String patientId;
    private String name;
    private String nik; // ini data sensitif, tidak boleh sembarangan ditampilkan
    private String bloodType;
    private String diagnosis; // ini juga data sensitif, hanya dokter tertentu yang boleh lihat
    private int securityLevel;
    private boolean masked = false; // penanda apakah data sudah di-mask atau belum

    public PatientProfileV1(String patientId, String name, String nik, String diagnosis, String bloodType, int securityLevel) {
        this.patientId = patientId;
        this.name = name;
        this.nik = nik;
        this.bloodType = bloodType;
        this.diagnosis = diagnosis;
        this.securityLevel = securityLevel;
    }

    // implementasi dari interface Confidential
    @Override
    public int getSecurityLevel() {
        return securityLevel;
    }


    // method ini yang akan dipanggil oleh gateway saat akses tidak mencukupi
    // logika masking ada di sini, bukan di gateway, supaya gateway tidak perlu tahu
    // data ini tipe apa (prinsip Tell Don't Ask)
    @Override
    public void maskSensitiveData() {
        if (!masked) {
            this.nik = "******";
            this.diagnosis = "[DIAGNOSIS DISEMBUNYIKAN}";
            masked = true;
        }
    }


    // implementasi dari interface MedicalRecord
    @Override
    public String getPatientId() {
        return patientId;
    }
    @Override
    public String getName() {
        return name;
    }


    // implementasi dari interface Versioned, versi ini selalu return 1
    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public String getSecurityLevelLabel() {
        switch (securityLevel) {
            case 1:
                return "PUBLIC";
            case 2:
                return "RESTRICTED";
            case 3:
                return "SECRET";
            default:
                return "UNKWON";
        }
    }

    public String getNik() {
        return nik;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getBloodType() {
        return bloodType;
    }

    public boolean isMasked() {
        return masked;
    }

    @Override
    public String toString() {
        return String.format(
                "[V1] ID=%-8s | Nama=%-20s | NIK=%-18s | Gol.Darah=%-3s | Diagnosis=%-30s | Level=%s",
                patientId, name, nik, bloodType, diagnosis, getSecurityLevelLabel()
        );
    }

    public static PatientProfileV1[] getSampleData() {
        return new PatientProfileV1[]{
                new PatientProfileV1("P1-001", "Budi Santoso", "3578010101800001", "Hipertensi Primer", "A+", 2),
                new PatientProfileV1("P1-002", "Siti Rahayu", "3578014505820002", "Diabetes Melitus Tipe 2", "B+", 2),
                new PatientProfileV1("P1-003", "Ahmad Fauzi", "3578011212750003", "Gagal Ginjal Kronik", "O+", 3),
                new PatientProfileV1("P1-004", "Dewi Lestari", "3578016706900004", "Asma Bronkial", "AB+", 1),
                new PatientProfileV1("P1-005", "Eko Prasetyo", "3578010303850005", "Hepatitis B Kronis", "A-", 3),
                new PatientProfileV1("P1-006", "Fitri Handayani", "3578015809780006", "Anemia Defisiensi Besi", "B-", 2),
                new PatientProfileV1("P1-007", "Gunawan Wibowo", "3578011507720007", "Tuberkulosis Paru", "O-", 2),
                new PatientProfileV1("P1-008", "Heni Kusumawati", "3578012002880008", "Kanker Serviks Stadium II", "A+", 3),
                new PatientProfileV1("P1-009", "Irfan Maulana", "3578010904950009", "Migrain Kronis", "B+", 1),
                new PatientProfileV1("P1-010", "Juwita Permatasari", "3578016101830010", "Lupus Eritematosus Sistemik", "AB-", 3),
        };
    }
}
