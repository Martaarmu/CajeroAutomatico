package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class UsuarioDAO extends Usuario implements Serializable{
	@Serial
	private static final long serialVersionUID = 1L;
	
	private final static String INICIO ="SELECT id,usuario,contrasena FROM usuario WHERE usuario =? AND contrasena=?";
	private final static String VER_SALDO="SELECT c.id,c.nCuenta,c.saldo FROM cuenta as c, usuario as u WHERE c.id_usuario=u.id AND u.id=?";
	private final static String UPDATE_CUENTA ="UPDATE cuenta SET saldo=? WHERE id=?";
	
	public UsuarioDAO() {}
	public UsuarioDAO(String nombre, String contrasena) {
		super(nombre,contrasena);
	}
	public UsuarioDAO(int id, String nombre, String contrasena) {
		super(id, nombre, contrasena);
		
	}
	


	private Connection con = null;
	
	/**
	 * Método para comprobar si en la BD si existe un usuario 
	 * con un nombre y una contraseña dados
	 * @param u
	 * @return
	 * @throws DAOExcepcion 
	 */
	public boolean getPassword(UsuarioDAO u) {
		boolean result=true;
		//UsuarioDAO u1 = new UsuarioDAO();
		con = utils.Connect.getConnect();
		if(con!=null) {
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				ps = con.prepareStatement(INICIO);
				ps.setString(1, u.getNombre());
				ps.setString(2, u.getContrasena());
				rs=ps.executeQuery();
				while(rs.next()) {
				
				u.setNombre(rs.getString("usuario"));
					
				u.setContrasena(rs.getString("contrasena"));
				u.setId(rs.getInt("id"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
				}
				
			}
	
		}
		if(u.getId()==0) {
			result=false;
		}else {
			result=true;
		}
		return result;
		
		
	}
	
	
	public Cuenta getSaldoDelUsuario(Usuario u) {
		Cuenta c= new Cuenta ();
		con = utils.Connect.getConnect();
		if(con!=null) {
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				ps = con.prepareStatement(VER_SALDO);
				ps.setInt(1, u.getId());
				
				rs=ps.executeQuery();
				while(rs.next()) {
				
				c.setId(rs.getInt("id"));
				c.setnCuenta(rs.getInt("nCuenta"));
					
				c.setSaldo(rs.getInt("saldo"));
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
				}
				
			}
	
		}
		return c;
	}
	
	public int actualizarSaldoDelUsuario (UsuarioDAO u) {
		int rs = 0;
		Cuenta c = u.getSaldoDelUsuario(u);
		con = utils.Connect.getConnect();
		if (con != null) {
			PreparedStatement q=null;
			try {
				q = con.prepareStatement(UPDATE_CUENTA);
				q.setInt(1,c.saldo);
				q.setInt(2,c.id);
				
				
				rs = q.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}finally {
				try {
					q.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
				}
				
			}
		}
		return rs;
	}

		

}
