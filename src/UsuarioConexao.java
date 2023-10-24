import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class UsuarioConexao {
    
    Connection conexao;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<Usuario> lista = new ArrayList<>();
    ArrayList<Jogos> gameList = new ArrayList<>();

    public ResultSet fazerLogin(Usuario objUsuario){

        conexao = new Conexao().conectaDB();

        try {

            String query = "select * from usuario_login where nm_usuario = ? and pw_senha = ?";

            pstm = conexao.prepareStatement(query);
            pstm.setString(1, objUsuario.getNm_usuario());
            pstm.setString(2, objUsuario.getSenha());

            rs = pstm.executeQuery();
            return rs;

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(null, "UsuarioConexao.fazerLogin: " + erro, "Erro!", 0);
            return null;
        }
        



    }

    public void fazerCadastro(Usuario objUsuario){

        conexao = new Conexao().conectaDB();

        try {

            String query = "insert into usuario(nm_usuario, ds_nome, pw_senha, ds_email) values (?, ?, ?, ?)";

            pstm = conexao.prepareStatement(query);
            pstm.setString(1, objUsuario.getNm_usuario());
            pstm.setString(2, objUsuario.getNome());
            pstm.setString(3, objUsuario.getSenha());
            pstm.setString(4, objUsuario.getEmail());

            pstm.execute();
            pstm.close();

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(null, "UsuarioConexao.fazerCadastro: " + erro, "Erro!", 0);
        }
    

    }

    public ResultSet verificaUsuario(Usuario objUsuario){

        conexao = new Conexao().conectaDB();

        try {

            String query = "select * from usuario_login where nm_usuario = ?";

            pstm = conexao.prepareStatement(query);
            pstm.setString(1, objUsuario.getNm_usuario());

            rs = pstm.executeQuery();
            return rs;

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(null, "UsuarioConexao.verificaUsuario: " + erro, "Erro!", 0);
            return null;
        }
    }

    public ResultSet verificaEmail(Usuario objUsuario){

        conexao = new Conexao().conectaDB();

        try {

            String query = "select * from usuario where ds_email = ?";

            pstm = conexao.prepareStatement(query);
            pstm.setString(1, objUsuario.getEmail());

            rs = pstm.executeQuery();
            return rs;

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(null, "UsuarioConexao.verificaEmail: " + erro, "Erro!", 0);
            return null;
        }
    }

    public ArrayList<Usuario> pesquisaUsuario(Usuario objUsuario) {

        conexao = new Conexao().conectaDB();

        try {

            String query = "select nm_usuario, ds_nome from usuario where nm_usuario like ? or ds_nome like ?";
            
            pstm = conexao.prepareStatement(query);
            pstm.setString(1, objUsuario.getNm_usuario());
            pstm.setString(2, objUsuario.getNm_usuario());

            rs = pstm.executeQuery();

            while(rs.next()){

                Usuario objUsuarioPesquisa = new Usuario();
                objUsuarioPesquisa.setNm_usuario(rs.getString("nm_usuario"));
                objUsuarioPesquisa.setNome(rs.getString("ds_nome"));

                lista.add(objUsuarioPesquisa);

            }
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "UsuarioConexao.pesquisaUsuario: " + erro, "Erro!", 0);
            return null;
        }

        return lista;

    }

    public ResultSet mostraJogos() {

        conexao = new Conexao().conectaDB();

        try {

            String query = "select nm_jogo from jogos";
            
            pstm = conexao.prepareStatement(query); 

            return pstm.executeQuery();
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "UsuarioConexao.mostraJogos: " + erro, "Erro!", 0);
            return null;
        }
    }

    /*public void editaPerfil(Usuario objUsuario){

        conexao = new Conexao().conectaDB();

        try {

            String query = "insert into jogo_usuario values (?, ?)"
            
        } catch (Exception e) {
            // TODO: handle exception
        }

    }*/

}