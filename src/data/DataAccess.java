package data;

import java.sql.*;

public class DataAccess {
    private final static String url = "jdbc:postgresql://localhost:5432/postgres";
    private final static String user = "postgres";
    private final static String pass = "6464";
    private static Connection conn;

    // public static void main(String[] args) throws SQLException {
    // conn = DriverManager.getConnection(url, user, pass);

    // System.out.println(verifyLogin("Fibbe", "1337"));

    // }

    public static Boolean verifyLogin(String username, String password) throws SQLException {
        conn = DriverManager.getConnection(url, user, pass);
        Boolean res = false;

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(
                String.format(
                        "SELECT EXISTS( SELECT * FROM public.user WHERE username = '%s' AND password = '%s')",
                        username,
                        password));

        while (rs.next()) {
            res = rs.getBoolean(1);
        }
        rs.close();
        st.close();

        return res;
    }
}
