package parte3;

import java.io.Serializable;
import java.util.Vector;

/**
 * Classe che rappresenta la descrizione di una risorsa multimediale di tipo Libro
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Libro extends Risorsa implements Serializable
{	
	/********************************************************************
	 * ogni categoria ha i suoi vincoli per quanto riguarda i PRESTITI: *
	 ********************************************************************/
	/**
	 * quanto tempo un Libro pu� restare in prestito ad un fruitore
	 */
	public static final int GIORNI_DURATA_PRESTITO = 30;
	/**
	 * quanto dura una proroga del prestito di un Libro
	 */
	public static final int GIORNI_DURATA_PROROGA = 30;
	/**
	 * quanti giorni prima della scadenza si pu� chiedere una proroga del prestito del Libro
	 */
	public static final int GIORNI_PRIMA_PER_PROROGA = -7;
	/**
	 * quanti Libri possono essere in prestito contemporaneamente allo stesso fruitore
	 */
	public static final int PRESTITI_MAX = 3;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * ID univoco del libro
	 */
	private int id;
	
	/**
	 * sottocategorie della categoria LIBRO: Romanzo, Fumetto, Poesia...
	 */
	private String sottoCategoria;
	private String nome;
	private Vector<String> autori = new Vector<>();
	private int pagine;
	private int annoPubblicazione;
	private String casaEditrice;
	private String lingua;
	private String genere;
	private int nLicenze;
	/**
	 * quante copie di questo libro sono gi� in prestito (<= nLicenze)
	 */
	private int inPrestito;
	
	/**
	 * Costuttore della classe libro
	 * @param id l'id univoco del libro
	 * @param nome il titolo del libro
	 * @param autori il vector degli autori del libro
	 * @param pagine il numero di pagine
	 * @param annoPubblicazione l'anno di pubblicazione
	 * @param casaEditrice la casa editrice
	 * @param lingua la lingua del testo
	 * @param sottoCategoria la sottocategoria della categoria LIBRO (es. Romanzo, Fumetto, Poesia...)
	 * @param genere il genere del libro ( "-" se il genere non ha sottogeneri)
	 * @param nLicenze il numero di licenze disponibili
	 */
	public Libro(int id, String sottoCategoria, String nome, Vector<String> autori, int pagine, int annoPubblicazione, String casaEditrice,
			String lingua, String genere, int nLicenze) 
	{
		this.id = id;
		this.sottoCategoria = sottoCategoria;
		this.nome = nome;
		this.autori = autori;
		this.pagine = pagine;
		this.annoPubblicazione = annoPubblicazione;
		this.casaEditrice = casaEditrice;
		this.lingua = lingua;
		this.genere = genere;
		this.nLicenze = nLicenze;
		this.inPrestito = 0;
	}
	
	/**
	 * Stampa tutte le informazioni del libro
	 */
	public void stampaDati(boolean perPrestito)
	{
//		System.out.println(BelleStringhe.CORNICE);
		System.out.println("Categoria-----------------: Libro");
//		System.out.println("ID------------------------: " + id);
//		System.out.println("Hashcode------------------: " + hashCode());
		System.out.println("Sottocategoria------------: " + sottoCategoria);
		System.out.println("Titolo--------------------: " + nome);
		System.out.print("Autori--------------------:");
		for(int i = 0; i < autori.size(); i++)
		{
			System.out.print(" " + autori.elementAt(i));
			if(i < autori.size()-1)
			{
				System.out.print(",");
			}
			else System.out.println();
		}
		if(!genere.equals("-"))
		{
			System.out.println("Genere--------------------: " + genere);
		}
		System.out.println("Numero pagine-------------: " + pagine);
		System.out.println("Anno di pubblicazione-----: " + annoPubblicazione);
		System.out.println("Casa editrice-------------: " + casaEditrice);
		System.out.println("Lingua--------------------: " + lingua);
		if(!perPrestito)
		{
			System.out.println("Numero licenze------------: " + nLicenze);
			System.out.println("In prestito---------------: " + inPrestito);
		}
		else
		{
			System.out.println("Copie disponibili---------: " + (nLicenze - inPrestito));
		}
	}

	public int getId() 
	{
		return id;
	}
	public String getNome()
	{
		return nome;
	}
	public Vector<String> getAutori() 
	{
		return autori;
	}
	public int getPagine()
	{
		return pagine;
	}
	public int getAnnoPubblicazione() 
	{
		return annoPubblicazione;
	}
	public String getCasaEditrice() 
	{
		return casaEditrice;
	}
	public String getLingua() 
	{
		return lingua;
	}
	public String getSottoCategoria() 
	{
		return sottoCategoria;
	}
	public String getGenere() 
	{
		return genere;
	}
	public int getnLicenze() 
	{
		return nLicenze;
	}
	public int getInPrestito() 
	{
		return inPrestito;
	}
	
	/**
	 * precondizione: ci sono copie del Libro disponibili per il prestito
	 */
	public void mandaInPrestito() 
	{
		inPrestito++;
	}
	public void tornaDalPrestito()
	{
		inPrestito--;
	}

	@Override
	public int getGiorniDurataPrestito() 
	{
		return Libro.GIORNI_DURATA_PRESTITO;
	}

	@Override
	public int getGiorniDurataProroga() 
	{
		return Libro.GIORNI_DURATA_PROROGA;
	}

	@Override
	public int getGiorniPrimaPerProroga() 
	{
		return Libro.GIORNI_PRIMA_PER_PROROGA;
	}

	@Override
	public int getPrestitiMax() 
	{
		return Libro.PRESTITI_MAX;
	}
}
