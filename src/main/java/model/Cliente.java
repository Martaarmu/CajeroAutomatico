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
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente extends Thread implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		Scanner sn = new Scanner(System.in);
		try {
			Socket sc = new Socket("localhost", 9999);

			DataInputStream in = new DataInputStream(sc.getInputStream());
			DataOutputStream out = new DataOutputStream(sc.getOutputStream());
			ObjectInputStream ino = new ObjectInputStream(sc.getInputStream());
			// ObjectOutputStream outo = new ObjectOutputStream(sc.getOutputStream());
			boolean loggeo = false;

			String mensaje = in.readUTF();
			System.out.println(mensaje);

			String mensaje1 = in.readUTF();
			System.out.println(mensaje1);

			String mensaje2 = in.readUTF();
			System.out.println(mensaje2);

			int opcion = sn.nextInt();
			out.writeInt(opcion);

			while (!loggeo) {

				String nombreUsuario = in.readUTF();
				System.out.println(nombreUsuario);
				String nombre = sn.next();
				out.writeUTF(nombre);
				String contrasenaUsuario = in.readUTF();
				System.out.println(contrasenaUsuario);
				String contrasena = sn.next();
				out.writeUTF(contrasena);

				String validador = in.readUTF();
				if (validador.equals("OK")) {
					System.out.println("logeo correcto");
					loggeo = true;
					Cliente c = new Cliente(in, out);
					c.start();
					// c.join();

				} else {

					System.out.println("datos incorrectos");

				}
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("El servidor se ha desconectado.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private DataInputStream in;
	private DataOutputStream out;

	public Cliente(DataInputStream in, DataOutputStream out) {
		super();
		this.in = in;
		this.out = out;
	}

	public void run() {
		Scanner sn = new Scanner(System.in);
		int opcion;
		String mensaje = "";
		boolean salir = false;

		while (!salir) {

			try {

				String opcion1 = in.readUTF();
				String opcion2 = in.readUTF();
				String opcion3 = in.readUTF();
				String opcion4 = in.readUTF();

				System.out.println(opcion1);
				System.out.println(opcion2);
				System.out.println(opcion3);
				System.out.println(opcion4);

				opcion = sn.nextInt();
				out.writeInt(opcion);

				switch (opcion) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				default:
					mensaje = in.readUTF();
					System.out.println(mensaje);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//System.out.println("El servidor se ha desconectado.");
			}
		}
	}

}
