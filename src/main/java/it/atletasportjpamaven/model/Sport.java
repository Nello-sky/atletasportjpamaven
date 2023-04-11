package it.atletasportjpamaven.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sport")
public class Sport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "descrizione")
	private String descrizione;
	@Column(name = "datainizio")
	private LocalDate dataInizio;
	@Column(name = "datafine")
	private LocalDate dataFine;

	public Sport() {
	}

	public Sport(Long id, String descrizione, LocalDate dataInizio, LocalDate dataFine) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDate getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}

	@Override
	public String toString() {
		String dataInizioString = dataInizio != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dataInizio)
				: " N.D.";
		String dataFineString = dataFine != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dataFine) : " N.D.";

		return "Sport [id=" + id + ", descrizione=" + descrizione + ", dataInizio=" + dataInizioString + ", dataFine="
				+ dataFineString + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataFine, dataInizio, descrizione, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sport other = (Sport) obj;
		return Objects.equals(dataFine, other.dataFine) && Objects.equals(dataInizio, other.dataInizio)
				&& Objects.equals(descrizione, other.descrizione) && Objects.equals(id, other.id);
	}

}
