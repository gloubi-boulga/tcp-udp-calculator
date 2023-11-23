package tcp;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author guho
 */
public class CalculatorService {

    public static String getFromScanner() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Waiting for input : ");
        String input = scanner.nextLine();
        System.out.println(input);
        return input;
    }

    public static Integer getResult(final String input) {

        final List<String> inputs = extractInput(input);

        Integer firstNumber = Integer.parseInt(inputs.get(0));
        char operation = inputs.get(1).charAt(0);
        Integer secondNumber = Integer.parseInt(inputs.get(2));

        switch (operation) {
            case '+': return firstNumber + secondNumber;
            case '-': return firstNumber - secondNumber;
            case '*': return firstNumber * secondNumber;
            case '/': return firstNumber / secondNumber;
            default: return 0;
        }
    }

    public static List<String> extractInput(final String input) {
        return Arrays.stream(input.split("GHP:"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

}
