package src;
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
        final String divisor = "---------------------------------------------------------------\n";
        StringBuilder sb = new StringBuilder();

        if (parameter) {
            sql = "SELECT usuario.apelido AS apelido, mensagem.texto AS texto FROM mensagem" +
                    " INNER JOIN usuario ON mensagem.idRemetente = usuario.rowid" +
                    " WHERE mensagem.idDestinatario = (SELECT usuario.rowid FROM usuario WHERE apelido = ?);";
        } else {
            sql = "SELECT usuario.apelido, mensagem.texto FROM mensagem" +
                    " INNER JOIN usuario ON mensagem.idDestinatario = usuario.rowid" +
                    " WHERE mensagem.idRemetente = (SELECT usuario.rowid FROM usuario WHERE apelido = ?);";
        }
    
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            
    
            pstmt.setString(1, apelido); // Mova essa linha para antes de executeQuery()
    
            ResultSet rs = pstmt.executeQuery();
    
            sb.append(divisor);
            sb.append(String.format("|%-25s|%-35s|\n", "USUARIO", "TEXTO"));
            sb.append(divisor);
    
            while (rs.next()) {
                String apelidoUsuario = rs.getString("apelido");
                String texto = rs.getString("texto");
    
                sb.append(String.format("|%-25s|%-35s|\n", apelidoUsuario, texto));
            }
    
            sb.append(divisor);
    
            System.out.println(sb.toString());
    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    public void lerMensagem() {
        final String divisor = "-----------------------------------------------------------------------------------------\n";
        StringBuilder sb = new StringBuilder();

        String sql = "SELECT Remetente.apelido AS ApelidoRemetente, " + 
                "Destinatario.apelido AS ApelidoDestinatario, " + 
                "Mensagem.texto FROM Mensagem " + 
                "JOIN Usuario Remetente ON Mensagem.idRemetente = Remetente.rowid " + 
                "JOIN Usuario Destinatario ON Mensagem.idDestinatario = Destinatario.rowid;";
        
        try (Connection conn = connect();
        Statement stmt  = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(sql);

            sb.append(divisor);
            sb.append(String.format("|%-25s|%-25s|%-35s|\n", "REMETENTE", "DESTINATARIO", "TEXTO"));
            sb.append(divisor);

            while(rs.next()) {
                String apelidoRemetente = rs.getString("ApelidoRemetente");
                String apelidDestinatario = rs.getString("ApelidoDestinatario");
                String texto = rs.getString("texto");

                sb.append(String.format("|%-25s|%-25s|%-35s|\n", apelidoRemetente, apelidDestinatario, texto));
            
            }

            sb.append(divisor);

            System.out.println(sb.toString());
        
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
}
