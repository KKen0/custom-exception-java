import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: Holds a username/password pair and enforces validation rules.
 *
 * Username Rules:
 *   - Must be 8 or more characters long.
 *   - Must contain at least one UPPERCASE letter.
 *   - Must contain at least one number.
 *
 * Password Rules:
 *   - Must be 12 or more characters long.
 *   - Must contain at least 3 UPPERCASE letters.
 *   - All digits in the password must add up to an EVEN number.
 */
public class PwdVerify {

    private final String username;
    private final String password;

    public PwdVerify(String username, String password) throws PwdVerifyException {
        List<String> uErr = validateUsername(username);
        if (!uErr.isEmpty()) {
            throw new PwdVerifyException(username, password,
                    "Invalid username: " + String.join(" | ", uErr));
        }
        List<String> pErr = validatePassword(password);
        if (!pErr.isEmpty()) {
            throw new PwdVerifyException(username, password,
                    "Invalid password: " + String.join(" | ", pErr));
        }
        this.username = username;
        this.password = password;
    }

    //Username validation
    public static List<String> validateUsername(String u) {
        List<String> errors = new ArrayList<>();
        if (u == null) {
            errors.add("Username cannot be null.");
            return errors;
        }
        if (u.length() < 8) {
            errors.add("Username must be 8 or more characters long.");
        }
        if (countUppercase(u) < 1) {
            errors.add("Username must contain at least one UPPERCASE letter.");
        }
        if (countDigits(u) < 1) {
            errors.add("Username must contain at least one number.");
        }
        return errors;
    }

    // Password validation
    public static List<String> validatePassword(String p) {
        List<String> errors = new ArrayList<>();
        if (p == null) {
            errors.add("Password cannot be null.");
            return errors;
        }
        if (p.length() < 12) {
            errors.add("Password must be 12 or more characters long.");
        }
        if (countUppercase(p) < 3) {
            errors.add("Password must contain at least 3 UPPERCASE letters.");
        }
        int sum = sumDigits(p);
        if (sum == 0) {
            errors.add("Password must contain at least one digit so digits can add up.");
        } else if (sum % 2 != 0) {
            errors.add("Password digits must add up to an EVEN number (your sum was " + sum + ").");
        }
        return errors;
    }

    // Helpers
    private static int countUppercase(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isUpperCase(s.charAt(i))) count++;
        }
        return count;
    }

    private static int countDigits(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) count++;
        }
        return count;
    }

    private static int sumDigits(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                sum += Character.getNumericValue(s.charAt(i));
            }
        }
        return sum;
    }

    //Override toString
    @Override
    public String toString() {
        return "Username: " + username + " | Password: " + password;
    }

    // Getters if needed
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
