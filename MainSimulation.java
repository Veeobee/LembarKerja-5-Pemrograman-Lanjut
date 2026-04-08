package mediaguard;

import mediaguard.gateway.IntegrationGateway;
import mediaguard.model.PatientProfileV1;
import mediaguard.model.PatientProfileV2;
import mediaguard.response.SecureResponse;

public class MainSimulation {
    static final int AKSES_PUBLIC = 1;
    static final int AKSES_SECRET = 3;

    public static void main(String[] args) {

        printBanner();

        PatientProfileV1[] listV1 = PatientProfileV1.getSampleData();
        PatientProfileV2[] listV2 = PatientProfileV2.getSampleData();

        // ================================================================
        //  BAGIAN 1 — PatientProfile V1, Akses RENDAH (PUBLIC = 1)
        // ================================================================
        printSectionHeader("BAGIAN 1", "PatientProfile V1", "AKSES RENDAH (PUBLIC - Level 1)");
        for (PatientProfileV1 p : listV1) {
            IntegrationGateway<PatientProfileV1> gw = new IntegrationGateway<>(p);
            SecureResponse<PatientProfileV1> resp   = gw.fetchData(p.getPatientId(), AKSES_PUBLIC);
            printResult(resp, p.getPatientId(), AKSES_PUBLIC);
        }

        // ================================================================
        //  BAGIAN 2 — PatientProfile V1, Akses TINGGI (SECRET = 3)
        // ================================================================
        printSectionHeader("BAGIAN 2", "PatientProfile V1", "AKSES TINGGI (SECRET - Level 3)");
        for (PatientProfileV1 p : PatientProfileV1.getSampleData()) {
            IntegrationGateway<PatientProfileV1> gw = new IntegrationGateway<>(p);
            SecureResponse<PatientProfileV1> resp   = gw.fetchData(p.getPatientId(), AKSES_SECRET);
            printResult(resp, p.getPatientId(), AKSES_SECRET);
        }

        // ================================================================
        //  BAGIAN 3 — PatientProfile V2, Akses RENDAH (PUBLIC = 1)
        // ================================================================
        printSectionHeader("BAGIAN 3", "PatientProfile V2", "AKSES RENDAH (PUBLIC - Level 1)");
        for (PatientProfileV2 p : listV2) {
            IntegrationGateway<PatientProfileV2> gw = new IntegrationGateway<>(p);
            SecureResponse<PatientProfileV2> resp   = gw.fetchData(p.getPatientId(), AKSES_PUBLIC);
            printResult(resp, p.getPatientId(), AKSES_PUBLIC);
        }

        // ================================================================
        //  BAGIAN 4 — PatientProfile V2, Akses TINGGI (SECRET = 3)
        // ================================================================
        printSectionHeader("BAGIAN 4", "PatientProfile V2", "AKSES TINGGI (SECRET - Level 3)");
        for (PatientProfileV2 p : PatientProfileV2.getSampleData()) {
            IntegrationGateway<PatientProfileV2> gw = new IntegrationGateway<>(p);
            SecureResponse<PatientProfileV2> resp   = gw.fetchData(p.getPatientId(), AKSES_SECRET);
            printResult(resp, p.getPatientId(), AKSES_SECRET);
        }

        // ================================================================
        //  BAGIAN 5 — Uji validasi ID tidak valid
        // ================================================================
        printSectionHeader("BAGIAN 5", "Uji Validasi", "ID PASIEN TIDAK VALID");
        PatientProfileV1 dummy = PatientProfileV1.getSampleData()[0];
        IntegrationGateway<PatientProfileV1> gwInvalid = new IntegrationGateway<>(dummy);
        SecureResponse<PatientProfileV1> invalid = gwInvalid.fetchData("P1-999", AKSES_SECRET);
        printResult(invalid, "P1-999", AKSES_SECRET);

        printFooter();
    }

    static <T extends mediaguard.interfaces.MedicalRecord & mediaguard.interfaces.Confidential>
    void printResult(SecureResponse<T> resp, String requestedId, int clearance) {
        System.out.println("  [Request] ID: " + requestedId + " | Clearance Level: " + clearance);
        resp.printResponse();
        System.out.println("  " + "-".repeat(100));
        System.out.println();
    }

    static void printSectionHeader(String bag, String type, String desc) {
        System.out.println();
        System.out.println("=".repeat(112));
        System.out.printf("  %s | %s | %s%n", bag, type, desc);
        System.out.println("=".repeat(112));
        System.out.println();
    }

    static void printBanner() {
        System.out.println("=".repeat(112));
        System.out.println("  MediGuard Integration Gateway");
        System.out.println("  LK05 - Generic Class | PT Asuransi BPJS");
        System.out.println("  Security Level: 1=PUBLIC  2=RESTRICTED  3=SECRET");
        System.out.println("=".repeat(112));
    }

    static void printFooter() {
        System.out.println();
        System.out.println("=".repeat(112));
        System.out.println("  Simulasi selesai - Semua data ditangani oleh satu Gateway generik tanpa instanceof!");
        System.out.println("=".repeat(112));
    }
}
