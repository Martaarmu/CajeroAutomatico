package model;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente extends Thread implements Serializable{

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
		Scanner sn = new Scanner(System.in);
		try {
			Socket sc = new Socket("localhost", 9999);
			
			DataInputStream in = new DataInputStream(sc.getInputStream());
			DataOutputStream out = new DataOutputStream(sc.getOutputStream());
			
			ObjectInputStream ino = new ObjectInputStream(sc.getInputStream());
			//ObjectOutputStream outo = new ObjectOutputStream(sc.getOutputStream());
			boolean loggeo =false;
			
			while(!loggeo) {
				String mensaje = in.readUTF();
				System.out.println(mensaje);
				
				String nombre = sn.next();
				out.writeUTF(nombre);
				
				String mensaje1 = in.readUTF();
				System.out.println(mensaje1);
				String contrasena = sn.next();
				out.writeUTF(contrasena);
				
				//UsuarioDAO u = new UsuarioDAO(nombre,contrasena);
				//outo.writeObject(u);
				//Usuario u = (Usuario) ino.readObject();
				//System.out.println(u);
				String validador = in.readUTF();
				if(validador.equals("OK")) {
					System.out.println("logeo correcto");
					loggeo=true;
					Cliente c = new Cliente(in,out);
					c.start();
					//c.join();
					
				}else {
					
					System.out.println("datos incorrectos");
		
				}
			}	
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		String mensaje="";
		boolean salir =false;
		
		while(!salir) {
			
			try {
			System.out.println("1. Eres usuario");
			System.out.println("2. Eres operario");
			System.out.println("3. Salir");
			
			opcion = sn.nextInt();
			out.writeInt(opcion);
			
			switch(opcion) {
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
			}
		}
	}
	
	
	
	
	
	
	
/*
	public void run() {
		String host = "localhost";
		int puerto = 9999;
		// DataInputStream in;
		// DataOutputStream out;

		ObjectInputStream in;
		ObjectOutputStream out;
		String usuario="marta";
		String contrasena="1234";

		try {

			Socket sc = new Socket(host, puerto);
			// in = new DataInputStream(sc.getInputStream());
			// out = new DataOutputStream(sc.getOutputStream());
			in = new ObjectInputStream(sc.getInputStream());
			out = new ObjectOutputStream(sc.getOutputStream());

			Usuario u = new Usuario(usuario,contrasena);
			// out.writeUTF("Hola desde el cliente");
			out.writeObject(u);

			String mensaje = in.readUTF();
			System.out.println(mensaje);

			sc.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
*/
}
