import java.util.Scanner;

public class AccountMenu {

    protected static void logIn(DataBase base) {
        while (true) {
            System.out.println("Enter your card number:");
            Scanner sc = new Scanner(System.in);
            String cardNumber = sc.nextLine();
            if (cardNumber.equals("0")) {
                System.exit(0);
            }
            System.out.println("Enter your PIN:");
            String password = sc.nextLine();
            if (password.equals("0")) {
                System.exit(0);
            }
            if (base.checkPassword(cardNumber, password)) {
                System.out.println("You have successfully logged in!");
                System.out.println();
                logMenu(cardNumber, base);
                break;
            } else {
                System.out.println("Wrong card number or PIN!");
                System.out.println();
            }
        }
    }

    protected static void logMenu(String cardNumber, DataBase base) {

        boolean isTrue = true;

        while (isTrue) {
            System.out.println("1. Balance");
            System.out.println("2. Add income");
            System.out.println("3. Do transfer");
            System.out.println("4. Close account");
            System.out.println("5. Log out");
            System.out.println("0. Exit");

            Scanner sc = new Scanner(System.in);
            int pick = sc.nextInt();

            switch (pick) {
                case 1 -> getBalance(cardNumber, base);
                case 2 -> {
                    System.out.println("Enter income:");
                    int amount = sc.nextInt();
                    if (amount > 0) {
                        base.addIncome(cardNumber, amount);
                        System.out.println("Income was added!");
                    } else {
                        System.out.println("Enter the positive number!");
                    }
                    System.out.println();
                }
                case 3 -> transfer(cardNumber, base);
                case 4 -> {
                    base.closeAccount(cardNumber);
                    isTrue = false;
                    System.out.println("The account has been closed!");
                    System.out.println();
                }
                case 5 -> {
                    isTrue = false;
                    System.out.println("You have successfully logged out!");
                    System.out.println();
                }
                case 0 -> System.exit(0);
                default -> {
                    System.out.println("Choose one of the following numbers!");
                    System.out.println();
                }
            }
        }
    }

    protected static void getBalance(String cardNumber, DataBase base) {
        System.out.println("Balance: " + base.balance(cardNumber));
        System.out.println();
    }

    protected static void transfer(String cardNumber, DataBase base) {
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        Scanner sc = new Scanner(System.in);
        String recipientsCard = sc.nextLine();
        if (base.checkNumber(recipientsCard)) {
            System.out.println("Enter how much money you want to transfer:");
            int amount = sc.nextInt();
            if (base.balance(cardNumber) < amount) {
                System.out.println("Not enough money!");
            } else {
                base.doTransfer(cardNumber, recipientsCard, amount);
                System.out.println("Success!");
            }
            System.out.println();
        } else if (cardNumber.equals(recipientsCard)) {
            System.out.println("You can't transfer money to the same account!");
            System.out.println();
        } else if (!isLuhnTrue(recipientsCard)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            System.out.println();
        } else {
            System.out.println("Such a card does not exist.");
            System.out.println();
        }
    }

    protected static boolean isLuhnTrue(String cardNumber) {
        try {
            long l = Long.parseLong(cardNumber);
            int temp = 0;
            for (int i = 1; i < 17; i++) {
                if (i % 2 != 0) {
                    temp += l % 10;
                } else {
                    if ((l % 10) * 2 > 9) {
                        temp += (((l % 10) * 2) - 9);
                    } else {
                        temp += ((l % 10) * 2);
                    }
                }
                l /= 10;
            }
            return temp % 10 == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
