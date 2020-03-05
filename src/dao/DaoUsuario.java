package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanUsuario;
import connection.SingleConnection;

public class DaoUsuario {
    private Connection connection;

    public DaoUsuario() {
        connection = SingleConnection.getConnection();
    }

    public void salvar(BeanUsuario usuario){
        try {
            String sql = "insert into usuario(login, senha, nome, telefone, cep, rua, bairro, cidade, estado, ibge, fotobase64, contenttype) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // Query de insert no
            // banco
            PreparedStatement insert = connection.prepareStatement(sql); // Declaração da query sql
            // Inserindo os dados no banco
            insert.setString(1, usuario.getLogin());
            insert.setString(2, usuario.getSenha());
            insert.setString(3, usuario.getNome());
            insert.setString(4, usuario.getTelefone());
            insert.setString(5, usuario.getCep());
            insert.setString(6, usuario.getRua());
            insert.setString(7, usuario.getBairro());
            insert.setString(8, usuario.getCidade());
            insert.setString(9, usuario.getEstado());
            insert.setString(10, usuario.getIbge());
            insert.setString(11, usuario.getFotoBase64());
            insert.setString(12, usuario.getContentType());
            insert.execute();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public List<BeanUsuario> listar() throws Exception {
        List<BeanUsuario> lista = new ArrayList<BeanUsuario>();
        String sql = "select * from usuario";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            BeanUsuario beanUsuario = new BeanUsuario();
            beanUsuario.setId(resultSet.getLong("id"));
            beanUsuario.setLogin(resultSet.getString("login"));
            beanUsuario.setSenha(resultSet.getString("senha"));
            beanUsuario.setNome(resultSet.getString("nome"));
            beanUsuario.setTelefone(resultSet.getString("telefone"));
            beanUsuario.setCep(resultSet.getString("cep"));
            beanUsuario.setRua(resultSet.getString("rua"));
            beanUsuario.setBairro(resultSet.getString("bairro"));
            beanUsuario.setCidade(resultSet.getString("cidade"));
            beanUsuario.setEstado(resultSet.getString("estado"));
            beanUsuario.setIbge(resultSet.getString("ibge"));

            lista.add(beanUsuario);
        }
        return lista;
    }

    public void delete(String id) {
        try {
            String sql = "delete from usuario where id = '" + id + "'";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public BeanUsuario consultar(String id) throws Exception {
        String sql = "select * from usuario where id = '" + id + "'";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            BeanUsuario beanUsuario = new BeanUsuario();
            beanUsuario.setId(resultSet.getLong("id"));
            beanUsuario.setLogin(resultSet.getString("login"));
            beanUsuario.setSenha(resultSet.getString("senha"));
            beanUsuario.setNome(resultSet.getString("nome"));
            beanUsuario.setTelefone(resultSet.getString("telefone"));
            beanUsuario.setCep(resultSet.getString("cep"));
            beanUsuario.setRua(resultSet.getString("rua"));
            beanUsuario.setBairro(resultSet.getString("bairro"));
            beanUsuario.setCidade(resultSet.getString("cidade"));
            beanUsuario.setEstado(resultSet.getString("estado"));
            beanUsuario.setIbge(resultSet.getString("ibge"));

            return beanUsuario;
        }
        return null;
    }

    public boolean validarSenha(String senha) throws Exception {
        String sql = "select count(1) as qtd from usuario where senha = '" + senha + "'";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("qtd") <= 0;
        }
        return false;
    }

    public boolean validarSenhaUpdate(String senha, String id) throws Exception {
        String sql = "select count(1) as qtd from usuario where senha = '" + senha + "' and id <> " + id;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("qtd") <= 0;
        }
        return false;
    }

    public boolean validarLogin(String login) throws Exception {
        String sql = "select count(1) as qtd from usuario where login = '" + login + "'";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("qtd") <= 0;
        }
        return false;
    }

    public boolean validarLoginUpdate(String login, String id) throws Exception {
        String sql = "select count(1) as qtd from usuario where login = '" + login + "' and id <> " + id;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("qtd") <= 0;
        }
        return false;
    }

    public void atualizar(BeanUsuario usuario) {
        try {
            String sql = "update usuario set login = ?, senha = ?, nome = ?, telefone = ?, cep = ?, rua = ?, bairro = ?, cidade = ?, estado = ?, ibge = ? where id = "
                    + usuario.getId();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuario.getLogin());
            statement.setString(2, usuario.getSenha());
            statement.setString(3, usuario.getNome());
            statement.setString(4, usuario.getTelefone());
            statement.setString(5, usuario.getCep());
            statement.setString(6, usuario.getRua());
            statement.setString(7, usuario.getBairro());
            statement.setString(8, usuario.getCidade());
            statement.setString(9, usuario.getEstado());
            statement.setString(10, usuario.getIbge());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}