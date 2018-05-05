SENAI.contato = new Object();



$(document).ready(function(){
		
SENAI.contato.cadastrar = function(){
	
	if(document.getElementById("nome").value == "" ||
			document.getElementById("endereco").value == "" ||
			document.getElementById("telefone").value == ""){
		alert("Todos os campos sao obrigatorios de preenchimento");
		document.getElementById("nome").focus();
		return false;
	}else{
		$.ajax({
			type: "POST",
			url: "CadastroContato",
			data: $("#cadastrarContato").serialize(),
			success: function (msg) {
				var cfg = {
						title: "Mensagem",
						height: 250,
						width: 400,
						modal: true,
						buttons: {
								"Ok": function(){
									$(this).dialog("close");
								}
						}
				};
				
			$("#msg").html(msg.msg);
			$("#msg").dialog(cfg);
			SENAI.contato.buscar();
			},
			error: function (rest) {
				alert("Erro ao cadastrar um novo contato")
			}
		});
	}
	
};
SENAI.contato.buscar = function(){
	var valorBusca = $("#consultarContato").val();
	SENAI.contato.exibirContatos(undefined, valorBusca);
};

	SENAI.contato.exibirContatos = function(listaDeContatos, valorBusca){
		var html = "<table class='table'>";
		html += "<tr><th> Nome </th><th> Endereço </th><th> Telefone </th><th> Açoes</th></tr>";
		
		if (listaDeContatos != undefined && listaDeContatos.length > 0 && listaDeContatos[0].id != undefined) {
			for (var i=0; i < listaDeContatos.length; i++) {
				html += "<tr>" +
						"<td>" + listaDeContatos[i].nome + "</td>" +
						"<td>" + listaDeContatos[i].endereco + "</td>" +
						"<td>" + listaDeContatos[i].telefone + "</td>" +
						"<td>" +
							"<a class='link' onclick='SENAI.contato.editarContato("+listaDeContatos[i].id+")'> Editar</a> "+
							"<a id='del' class='link' onclick='SENAI.contato.deletarContato("+listaDeContatos[i].id+")'> Deletar</a> "+
						"</td>" +
						"</tr>";
			}
		}else{
			if (listaDeContatos == undefined || (listaDeContatos !=undefined && listaDeContatos.length > 0)) {
				$.ajax({
					type: "POST",
					url: "ConsultaContato",
					data: "valorBusca=" +valorBusca,
					success: function (listaDeContatos) {
						SENAI.contato.exibirContatos(listaDeContatos);
					},
					error: function (rest) {
						alert("Erro ao consultar os contatos");
					}
				});
			}else{
				html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>"
			}
		}
		html += "</table>";
		$("#resultadoContatos").html(html);
		
	};
	SENAI.contato.exibirContatos(undefined, "");
	
	SENAI.contato.deletarContato = function(id) {
		
		$.ajax({
			type: "POST",
			url: "DeletaContato", // Mostrando o mapeamento para o Servlet
			data: "id="+id, // parametro que será enviado para o DeletaContato
			success: function (data) {
				var cfg = {
						title: "Mensagem",
						height: 250,
						width: 400,
						modal: true,
						buttons: {
								"Ok": function(){
									$(this).dialog("close");
								}
						}
				};
				
			$("#msg").html(data.msg);
			$("#msg").dialog(cfg);
			SENAI.contato.buscar();
				//alertify.success("Registro excluido");
			},
			error: function (rest) {
				alert("Erro ao deletar os contatos");
			}
		});		
	};
	
	SENAI.contato.editarContato = function(id) {
		
		$.ajax({
			type: "POST",
			url: "ConsultaContatoPorId", //Referencia do Servlet mapeado no xml
			data: "id="+id, // parametro que será enviado para o ConsultaContatoPorId
			success: function (conta){ // objeto que recebe os dados nome, endereco, telefone
				
				$("#nomeEdit").val(conta.nome);
				$("#enderecoEdit").val(conta.endereco);
				$("#telEdit").val(conta.telefone);
				$("#idContatoEdit").val(conta.id);
				SENAI.contato.exibirEdicao(conta);
			},
			error: function (rest) {
				alert("Erro ao editar o contato");
			}
		});
		
	};
	
	
	SENAI.contato.exibirEdicao = function(conta) {
		
	
		var cfg = {
				title: "Editar Contato",
				height: 400,
				width: 550,
				modal: true,
				buttons: {
					"Salvar": function() {
						
						var dialog = this;
						var newConta = "nome="+$("#nomeEdit").val()+
						"&endereco="+$("#enderecoEdit").val()+
						"&telefone="+$("#telEdit").val()+
						"&id="+$("#idContatoEdit").val();
					
						
						$.ajax({
							type: "POST",
							url: "EditarContato",
							data: newConta,
							success: function (data) {
							
								$( dialog ).dialog("close");
								SENAI.contato.buscar();
							},
							error: function (rest) {
								alert("Erro ao editar o contato");
							}
						
						});
					}, "Cancelar": function(){
						$( this ).dialog("close");
					}
				},
				close: function() {
				}
		};
		$("#editarContato").dialog(cfg);
	};
	
});
