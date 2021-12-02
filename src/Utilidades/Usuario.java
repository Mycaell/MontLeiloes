package Utilidades;

import java.util.ArrayList;
import java.util.Date;

public class Usuario {

	private String nome;
	private String sobrenome;
	private char sexo;
	private Date dataDeNascimento;
	private String endereço;
	private String telefone;
	private String CPF;
	private String email;
	private String senha;
	private ArrayList<Leilao> leiloesCadastrados = new ArrayList<Leilao>();
	private ArrayList<Leilao> leiloesGanhos = new ArrayList<Leilao>();
	private ArrayList<Leilao> leiloesVendidos = new ArrayList<Leilao>();
	// adicionar outras listas
//	ADICIONAR UMA LISTA COM OS LEILOES VENDIDOS
//	
	public Usuario() {
		
	}
	
	public Usuario (String nome, String sobrenome, char sexo, Date dateDeNascimento, String endereço, String telefone,
			String CPF, String email, String senha) {
		
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.sexo = sexo;
		this.dataDeNascimento = dateDeNascimento;
		this.endereço = endereço;
		this.telefone = telefone;
		this.CPF = CPF;
		this.email = email;
		this.senha = senha;
	}

	
	public String toString() {
		return this.email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public String getEndereço() {
		return endereço;
	}

	public void setEndereço(String endereço) {
		this.endereço = endereço;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public ArrayList<Leilao> getLeiloesCadastrados() {
		return leiloesCadastrados;
	}

	public void setLeiloesCadastrados(ArrayList<Leilao> leiloesCadastrados) {
		this.leiloesCadastrados = leiloesCadastrados;
	}

	public ArrayList<Leilao> getLeiloesGanhos() {
		return leiloesGanhos;
	}

	public void setLeiloesGanhos(ArrayList<Leilao> leiloesGanhos) {
		this.leiloesGanhos = leiloesGanhos;
	}
	
	public ArrayList<Leilao> getLeiloesVendidos() {
		return leiloesVendidos;
	}

	public void setLeiloesVendidos(ArrayList<Leilao> leiloesVendidos) {
		this.leiloesVendidos = leiloesVendidos;
	}

	public void cadastrarNovoLeilao(Leilao leilao) {
		leiloesCadastrados.add(leilao);
	}
	
	public void excluirLeilao(Leilao leilao) {
		leiloesCadastrados.remove(leilao);
	}
	
	public Leilao recuperarLeilao(Leilao leilao) {
		for (Leilao l : leiloesCadastrados) {
			if(l.equals(leilao)) {
				return l;
			}
		}
		return null;
	}
	
	
}
