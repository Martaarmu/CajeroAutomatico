package modelDAO;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Operario;

public class OperarioDAO extends Operario implements Serializable{

	@Serial
	private static final long serialVersionUID = 1L;
	
	private final static String INICIO ="SELECT id,usuario,contrasena FROM operario WHERE usuario =? AND contrasena=?";
	
	public OperarioDAO() {}
	public OperarioDAO(String nombre, String contrasena) {
		super(nombre,contrasena);
	}
	
	private Connection con = null;
	
	/**
	 * Método para comprobar si en la BD si existe un operario 
	 * con un nombre y una contraseña dados
	 * @param u
	 * @return
	 * @throws DAOExcepcion 
	 */
	public synchronized boolean getPassword(OperarioDAO u) {
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
	
	
	
}
