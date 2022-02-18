package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import model.UsuarioDAO;

public class Servidor extends Thread implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		try {
			ServerSocket servidor = new ServerSocket(9999);
			Socket sc;
			

			System.out.println("Servidor iniciado");

			while (true) {
				boolean loggeo = false;
				sc = servidor.accept();
				DataInputStream in = new DataInputStream(sc.getInputStream());
				DataOutputStream out = new DataOutputStream(sc.getOutputStream());

				// ObjectInputStream ino = new ObjectInputStream(sc.getInputStream());
				ObjectOutputStream outo = new ObjectOutputStream(sc.getOutputStream());

				while(!loggeo) {
					out.writeUTF("Escribe tu usuario: ");
					String usuario = in.readUTF();
					out.writeUTF("Escribe tu contrasena: ");
					String contrasena = in.readUTF();

					UsuarioDAO u = new UsuarioDAO(usuario, contrasena);

					System.out.println(u);

					if (u.getPassword(u)) {

						System.out.println("Creada la conexión con el cliente " + u.getNombre());
						out.writeUTF("OK");
						loggeo =true;
						Servidor s = new Servidor(in, out, u.getNombre());
						s.start();
						// outo.writeObject(u);

					} else {
						out.writeUTF("NANAI");
						System.out.println("No existe el usuario");
						//loggeo =false;
					}

					
				}
				

				System.out.println("llego aqui");

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private DataInputStream in;
	private DataOutputStream out;
	private String u;

	public Servidor(DataInputStream in, DataOutputStream out, String u) {
		this.in = in;
		this.out = out;
		this.u = u;
	}

	public void run() {

		int opcion;

		while (true) {
			try {
				opcion = in.readInt();

				switch (opcion) {
				case 1:
					System.out.println("opcion 1 recibida");
					break;
				case 2:
					break;

				case 3:
					break;
				default:
					out.writeUTF("Solo numero del 1 al 6");

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * public void run() { // TODO Auto-generated method stub ServerSocket servidor
	 * = null; Socket sc = null; //DataInputStream in; //DataOutputStream out;
	 * ObjectInputStream in; ObjectOutputStream out; int puerto = 9999;
	 * 
	 * try {
	 * 
	 * servidor = new ServerSocket(puerto); System.out.println("Servidor iniciado");
	 * 
	 * // Siempre escuchando peticiones while (true) {
	 * 
	 * // Espero a que un cliente se conecte sc = servidor.accept();
	 * System.out.println("Cliente conectado");
	 * 
	 * //in = new DataInputStream(sc.getInputStream()); //out = new
	 * DataOutputStream(sc.getOutputStream()); in = new
	 * ObjectInputStream(sc.getInputStream()); out = new
	 * ObjectOutputStream(sc.getOutputStream());
	 * 
	 * // Leo el mensaje que me envía //String mensaje = in.readUTF(); String
	 * mensaje = in.readUTF(); System.out.println(mensaje);
	 * 
	 * // Le envío un mensaje out.writeUTF("Hola desde el servidor");
	 * 
	 * // Cierro conexión sc.close(); System.out.println("Cliente desconectado");
	 * 
	 * }
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 */
}
