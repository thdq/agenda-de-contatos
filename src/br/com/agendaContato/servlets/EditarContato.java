package br.com.agendaContato.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.agendaContato.bd.conexao.Conexao;
import br.com.agendaContato.jdbc.JDBCContatoDAO;
import br.com.agendaContato.objetos.Contato;

import com.google.gson.Gson;

public class EditarContato extends HttpServlet {

	private static final long serialVersionUID =1L; 
	
	
	
	public EditarContato()  {
		super();
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Contato contato = new Contato();
		contato.setNome(request.getParameter("nome"));
		contato.setEndereco(request.getParameter("endereco"));
		contato.setTelefone(request.getParameter("telefone"));
		contato.setId(Integer.parseInt(request.getParameter("id")));
		
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
			JDBCContatoDAO jdbcContato = new JDBCContatoDAO(conexao);
			boolean retorno = jdbcContato.atualizar(contato);
		conec.fecharConexao();
		
		Map<String, String> msg = new HashMap<String, String>();
		
		if (retorno) {
			msg.put("msg", "Contato editado com sucesso.");
		}else{
			msg.put("msg", "Nao foi possivel editar o contato");
		}
		
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(new Gson().toJson(msg)); // Convertendo o HashMap msg para JSON
		} catch (IOException e) {                               // para usar no JS e respectivamente 
			e.printStackTrace();                                // imprimir a mensagem no Cliente
		}
	}
	
}
