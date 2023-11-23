package tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author guho
 */
public class CalculatorService {

    public static String getFromScanner(final String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        String input = scanner.nextLine();
        return input;
    }

    public static Integer getResult(final String input) {

        final List<String> inputs = extractInput(input);

        Integer firstNumber = Integer.parseInt(inputs.get(0));
        Integer secondNumber = Integer.parseInt(inputs.get(1));
        char operation = inputs.get(2).charAt(0);

        switch (operation) {
            case '+': return firstNumber + secondNumber;
            case '-': return firstNumber - secondNumber;
            case '*': return firstNumber * secondNumber;
            case '/': return firstNumber / secondNumber;
            default: return 0;
        }
    }

    public static List<String> extractInput(final String input) {
        return Arrays.stream(input.split(":"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

}
