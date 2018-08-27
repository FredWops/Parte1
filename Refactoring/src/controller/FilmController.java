package controller;

import java.util.Vector;
import menus.risorse.films.*;
import model.Film;
import model.Films;
import view.FilmsView;
import view.MessaggiSistemaView;

public class FilmController 
{
	private Films model;
	private int lastId;	
	
	public FilmController(Films films) 
	{
		this.model = films;
		this.lastId = films.getLastId();
	}
	
	public int getLastId() 
	{
		return lastId;
	}

	public void addFilm()
	{
		String sottoCategoria = this.scegliSottoCategoria();//la sottocategoria della categoria FILM ("Azione","Avventura","Fantascienza"...)
//		se l'utente annulla la procedura
		if(sottoCategoria == "annulla")
		{
			return;
		}

		String titolo = FilmsView.chiediTitolo(Film.class);
		int durata = FilmsView.chiediDurata();
		int annoDiUscita = FilmsView.chiediAnnoUscita();
		String lingua = FilmsView.chiediLingua();
		String regista = FilmsView.chiediRegista();
		int nLicenze = FilmsView.chiediNumeroLicenze();
		
		Film f = new Film("F"+ lastId++, sottoCategoria, titolo, regista, durata, annoDiUscita, lingua, nLicenze);
		
//		aggiunge il film al model del controller (se non � gi� esistente)
		boolean aggiuntaRiuscita = model.addFilm(f);
		
		if(aggiuntaRiuscita)
		{
			FilmsView.aggiuntaRiuscita(Film.class);
			
//			il main salva su file quell'archivio, nella classe MenuOperatore
//			non c'� bisogno di settare l'archivio del main perch� � lo stesso riferimento
//			Main.setArchivio(model);
		}
		else
		{
			FilmsView.aggiuntaNonRiuscita(Film.class);
		}
	}
	
	/**
 	 * metodo per Test che consente di non chiedere in input all'utente i campi per creare la risorsa
	 */
	public void addFilm(String sottoCategoria, String titolo, String regista, int durata, int annoDiUscita, String lingua, int nLicenze)
	{
		Film f = new Film("F"+ lastId++, sottoCategoria, titolo, regista, durata, annoDiUscita, lingua, nLicenze);
		
//		aggiunge il film al model del controller (se non � gi� esistente)
		model.addFilm(f);
	}
	
//	deve restituire al main l'id della risorsa da eliminare (far diventare non prestabile)
	public String removeFilm()
	{
		Vector<Film> films = model.getFilms();
		
		String idSelezionato;
		
		String titolo = FilmsView.chiediRisorsaDaRimuovere(Film.class);
		
		Vector<Integer> posizioniRicorrenze = new Vector<>();
		
		for (int i = 0; i < films.size(); i++) 
		{
			if(films.get(i).isPrestabile() && films.get(i).getTitolo().toLowerCase().equals(titolo.toLowerCase()))
			{
//				ogni volta che in films trovo un libro con il nome inserito dall'operatore, aggiungo la sua posizione al vettore
				posizioniRicorrenze.add(i);
			}
		}
		if(posizioniRicorrenze.size()==0)
		{
			FilmsView.risorsaNonPresente(Film.class);
			idSelezionato = "-1";
		}
//		se nel vettore delle ricorrenze c'� solo una posizione, elimino l'elemento in quella posizioni in films
		else if(posizioniRicorrenze.size()==1)
		{
			idSelezionato = films.get((int)posizioniRicorrenze.get(0)).getId();
			films.get((int)posizioniRicorrenze.get(0)).setPrestabile(false);
			FilmsView.rimozioneAvvenuta();
		}
//		se ci sono pi� elementi nel vettore (pi� films con il nome inserito dall'operatore) li stampo e chiedo di selezionare quale si vuole rimuovere:
//		l'utente inserisce quale rimuovere -> prendo la posizione in films di quell'elemento e lo rimuovo da films
		else
		{
			FilmsView.pi�RisorseStessoTitolo(Film.class, titolo);
			
			int pos = 0;
			for(Integer i : posizioniRicorrenze)
			{
				FilmsView.numeroRicorrenza(pos);
				MessaggiSistemaView.cornice();
				stampaDatiFilm(films.elementAt((int)i), false);
				MessaggiSistemaView.cornice();
			}
			
			int daRimuovere = FilmsView.chiediRicorrenzaDaRimuovere(posizioniRicorrenze.size());
					
			if(daRimuovere > 0)
			{
				idSelezionato = films.get((int)posizioniRicorrenze.get(daRimuovere-1)).getId();
				films.get((int)posizioniRicorrenze.get(daRimuovere-1)).setPrestabile(false);;
				FilmsView.rimozioneAvvenuta();
			}
			else//0: annulla
			{
				idSelezionato = "-1";
			}
		}		
		return idSelezionato;
	}
	
	/**
	 * metodo di Test, per non dover chiedere all'utente l'input per il titolo della risorsa da eliminare
	 */
	public void removeFilm(String titolo)
	{
		Vector<Film> films = model.getFilms();
				
		Vector<Integer> posizioniRicorrenze = new Vector<>();
		
		for (int i = 0; i < films.size(); i++) 
		{
			if(films.get(i).isPrestabile() && films.get(i).getTitolo().toLowerCase().equals(titolo.toLowerCase()))
			{
//				ogni volta che in films trovo un libro con il nome inserito dall'operatore, aggiungo la sua posizione al vettore
				posizioniRicorrenze.add(i);
			}
		}
		if(posizioniRicorrenze.size()==0)
		{
			FilmsView.risorsaNonPresente(Film.class);
		}
//		se nel vettore delle ricorrenze c'� solo una posizione, elimino l'elemento in quella posizioni in films
		else if(posizioniRicorrenze.size()==1)
		{
			films.get((int)posizioniRicorrenze.get(0)).setPrestabile(false);
		}
//		se ci sono pi� elementi nel vettore (pi� films con il nome inserito dall'operatore) li stampo e chiedo di selezionare quale si vuole rimuovere:
//		l'utente inserisce quale rimuovere -> prendo la posizione in films di quell'elemento e lo rimuovo da films
		else
		{
			FilmsView.pi�RisorseStessoTitolo(Film.class, titolo);
			
			int pos = 0;
			for(Integer i : posizioniRicorrenze)
			{
				FilmsView.numeroRicorrenza(pos);
				MessaggiSistemaView.cornice();
				stampaDatiFilm(films.elementAt((int)i), false);
				MessaggiSistemaView.cornice();
			}
			
			int daRimuovere = FilmsView.chiediRicorrenzaDaRimuovere(posizioniRicorrenze.size());
					
			if(daRimuovere > 0)
			{
				films.get((int)posizioniRicorrenze.get(daRimuovere-1)).setPrestabile(false);;
				FilmsView.rimozioneAvvenuta();
			}
		}
	}
	
	public void stampaDatiFilm(Film film, boolean perPrestito) 
	{
		FilmsView.stampaDati(film, perPrestito);
	}
	
//	main contatta il controller che si occupa dell'interazione con la view
	public void stampaDatiFilmPrestabili() 
	{
		Vector<Film>filmDaStampare = new Vector<>();
		for(Film film : model.getFilms())
		{
			if(film.isPrestabile())
			{
				filmDaStampare.add(film);
			}
		}
		FilmsView.stampaDati(filmDaStampare);
	}

	private String scegliSottoCategoria()
	{
		String sottocategoria = MenuSottoCategoriaFilm.show();
		
		return sottocategoria;
	}

	public void conferma(boolean aggiuntaRiuscita) 
	{
		if(aggiuntaRiuscita)
		{
			FilmsView.aggiuntaRiuscita(Film.class);
		}
		else
		{
			FilmsView.aggiuntaNonRiuscita(Film.class);
		}
	}
	
	public Films getModel()
	{
		return model;
	}
}