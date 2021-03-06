package model;

import java.io.Serializable;

import com.sun.corba.se.impl.io.TypeMismatchException;

import interfaces.Risorsa;

/**
 * Classe che rappresenta la descrizione di una risorsa multimediale di tipo Film
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Film implements Risorsa, Serializable
{
	private static final long serialVersionUID = 1L;
	/**
	 * utile per confrontare risorse con categoria selezionata da utente
	 */
	private static final String CATEGORIA = "Films";
	private static final int ANNO_PRIMA_PELLICOLA = 1885;

	/********************************************************************
	 * ogni categoria ha i suoi vincoli per quanto riguarda i PRESTITI: *
	 ********************************************************************/
	/**
	 * quanto tempo un Film pu� restare in prestito ad un fruitore
	 */
	private static final int GIORNI_DURATA_PRESTITO = 30;
	/**
	 * quanto dura una proroga del prestito di un Film
	 */
	private static final int GIORNI_DURATA_PROROGA = 30;
	/**
	 * quanti giorni prima della scadenza si pu� chiedere una proroga del prestito del Film
	 */
	private static final int GIORNI_PRIMA_PER_PROROGA = -7;
	/**
	 * quanti Films possono essere in prestito contemporaneamente allo stesso fruitore
	 */
	private static final int PRESTITI_MAX = 3;
	/**
	 * ID univoco del Film: Fxxx
	 */
	private String id;
	/**
	 * sottocategorie della categoria FILM: azione, avventura, horror ...
	 */
	private String sottoCategoria;
	private String titolo;
	private String regista;
	private int durata;
	private int annoDiUscita;
	private String lingua;
	private int nLicenze;
	private boolean prestabile;
	/**
	 * quante copie di questo Film sono gi� in prestito (<= nLicenze)
	 */
	private int inPrestito;
	
	/**
	 * Costuttore della classe Film
	 * @param id l'id univoco del film
	 * @param sottoCategoria la sottocategoria della categoria FILM (es. azione, avventura, horror ...)
	 * @param titolo il titolo del film
	 * @param regista il regista del film
	 * @param durata la durata (in minuti) del film
	 * @param annoDiUscita l'anno di uscita del film
	 * @param lingua la lingua del film
	 * @param nLicenze il numero di licenze possedute per il film
	 */
	public Film(String id, String sottoCategoria, String titolo, String regista, int durata, int annoDiUscita, String lingua, int nLicenze) 
	{
		this.id = (id);
		this.sottoCategoria = (sottoCategoria);
		this.titolo = (titolo);
		this.regista = (regista);
		this.durata = (durata);
		this.annoDiUscita = (annoDiUscita);
		this.lingua = (lingua);
		this.nLicenze = (nLicenze);
		this.setInPrestito(0);
		this.prestabile = true;
	}
	
	public boolean equals(Risorsa r)
	{
		if(this.id.equals(r.getId()))
		{
			return true;
		}
		else return false;
	}
	
	public boolean stessiAttributi(Risorsa r)
	{
		if(r instanceof Film)
		{
//			� istanza di Film, quindi posso fare il casting per poter usare i metodi di Film
			Film f=(Film)r;
			
			if(f.getTitolo().equals(titolo) && f.getRegista().equals(regista) && f.getDurata() == durata 
					&& f.getAnnoDiUscita() == annoDiUscita && f.getLingua().equals(lingua) 
					&& f.getSottoCategoria().equals(sottoCategoria) && f.isPrestabile() == prestabile)
			{
					return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			throw new TypeMismatchException();
		}
	}
	
	public String toString(boolean perPrestito)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Categoria-----------------: Film" + "\n");
		sb.append("Sottocategoria------------: " + sottoCategoria + "\n");
		sb.append("Titolo--------------------: " + titolo + "\n");
		sb.append("Durata--------------------: " + durata + "'" + "\n");
		sb.append("Anno di uscita------------: " + annoDiUscita + "\n");
		sb.append("Lingua--------------------: " + lingua + "\n");
		if(!perPrestito)//dati utili all'operatore
		{
			sb.append("Numero licenze------------: " + nLicenze + "\n");
			sb.append("In prestito---------------: " + inPrestito + "\n");
		}
		else//dati utili al fruitore
		{
			sb.append("Copie disponibili---------: " + (nLicenze - inPrestito) + "\n");
		}
		return sb.toString();
	}

	public String getCategoria() 
	{
		return CATEGORIA;
	}
	public String getId() 
	{
		return id;
	}
	public String getSottoCategoria() 
	{
		return sottoCategoria;
	}
	public String getTitolo() 
	{
		return titolo;
	}
	public String getRegista() 
	{
		return regista;
	}
	public int getDurata() 
	{
		return durata;
	}
	public int getAnnoDiUscita() 
	{
		return annoDiUscita;
	}
	public String getLingua() 
	{
		return lingua;
	}
	public int getNLicenze() 
	{
		return nLicenze;
	}
	public int getInPrestito() 
	{
		return inPrestito;
	}
	public int getGiorniDurataPrestito() 
	{
		return Film.GIORNI_DURATA_PRESTITO;
	}
	public int getGiorniDurataProroga() 
	{
		return Film.GIORNI_DURATA_PROROGA;
	}
	public int getGiorniPrimaPerProroga() 
	{
		return Film.GIORNI_PRIMA_PER_PROROGA;
	}
	public int getPrestitiMax() 
	{
		return Film.PRESTITI_MAX;
	}	
	
	public void setInPrestito(int inPrestito) 
	{
		this.inPrestito = inPrestito;
	}
	public void setPrestabile(boolean prestabile) 
	{
		this.prestabile = prestabile;
	}
	public void mandaInPrestito() 
	{
		inPrestito++;
	}
	public void tornaDalPrestito()
	{
		inPrestito--;
	}
	public boolean isPrestabile() 
	{
		return prestabile;
	}

	public static int getAnnoPrimaPellicola() 
	{
		return ANNO_PRIMA_PELLICOLA;
	}
}