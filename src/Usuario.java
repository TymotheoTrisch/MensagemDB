package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Usuario {

    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:mensagens.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public void novoUsuario(String apelido) {
        String sql = "INSERT INTO usuario(apelido) VALUES(?)";

        try (
                Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, apelido);
            pstmt.executeUpdate();

            System.out.println("-------------------------------");
            System.out.println("Usuario criado com sucesso!!");
            System.out.println("-------------------------------");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void lerUsuario() {
        final String divisor = "-----------------------\n";
        StringBuilder sb = new StringBuilder();

        String sql = "SELECT rowid, apelido FROM usuario;";
        
        try (
                Connection conn = connect();
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){
            

            sb.append(divisor);
            sb.append(String.format("|%-5s|%-15s|\n", "ID", "USUARIO"));
            sb.append(divisor);

            while(rs.next()) {
                int id = rs.getInt("rowid");
                String apelidoUsuario = rs.getString("apelido");

                sb.append(String.format("|%-5s|%-15s|\n", id, apelidoUsuario));

            }

            sb.append(divisor);

            System.out.println(sb.toString());
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    // public void lerUsuarioPorApelido(String apelido) {
    //     String sql = "SELECT rowid, apelido FROM usuario WHERE apelido = ?;";
        
    //     try (Connection conn = connect();
    //     PreparedStatement pstmt  = conn.prepareStatement(sql)){

    //         ResultSet rs = pstmt.executeQuery(sql);
            
    //         pstmt.setString(1, apelido);

    //         while(rs.next()) {
    //             int id = rs.getInt("rowid");
    //             String apelidoUsuario = rs.getString("apelido");
    //             System.out.println("ID: "+ id + " - " + apelidoUsuario);
    //         }
    //     } catch(SQLException e) {
    //         System.out.println(e.getMessage());
    //     }
        
    // }
}
