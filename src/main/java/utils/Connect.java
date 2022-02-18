package utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;



/**
 * 
 * @author marta
 *
 */
public class Connect {

	private static Connection con;
	private final static String server = Connect.load().getServer();
	private final static String database = Connect.load().getDatabase();
	private final static String username = Connect.load().getUsername();
	private final static String password = Connect.load().getPassword();
	private final static String file = "conexion.xml";

	public static void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(server + "/" + database, username, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConnect() {
		if (con == null) {
			connect();
		}
		return con;
	}

	/**
	 * Guarda en un XML los datos de una conexion
	 * @param c
	 */
	public static void save(Conexion c) {
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Conexion.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(c, new File(file));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Carga los datos de un XML
	 */
	public static Conexion load() {
		Conexion con = new Conexion();
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Conexion.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Conexion newR = (Conexion) jaxbUnmarshaller.unmarshal(new File(file));
			con = newR;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

}
