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


import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

public class CadastroContato extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public CadastroContato() {
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

		Contato contato = new Contato();

		try {
			contato.setNome(request.getParameter("txtnome"));
			contato.setEndereco(request.getParameter("txtendereco"));
			contato.setTelefone(request.getParameter("txttelefone"));
			
			Conexao conec = new Conexao();
			
				Connection conexao = conec.abrirConexao();
			
				JDBCContatoDAO jdbcContato = new JDBCContatoDAO(conexao);
				boolean retorno = jdbcContato.inserir(contato);
				
			conec.fecharConexao();

			Map<String, String> msg = new HashMap<String, String>();
			
			if (retorno) {
				msg.put("msg", "Contato cadastrado com sucesso");
				
			} else {
				msg.put("msg", "Nao foi possivel cadastrar o contato");
				
			}

			String json = new Gson().toJson(msg);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
