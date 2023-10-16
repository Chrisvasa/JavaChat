package data;

import java.sql.*;
import java.util.ArrayList;

// Handles communication with the Postgres database
public class DataAccess {
    private final static String url = "jdbc:postgresql://localhost:5432/postgres";
    private final static String user = "";
    private final static String pass = "";
    private static Connection conn;

    public static Boolean verifyLogin(String username, String password) throws SQLException {
        conn = DriverManager.getConnection(url, user, pass);
        Boolean res = false;
        ResultSet rs;

        Statement st = conn.createStatement();
        rs = st.executeQuery(String.format("SELECT \"isOnline\" FROM public.user WHERE username = '%s'", username));
        while (rs.next()) {
            System.out.println(rs.getBoolean(1));
            res = rs.getBoolean(1);
        }
        if (!res) {
            rs = st.executeQuery(
                    String.format(
                            "UPDATE public.user SET \"isOnline\" = true WHERE username = '%s' AND password = '%s' RETURNING \"isOnline\"",
                            username,
                            password));

            while (rs.next()) {
                System.out.println(rs.getBoolean(1));
                res = rs.getBoolean(1);
            }
            return res;
        }
        rs.close();
        st.close();

        return false;
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

    public static ArrayList<String> getOnlineUsers(String username) throws SQLException {
        ArrayList<String> users = new ArrayList<>();
        conn = DriverManager.getConnection(url, user, pass);
        Statement st = conn.createStatement();

        ResultSet rs = st
                .executeQuery(String.format(
                        "SELECT username FROM public.user WHERE username <> '%s' AND \"isOnline\" = 'true'", username));

        while (rs.next()) {
            System.out.println(rs.getString(1));
            users.add(rs.getString(1));
        }
        rs.close();
        st.close();

        return users;
    }
}
