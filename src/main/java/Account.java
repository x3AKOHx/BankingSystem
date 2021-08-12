public class Account {

    protected static void createAccount(DataBase base) {
        String cardNumber = generateCardNumber(base);
        String PIN = generatePIN();

        base.putCardInfo(cardNumber, PIN);
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(cardNumber);
        System.out.println("Your card PIN:");
        System.out.println(PIN);
        System.out.println();
    }

    private static String generateCardNumber(DataBase base) {
        //create card number using Luhn algorithm
        int check = 0;
        long cardNumber = 400000000000000L  + (long) (Math.random() * 1000000000L);
        long temp = cardNumber;
        for (int i = 0; i < 15; i++) {
            if (i % 2 == 0) {
                if ((temp % 10) * 2 > 9) {
                    check += ((((int) (temp % 10)) * 2) - 9);
                } else {
                    check += (((int) (temp % 10)) * 2);
                }
            } else {
                check += temp % 10;
            }
            temp /= 10;
        }
        cardNumber *= 10;
        if (check % 10 != 0) {
            cardNumber += (10 - check % 10);
        }
        if (base.checkNumber(Long.toString(cardNumber))) {
            generateCardNumber(base);
        }
        return Long.toString(cardNumber);
    }

    private static String generatePIN() {
        String password = "";
        for (int i = 0; i < 4; i++)
            password += (int) (Math.random() * 10);
        return password;
    }
}
