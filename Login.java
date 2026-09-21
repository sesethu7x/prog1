import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class Login {

    // ---- in-memory "database" of everyone who has registered ----
    private static final List<User> registeredUsers = new ArrayList<>();

   
    private static final Pattern CELL_PATTERN =
            Pattern.compile("^\\+\\d{1,3}\\d{9,10}$");

    // ---- details captured for THIS registration attempt ----
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    // ---- details captured for the current login attempt ----
    private String loginUsername;
    private String loginPassword;
    private boolean loginSuccessful;
    private User loggedInUser; // set only once loginUser() succeeds

    
    public Login(String firstName, String lastName, String username,
                 String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

   
    public Login(String username, String password) {
        this.loginUsername = username;
        this.loginPassword = password;
    }

    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // ------------------------------------------------------------------
    // Validation checks
    // ------------------------------------------------------------------

    /**
     * @return true if the username contains an underscore and is
     *         no longer than 5 characters in total.
     */
    public boolean checkUserName() {
        if (username == null) {
            return false;
        }
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * @return true if the password is at least 8 characters long and
     *         contains at least one capital letter, one digit and one
     *         special (non-alphanumeric) character.
     */
    public boolean checkPasswordComplexity() {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        return hasUpperCase && hasDigit && hasSpecialChar;
    }

    /**
     * @return true if the cell phone number matches the expected
     *         international format, e.g. "+27838968976"
     *         (a '+' country code followed by no more than 10 digits).
     */
    public boolean checkCellPhoneNumber() {
        if (cellPhoneNumber == null) {
            return false;
        }
        return CELL_PATTERN.matcher(cellPhoneNumber).matches();
    }

    /**
     * @return true if some other already-registered user already has
     *         this exact username.
     */
    private boolean isUsernameTaken(String candidate) {
        for (User user : registeredUsers) {
            if (user.getUsername().equals(candidate)) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    // Registration
    // ------------------------------------------------------------------

    
    public String registerUser() {
        boolean formatOk = checkUserName();
        boolean duplicate = formatOk && isUsernameTaken(username);
        boolean userNameOk = formatOk && !duplicate;
        boolean passwordOk = checkPasswordComplexity();
        boolean cellOk = checkCellPhoneNumber();

        StringBuilder result = new StringBuilder();

        if (!formatOk) {
            result.append("Username is not correctly formatted; please "
                    + "ensure that your username contains an underscore "
                    + "and is no more than five characters in length.");
        } else if (duplicate) {
            result.append("That username is already taken; please "
                    + "choose a different username.");
        } else {
            result.append("Username successfully captured.");
        }
        result.append(System.lineSeparator());

        result.append(passwordOk
                ? "Password successfully captured."
                : "Password is not correctly formatted; please ensure that "
                + "the password contains at least eight characters, a "
                + "capital letter, a number, and a special character.");
        result.append(System.lineSeparator());

        result.append(cellOk
                ? "Cell number successfully captured."
                : "Cell number is incorrectly formatted or does not "
                + "contain an international code; please correct the "
                + "number and try again.");

        if (userNameOk && passwordOk && cellOk) {
            registeredUsers.add(new User(firstName, lastName, username,
                    password, cellPhoneNumber));
        }

        return result.toString();
    }

    // ------------------------------------------------------------------
    // Login
    // ------------------------------------------------------------------

    /**
     * Record the credentials a user has just typed in on the login screen.
     */
    public void captureLoginCredentials(String username, String password) {
        this.loginUsername = username;
        this.loginPassword = password;
    }

    /**
     * @return true if the captured login username/password match a
     *         previously registered user.
     */
    public boolean loginUser() {
        loginSuccessful = false;
        loggedInUser = null;

        for (User user : registeredUsers) {
            if (user.getUsername().equals(loginUsername)
                    && user.getPassword().equals(loginPassword)) {
                loginSuccessful = true;
                loggedInUser = user;
                break;
            }
        }
        return loginSuccessful;
    }

    /**
     * @return the message to show the user after a loginUser() call:
     *         a personalised welcome on success, or an error on failure.
     */
    public String returnLoginStatus() {
        if (loginSuccessful && loggedInUser != null) {
            return "Welcome " + loggedInUser.getFirstName() + ", "
                    + loggedInUser.getLastName()
                    + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    

    /** Clears the in-memory user store. Handy between unit tests. */
    public static void clearRegisteredUsers() {
        registeredUsers.clear();
    }
}
