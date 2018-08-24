package model;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;
import myLib.BelleStringhe;
import myLib.GestioneDate;

/**
 * Classe rappresentante un fruitore dell'archivio multimediale
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Fruitore implements Serializable 
{	
	private static final long serialVersionUID = 1L;
	private String nome; 
	private String cognome;
	private GregorianCalendar dataNascita; 
	private GregorianCalendar dataIscrizione;
	private GregorianCalendar dataScadenza;
	private GregorianCalendar dataInizioRinnovo; //la data dalla quale si pu� rinnovare l'iscrizione
	private String username;
	private String password;
	private Boolean decaduto;
	private Vector<GregorianCalendar> rinnovi;

	/**
	 * Costruttore della classe Fruitore
	 * @param nome il nome del fruitore
	 * @param cognome il cognome del fruitore
	 * @param dataNascita la data di nascita del fruitore
	 * @param dataIscrizione la data di iscrizione del fruitore
	 * @param user username del fruitore
	 * @param password password del fruitore
	 */
	public Fruitore(String nome, String cognome, GregorianCalendar dataNascita, GregorianCalendar dataIscrizione, String user, String password) 
	{
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.dataIscrizione = dataIscrizione;
		this.username = user;
		this.password = password;
//		calcolo scadenza e inizioRinnovo in base alla data di iscrizione
		this.dataScadenza = calcolaScadenza(dataIscrizione);
		/**
		 * la data dalla quale � possibile richiedere il rinnovo dell'iscrizione
		 */
		this.dataInizioRinnovo = CalcolaInizioRinnovo(dataScadenza);
		this.decaduto = false;
		this.rinnovi = new Vector<>();
	}
	
	/**
	 * override del metodo equals di object
	 * @param f il fruitore con il quale fare il confronto
	 * @return true se i due fruitori hanno lo stesso username
	 */
	public boolean equals(Fruitore f)
	{
		if(this.username.equals(f.getUser()))
		{
			return true;
		}
		else return false;
	}
	
	/**
	 * calcola la data in cui scade l'iscrizione, cio� 5 anni dopo l'iscrizione o il rinnovo
	 * (precondizione: data != null)
	 * @return	la data di scadenza dell'iscrizione
	 * @param data la data di iscrizione o di rinnovo dell'iscrizione, da cui calcolare la data di scadenza
	 */
	private GregorianCalendar calcolaScadenza(GregorianCalendar data) 
	{
//		il metodo add tiene conto degli anni bisestili e della lunghezza dei mesi: se mi iscrivo il 29 febbraio e tra 5 anni non c'� il 29 febbraio, ritorna il 28.
//		metto scadenza uguale a dataIscrizione e poi aggiungo 5 anni, non faccio scadenza = dataIscrizione senn� si modifica anche dataIscrizione
		GregorianCalendar scadenza = new GregorianCalendar(data.get(GregorianCalendar.YEAR), data.get(GregorianCalendar.MONTH), data.get(GregorianCalendar.DAY_OF_MONTH));
		scadenza.add(GregorianCalendar.YEAR, 5);
		return scadenza;
	}
	
	/**
	 * calcola la data dalla quale l'utente pu� richiedere il rinnovo dell'iscrizione (10 giorni prima della scadenza)
	 * (precondizione: dataScadenza != null)
	 * il metodo add(field, amount) tiene conto della lunghezza dei mesi e degli anni bisestili
	 * @return la data dalla quale si pu� rinnovare l'iscrizione
	 */
	private GregorianCalendar CalcolaInizioRinnovo(GregorianCalendar dataScadenza)
	{
//		metto scadenza uguale a dataIscrizione e poi tolgo i 10 giorni, non faccio scadenza = dataScadenza senn� si modifica anche data iscrizione
		GregorianCalendar scadenza = new GregorianCalendar(dataScadenza.get(GregorianCalendar.YEAR), dataScadenza.get(GregorianCalendar.MONTH), dataScadenza.get(GregorianCalendar.DAY_OF_MONTH));
		scadenza.add(GregorianCalendar.DAY_OF_MONTH, -10);
		return scadenza;
	}
	
	/**
	 * controlla che la data odierna sia successiva alla data dalla quale si pu� rinnovare l'iscrizione
	 * non controllo che sia precedente alla data di scadenza perch� a inizio programma le iscrizioni scadute vengono rimosse
	 * @return true se il fruitore pu� rinnovare l'iscrizione
	 */
	public boolean fruitoreRinnovabile()
	{
//		compareTo ritorna 1 solo quando DATACORRENTE � successiva a dataInizioRinnovo
		if(GestioneDate.DATA_CORRENTE.compareTo(dataInizioRinnovo) == 1)
		{
			return true;
		}
		else return false;
	}
	
	/**
	 * Rinnova l'iscrizione di un Fruitore (se possibile)
	 * @return true o false: il controller viene informato della riuscita per dire alla view cosa stampare
	 */
	public boolean rinnovo()
	{  
		if(fruitoreRinnovabile())
		{			
			rinnovi.addElement(GestioneDate.DATA_CORRENTE);
			calcolaScadenza(GestioneDate.DATA_CORRENTE);
			CalcolaInizioRinnovo(dataScadenza);
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(BelleStringhe.CORNICE + "\n");
		sb.append("Nome----------------------: " + nome + "\n");
		sb.append("Cognome-------------------: " + cognome + "\n");
		sb.append("Username------------------: " + username + "\n");
		sb.append("Data di nascita-----------: " + GestioneDate.visualizzaData(dataNascita) + "\n");
		sb.append("Data di iscrizione--------: " + GestioneDate.visualizzaData(dataIscrizione) + "\n");
		sb.append("Data scadenza iscrizione--: " + GestioneDate.visualizzaData(dataScadenza) + "\n");
		sb.append("Rinnovo iscrizione dal----: " + GestioneDate.visualizzaData(dataInizioRinnovo) + "\n");
		
		return sb.toString();
	}

	// -- GETTER -- //
	public String getNome() 
	{
		return nome;
	}
	public String getCognome() 
	{
		return cognome;
	}
	public GregorianCalendar getDataNascita() 
	{
		return dataNascita;
	}
	public GregorianCalendar getDataIscrizione() 
	{
		return dataIscrizione;
	}
	public String getUser() 
	{
		return username;
	}
	public String getPassword() 
	{
		return password;
	}
	public GregorianCalendar getDataScadenza() 
	{
		return dataScadenza;
	}
	public Boolean isDecaduto() 
	{
		return decaduto;
	}
	public Vector<GregorianCalendar> getRinnovi() 
	{
		return rinnovi;
	}
	// -- SETTER -- //
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}
	public void setDataNascita(GregorianCalendar dataNascita) 
	{
		this.dataNascita = dataNascita;
	}
	public void setDataIscrizione(GregorianCalendar dataIscrizione) 
	{
		this.dataIscrizione = dataIscrizione;
	}
	public void setDataScadenza(GregorianCalendar dataScadenza) 
	{
		this.dataScadenza = dataScadenza;
	}
	public void setUser(String user) 
	{
		this.username = user;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}
	public void setDecaduto(Boolean decaduto) 
	{
		this.decaduto = decaduto;
	}
	public void setRinnovi(Vector<GregorianCalendar> rinnovi) 
	{
		this.rinnovi = rinnovi;
	}
	public GregorianCalendar getDataInizioRinnovo() 
	{
		return dataInizioRinnovo;
	}
}
