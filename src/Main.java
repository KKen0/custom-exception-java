import java.util.List;
import java.util.Scanner;

/**
 * Name: Kaveen Amin
 * Course: COP 3330 Object-Oriented Programming
 * Date: 2025-09-19
 *
 * Program Objective:
 *   Part 1: Prompt for two integers, divide first by second, handle bad inputs with specific hints
 *           (letters or decimals) and handle ArithmeticException (divide by zero). Print result as
 *           a double with 2 decimal places.
 *   Part 2: After pressing Enter for username, validate immediately and list missing rules.
 *           Do the same for the password. When both are valid, create a PwdVerify object.
 *           Repeat until 4 valid accounts have been collected, then print them.
 *
 * Inputs:
 *   - Part 1: Two integers from the keyboard (num1 and num2).
 *   - Part 2: Username then Password, validated step-by-step with rule messages.
 *
 * Outputs:
 *   - Part 1: Either "a / b = x.xx" or specific guidance/errors for invalid inputs.
 *   - Part 2: Rule feedback for username/password until valid; then success messages and a final list.
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //Part 1: Division
        System.out.println("=== Part 1: Division with Exception Handling ===");
        int num1 = readIntWithHints(sc, "Enter first integer: ");
        int num2 = readIntWithHints(sc, "Enter second integer: ");

        try {
            int div = num1 / num2;                // this throws ArithmeticException if num2 == 0
            double result = (double) div;         // then convert to double
            System.out.printf("%d / %d = %.2f%n", num1, num2, result);
        } catch (ArithmeticException ex) {
            System.out.println("Error: " + ex.getMessage());
        }


        //Part 2: Immediate validation after each input
        System.out.println("\n=== Part 2: Username/Password Validation (Your Rules) ===");
        printYourRules();

        PwdVerify[] accounts = new PwdVerify[4];
        int count = 0;

        while (count < 4) {
            // Username input + immediate validation loop
            String username;
            while (true) {
                System.out.print("\nEnter username: ");
                username = sc.nextLine().trim();
                List<String> uErrors = PwdVerify.validateUsername(username);
                if (uErrors.isEmpty()) {
                    System.out.println("Username OK.");
                    break;
                } else {
                    System.out.println("Username issues:");
                    for (String msg : uErrors) System.out.println("   - " + msg);
                    System.out.println("Please try the username again.");
                }
            }

            // Password input + immediate validation loop
            String password;
            while (true) {
                System.out.print("Enter password: ");
                password = sc.nextLine();
                List<String> pErrors = PwdVerify.validatePassword(password);
                if (pErrors.isEmpty()) {
                    System.out.println("Password OK.");
                    break;
                } else {
                    System.out.println("Password issues:");
                    for (String msg : pErrors) System.out.println("   - " + msg);
                    System.out.println("Please try the password again.");
                }
            }

            // Safety: constructor still enforces rules and could throw, but should be OK now.
            try {
                PwdVerify pv = new PwdVerify(username, password);
                accounts[count] = pv;
                count++;
                System.out.println("Account accepted (" + count + "/4).");
            } catch (PwdVerifyException ex) {
                // Shouldn’t happen if step-by-step validation matches constructor rules,
                // but we’ll still show the message if it does.
                System.out.println(ex.getMessage());
            }
        }

        System.out.println("\n=== All Valid Accounts (4/4) ===");
        for (PwdVerify pv : accounts) System.out.println(pv);
        System.out.println("\nProgram finished. Goodbye!");
    }

    //Your rule display
    private static void printYourRules() {
        System.out.println("Username Rules:");
        System.out.println(" - Must be 8 or more characters long.");
        System.out.println(" - Must contain at least one UPPERCASE letter.");
        System.out.println(" - Must contain at least one number.");

        System.out.println("\nPassword Rules:");
        System.out.println(" - Must be 12 or more characters long.");
        System.out.println(" - Must contain at least one UPPERCASE letter.");
        System.out.println(" - Must contain at least one number.");
        System.out.println(" - Must contain at least one special character (non-letter, non-digit).");
    }

    //Robust integer reader with specific hints for letters vs decimals
    private static int readIntWithHints(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();

            if (line.isEmpty()) {
                System.out.println("Please enter a whole number like 7.");
                continue;
            }

            // Detect decimal format explicitly
            if (isDecimalNumber(line)) {
                System.out.println("Do not input a decimal. Example of decimal: 3.14");
                System.out.println("Enter a WHOLE number (integer) like 7 or -12.");
                continue;
            }

            // Detect letters or mixed input
            if (!isIntegerFormat(line)) {
                System.out.println("Do not input letters/words. Example of invalid: \"ten\" or \"12abc\".");
                System.out.println("Enter a WHOLE number (integer) like 7 or -12.");
                continue;
            }

            // Safe to parse
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException nfe) {
                // Overflow or other parsing issue
                System.out.println("That number is out of range. Try a smaller/larger whole number (e.g., 42).");
            }
        }
    }

    // Accepts optional leading +/-, then digits only
    private static boolean isIntegerFormat(String s) {
        if (s.startsWith("+") || s.startsWith("-")) s = s.substring(1);
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) if (!Character.isDigit(s.charAt(i))) return false;
        return true;
    }

    //Detects something like 3.14, .5, 5., +2.0, -0.75 (not an integer)
    private static boolean isDecimalNumber(String s) {
        // quick check: contains a dot and at least one digit total
        if (!s.contains(".")) return false;
        boolean hasDigit = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) { hasDigit = true; break; }
        }
        return hasDigit;
    }
}
