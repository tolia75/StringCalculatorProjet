import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ValidNumbersTest {

    private static final String NOMBRES_NEGATIFS_PRESENTS = "Nombres négatifs présents :";

    public ValidNumbersTest() {
    }

    public List<Integer> checkNegativeNumbers(List<String> numbers) {

        List<String> notEmptyStrings = numbers.stream()
                .filter(this::isNotEmptyString)
                .collect(Collectors.toList());

        List<Integer> negativeNumbers = notEmptyStrings.stream()
                .filter(this::isANumber)
                .map(Integer::parseInt)
                .filter(this::isNegativeNumber)
                .collect(Collectors.toList());

        if (!negativeNumbers.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            negativeNumbers.forEach(negativeNumber -> stringBuilder.append(" ").append(negativeNumber));
            throw new UnauthorizedNegativesNumbersException(NOMBRES_NEGATIFS_PRESENTS + stringBuilder.toString());
        }

        return notEmptyStrings.stream().filter(this::isANumber).map(Integer::parseInt).collect(Collectors.toList());
    }

    private boolean isNotEmptyString(String number) {
        return !number.equals("") && !number.isEmpty() && !number.isBlank();
    }

    private boolean isANumber(String numberToCheck) {
        Scanner scanner = new Scanner(numberToCheck);
        if(!scanner.hasNextInt(10)) {
            return false;
        }
        scanner.nextInt(10);
        return !scanner.hasNext();
    }

    private boolean isNegativeNumber(int number) {
        return number <= 0;
    }
}
