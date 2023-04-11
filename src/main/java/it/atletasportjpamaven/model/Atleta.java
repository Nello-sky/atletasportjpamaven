package it.atletasportjpamaven.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "atleta")
public class Atleta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cognome")
	private String cognome;
	@Column(name = "codice")
	private String codice;
	@Column(name = "medaglievinte")
	private int numeroMedaglieVinte;
	@Column(name = "dateborn")
	private LocalDate dataNascita;

	@ManyToMany
	@JoinTable(name = "atleta_sport", joinColumns = @JoinColumn(name = "atleta_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "sport_id", referencedColumnName = "ID"))
	private Set<Sport> sports = new HashSet<>(0);

	public Atleta() {
	}

	public Atleta(Long id, String nome, String cognome, String codice, int numeroMedaglieVinte, LocalDate dataNascita) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codice = codice;
		this.numeroMedaglieVinte = numeroMedaglieVinte;
		this.dataNascita = dataNascita;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public int getNumeroMedaglieVinte() {
		return numeroMedaglieVinte;
	}

	public void setNumeroMedaglieVinte(int numeroMedaglieVinte) {
		this.numeroMedaglieVinte = numeroMedaglieVinte;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Set<Sport> getSports() {
		return sports;
	}

	public void setSports(Set<Sport> sports) {
		this.sports = sports;
	}

	@Override
	public String toString() {
		String dataNascitaString = dataNascita != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dataNascita)
				: " N.D.";
		return "Atleta [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", codice=" + codice
				+ ", numeroMedaglieVinte=" + numeroMedaglieVinte + ", dataNascita=" + dataNascitaString + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codice, cognome, dataNascita, id, nome, numeroMedaglieVinte);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Atleta other = (Atleta) obj;
		return Objects.equals(codice, other.codice) && Objects.equals(cognome, other.cognome)
				&& Objects.equals(dataNascita, other.dataNascita) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && numeroMedaglieVinte == other.numeroMedaglieVinte;
	}

}
