package menus.risorse.libri;

import java.util.Vector;

import model.Libri;
import model.Libro;
import myLib.MyMenu;
import view.LibriView;
import view.MessaggiSistemaView;

public class MenuScegliLibro 
{	
	public static Libro show(Vector<Libro> libri)
	{
		MyMenu menuSceltaLibro = new MyMenu("\nScegli come visualizzare le risorse: ", new String[] {"Filtra ricerca", "Visualizza archivio"}, true); 
		int scelta = menuSceltaLibro.scegliBase();
		switch(scelta)
		{
			case 0://INDIETRO
			{
				return null;
			}
			case 1://FILTRA RICERCA
			{
				
				
				Vector<Libro> libriFiltrati = MenuFiltroLibri.show(libri,true);
				
				if(!libriFiltrati.isEmpty())
				{
					for(int i = 0; i < libriFiltrati.size(); i++)
					{
						LibriView.stampaPosizione(i);
						MessaggiSistemaView.cornice();
						libriFiltrati.get(i).stampaDati(true);
						MessaggiSistemaView.cornice();
					}
					
					int selezione;
					do
					{
						MessaggiSistemaView.cornice();
						selezione = LibriView.selezionaPrestito(libri, Libro.class);
						if(selezione == 0)
						{
							return null;
						}
						else if(libri.get(selezione-1).getInPrestito() < libri.get(selezione-1).getnLicenze())
						{
							return libri.get(selezione-1);
						}
						else
						{
							LibriView.copieTutteInPrestito(libri.get(selezione-1).getTitolo());
						}
					}
					while(true);
				}
				else//nessuna corrispondenza: vettore vuoto
				{
					LibriView.nessunaCorrispondenza(Libro.class);
					return null;
				}
			}
			case 2://VISUALIZZA ARCHIVIO
			{
				Vector<Libro>libriPrestabili = new Vector<>();
				for(Libro libro : libri)
				{
					if(libro.isPrestabile())
					{
						libriPrestabili.add(libro);
					}
				}
				if(libriPrestabili.isEmpty())
				{
					LibriView.noRisorseDisponibili(Libri.class);
					return null;
				}
				
				LibriView.risorseInArchivio(Libri.class);;
				for(int i = 0; i < libriPrestabili.size(); i++)
				{
					LibriView.stampaPosizione(i);
					MessaggiSistemaView.cornice();
					libriPrestabili.get(i).stampaDati(true);
					MessaggiSistemaView.cornice();
				}
				int selezione;
				do
				{
					selezione = LibriView.selezionaPrestito(libriPrestabili, Libro.class);
					if(selezione == 0)
					{
						return null;
					}
					else if(libriPrestabili.get(selezione-1).getInPrestito() < libriPrestabili.get(selezione-1).getnLicenze())
					{
						return libriPrestabili.get(selezione-1);
					}
					else
					{
						LibriView.copieTutteInPrestito(libriPrestabili.get(selezione-1).getTitolo());
					}
				}
				while(true);
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}
	
}
