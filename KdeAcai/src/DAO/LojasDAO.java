package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import controle.ManipulacaoBanco;
import modelo.Lojas;

public class LojasDAO {
	public static void cadastrar(Lojas loja) {
		// Função que cadastra uma loja
		
		String sql = String.format("INSERT INTO lojas (email, senha, nome, avaliacao, localizacao, telefone, tipoFino, tipoMedio, tipoGrosso) VALUES('%s', '%s', '%s', '%f', '%s', '%f', '%f', '%f')",
				loja.getEmail(), loja.getSenha(), loja.getNome(), 0, loja.getLocalizacao(), 
				loja.getTelofone(), loja.getTipoFino(), loja.getTipoMedio(), loja.getTipoGrosso());
		
		ManipulacaoBanco.manipular(sql);
		System.out.println("Loja cadastrada");
	}
	
	public static void deletar(Lojas loja) {
		// Função que deleta uma loja
		
		int id = 0;
		ResultSet resultado = consultarLoja(loja);
		String sql = String.format("DETELE FROM lojas WHERE id=%d", id);
		
		try {
			if(resultado.next()) {
				id = resultado.getInt("id");
				sql = String.format("DETETE FROM lojas WHERE id=%d", id);
				System.out.println("Loja deletado!");
			} else {
				System.out.println("Loja não existe");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void atualizar(Lojas loja) {
		// Função que atualiza os dados da loja
		
		String sql = "";
		ResultSet resultado = consultarLoja(loja);
		int id = 0;
		
		String novoEmail = loja.getEmail();
		String novoSenha = loja.getSenha();
		String novoNome = loja.getNome();
		float novaAvalicao = loja.getAvaliacao();
		String novaLocalizacao = loja.getLocalizacao();
		String novoTelefone = loja.getTelofone();
		float novoTipoFino = loja.getTipoFino();
		float novoTipoMedio = loja.getTipoMedio();
		float novoTipoGrosso = loja.getTipoGrosso();
		
		try {
			if(resultado.next()) {
				id = resultado.getInt("id");
				
				if(!novoEmail.equals("")) {
					sql = String.format("UPDATE loja SET email='%s' WHERE id=%d", novoEmail, id);
				} 
				
				if(!novoSenha.equals("")) {
					sql = String.format("UPDATE loja SET senha='%s' WHERE id=%d", novoSenha, id);
				} 
				
				if(!novoNome.equals("")) {
					sql = String.format("UPDATE loja SET nome='%s' WHERE id=%d", novoNome, id);
				} 
				
				if(novaAvalicao != 0) {
					sql = String.format("UPDATE loja SET avaliacao='%f' WHERE id=%d", novaAvalicao, id);
				} 
				
				if(!novaLocalizacao.equals("")) {
					sql = String.format("UPDATE loja SET localizacao='%s' WHERE id=%d", novaLocalizacao, id);
				}
				
				if(!novoTelefone.equals("")) {
					sql = String.format("UPDATE loja SET telefone='%s' WHERE id=%d", novoTelefone, id);
				}
				
				if(novoTipoFino!= 0) {
					sql = String.format("UPDATE loja SET tipoFino='%f' WHERE id=%d", novoTipoFino, id);
				} 
				
				if(novoTipoMedio!= 0) {
					sql = String.format("UPDATE loja SET tipoMedio='%f' WHERE id=%d", novoTipoMedio, id);
				}
				
				if(novoTipoGrosso!= 0) {
					sql = String.format("UPDATE loja SET tipoGrosso='%f' WHERE id=%d", novoTipoGrosso, id);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected static ResultSet consultarLoja(Lojas loja) {
		// Função que pesquisa no banco uma loja que já existe;
		
		String sql  = String.format("SELECT * FROM lojas WHERE email='%s'", loja.getEmail());
		return ManipulacaoBanco.pegarLojas(sql);
	}
	
}
