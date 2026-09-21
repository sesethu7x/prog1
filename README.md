# Chat App – Registration & Login

## Files
- `src/User.java` – simple data holder for a registered user.
- `src/Login.java` – all validation, registration and login logic (matches the method table in the brief).
- `src/Main.java` – console demo so you can register + log in by hand.
- `test/LoginTest.java` – JUnit 5 tests using the exact test data from the brief.

## Running in VS Code
1. Install the **Extension Pack for Java** (Microsoft) from the Extensions marketplace — this gives you a JDK-aware build/run/test setup and a JUnit runner.
2. Open this folder in VS Code.
3. Open `src/Main.java` and click **Run** above `main` to try registration/login by hand.
4. Open `test/LoginTest.java` and click **Run Test** above the class (or above each `@Test`) to run the unit tests. The extension will prompt you to auto-resolve the JUnit 5 dependency the first time — accept that.

## Running in NetBeans
1. Create a new **Java Application** project.
2. Copy `User.java`, `Login.java`, `Main.java` into the project's `src` (default package is fine, or update accordingly).
3. Copy `LoginTest.java` into `Test Packages`.
4. Right-click the project → **Properties** → **Libraries** → add **JUnit 5.x** if it isn't already there.
5. Right-click `LoginTest.java` → **Test File** to run the unit tests.
6. Right-click `Main.java` → **Run File** to try the console demo.

## Notes on the validation rules implemented
- **Username**: must contain `_` and be ≤ 5 characters total (e.g. `kyl_1` passes, `kyle!!!!!!` fails).
- **Password**: ≥ 8 characters, at least one capital letter, one digit, one special character.
- **Cell number**: must match `^\+\d{1,3}\d{9,10}$` — a `+` country code followed by 9–10 more digits (e.g. `+27838968976` passes, `08966553` fails since it has no `+` code).
- **Login**: compares entered username/password against the in-memory list of registered users (`Login.registeredUsers`, static so it persists across `Login` objects within one run).

This is intentionally storage-free (no database/file yet) — registered users live in a static in-memory list in `Login`, which is enough for this stage and easy to swap out later.
