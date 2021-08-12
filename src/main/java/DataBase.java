import org.sqlite.SQLiteDataSource;
import java.sql.*;

public class DataBase {

    public static Connection connectToDB() {

        String url = "jdbc:sqlite:AccountsInfo.db";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void createTable() {
        try (Statement statement = connectToDB().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                    "id       INTEGER PRIMARY KEY," +
                    "number   TEXT NOT NULL," +
                    "pin TEXT NOT NULL," +
                    "balance  INTEGER DEFAULT 0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void putCardInfo(String cardNumber, String password) {
        String sql = "INSERT INTO card (number, pin) VALUES(?, ?)";

        try (Connection con = connectToDB();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, cardNumber);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean checkNumber(String cardNumber) {
        String sql = "SELECT number FROM card";

        try (Connection con = connectToDB();
             Statement stmt  = con.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()) {
                if (rs.getString("number").equals(cardNumber)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean checkPassword(String cardNumber, String password) {
        String sql = "SELECT number, pin FROM card";

        try (Connection con = connectToDB();
             Statement stmt  = con.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()) {
                if (rs.getString("number").equals(cardNumber) &&
                    rs.getString("pin").equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static int balance(String cardNumber) {
        String sql = "SELECT balance FROM card WHERE number = ?";
        int balance = 0;
        try (Connection con = connectToDB();
             PreparedStatement pstmt  = con.prepareStatement(sql)) {

            pstmt.setString(1, cardNumber);
            ResultSet rs  = pstmt.executeQuery();

            balance = rs.getInt("balance");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return balance;
    }

    public static void addIncome(String cardNumber, int amount) {
        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";

        try (Connection con = connectToDB();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setString(2, cardNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void doTransfer(String cardNumber, String recipientsCard, int amount) {
        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";
        String sql1 = "UPDATE card SET balance = balance - ? WHERE number = ?";

        try (Connection con = connectToDB();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setString(2, recipientsCard);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try (Connection con = connectToDB();
             PreparedStatement pstmt = con.prepareStatement(sql1)) {
            pstmt.setInt(1, amount);
            pstmt.setString(2, cardNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeAccount(String cardNumber) {
        String sql = "DELETE FROM card WHERE number = ?";

        try (Connection con = connectToDB();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, cardNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}