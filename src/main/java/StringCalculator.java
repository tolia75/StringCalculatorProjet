import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringCalculator {

    private ValidNumbersTest validNumbersTest;

    private static final String PREFIX_FOR_NEW_SEPARATOR = "//";
    private static final String SUFFIX_FOR_NEW_SEPARATOR = System.lineSeparator();

    private List<String> separators = new ArrayList<>();

    public StringCalculator(List<String> separators, ValidNumbersTest validNumbersTest) {
        this.separators.addAll(separators);
        this.validNumbersTest = validNumbersTest;
    }

    public int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        } else if (hasANewSeparator(numbers)) {
            prepareNewSeparator(numbers);
            numbers = prepareNumbersWithNewSeparator(numbers);
        }

        String regexForSeparators = getRegexForSeparators();
        List<Integer> numbersSecured = getSecuredNumbers(numbers, regexForSeparators);
        return getSumFromSecuredNumbers(numbersSecured);
    }

    private String prepareNumbersWithNewSeparator(String numbers) {
        return numbers.replaceAll(PREFIX_FOR_NEW_SEPARATOR +this.separators.get(0)+ SUFFIX_FOR_NEW_SEPARATOR, "");
    }

    private void prepareNewSeparator(String numbers) {
        this.separators.clear();
        String newSeparator = numbers.substring(numbers.indexOf(PREFIX_FOR_NEW_SEPARATOR) + PREFIX_FOR_NEW_SEPARATOR.length(), numbers.indexOf(SUFFIX_FOR_NEW_SEPARATOR));
        this.separators.add(newSeparator);
    }

    private boolean hasANewSeparator(String numbers) {
        return numbers.startsWith(PREFIX_FOR_NEW_SEPARATOR);
    }

    private int getSumFromSecuredNumbers(List<Integer> numbersSecured) {
        return numbersSecured.stream().mapToInt(Integer::valueOf).sum();
    }

    private List<Integer> getSecuredNumbers(String numbers, String regex) {
        List<String> numbersPreSecured = Arrays.asList(numbers.split(regex));
        return this.validNumbersTest.checkNegativeNumbers(numbersPreSecured);
    }

    private String getRegexForSeparators() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        separators.forEach(stringBuilder::append);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
