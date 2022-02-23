package model;

import java.io.Serial;
import java.io.Serializable;

public class Operario implements Serializable{
	@Serial
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nombre;
	private String contrasena;
	public Operario() {
		super();
	}
	
	public Operario(String nombre, String contrasena) {
		super();
		this.nombre = nombre;
		this.contrasena = contrasena;
	}

	public Operario(int id, String nombre, String contrasena) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.contrasena = contrasena;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operario other = (Operario) obj;
		if (contrasena == null) {
			if (other.contrasena != null)
				return false;
		} else if (!contrasena.equals(other.contrasena))
			return false;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Operario [nombre=" + nombre + ", contrasena=" + contrasena + "]";
	}
	
	
	
	
	
	
	
	
}
