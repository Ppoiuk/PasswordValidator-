package CS3090;

import java.util.*;
import java.util.regex.*;

public class PasswordValidator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a password to check its strength: ");
        String password = scanner.nextLine();
        scanner.close();

        PasswordCheckResult<String, List<String>> result = validate(password);

        System.out.println(result.status);
        if (!result.feedback.isEmpty()) {
            System.out.println("Conditions:");
            for (String suggestion : result.feedback) {
                System.out.println("- " + suggestion);
            }
        }
    }

    public static PasswordCheckResult<String, List<String>> validate(String password) {
        int count = 0;
        List<String> feedback = new ArrayList<>();

        // Check length
        if (password.length() >= 8)
            count++;
        else
            feedback.add("Password should be at least 8 characters long.");

        // Check uppercase letters
        if (Pattern.compile("[A-Z]").matcher(password).find())
            count++;
        else
            feedback.add("Password should include at least one uppercase letter.");

        // Check lowercase letters
        if (Pattern.compile("[a-z]").matcher(password).find())
            count++;
        else
            feedback.add("Password should include at least one lowercase letter.");

        // Check digits
        if (Pattern.compile("\\d").matcher(password).find())
            count++;
        else
            feedback.add("Password should include at least one number.");

        // Check special characters
        if (Pattern.compile("[!@#$%+^&*/~(),.?\":{}|<>]").matcher(password).find())
            count++;
        else
            feedback.add("Password should include at least one special character (!@#$%^&* etc.).");

        // Check for repeated characters (e.g., "aaa", "111")
        if (Pattern.compile("(.)\\1{2,}").matcher(password).find()) {
            feedback.add("Your password contains repeated characters (e.g., 'aaa' or '111'). Avoid repetition.");
            count = 0;
        }

        // Check for sequential numbers (e.g., "1234", "5678")
        if (Pattern.compile("012|123|234|345|456|567|678|789|890").matcher(password).find()) {
            feedback.add("Your password contains sequential numbers (e.g., '123'). Avoid sequences.");
            count = 0;
        }

        // Strength classification
        if (count == 5)
            return new PasswordCheckResult<>("Password created.", feedback);
        else
            return new PasswordCheckResult<>("You should meet following conditions.", feedback);
    }
}

class PasswordCheckResult<K, V> {
    public final K status;
    public final V feedback;

    public PasswordCheckResult(K status, V feedback) {
        this.status = status;
        this.feedback = feedback;
    }
}