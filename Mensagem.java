import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mensagem {
    
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
    
    public void novaMensagem(String remetente, String destinatario, String texto) {
        String sql = "INSERT INTO mensagem(idRemetente, idDestinatario, texto) VALUES " +
                        "((SELECT rowid FROM usuario WHERE apelido = ?), (SELECT rowid FROM usuario WHERE apelido = ?), ?)";
        
        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, remetente);
            pstmt.setString(2, destinatario);
            pstmt.setString(3, texto);
            pstmt.executeUpdate();

            System.out.println("-------------------------------");
            System.out.println("Mensagem enviada com sucesso!!");
            System.out.println("-------------------------------");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void lerMensagemPorUsuario(String apelido, boolean parameter) {
        String sql;
        
        if(parameter)
        sql = "SELECT usuario.apelido, mensagem.texto FROM mensagem" 
                    + " INNER JOIN usuario ON mensagem.idRemetente = usuario.rowid"
                    + " WHERE mensagem.idDestinatario = ( SELECT usuario.rowid FROM usuario WHERE apelido= ?);";
        else
        sql = "SELECT usuario.apelido, mensagem.texto FROM mensagem" 
                    + " INNER JOIN usuario ON mensagem.idDestinatario = usuario.rowid"
                    + " WHERE mensagem.idRemetente = ( SELECT usuario.rowid FROM usuario WHERE apelido= ?);";
        
        try (Connection conn = connect();
        PreparedStatement pstmt  = conn.prepareStatement(sql)){

            ResultSet rs = pstmt.executeQuery();
            
            pstmt.setString(1, apelido);

            while(rs.next()) {
                int apelidoUsuario = rs.getInt("apelido");
                String texto = rs.getString("texto");
                System.out.println("Usuario: "+ apelidoUsuario + " - " + texto);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void lerMensagem() {
        String sql = "SELECT Remetente.apelido AS ApelidoRemetente, " + 
                "Destinatario.apelido AS ApelidoDestinatario, " + 
                "Mensagem.texto FROM Mensagem " + 
                "JOIN Usuario Remetente ON Mensagem.idRemetente = Remetente.rowid " + 
                "JOIN Usuario Destinatario ON Mensagem.idDestinatario = Destinatario.rowid;";
        
        try (Connection conn = connect();
        Statement stmt  = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                String apelidoRemetente = rs.getString("ApelidoRemetente");
                String apelidDestinatario = rs.getString("ApelidoDestinatario");
                String texto = rs.getString("texto");
                System.out.println("Remetente: "+ apelidoRemetente + " - Destinatario: " + apelidDestinatario + " - " + texto);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
}
