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
                        "UPDATE public.user SET \"isOnline\" = true WHERE username = '%s' AND password = '%s' RETURNING \"isOnline\"",
                        username,
                        password));

        while (rs.next()) {
            System.out.println(rs.getBoolean(1));
            res = rs.getBoolean(1);
        }
        rs.close();
        st.close();

        return res;
    }

    public static Boolean setOffline(String username) throws SQLException {
        conn = DriverManager.getConnection(url, user, pass);
        Boolean res = false;
        System.out.println(username);

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(
                String.format(
                        "UPDATE public.user SET \"isOnline\" = false WHERE username = '%s' RETURNING \"isOnline\"",
                        username));

        while (rs.next()) {
            System.out.println(rs.getBoolean(1));
            res = rs.getBoolean(1);
        }
        rs.close();
        st.close();

        return res;
    }
}
