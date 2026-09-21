import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Register a new user ===");
        System.out.print("First name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last name: ");
        String lastName = scanner.nextLine();

        // Start with placeholder values for the fields we're about to
        // validate one at a time; setters below fill them in properly.
        Login registration = new Login(firstName, lastName, "", "", "");

        // --- Username: keep asking until it passes checkUserName() ---
        boolean usernameValid;
        do {
            System.out.print("Username (must contain '_' and be <= 5 chars): ");
            registration.setUsername(scanner.nextLine());
            usernameValid = registration.checkUserName();
            System.out.println(usernameValid
                    ? "Valid: Username successfully captured."
                    : "Invalid: Username is not correctly formatted; please "
                    + "ensure that your username contains an underscore and "
                    + "is no more than five characters in length.");
        } while (!usernameValid);

        // --- Password: keep asking until it passes checkPasswordComplexity() ---
        boolean passwordValid;
        do {
            System.out.print("Password (>=8 chars, capital, number, special char): ");
            registration.setPassword(scanner.nextLine());
            passwordValid = registration.checkPasswordComplexity();
            System.out.println(passwordValid
                    ? "Valid: Password successfully captured."
                    : "Invalid: Password is not correctly formatted; please "
                    + "ensure that the password contains at least eight "
                    + "characters, a capital letter, a number, and a "
                    + "special character.");
        } while (!passwordValid);

        // --- Cell number: keep asking until it passes checkCellPhoneNumber() ---
        boolean cellValid;
        do {
            System.out.print("Cell number (e.g. +27838968976): ");
            registration.setCellPhoneNumber(scanner.nextLine());
            cellValid = registration.checkCellPhoneNumber();
            System.out.println(cellValid
                    ? "Valid: Cell number successfully captured."
                    : "Invalid: Cell number is incorrectly formatted or does "
                    + "not contain an international code; please correct "
                    + "the number and try again.");
        } while (!cellValid);

        // Every field already passed its individual check above, so this
        // call is really just the final confirmation + actually storing
        // the user (registerUser() re-checks everything internally too).
        System.out.println();
        System.out.println("All requirements met - registering...");
        System.out.println(registration.registerUser());

        System.out.println();
        System.out.println("=== Try logging in ===");
        System.out.print("Username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Password: ");
        String loginPassword = scanner.nextLine();

        Login loginAttempt = new Login(loginUsername, loginPassword);
        loginAttempt.loginUser();
        System.out.println(loginAttempt.returnLoginStatus());

        scanner.close();
    }
}
