public class Main {
    public static void main(String[] args) {
        DataBase.connectToDB();
        DataBase.createTable();
        Menu.start();
    }
}
