package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author guho
 */
public class Service {

    public static String getFromScanner(final String message) {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String getStringFromBytes(final InputStream inputStream) throws IOException {
        byte[] bytes = new byte[1000];
        int bytesRead = inputStream.read(bytes);
        return new String(bytes, 0, bytesRead, StandardCharsets.UTF_8);
    }

    public static List<String> extractInput(final String input) {
        return Arrays.stream(input.split(":"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

}
