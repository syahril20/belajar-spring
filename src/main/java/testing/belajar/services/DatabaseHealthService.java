package testing.belajar.services;

import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Connection;

@Service
public class DatabaseHealthService {

    private final DataSource dataSource;

    public DatabaseHealthService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean isDatabaseUp() {
        try (Connection conn = dataSource.getConnection()) {
            return conn.isValid(1); // cek validitas dalam 1 detik
        } catch (Exception e) {
            return false;
        }
    }
}
