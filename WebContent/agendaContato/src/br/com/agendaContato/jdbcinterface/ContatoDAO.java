package br.com.agendaContato.jdbcinterface;

import java.util.List;

import br.com.agendaContato.objetos.Contato;

public interface ContatoDAO {

	
	public boolean inserir(Contato contato);
	public List<Contato> buscarPorNome(String nome);
	public Contato buscarPorId(int cod);
	public boolean atualizar(Contato contato);
	public boolean deletarContato(int id);
	
}
