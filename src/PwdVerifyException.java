/**
 * Purpose: Custom checked exception for username/password validation failures.
 * Notes: I've also added an overload that accepts a specific reason message so it can tell the user exactly which rule
 *        was broken.
 */
public class PwdVerifyException extends Exception {
    private final String username;
    private final String password;

    public PwdVerifyException(String username, String password) {
        super("Username/Password failed validation.");
        this.username = username;
        this.password = password;
    }

    public PwdVerifyException(String username, String password, String reason) {
        super(reason);
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
