package Loja;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class coneçao {
    public static Connection CreateConnection() throws SQLException {
        String url="jdbc:mysql://localhost:3306/bdlojamarco";
        String user="root";
        String pass="1234";
        Connection connexao=null;
        connexao= DriverManager.getConnection(url,user,pass);
        return connexao;

    }
}
