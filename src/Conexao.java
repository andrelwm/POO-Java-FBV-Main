import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexao {


    public Connection conectaDB() {
        Connection conBD = null;

        try {

            //String url = "jdbc:mysql://localhost:3306/dbams?useTimezone=true&serverTimezone=UTC";
            String url = "jdbc:postgresql://localhost:5432/dbams";
            conBD = DriverManager.getConnection(url, "postgres", "5522");

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(null, erro.getMessage(), "ERRO!", 0);

        }

        return conBD;
    }
    
}

//
