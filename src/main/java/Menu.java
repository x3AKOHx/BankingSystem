import java.util.Scanner;

public class Menu {
    public static void start(DataBase base) {
        boolean isTrue = true;

        while (isTrue) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");

            Scanner sc = new Scanner(System.in);
            int pick = sc.nextInt();

            switch (pick) {
                case 1 -> Account.createAccount(base);
                case 2 -> AccountMenu.logIn(base);
                case 0 -> {
                    isTrue = false;
                    System.out.println("Bye!");
                }
                default -> System.out.println("Choose one of the following numbers!");
            }
        }
    }
}
