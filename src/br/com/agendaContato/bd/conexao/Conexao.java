package br.com.agendaContato.bd.conexao;

import java.sql.Connection;

public class Conexao {

	private Connection conexao;

	public Connection abrirConexao() {

		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			conexao = java.sql.DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/contato", "root", "root");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conexao;
	}

	public void fecharConexao(){
		try {
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
