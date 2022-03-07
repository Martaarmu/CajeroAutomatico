package ejecutable;

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

import modelDAO.OperarioDAO;
import modelDAO.UsuarioDAO;

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
				if (validador.equals("OK,USUARIO")) {
					System.out.println("logeo correcto");
					UsuarioDAO u =new UsuarioDAO();
					
					u = (UsuarioDAO) ino.readObject();
					System.out.println(u);
					loggeo = true;
					Cliente c = new Cliente(in, out, u);
					c.start();
					
					// c.join();

				} else if(validador.equals("OK,OPERARIO")) {

					System.out.println("logeo correcto");
					loggeo = true;
					Cliente c = new Cliente(in, out);
					c.start();
				}else {
					System.out.println("datos incorrectos");
					
				}
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("El servidor se ha desconectado.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("No se pudo enviar el objecto porque se desconectó el cliente con el servidor, intentelo más tarde");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
////////////////////////////////////////////////////////////////AQUÍ ACABA EL MAIN DEL CLIENTE
	private DataInputStream in;
	private DataOutputStream out;
	private UsuarioDAO u;
	private OperarioDAO o;
	
	public Cliente(DataInputStream in, DataOutputStream out) {
		super();
		this.in = in;
		this.out = out;
	}
	

	public Cliente(DataInputStream in, DataOutputStream out, UsuarioDAO u) {
		super();
		this.in = in;
		this.out = out;
		this.u = u;
		
	}


	public void run() {
		
		
		Scanner sn = new Scanner(System.in);
		int opcion;
		String mensaje = "";
		boolean salir = false;
		
		if(u!=null) {
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
						String respuesta1 = in.readUTF();
						System.out.println(respuesta1);
						break;
					case 2:
						String respuesta2 = in.readUTF();
						System.out.println(respuesta2);
						int cantidad = sn.nextInt();
						out.writeInt(cantidad);
						String respuesta3 = in.readUTF();
						System.out.println(respuesta3);
						break;
					case 3:
						String respuesta4 = in.readUTF();
						System.out.println(respuesta4);
						int cantidad1 = sn.nextInt();
						out.writeInt(cantidad1);
						String respuesta5 = in.readUTF();
						System.out.println(respuesta5);
						break;
					case 4:
						System.exit(0);
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
		}else {
			while (!salir) {

				try {

					String opcion1 = in.readUTF();
					String opcion2 = in.readUTF();
					String opcion3 = in.readUTF();
					String opcion4 = in.readUTF();
					String opcion5 = in.readUTF();

					System.out.println(opcion1);
					System.out.println(opcion2);
					System.out.println(opcion3);
					System.out.println(opcion4);
					System.out.println(opcion5);

					opcion = sn.nextInt();
					out.writeInt(opcion);

					switch (opcion) {
					case 1:
						String nombreUsuario = in.readUTF();
						System.out.println(nombreUsuario);
						String nombre = sn.next();
						out.writeUTF(nombre);
						
						String contrasenaUsuario = in.readUTF();
						System.out.println(contrasenaUsuario);
						String contrasena = sn.next();
						out.writeUTF(contrasena);
						
						String confirmacion = in.readUTF();
						System.out.println(confirmacion);
						break;
					case 2:
						String respuesta2 = in.readUTF();
						System.out.println(respuesta2);
						int usuario = sn.nextInt();
						out.writeInt(usuario);
						
						
						
						String respuesta11 = in.readUTF();
						System.out.println(respuesta11);
						int nCuenta = sn.nextInt();
						out.writeInt(nCuenta);
						
						break;
					case 3:
						String respuesta4 = in.readUTF();
						System.out.println(respuesta4);
						int id1 = sn.nextInt();
						out.writeInt(id1);
						String respuesta5 = in.readUTF();
						System.out.println(respuesta5);
						break;
					case 4:
						String respuesta8 = in.readUTF();
						System.out.println(respuesta8);
						int id2 = sn.nextInt();
						out.writeInt(id2);
						String respuesta9 = in.readUTF();
						System.out.println(respuesta9);
						break;
					case 5:
						String respuesta6 = in.readUTF();
						System.out.println(respuesta6);
						int id = sn.nextInt();
						out.writeInt(id);
						String respuesta7 = in.readUTF();
						System.out.println(respuesta7);
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

}
