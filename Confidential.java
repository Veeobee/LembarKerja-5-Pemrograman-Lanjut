package mediaguard.interfaces;

// interface ini dipakai untuk data yang bersifat rahasia dan butuh proteksi
// ada 3 level keamanan:
// level 1 = PUBLIC (bisa diakses siapa saja)
// level 2 = RESTRICTED (hanya dokter umum)
// level 3 = SECRET (hanya dokter spesialis atau admin)

public interface Confidential {
    int getSecurityLevel();
    void maskSensitiveData(); // method ini yang akan menyembunyikan data sensitif
    String getSecurityLevelLabel();
}
