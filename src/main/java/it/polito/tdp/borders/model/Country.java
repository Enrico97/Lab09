package it.polito.tdp.borders.model;

public class Country implements Comparable <Country>{
	
	private int cod;
	private String abb;
	private String nome;

	public Country(int cod, String abb, String nome) {
		this.cod=cod;
		this.abb=abb;
		this.nome=nome;
	}

	/**
	 * @return the cod
	 */
	public int getCod() {
		return cod;
	}

	/**
	 * @param cod the cod to set
	 */
	public void setCod(int cod) {
		this.cod = cod;
	}

	/**
	 * @return the abb
	 */
	public String getAbb() {
		return abb;
	}

	/**
	 * @param abb the abb to set
	 */
	public void setAbb(String abb) {
		this.abb = abb;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cod;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (cod != other.cod)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public int compareTo(Country o) {
		return -(o.getNome().compareTo(this.getNome()));
	}
	
	

}
