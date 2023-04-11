package it.atletasportjpamaven.test;

import java.time.LocalDate;

import it.atletasportjpamaven.dao.EntityManagerUtil;
import it.atletasportjpamaven.model.Atleta;
import it.atletasportjpamaven.model.Sport;
import it.atletasportjpamaven.service.AtletaService;
import it.atletasportjpamaven.service.MyServiceFactory;
import it.atletasportjpamaven.service.SportService;

public class AtletaSportTest {

	public static void main(String[] args) {

		AtletaService atletaServiceInstance = MyServiceFactory.getAtletaServiceInstance();
		SportService sportServiceInstance = MyServiceFactory.getSportServiceInstance();

		// ora passo alle operazioni CRUD
		try {

//********* ATLETA CRUD ***********************************************************************************

//			System.out.println("In tabella Atleta ci sono " + atletaServiceInstance.listAll().size() + " elementi.");				

//			testGetAtleta(atletaServiceInstance);
//			System.out.println(atletaServiceInstance.caricaSingoloElemento(1L));

//			testAggiornaAtleta(atletaServiceInstance);

//			testInserisciNuovo(atletaServiceInstance);

//			testDeleteAtleta(atletaServiceInstance);

//			testDeleteAtletaScollegato(atletaServiceInstance);

//********* ATLETA EXTRA  ***********************************************************************************			

//			testCollegaAtletaASportEsistenti(sportServiceInstance, atletaServiceInstance);

//			testRimuoviSportDaAtleta(sportServiceInstance, atletaServiceInstance);

//			TestDeleteAtletaClean(atletaServiceInstance);

			System.out.println(atletaServiceInstance.contaMedaglieSportChiusi());

//********* SPORT CRUD ***********************************************************************************			

//			System.out.println("In tabella Sport ci sono " + sportServiceInstance.listAll().size() + " elementi.");

//			testGetsSport(sportServiceInstance);
//			System.out.println(sportServiceInstance.caricaSingoloElemento(1L));

//			testAggiornaSport(sportServiceInstance);

//			testInserisciSport(sportServiceInstance);

//			testDeleteSport(sportServiceInstance);

//			testDeleteSportScollegato(sportServiceInstance);

//********* SPORT EXTRA ***********************************************************************************	

//			testTrovaErrori(sportServiceInstance);
//			System.out.println("In tabella SportErrori ci sono " + sportServiceInstance.trovaErrori().size() + " elementi.");
//			System.out.println(sportServiceInstance.trovaErrori());

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}

	}

/////////////// METODI INTERMEDI //////////////////////////////////////////////////////////////////////////////////////////////////////

//************* ATLETA CRUD BASE *******************************************************************************************************
	public static void testGetAtleta(AtletaService atletaServiceInstance) throws Exception {
		System.out.println(".......testGetAtleta inizio.............");
		if (atletaServiceInstance.listAll().isEmpty())
			throw new RuntimeException("testGetAtleta fallito: non ci sono atleti a cui collegarci ");
		// ne controllo uno presente, osservando la table....
		atletaServiceInstance.caricaSingoloElemento(1L);

		System.out.println(".......testGetAtleta fine: PASSED.............");
	}

	public static void testAggiornaAtleta(AtletaService atletaServiceInstance) throws Exception {
		System.out.println(".......testAggiornaAtleta start.............");
		if (atletaServiceInstance.listAll().isEmpty())
			throw new RuntimeException("testAggiornaAtleta failed: no atleti to connect ");
		// ne controllo uno presente
		Atleta atletaTestUpdate = atletaServiceInstance.listAll().get(0);
		atletaTestUpdate.setCodice("xxx");
		atletaTestUpdate.setNome("nino");
		atletaServiceInstance.aggiorna(atletaTestUpdate);
		// controllo che l'update abbia modificato, ovviamente il dato di partenza non
		// deve essere uguale alla modifica
		Atleta atletaAfterUpdate = atletaServiceInstance.listAll().get(0);
		if (atletaTestUpdate.equals(atletaAfterUpdate))
			throw new RuntimeException("testAggiornaAtleta failed: update non corretto ");
		// lo riporti allo stato originario poi...
		System.out.println(".......testAggiornaAtleta end: PASSED.............");
	}

	private static void testInserisciNuovo(AtletaService atletaServiceInstance) throws Exception {
		System.out.println(".......testInserisciNuovo inizio.............");

		Atleta atletaNuovo = new Atleta();
		atletaNuovo.setNome("ale");
		atletaNuovo.setCognome("bull");
		atletaNuovo.setCodice("ppp");
		atletaNuovo.setDataNascita(LocalDate.of(1998, 05, 12));
		atletaNuovo.setNumeroMedaglieVinte(2);

		atletaServiceInstance.inserisciNuovo(atletaNuovo);
		if (atletaNuovo.getId() == null)
			throw new RuntimeException("testInserisciNuovo fallito ");

		System.out.println(".......testInserisciNuovo fine: PASSED.............");
	}

	public static void testDeleteAtleta(AtletaService atletaServiceInstance) throws Exception {
		System.out.println(".......testDeleteAtleta inizio.............");
		if (atletaServiceInstance.listAll().isEmpty())
			throw new RuntimeException("testDeleteAtleta fallito: non ci sono atleti da eliminare. ");
		atletaServiceInstance.rimuovi(1L); // per vedere l'eccezione custom: ne trovo uno presente nalla join.table
		// deve fermarsi sempre qui!
		System.out.println(".......testDeleteAtleta fine: PASSED.............");
	}

	public static void testDeleteAtletaScollegato(AtletaService atletaServiceInstance) throws Exception {
		System.out.println(".......testDeleteAtletaScollegato inizio.............");
		if (atletaServiceInstance.listAll().isEmpty())
			throw new RuntimeException("testDeleteAtletaScollegato fallito: non ci sono atleti da eliminare. ");
		Atleta atletaNuovo = new Atleta();
		atletaServiceInstance.inserisciNuovo(atletaNuovo);
		Long idAtletaToDelete = atletaNuovo.getId();
		atletaServiceInstance.rimuovi(idAtletaToDelete);
		System.out.println(".......testDeleteAtletaScollegato fine: PASSED.............");
	}

//************* ATLETA EXTRA *******************************************************************************************************	

	private static void testCollegaAtletaASportEsistenti(SportService sportServiceInstance,
			AtletaService atletaServiceInstance) throws Exception {
		System.out.println(".......testCollegaAtletaASportEsistenti inizio.............");
		// caric
		Sport sportEsistenteSuDb = sportServiceInstance.listAll().get(1);
		if (sportEsistenteSuDb == null)
			throw new RuntimeException("testCollegaAtletaASportEsistenti fallito: sport inesistente ");

		Atleta atletaEsistenteSuDb = atletaServiceInstance.listAll().get(1);
		if (atletaEsistenteSuDb.getId() == null)
			throw new RuntimeException("testCollegaAtletaASportEsistenti fallito: utente inesistente ");
		// appoggio per confronto sports.size diverse dopo add
		Atleta atletaOld = atletaServiceInstance.caricaAtletaSingoloConSports(atletaEsistenteSuDb.getId());
		int oldSportsSize = atletaOld.getSports().size();
		// collegamento
		atletaServiceInstance.aggiungiSport(atletaEsistenteSuDb, sportEsistenteSuDb);
		// verifico che ho aggiunto uno sport all'atleta
		// per fare il test ricarico interamente l'oggetto e la relazione
		Atleta atletaReloaded = atletaServiceInstance.caricaAtletaSingoloConSports(atletaEsistenteSuDb.getId());
		if (atletaReloaded.getSports().size() == oldSportsSize)
			throw new RuntimeException("testInserisciNuovoUtente fallito: ruoli non aggiunti ");
		System.out.println(".......testCollegaAtletaASportEsistenti fine: PASSED.............");
	}

	private static void testRimuoviSportDaAtleta(SportService sportServiceInstance, AtletaService atletaServiceInstance)
			throws Exception {
		System.out.println(".......testRimuoviSportDaAtleta inizio.............");

		// carico uno sport e lo associo ad un nuovo atleta
		Sport sportEsistenteSuDb = sportServiceInstance.listAll().get(0);
		if (sportEsistenteSuDb == null)
			throw new RuntimeException("testRimuoviSportDaAtleta fallito: sport inesistente ");

		// mi creo un utente inserendolo direttamente su db
		Atleta atletaNuovo = new Atleta();
		atletaNuovo.setNome("bill");
		atletaNuovo.setCognome("moon");
		atletaNuovo.setCodice("mmm");
		atletaNuovo.setDataNascita(LocalDate.of(1991, 05, 12));
		atletaNuovo.setNumeroMedaglieVinte(1);
		atletaServiceInstance.inserisciNuovo(atletaNuovo);
		if (atletaNuovo.getId() == null)
			throw new RuntimeException("testRimuoviSportDaAtleta fallito: atleta non inserito ");
		atletaServiceInstance.aggiungiSport(atletaNuovo, sportEsistenteSuDb);

		// ora ricarico il record e provo a disassociare il ruolo
		Atleta atletaReloaded = atletaServiceInstance.caricaAtletaSingoloConSports(atletaNuovo.getId());
		boolean confermoSportPresente = false;
		for (Sport sportItem : atletaReloaded.getSports()) {
			if (sportItem.getDescrizione().equals(sportEsistenteSuDb.getDescrizione())) {
				confermoSportPresente = true;
				break;
			}
		}

		if (!confermoSportPresente)
			throw new RuntimeException("testRimuoviSportDaAtleta fallito: atleta e sport non associati ");

		// ora provo la rimozione vera e propria ma poi forzo il caricamento per fare un
		// confronto 'pulito'
		atletaServiceInstance.rimuoviSportDaAtleta(atletaReloaded.getId(), sportEsistenteSuDb.getId());
		atletaReloaded = atletaServiceInstance.caricaAtletaSingoloConSports(atletaNuovo.getId());
		if (!atletaReloaded.getSports().isEmpty())
			throw new RuntimeException("testRimuoviSportDaAtleta fallito: sport ancora associato ");

		System.out.println(".......testRimuoviRuoloDaUtente fine: PASSED.............");
	}

	public static void TestDeleteAtletaClean(AtletaService atletaServiceInstance) throws Exception {
		atletaServiceInstance.deleteClean(atletaServiceInstance.caricaSingoloElemento(6L));
	}

//************* SPORT CRUD BASE *******************************************************************************************************

	public static void testGetsSport(SportService sportServiceInstance) throws Exception {
		System.out.println(".......testGetsSport inizio.............");
		if (sportServiceInstance.listAll().isEmpty())
			throw new RuntimeException("testGetsSport fallito: non ci sono sport a cui collegarci ");
		// ne controllo uno presente, osservando la table....
		sportServiceInstance.caricaSingoloElemento(1L);

		System.out.println(".......testGetsSport fine: PASSED.............");
	}

	public static void testAggiornaSport(SportService sportServiceInstance) throws Exception {
		System.out.println(".......testAggiornaSport start.............");
		if (sportServiceInstance.listAll().isEmpty())
			throw new RuntimeException("testAggiornaSport failed: no sport to connect ");
		// ne controllo uno presente
		Sport sportTestUpdate = sportServiceInstance.listAll().get(0);
		sportTestUpdate.setDescrizione("calcio");
		sportServiceInstance.aggiorna(sportTestUpdate);

		// controllo che l'update abbia modificato, ovviamente il dato di partenza non
		// deve essere uguale alla modifica
		Sport sportAfterUpdate = sportServiceInstance.listAll().get(0);
		if (sportTestUpdate.equals(sportAfterUpdate))
			throw new RuntimeException("testAggiornaSport failed: update non corretto ");
		// lo riporti allo stato originario poi...
		System.out.println(".......testAggiornaSport end: PASSED.............");
	}

	private static void testInserisciSport(SportService sportServiceInstance) throws Exception {
		System.out.println(".......testInserisciSport inizio.............");

		Sport sportNuovo = new Sport();
		sportNuovo.setDescrizione("ballo");
		sportNuovo.setDataInizio(LocalDate.of(1950, 05, 12));
		sportNuovo.setDataFine(LocalDate.of(2001, 05, 12));

		sportServiceInstance.inserisciNuovo(sportNuovo);
		if (sportNuovo.getId() == null)
			throw new RuntimeException("testInserisciSport fallito ");

		System.out.println(".......testInserisciSport fine: PASSED.............");
	}

	public static void testDeleteSport(SportService sportServiceInstance) throws Exception {
		System.out.println(".......testDeleteSport inizio.............");
		if (sportServiceInstance.listAll().isEmpty())
			throw new RuntimeException("testDeleteSport fallito: non ci sono sport da eliminare. ");
		sportServiceInstance.rimuovi(1L); // per vedere l'eccezione custom: ne trovo uno presente nalla join.table
		// deve fermarsi sempre qui!
		System.out.println(".......testDeleteSport fine: PASSED.............");
	}

	public static void testDeleteSportScollegato(SportService sportServiceInstance) throws Exception {
		System.out.println(".......testDeleteSportScollegato inizio.............");
		if (sportServiceInstance.listAll().isEmpty())
			throw new RuntimeException("testDeleteSportScollegato fallito: non ci sono atleti da eliminare. ");
		// ne creo uno nuovo scollegato da atleti sicuramente e lo rimuovo subito, la
		// list di sport rimane quindi invariata
		Sport sportNuovo = new Sport();
		sportServiceInstance.inserisciNuovo(sportNuovo);
		Long idAtletaToDelete = sportNuovo.getId();
		sportServiceInstance.rimuovi(idAtletaToDelete);
		System.out.println(".......testDeleteSportScollegato fine: PASSED.............");
	}
//************* SPORT EXTRA *******************************************************************************************************

	public static void testTrovaErrori(SportService sportServiceInstance) throws Exception {
		System.out.println(".......testTrovaErrori inizio.............");
		if (sportServiceInstance.listAll().isEmpty())
			throw new RuntimeException("testTrovaErrori fallito: non ci sono sport a cui collegarci ");
		sportServiceInstance.trovaErrori();

		System.out.println(".......testTrovaErrori fine: PASSED.............");
	}

}
