package br.com.agendaContato.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.agendaContato.objetos.Contato;
import br.com.agendaContato.bd.conexao.Conexao;
import br.com.agendaContato.jdbc.JDBCContatoDAO;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;;


public class ConsultaContato extends HttpServlet{
	private static final long serialVersionUID =1L; 
	
	
	
	public ConsultaContato()  {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Contato> contatos = new ArrayList<Contato>();
		
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		JDBCContatoDAO jdbcContato = new JDBCContatoDAO(conexao);
		contatos = jdbcContato.buscarPorNome(request.getParameter("valorBusca").toString());
		conec.fecharConexao();
		
		String json = new Gson().toJson(contatos);
		
		
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			
		} catch (IOException e){
			
			e.printStackTrace();
			
		}
	}
}
