import org.sqlite.SQLiteDataSource;

public class Main {
    public static void main(String[] args) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:AccountsInfo.db");
        DataBase base = new DataBase(dataSource);
        Menu.start(base);
    }
}
