package Utilidades;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Leilao {
	private String dono;
	private String nomeDoProduto;
	private String descricao;
	private double preco;
	private double lanceMinimo;
	private int dias;
	private int horas;
	private Date dataDeCadastro;
	private ArrayList<Double> lancesRecebidos = new ArrayList<Double>();
	private ArrayList<String> DonosDosLances = new ArrayList<String>();
	private String ganhador;
	private boolean comprado;
	
	public Leilao(String nomeDoProduto,String descricao,double preco,double lanceMinimo, int dias, int horas) {
		
		this.nomeDoProduto = nomeDoProduto;
		this.descricao = descricao;
		this.preco = preco;
		this.lanceMinimo = lanceMinimo;
		this.dias = dias;
		this.horas = horas;
		this.dataDeCadastro = Calendar.getInstance().getTime();
	}
	
	public String toString() {
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		return "\n--- Leilão ---"
				+"\nProduto:"+this.nomeDoProduto
				+"\nDescrição:"+this.descricao
				+"\nValor:"+this.preco
				+"\nPrazo: "+this.dias+" Dia(s) e "+this.horas+" Hora(s)"
				+"\nData de Cadastro: "+formataData.format(this.dataDeCadastro)+"h";
	}

	public Boolean equals(Leilao leilao) {
		if(leilao.getDataDeCadastro().equals(getDataDeCadastro())) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	public String getDono() {
		return dono;
	}

	public void setDono(String dono) {
		this.dono = dono;
	}

	public String getNomeDoProduto() {
		return nomeDoProduto;
	}

	public void setNomeDoProduto(String nomeDoProduto) {
		this.nomeDoProduto = nomeDoProduto;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public double getLanceMinimo() {
		return lanceMinimo;
	}

	public void setLanceMinimo(double lanceMinimo) {
		this.lanceMinimo = lanceMinimo;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public Date getDataDeCadastro() {
		return dataDeCadastro;
	}
	public void setDataDeCadastro(Date dataDeCadastro) {
		this.dataDeCadastro = dataDeCadastro;
	}

	public ArrayList<Double> getLancesRecebidos() {
		return lancesRecebidos;
	}

	public void setLancesRecebidos(ArrayList<Double> lancesRecebidos) {
		this.lancesRecebidos = lancesRecebidos;
	}

	public ArrayList<String> getDonosDosLances() {
		return DonosDosLances;
	}

	public void setDonosDosLances(ArrayList<String> donosDosLances) {
		DonosDosLances = donosDosLances;
	}

	public String getGanhador() {
		return ganhador;
	}

	public void setGanhador(String ganhador) {
		this.ganhador = ganhador;
	}

	public boolean isComprado() {
		return comprado;
	}

	public void setComprado(boolean comprado) {
		this.comprado = comprado;
	}

	
	
	
	
}
