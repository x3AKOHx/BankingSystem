import org.sqlite.SQLiteDataSource;
import java.sql.*;

public class DataBase {

    SQLiteDataSource dataSource;
    Connection con;
    Statement statement;
    private static final String INSERT_INFO = "INSERT INTO card (number, pin) VALUES(?, ?)";
    private static final String SELECT_NUMBER = "SELECT number FROM card";
    private static final String SELECT_NUMBER_PIN = "SELECT number, pin FROM card";
    private static final String SELECT_BALANCE = "SELECT balance FROM card WHERE number = ?";
    private static final String UPDATE_BALANCE = "UPDATE card SET balance = balance + ? WHERE number = ?";
    private static final String INCREASE_BALANCE = "UPDATE card SET balance = balance + ? WHERE number = ?";
    private static final String DECREASE_BALANCE = "UPDATE card SET balance = balance - ? WHERE number = ?";
    private static final String DELETE_INFO = "DELETE FROM card WHERE number = ?";

    DataBase() {
        this.dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:AccountsInfo.db");
        try {
            this.con = dataSource.getConnection();
            this.statement = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                    "id       INTEGER PRIMARY KEY," +
                    "number   TEXT NOT NULL," +
                    "pin TEXT NOT NULL," +
                    "balance  INTEGER DEFAULT 0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putCardInfo(String cardNumber, String password) {
        try {
            PreparedStatement pstmt = con.prepareStatement(INSERT_INFO);
            pstmt.setString(1, cardNumber);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkNumber(String cardNumber) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_NUMBER);

            while (rs.next()) {
                if (rs.getString("number").equals(cardNumber)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkPassword(String cardNumber, String password) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_NUMBER_PIN);

            while (rs.next()) {
                if (rs.getString("number").equals(cardNumber) &&
                        rs.getString("pin").equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int balance(String cardNumber) {
        try {
            PreparedStatement pstmt = con.prepareStatement(SELECT_BALANCE);
            pstmt.setString(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();

            return rs.getInt("balance");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void addIncome(String cardNumber, int amount) {
        try {
            PreparedStatement pstmt = con.prepareStatement(UPDATE_BALANCE);
            pstmt.setInt(1, amount);
            pstmt.setString(2, cardNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doTransfer(String cardNumber, String recipientsCard, int amount) {
        try {
            PreparedStatement pstmt = con.prepareStatement(INCREASE_BALANCE);
            pstmt.setInt(1, amount);
            pstmt.setString(2, recipientsCard);
            pstmt.executeUpdate();

            PreparedStatement pstmt1 = con.prepareStatement(DECREASE_BALANCE);
            pstmt1.setInt(1, amount);
            pstmt1.setString(2, cardNumber);
            pstmt1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeAccount(String cardNumber) {
        try {
            PreparedStatement pstmt = con.prepareStatement(DELETE_INFO);
            pstmt.setString(1, cardNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}