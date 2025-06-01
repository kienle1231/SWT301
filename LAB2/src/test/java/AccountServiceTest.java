import Kien.AccountService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountServiceTest {

    private final AccountService service = new AccountService();

    @ParameterizedTest
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    void testRegisterAccount_WriteResultToFile(String username, String password, String email, boolean expected) throws IOException {
        boolean actualResult = service.registerAccount(username, password, email);


        try (PrintWriter writer = new PrintWriter(new FileWriter("test-result.csv", true))) {
            writer.printf("%s,%s,%s,%s,%s,%s%n",
                    username, password, email, expected, actualResult,
                    expected == actualResult ? "PASS" : "FAIL");
        }

        assertEquals(expected, actualResult);
    }
}
