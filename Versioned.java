package mediaguard.interfaces;

// interface ini digunakan untuk menandai bahwa suatu data memiliki versi
// gunanya supaya kita bisa tahu data ini versi berapa (V1 atau V2)

public interface Versioned {
    int getVersion();
}
