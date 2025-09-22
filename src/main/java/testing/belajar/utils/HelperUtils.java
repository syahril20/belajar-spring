package testing.belajar.utils;

public class HelperUtils {

    private HelperUtils() {
        // mencegah instansiasi
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Mengecek apakah string null atau kosong
     * @param str String yang dicek
     * @return true jika null atau kosong, false jika ada isinya
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Mengecek apakah string null atau blank (hanya spasi)
     * @param str String yang dicek
     * @return true jika null atau blank, false jika ada isinya
     */
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
