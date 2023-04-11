package it.atletasportjpamaven.dao;

public class MyDAOFactory {
	// rendiamo questo factory SINGLETON
	private static AtletaDAO ATLETA_DAO_INSTANCE = null;
	private static SportDAO SPORT_DAO_INSTANCE = null;

	public static AtletaDAO getAtletaDAOInstance() {
		if (ATLETA_DAO_INSTANCE == null)
			ATLETA_DAO_INSTANCE = new AtletaDAOImpl();
		return ATLETA_DAO_INSTANCE;
	}

	public static SportDAO getSportDAOInstance() {
		if (SPORT_DAO_INSTANCE == null)
			SPORT_DAO_INSTANCE = new SportDAOImpl();
		return SPORT_DAO_INSTANCE;
	}

}
