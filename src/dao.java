import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {


    public boolean cadastrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email, usuario, senha) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getUsuario());
            stmt.setString(4, usuario.getSenha()); 
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }

    // Método para verificar login
    public Usuario login(String usuario, String senha) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setNome(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setUsuario(rs.getString("usuario"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fazer login: " + e.getMessage());
        }
        return null; 
    }
}
