import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringCalculatorTest {

    private static final String COMMA = ",";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String PREFIX_FOR_SEPARATOR = "//";

    private StringCalculator stringCalculator;
    private ValidNumbersTest validNumbersTest;
    @BeforeEach
    void setUp() {
        validNumbersTest = new ValidNumbersTest();
    }

    @ParameterizedTest
    @CsvSource({"2,2","4,4"})
    void shouldSendSameValueIfOnlyOneNumber(String input, String expected) {
        // Given
        stringCalculator = new StringCalculator(List.of(COMMA), validNumbersTest);

        // When
        int result = stringCalculator.add(input);

        // Assert
        assertEquals(Integer.parseInt(expected), result);
    }


    @Test
    void shouldSend0IfEmptyString() {
        // Given
        stringCalculator = new StringCalculator(List.of(COMMA), validNumbersTest);

        // When
        int result = stringCalculator.add("");

        // Assert
        assertEquals(0, result);
    }

    @Test
    void shouldSend0IfNoNumberIsPresent() {
        // Given
        stringCalculator = new StringCalculator(List.of(COMMA), validNumbersTest);

        // When
        int result = stringCalculator.add("a");

        // Assert
        assertEquals(0, result);
    }

    @Test
    void shouldSend4IfNumber2AndNumber2ArePresents() {
        // Given
        stringCalculator = new StringCalculator(List.of(COMMA), validNumbersTest);

        // When
        int result = stringCalculator.add("2,2");

        // Assert
        assertEquals(4, result);
    }

    @Test
    void shouldSend5IfNumber3AndNumber2ArePresents() {
        // Given
        stringCalculator = new StringCalculator(List.of(COMMA), validNumbersTest);

        // When
        int result = stringCalculator.add("3,2");

        // Assert
        assertEquals(5, result);
    }

    @Test
    void shouldSend32IfNumber3AndNumber2AndNumber27ArePresentsWithAComma() {
        // Given
        stringCalculator = new StringCalculator(List.of(COMMA), validNumbersTest);

        // When
        int result = stringCalculator.add("3,2,27");

        // Assert
        assertEquals(32, result);
    }

    @Test
    void shouldSend32IfNumber3AndNumber2AndNumber27ArePresentsWithANewLine() {
        // Given
        stringCalculator = new StringCalculator(List.of(LINE_SEPARATOR), validNumbersTest);

        // When
        int result = stringCalculator.add("3"+LINE_SEPARATOR+"2"+LINE_SEPARATOR+"27");

        // Assert
        assertEquals(32, result);
    }

    @Test
    void shouldSend3IfOnlyOneNumberIsPresent() {
        // Given
        stringCalculator = new StringCalculator(List.of(LINE_SEPARATOR), validNumbersTest);

        // When
        int result = stringCalculator.add("3"+LINE_SEPARATOR+"A"+LINE_SEPARATOR+"B");

        // Assert
        assertEquals(3, result);
    }

    @Test
    void shouldSend32IfNumber3AndNumber2AndNumber27ArePresentsWithaCommaAndANewLine() {
        // Given
        stringCalculator = new StringCalculator(List.of(LINE_SEPARATOR, COMMA), validNumbersTest);

        // When
        int result = stringCalculator.add("3"+LINE_SEPARATOR+"2"+LINE_SEPARATOR+"27");

        // Assert
        assertEquals(32, result);
    }

    @Test
    void shouldSend6WithANewSeparator() {
        // Given
        stringCalculator = new StringCalculator(List.of(LINE_SEPARATOR, COMMA), validNumbersTest);

        // When
        int result = stringCalculator.add(PREFIX_FOR_SEPARATOR+";"+LINE_SEPARATOR+1+";"+2+";"+3);

        // Assert
        assertEquals(6, result);
    }

    @Test
    void shouldSend6WithANewSeparatorFromMinusType() {
        // Given
        stringCalculator = new StringCalculator(List.of(LINE_SEPARATOR, COMMA), validNumbersTest);

        // When
        int result = stringCalculator.add(PREFIX_FOR_SEPARATOR+"-"+LINE_SEPARATOR+1+"-"+2+"-"+3);

        // Assert
        assertEquals(6, result);
    }

    @Test
    void shouldThrowUnauthorizedNegativesNumbersExceptionIfANegativeNumberIsPresent() {
        // Given
        stringCalculator = new StringCalculator(List.of(LINE_SEPARATOR, COMMA), validNumbersTest);

        // When // Assert
        assertThrows(UnauthorizedNegativesNumbersException.class, () -> {
            stringCalculator.add("-1");
        });
    }

    @Test
    void shouldThrowUnauthorizedNegativesNumbersExceptionIfANegativeNumberIsPresentAmongPositiveNumbers() {
        // Given
        stringCalculator = new StringCalculator(List.of(LINE_SEPARATOR, COMMA), validNumbersTest);

        // When // Assert
        assertThrows(UnauthorizedNegativesNumbersException.class, () -> {
            stringCalculator.add("3"+LINE_SEPARATOR+"-2"+LINE_SEPARATOR+"27");
        });
    }

    @Test
    void shouldThrowUnauthorizedNegativesNumbersExceptionIfNegativesNumbersArePresentsAndShowThem() {
        // Given
        stringCalculator = new StringCalculator(List.of(LINE_SEPARATOR, COMMA), validNumbersTest);

        // When // Assert
        try {
            stringCalculator.add("3"+LINE_SEPARATOR+"-2"+LINE_SEPARATOR+"27"+LINE_SEPARATOR+"-4");
        } catch (UnauthorizedNegativesNumbersException e) {
            assertEquals("Nombres négatifs présents : -2 -4", e.getMessage());
        }

    }
}
