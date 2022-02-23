package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import model.UsuarioDAO;

public class Servidor extends Thread implements Serializable {
	@Serial
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

				sc = servidor.accept();
				DataInputStream in = new DataInputStream(sc.getInputStream());
				DataOutputStream out = new DataOutputStream(sc.getOutputStream());
				// ObjectInputStream ino = new ObjectInputStream(sc.getInputStream());
				ObjectOutputStream outo = new ObjectOutputStream(sc.getOutputStream());
			
				out.writeUTF("1. Eres usuario");
				out.writeUTF("2. Eres operario");
				out.writeUTF("3. Salir");
				int opcion = in.readInt();

				switch (opcion) {
				case 1:
					logeoUsuario(in, out, outo);
					break;
				case 2:
					logeoOperario(in, out);
					break;
				case 3:
					break;
				default:
					out.writeUTF("Solo numero del 1 al 3");
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void logeoUsuario(DataInputStream in, DataOutputStream out, ObjectOutputStream outo) {
		boolean loggeo = false;

		while (!loggeo) {

			try {
				
				out.writeUTF("Escribe tu usuario: ");
				String usuario = in.readUTF();
				out.writeUTF("Escribe tu contrasena: ");
				String contrasena = in.readUTF();

				UsuarioDAO u1 = new UsuarioDAO(usuario, contrasena);

				if (u1.getPassword(u1)) {

					System.out.println("Creada la conexión con el cliente " + u1.getNombre());
					out.writeUTF("OK");

					loggeo = true;

					Servidor s = new Servidor(in, out, u1);
					s.start();

				} else {
					
					System.out.println("No existe el usuario");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void logeoOperario(DataInputStream in, DataOutputStream out) {
		boolean loggeo = false;
		while (!loggeo) {

			try {
				out.writeUTF("Escribe tu usuario: ");
				String usuario = in.readUTF();
				out.writeUTF("Escribe tu contrasena: ");
				String contrasena = in.readUTF();

				OperarioDAO o = new OperarioDAO(usuario, contrasena);

				if (o.getPassword(o)) {

					System.out.println("Creada la conexión con el cliente " + o.getNombre());
					out.writeUTF("OK");
					loggeo = true;
					Servidor s = new Servidor(in, out, o);
					s.start();

				} else {
					System.out.println("No existe el usuario");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private static DataInputStream in;
	private static DataOutputStream out;
	private static Usuario u;
	private static Operario o;

	public Servidor(DataInputStream in, DataOutputStream out, Usuario u) {
		this.in = in;
		this.out = out;
		this.u = u;
	}

	public Servidor(DataInputStream in, DataOutputStream out, Operario o) {
		this.in = in;
		this.out = out;
		this.o = o;
	}

	public void run() {

		if (u != null) {

			try {

				int opcion;

				while (true) {
					out.writeUTF("1. Ver saldo de la cuenta");
					out.writeUTF("2. Sacar dinero de la cuenta");
					out.writeUTF("3. Ingresar dinero en la cuenta");
					out.writeUTF("4. Salir");
					

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

				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else {
			try {

				int opcion;

				while (true) {
					out.writeUTF("1. Ingresar un nuevo usuario");
					out.writeUTF("2. Crear cuenta bancaria");
					out.writeUTF("3. Ver datos de un cliente");
					out.writeUTF("4. Salir");
					

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

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
