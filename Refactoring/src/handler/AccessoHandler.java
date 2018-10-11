package handler;

import controllerMVC.ArchivioController;
import controllerMVC.FruitoriController;
import controllerMVC.PrestitiController;
import controllerMVC.StoricoController;
import interfaces.ISavesManager;
import myLib.MyMenu;
import view.FruitoriView;
import view.MessaggiSistemaView;

public class AccessoHandler 
{
	FruitoreHandler fruitoreHandler;
	OperatoreHandler operatoreHandler;
	
	private static final String PASSWORD_ACCESSO_OPERATORE = "operatore";	
	
	public AccessoHandler(ISavesManager gestoreSalvataggi, ArchivioController archivioController,
			FruitoriController fruitoriController, StoricoController storicoController, PrestitiController prestitiController)
	{
		fruitoreHandler = new FruitoreHandler(fruitoriController, archivioController, prestitiController, gestoreSalvataggi);
		operatoreHandler = new OperatoreHandler(fruitoriController, archivioController, prestitiController, storicoController, gestoreSalvataggi);
	}
	
	/**
	 * menu iniziale: si sceglie se si vuole accedere come fruitore (1) o come operatore (2)
	 * @param scelta la scelta selezionata dall'utente
	 */
	public boolean gestisciAccesso(int scelta) 
	{
		boolean terminato = false;

		switch(scelta)
		{
			case 0://EXIT
			{
				MessaggiSistemaView.stampaAddio();
				terminato = true;
				break;
			}
			case 1://accesso FRUITORE
			{				
				final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
				final String[] MENU_SCELTE = {"Registrazione", "Area personale (Login)"};
				MyMenu menuFruitore=new MyMenu(MENU_INTESTAZIONE, MENU_SCELTE, true);
				
				boolean finito = false;
				do
				{
					scelta = menuFruitore.scegli();
					finito = fruitoreHandler.entryMenuFruitore(scelta);
				}
				while(!finito);
				
				break;
			}
			case 2://accesso OPERATORE
			{
				String passwordOperatore = MessaggiSistemaView.chiediPasswordOperatore();
				if(passwordOperatore.equals(PASSWORD_ACCESSO_OPERATORE))
				{
					operatoreHandler.menuOperatore();
				}
				else
				{
					FruitoriView.pswErrata();
				}
				
				terminato = false;
				break;
			}
			default:
			{
				terminato = true;
			}
		}
		return terminato;
	}
}