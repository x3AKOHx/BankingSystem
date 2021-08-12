public class Account {

    protected static void createAccount() {
        int check = 0;
        //create card number using Luhn algorithm
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
        //create PIN
        String password = "";
        for (int i = 0; i < 4; i++)
            password += (int) (Math.random() * 10);
        String sCardNumber = Long.toString(cardNumber);

        if (DataBase.checkNumber(sCardNumber)) {
            createAccount();
        } else {
            DataBase.putCardInfo(sCardNumber, password);
            System.out.println("Your card has been created");
            System.out.println("Your card number:");
            System.out.println(sCardNumber);
            System.out.println("Your card PIN:");
            System.out.println(password);
            System.out.println();
        }
    }
}
