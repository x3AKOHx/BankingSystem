public class Main {
    public static void main(String[] args) {
        DataBase base = new DataBase();
        base.createTable();
        Menu.start(base);
    }
}
