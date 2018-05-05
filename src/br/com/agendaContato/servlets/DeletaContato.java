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

import com.google.gson.Gson;

public class DeletaContato extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeletaContato() {
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

	private void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		int id = (Integer.parseInt(request.getParameter("id"))); // recebendo idcontato que desejmos excluir

		Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCContatoDAO jdbcContato = new JDBCContatoDAO(conexao);
			boolean retorno = jdbcContato.deletarContato(id);
			
		conec.fecharConexao();

		Map<String, String> msg = new HashMap<String, String>();

		if (retorno) {
			msg.put("msg", "Contato deletado com sucesso");
		}else{
			msg.put("msg", "Nao foi possivel deletar o contato");
	}

		String json = new Gson().toJson(msg);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
}
