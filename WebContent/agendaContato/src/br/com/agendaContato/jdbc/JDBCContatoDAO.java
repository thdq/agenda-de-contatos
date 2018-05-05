package br.com.agendaContato.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.agendaContato.jdbcinterface.ContatoDAO;
import br.com.agendaContato.objetos.Contato;


public class JDBCContatoDAO implements ContatoDAO {
	private Connection conexao;
	
	public JDBCContatoDAO(Connection conexao){
		this.conexao = conexao;
	}
	
	public boolean inserir(Contato contato) {
		String comando = "insert into contato " +
						"(nome, endereco, telefone) " +
						"values (?,?,?)";
		
		PreparedStatement p; 
		
		try {
			p = this.conexao.prepareStatement(comando);
			
			p.setString(1, contato.getNome());
			p.setString(2, contato.getEndereco());
			p.setString(3, contato.getTelefone());
			p.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	return true;
	}
	
	public List<Contato> buscarPorNome(String nome) {
		String comando = "select * from contato ";
		if (!nome.equals("")) {
			comando += "where nome like'" + nome+"%'";
		}
		List<Contato> listContato = new ArrayList<Contato>();
		Contato contato = null;
		
		try {
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				contato = new Contato();
				String nomeContato = rs.getString("nome");
				String endereco = rs.getString("endereco");
				int idcontato = rs.getInt("idcontato");
				String telefone = rs.getString("telefone");
				
				contato.setId(idcontato);
				contato.setNome(nomeContato);
				contato.setEndereco(endereco);
				contato.setTelefone(telefone);
				
				listContato.add(contato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return listContato;
	}
	
	public boolean deletarContato(int id){
		String comando = "delete from contato where idcontato = " + id;
		Statement p;
		
		try {
			p = this.conexao.createStatement();
			p.execute(comando);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Contato buscarPorId(int cod){
		String comando = "select * from contato where idcontato = " +cod;
		Contato contato = new Contato();
		try {
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				String nomeContato = rs.getString("nome");
				String endereco = rs.getString("endereco");
				int idContato = rs.getInt("idcontato");
				String telefone = rs.getString("telefone");
				
				contato.setId(idContato);
				contato.setNome(nomeContato);
				contato.setEndereco(endereco);
				contato.setTelefone(telefone);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contato;
	}
	
	public boolean atualizar(Contato contato) {
		String comando = "update contato set nome=?, endereco=?, telefone=? ";
		comando += " where idcontato = ";
		comando += contato.getId();
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, contato.getNome());
			p.setString(2, contato.getEndereco());
			p.setString(3, contato.getTelefone());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
