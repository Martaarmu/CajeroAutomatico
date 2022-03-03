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
import java.util.List;
import java.util.Scanner;

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
					//Salir
					break;
				default:
					out.writeUTF("Solo numero del 1 al 3");
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("El cliente se ha desconectado.");
		}
	}
	
	
	///////////////////////////////////////////////////// SE ACABA DEL MAIN
	private static DataInputStream in;
	private static DataOutputStream out;
	private static UsuarioDAO u;
	private static OperarioDAO o;
	public Servidor(DataInputStream in, DataOutputStream out, UsuarioDAO u) {
		this.in = in;
		this.out = out;
		this.u = u;
	}

	public Servidor(DataInputStream in, DataOutputStream out, OperarioDAO o) {
		this.in = in;
		this.out = out;
		this.o = o;
	}

	
	/**
	 * Método de logeo usuario
	 * @param in
	 * @param out
	 * @param outo
	 */
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
					out.writeUTF("OK,USUARIO");
					UsuarioDAO u2 = new UsuarioDAO(u1.getNombre(),u1.getContrasena());
					outo.writeObject(u2);
					loggeo = true;

					Servidor s = new Servidor(in, out, u2);
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

	/**
	 * Método que loguea a un operario
	 * @param in
	 * @param out
	 */
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
					out.writeUTF("OK,OPERARIO");
					
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


	/**
	 * Consulta el saldo de un cliente
	 */
	public void verSaldo() {
		
		CuentaDAO c = new CuentaDAO();
		c = u.getSaldoDelUsuario(u);
		try {
			out.writeUTF("Su saldo es de: "+c.getSaldo()+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Ingresar dinero en la cuenta de un cliente
	 * @param u
	 */
	public void ingresarDinero(UsuarioDAO u) {
		
		Scanner sn = new Scanner(System.in);
		CuentaDAO c = new CuentaDAO();
	
		
		try {
			c = c.cuentaByIDUsuario(u.getId());
			out.writeUTF("¿Cuánto dinero quieres ingresar? \n");
			int saldo = in.readInt();
			c.IngresarSaldo(c.id, saldo);
			out.writeUTF("Su saldo se ha actual es: " + u.getSaldoDelUsuario(u)+"\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	/**
	 * Método para sacar dinero de una cuenta
	 */
	public void sacarDinero(UsuarioDAO u) {
		
		Scanner sn = new Scanner(System.in);
		CuentaDAO c = new CuentaDAO();
		
		try {
			c = c.cuentaByIDUsuario(u.getId());
			out.writeUTF("¿Cuánto dinero quieres sacar? \n");
			int saldo = in.readInt();
			c.SacarSaldo(c.id, saldo);
			out.writeUTF("Su saldo se ha actual es: " + u.getSaldoDelUsuario(u)+"\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}
	
	/**
	 *Método que el usuario utiliza para crear un usuario
	 */
	public void crearUsuario() {
		try {
			out.writeUTF("Escribe tu usuario: ");
			String usuario = in.readUTF();
			out.writeUTF("Escribe tu contrasena: ");
			String contrasena = in.readUTF();
			
			UsuarioDAO u = new UsuarioDAO(usuario,contrasena);
			u.crearUsuario(u);
			
			System.out.println("Usuario creado con éxito: \n"+u);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Método que el operario utiliza para crear una nueva cuenta
	 */
	public void crearCuenta() {
		try {
			out.writeUTF("Escribe tu id de usuario: ");
			int idusuario = in.readInt();
			
			
			UsuarioDAO u = new UsuarioDAO();
			u=	u.usuarioByID(idusuario);
			
			
			out.writeUTF("Escribe el numero de cuenta que deseas tener: ");
			int nCuenta = in.readInt();
			int saldo = 0;
			
			CuentaDAO c = new CuentaDAO(nCuenta,saldo,u);
			c.crearCuenta(c);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	/**
	 * Método que el operario utiliza para ver los datos de una cuenta
	 */
	public void verDatosCuenta() {
		CuentaDAO c = new CuentaDAO();
		try {
			out.writeUTF("Inserte el id de la cuenta que quiera consultar: ");
			int id = in.readInt();
			c = c.cuentaByID(id);
			System.out.println(c);
			out.writeUTF(c + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Método que el operario utiliza para ver los datos de un cliente
	 */
	public void verDatosCliente() {
		
		UsuarioDAO u = new UsuarioDAO();
		try {
			out.writeUTF("Inserte el id del cliente que quiera consultar: ");
			int id = in.readInt();
			u = u.usuarioByID(id);
			out.writeUTF(u + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Método que le operario utiliza para eliminar una cuenta de la BD
	 */
	public void eliminarCuenta() {
		Scanner sn = new Scanner(System.in);
	
		try {
			CuentaDAO c = new CuentaDAO();
			List<CuentaDAO> todas = c.allCuentas();
			out.writeUTF(todas + "\n Inserte el id de la que quiera eliminar: ");
			int id = in.readInt();
			c.eliminarCuenta(id);
			out.writeUTF("Cuenta eliminada con éxito");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Método run del servidor
	 * Muestra 2 menús según inicies sesión como usuario u operario
	 */
	public void run() {

		if (u != null) {

			try {

				int opcion;
				
				while (true) {
					out.writeUTF("1. Ver saldo de la cuenta");
					out.writeUTF("2. Sacar dinero de la cuenta");
					out.writeUTF("3. Ingresar dinero en la cuenta");
					out.writeUTF("5. Salir");
					opcion = in.readInt();

					switch (opcion) {
					case 1:
						
						System.out.println("opcion 1 recibida");
						verSaldo();
						break;
					case 2:
						System.out.println("opcion 2 recibida");
						sacarDinero(u);
						break;
					case 3:
						System.out.println("opcion 3 recibida");
						ingresarDinero(u);
						break;
					case 4:
						//salir
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
					out.writeUTF("3. Ver datos de una cuenta");
					out.writeUTF("4. Ver datos de un cliente");
					out.writeUTF("5. Eliminar cuenta");
					
					opcion = in.readInt();

					switch (opcion) {
					case 1:
						System.out.println("opcion 1 recibida");
						crearUsuario();
						break;
					case 2:
						crearCuenta();
						break;

					case 3:
						verDatosCuenta();
						break;
					case 4:
						verDatosCliente();
						break;
					case 5:
						eliminarCuenta();
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
