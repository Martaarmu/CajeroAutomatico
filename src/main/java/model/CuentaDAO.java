package model;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CuentaDAO extends Cuenta implements Serializable{
	@Serial

	private static final long serialVersionUID = 1L;

	private final static String INGRESAR_DINERO= "UPDATE cuenta SET saldo=? WHERE id=?";
	
	public CuentaDAO() {}
	
	
	
	public CuentaDAO(int id, int nCuenta, int saldo, Usuario u) {
		super(id, nCuenta, saldo, u);
		// TODO Auto-generated constructor stub
	}

	public CuentaDAO(int nCuenta, int saldo, Usuario u) {
		super(nCuenta, saldo, u);
		// TODO Auto-generated constructor stub
	}



	private Connection con = null;
	
	public int setSaldo() {
		
		int rs = 0;
		con = utils.Connect.getConnect();
		if (con != null) {
			PreparedStatement q=null;
			try {
				q = con.prepareStatement(INGRESAR_DINERO);
				q.setInt(1,this.saldo);
				q.setInt(2,this.id);
				
				
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
