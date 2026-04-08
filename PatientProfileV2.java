package mediaguard.model;

import mediaguard.interfaces.Confidential;
import mediaguard.interfaces.MedicalRecord;
import mediaguard.interfaces.Versioned;

// kelas ini adalah versi terbaru dari data pasien (V2)
// perbedaan dari V1 adalah adanya tambahan field riwayat alergi, nomor BPJS, dan tanggal kunjungan terakhir
// nomor BPJS juga termasuk data sensitif yang harus ikut di-mask

public class PatientProfileV2 implements MedicalRecord, Versioned, Confidential {
    private String patientId;
    private String name;
    private String nik; // data sensitif
    private String diagnosis; // data sensitif
    private String bloodType;
    private String alergiHistory; // field baru di V2, berisi riwayat alergi pasien
    private String bpjsNumber; // field baru di V2, ini juga data sensitif
    private String lastVisitDate;
    private int securityLevel;
    private boolean masked = false;

    public PatientProfileV2(String patientId, String name, String nik, String diagnosis, String bloodType, String alergiHistory, String bpjsNumber, String lastVisitDate, int securityLevel) {
        this.patientId = patientId;
        this.name = name;
        this.nik = nik;
        this.diagnosis = diagnosis;
        this.bloodType = bloodType;
        this.alergiHistory = alergiHistory;
        this.bpjsNumber = bpjsNumber;
        this.lastVisitDate = lastVisitDate;
        this.securityLevel = securityLevel;

    }

    @Override
    public int getSecurityLevel() {
        return securityLevel;
    }

    @Override
    public void maskSensitiveData() {
        if (!masked) {
            this.nik = "****************";
            this.diagnosis = "[DIAGNOSIS DISEMBUNYIKAN]";
            this.bpjsNumber = "***-***-****";
            this.masked = true;
        }

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
                return "UNKNOWN";
        }
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getVersion() {
        return 2;
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

    public String getAlergiHistory() {
        return alergiHistory;
    }

    public String getBpjsNumber() {
        return bpjsNumber;
    }

    public String getLastVisitDate() {
        return lastVisitDate;
    }

    @Override
    public String toString() {
        return String.format(
                "[V2] ID=%-8s | Nama=%-20s | NIK=%-18s | Gol.Darah=%-3s | " +
                        "Alergi=%-25s | BPJS=%-14s | Kunjungan=%-12s | Diagnosis=%-30s | Level=%s",
                patientId, name, nik, bloodType, alergiHistory, bpjsNumber, lastVisitDate, diagnosis, getSecurityLevelLabel()
        );
    }

    public static PatientProfileV2[] getSampleData() {
        return new PatientProfileV2[]{
                new PatientProfileV2("P2-001", "Kurniawan Adi", "3578010201880011", "Stroke Iskemik", "A+", "Penisilin, Sulfa", "0001-2345-6789", "2025-11-10", 3),
                new PatientProfileV2("P2-002", "Lina Marlina", "3578014803920012", "Hipotiroidisme", "B+", "Tidak Ada", "0001-3456-7890", "2025-12-01", 2),
                new PatientProfileV2("P2-003", "Muhamad Rizki", "3578011509870013", "Skizofrenia Paranoid", "O+", "Aspirin", "0001-4567-8901", "2026-01-15", 3),
                new PatientProfileV2("P2-004", "Nanda Agustina", "3578016207910014", "Osteoporosis", "AB+", "Tidak Ada", "0001-5678-9012", "2026-01-20", 2),
                new PatientProfileV2("P2-005", "Oscar Hidayat", "3578010405930015", "Penyakit Jantung Koroner", "A-", "Lateks, Ibuprofen", "0001-6789-0123", "2026-02-03", 3),
                new PatientProfileV2("P2-006", "Putri Anggraini", "3578015710890016", "Endometriosis Stadium III", "B-", "Tidak Ada", "0001-7890-1234", "2026-02-14", 3),
                new PatientProfileV2("P2-007", "Qori Ramadhani", "3578011108960017", "PPOK (Paru Obstruksi)", "O-", "Codein", "0001-8901-2345", "2026-02-20", 2),
                new PatientProfileV2("P2-008", "Rendra Saputra", "3578010906800018", "HIV/AIDS Stadium 2", "A+", "Nevirapine", "0001-9012-3456", "2026-03-01", 3),
                new PatientProfileV2("P2-009", "Suci Wahyuni", "3578016501940019", "Thalassemia Mayor", "B+", "Tidak Ada", "0001-0123-4567", "2026-03-10", 1),
                new PatientProfileV2("P2-010", "Teguh Firmansyah", "3578011704780020", "Kanker Prostat Stadium III", "AB-", "Morfin, Tramadol", "0001-1234-5678", "2026-03-18", 3),
        };
    }
}
