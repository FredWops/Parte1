package parte5;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;
import myLib.GestioneDate;
import myLib.InputDati;

/**
 * Classe rappresentante tutti i fruitori che hanno accesso all'archivio multimediale
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Fruitori implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Vector<Fruitore> fruitori;
	
	/**
	 * Costuttore della classe: inizializza l'attributo fruitori con un Vector vuoto
	 */
	public Fruitori()
	{
		this.fruitori = new Vector<Fruitore>();
	}	

	public Vector<Fruitore> getFruitori() 
	{
		return fruitori;
	}
	public void setFruitori(Vector<Fruitore> fruitori) 
	{
		this.fruitori = fruitori;
	}

	/**
	 * Crea e aggiunge un Fruitore, se maggiorenne al vettore "fruitori"
	 */
	public void addFruitore()
	{
		String nome = InputDati.leggiStringaNonVuota("Inserisci il tuo nome: ");
		String cognome = InputDati.leggiStringaNonVuota("Inserisci il tuo cognome: ");
		GregorianCalendar dataNascita = GestioneDate.creaDataGuidataPassata("inserisci la tua data di nascita: ", 1900);
		
		//controllo che l'utente sia maggiorenne
		if(GestioneDate.differenzaAnniDaOggi(dataNascita) < 18)
		{
			System.out.println("Ci dispiace, per accedere devi essere maggiorenne");
			return;
		}
		
		String user;
		do
		{
			user = InputDati.leggiStringaNonVuota("Inserisci il tuo username: ");
			if(!usernameDisponibile(user))
			{
				System.out.println("Nome utente non disponibile!");
			}
		}
		while(!usernameDisponibile(user));
				
		String password1;
		String password2;
		boolean corretta = false;
		do
		{
			password1 = InputDati.leggiStringaNonVuota("Inserisci la password: ");
			password2 = InputDati.leggiStringaNonVuota("Inserisci nuovamente la password: ");
			
			if(password1.equals(password2)) 
			{
				corretta = true;
			}
			else
			{
				System.out.println("Le due password non coincidono, riprova");
			}
		}
		while(!corretta);
		
		GregorianCalendar dataIscrizione = GestioneDate.DATA_CORRENTE;
//		creo il nuovo fruitore
		Fruitore f = new Fruitore(nome, cognome, dataNascita, dataIscrizione, user, password1); 
//		aggiungo al vector fruitori il nuovo fruitore
		fruitori.add(f);
		System.out.println("Registrazione avvenuta con successo!");
	}
	
	/**
	 * Controlla se lo username passato sia gi� in uso da un altro fruitore
	 * (precondizione: user != null)
	 * @param user lo username da verificare
	 * @return true se non � utilizzato da nessun altro (quindi � disponibile)
	 */
	private boolean usernameDisponibile(String user) 
	{
		for(Fruitore fruitore : fruitori)
		{
//			lo username non pu� essere nemmeno quello di un fruitore decaduto (senn� confusione con lo storico dei prestiti, username uguali)
			if(fruitore.getUser().equals(user))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Stampa per ogni fruitore:
	 * 	- Nome
	 *	- Cognome
	 *	- Username
	 *	- Data di nascita
	 *	- Data di iscrizione
	 *	- Data scadenza iscrizione
	 *	- Rinnovo iscrizione dal
	 */
	public void stampaFruitoriAttivi()
	{
		System.out.println("Numero fruitori: " + fruitori.size());
		for(Fruitore fruitore : fruitori)
		{
			if(!fruitore.isDecaduto())
			{
				fruitore.stampaDati();
			}
		}
	}
	
	/**
	 * Controllo se sono passati 5 anni dala data di iscrizione. Se sono passati i 5 anni assegna al fruitore lo status di "decaduto"
	 * @return un vettore contenente gli utenti eliminati
	 */
	public Vector<Fruitore> controlloIscrizioni()
	{
		Vector<Fruitore>utenti = new Vector<>();
		int rimossi = 0;
		for(Fruitore fruitore : fruitori) 
		{
			if((!fruitore.isDecaduto()) && fruitore.getDataScadenza().compareTo(GestioneDate.DATA_CORRENTE) < 0)//se dataScadenza � precedente a oggi ritorna -1
			{
				fruitore.setDecaduto(true);
				utenti.add(fruitore);
				rimossi++;
			}
		}
		System.out.println("\nIscrizioni scadute (utenti rimossi): " + rimossi);
		return utenti;
	}
	
	/**
	 * Restituisce un fruitore prendendo in ingresso uno username e una password
	 * (precondizione: username != null && password)
	 * @param username lo username inserito dall'utente
	 * @param password la password inserita dall'utente
	 * @return Fruitore il fruitore corrispondente ai dati passati (null se non presente)
	 */
	public Fruitore trovaUtente(String username, String password)
	{
		for(int i = 0; i < fruitori.size(); i++) 
		{
			if(fruitori.get(i).getUser().equals(username) && fruitori.get(i).getPassword().equals(password))
			{
				return fruitori.get(i);
			}
		}
		return null;	//se non � presente
	}	
}
