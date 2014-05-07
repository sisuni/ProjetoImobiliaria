package model;

public abstract class Cliente extends Pessoa {

	private String cpf;
	private String rg;
	private String email;
	private String uf;
	private String cidade;
	private String bairro;
	private String logradouro;
	private int numero;
	private String complemento;
	private Telefone[] listaTelefones;
	private int numTel;

	public static final int NUM_MAX_TELEFONES = 5;

	public Cliente(int cod, String nome, String cpf, String rg, String email,
			String uf, String cidade, String bairro, String logradouro,
			int numero, String complemento) {
		super(cod, nome);
		this.setCpf(cpf);
		this.setRg(rg);
		this.setEmail(email);
		this.setUf(uf);
		this.setCidade(cidade);
		this.setBairro(bairro);
		this.setLogradouro(logradouro);
		this.setNumero(numero);
		this.setComplemento(complemento);
		this.listaTelefones = new Telefone[NUM_MAX_TELEFONES];
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Telefone[] getListaTelefones() {
		return listaTelefones;
	}

	public static int getNumMaxTelefones() {
		return NUM_MAX_TELEFONES;
	}

	public void addTelefone(Telefone novoTel) {

		if (this.numTel == this.NUM_MAX_TELEFONES)
			return;
		else {
			for(int i = 0; i < this.numTel; i++){
				if(this.listaTelefones[i] == novoTel)
					return;
			}
			
			this.listaTelefones[numTel] = novoTel;
			novoTel.setCliente(this);
			this.numTel++;
		}
	}

	public void removeTelefone(Telefone antigoTel) {
		for (int i=0;i<=this.numTel;i++){
			if(listaTelefones[i]==antigoTel){
				this.listaTelefones[i]=this.listaTelefones[this.numTel-1];
				this.listaTelefones[this.numTel -1]=null;
				antigoTel.setCliente(this);
				this.numTel--;
				
				return;
			}
		}
	}

}
