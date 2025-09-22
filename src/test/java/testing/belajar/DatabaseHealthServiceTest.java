package testing.belajar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import testing.belajar.services.DatabaseHealthService;

import javax.sql.DataSource;
import java.sql.Connection;

@ExtendWith(MockitoExtension.class)
class DatabaseHealthServiceTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @InjectMocks
    private DatabaseHealthService databaseHealthService;

    @Test
    void isDatabaseUp_ReturnsTrue_WhenConnectionIsValid() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.isValid(1)).thenReturn(true);

        boolean result = databaseHealthService.isDatabaseUp();

        Assertions.assertTrue(result);
    }

    @Test
    void isDatabaseUp_ReturnsFalse_WhenConnectionIsNotValid() throws Exception {
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.isValid(1)).thenReturn(false);

        boolean result = databaseHealthService.isDatabaseUp();

        Assertions.assertFalse(result);
    }

    @Test
    void isDatabaseUp_ReturnsFalse_WhenConnectionThrowsException() throws Exception {
        Mockito.when(dataSource.getConnection()).thenThrow(new RuntimeException("Connection error"));

        boolean result = databaseHealthService.isDatabaseUp();

        Assertions.assertFalse(result);
    }
}
