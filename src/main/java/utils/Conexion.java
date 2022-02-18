package utils;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "conexion")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * 
 * @author marta
 *
 */
public class Conexion {

	protected String server = "";
	protected String database = "";
	protected String username = "";
	protected String password = "";

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Conexion [server=" + server + ", database=" + database + ", username=" + username + ", password="
				+ password + "]";
	}

}
