import java.io.*;

public class Persona implements Serializable
{

	private String nominativo;
	private String telefono;
	private String indirizzo;
	private String citta;
	
	
	public Persona(String nominativo,String telefono, String indirizzo,String citta)
	{
		setNominativo(nominativo);
		setTelefono(telefono);
		setIndirizzo(indirizzo);
		setCitta(citta);
	}
	
	
	
	public String getNominativo() {
		return nominativo;
	}
	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCitta()
	{
		return citta;
	}
	public void setCitta(String citta)
	{
		this.citta=citta;
	}
	
	
	//restituisce i dati in stringa CSV
	public String toCSV() 
	{
		return getNominativo()+";"+getTelefono()+";"+getIndirizzo()+";"+getCitta()+";";
	}
	

}
