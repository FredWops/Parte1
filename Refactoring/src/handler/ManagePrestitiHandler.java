package handler;

import controllerMVC.ArchivioController;
import controllerMVC.PrestitiController;
import interfaces.ISavesManager;
import model.Fruitore;
import model.Risorsa;

public class ManagePrestitiHandler 
{
	private static final String[] CATEGORIE = {"Libri","Film"};
	
	private Fruitore utenteLoggato;
	private PrestitiController prestitiController;
	private ArchivioController archivioController;
	private ISavesManager gestoreSalvataggi;
	
	public ManagePrestitiHandler(Fruitore utenteLoggato, PrestitiController prestitiController, 
									ArchivioController archivioController, ISavesManager gestoreSalvataggi)
	{
		this.utenteLoggato = utenteLoggato;
		this.prestitiController = prestitiController;
		this.archivioController = archivioController;
		this.gestoreSalvataggi = gestoreSalvataggi;
	}
	
	public void richiediPrestito(int scelta) 
	{
		try
		{
			String categoria = CATEGORIE[scelta - 1];
			
			Risorsa risorsa = archivioController.scegliRisorsa(categoria);
			if(risorsa != null)
			{
				prestitiController.effettuaPrestito(utenteLoggato, risorsa);
			}

			gestoreSalvataggi.salvaPrestiti();
			gestoreSalvataggi.salvaArchivio();
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] d� eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
	
	public void terminaPrestiti(int scelta) 
	{
		switch (scelta) 
		{
			case 0://indietro
			{
				break;
			}
			case 1://elimina tutti i prestiti
			{
				prestitiController.terminaTuttiPrestitiDi(utenteLoggato);
				
				gestoreSalvataggi.salvaPrestiti();
				gestoreSalvataggi.salvaArchivio();
				
				break;
			}
			case 2://elimina un solo prestito (sceglie l'utente)
			{
				prestitiController.terminaPrestitoDi(utenteLoggato);
				
				gestoreSalvataggi.salvaPrestiti();
				gestoreSalvataggi.salvaArchivio();
				
				break;
			}
		}		
	}
}
