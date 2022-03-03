package model;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO extends Cuenta implements Serializable{
	@Serial

	private static final long serialVersionUID = 1L;

	private final static String INGRESAR_DINERO= "UPDATE cuenta SET saldo=? WHERE id=?";
	private final static String GETCUENTABYID ="SELECT * FROM cuenta WHERE id=?";
	private final static String GETCUENTABYUSER = "SELECT * FROM cuenta WHERE id_usuario = ?";
	private final static String ELIMINARCUENTA = "DELETE FROM cuenta WHERE id = ?";
	private final static String TODAS_CUENTAS ="SELECT * from cuenta";
	private final static String CREARCUENTA = "INSERT INTO cuenta (nCuenta,saldo,id_usuario)" + "VALUES (?,?,?)";
	
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
	
	public List<CuentaDAO> allCuentas(){
		
		con = utils.Connect.getConnect();
		List<CuentaDAO> result = new ArrayList<CuentaDAO>();
		  if (con != null) {
	            try {
	                PreparedStatement q = con.prepareStatement(TODAS_CUENTAS);
	               // q.setInt(1, idCuenta);
	                ResultSet rs = q.executeQuery();
	                while (rs.next()) {
	                	UsuarioDAO u = new UsuarioDAO();
	                	u=u.usuarioByID(rs.getInt("id_usuario"));
	                	result.add(new CuentaDAO(rs.getInt("id"),
	                			rs.getInt("nCuenta"),rs.getInt("saldo"),u));
	                			
	                    
	                    
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	                
	            }
	        }else {
	            System.out.println("No se ha podido recuperar la cuenta, fallo en la conexión");
	        }

	        return result;
		
	}
	
    public CuentaDAO cuentaByID(int idCuenta) throws Exception {
      
    	con = utils.Connect.getConnect();
        CuentaDAO result = new CuentaDAO();

        if (con != null) {
            try {
                PreparedStatement q = con.prepareStatement(GETCUENTABYID);
                q.setInt(1, idCuenta);
                ResultSet rs = q.executeQuery();
                
                while (rs.next()) {
                	UsuarioDAO u = new UsuarioDAO();
                	u=u.usuarioByID(rs.getInt("id_usuario"));
                    result.setId(rs.getInt("id"));
                    result.setnCuenta(rs.getInt("nCuenta"));
                    result.setSaldo(rs.getInt("saldo"));
                    result.setU(u);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new Exception("Error en SQL", e);
            }
        }else {
            System.out.println("No se ha podido recuperar la cuenta, fallo en la conexión");
        }

        return result;
    }
    
    public CuentaDAO cuentaByIDUsuario(int id_usuario) throws Exception {
        
    	con = utils.Connect.getConnect();
        CuentaDAO result = new CuentaDAO();

        if (con != null) {
            try {
                PreparedStatement q = con.prepareStatement(GETCUENTABYID);
                q.setInt(1, id_usuario);
                ResultSet rs = q.executeQuery();
                while (rs.next()) {
                    result.setId(rs.getInt("id"));
                    result.setnCuenta(rs.getInt("nCuenta"));
                    result.setSaldo(rs.getInt("saldo"));
                    //result.setUsuario(UsuarioDAO.getUsuarioByID(rs.getInt("idUsuario")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new Exception("Error en SQL", e);
            }
        }else {
            System.out.println("No se ha podido recuperar la cuenta, fallo en la conexión");
        }

        return result;
    }
    
    
    
    
	
	public int IngresarSaldo(int idCuenta, int cantidad) throws Exception {
        int result = 0;
        
        con = utils.Connect.getConnect();
        
        if(con != null) {
            	int ingresar = cuentaByID(idCuenta).getSaldo() + cantidad;
               
                try {
                    PreparedStatement q = con.prepareStatement(INGRESAR_DINERO);
                    q.setFloat(1, ingresar);
                    q.setInt(2, idCuenta);
                    result = q.executeUpdate();
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new Exception("Error en SQL", e);
                }

        }else {
            System.out.println("No se ha podido hacer la transacción, fallo en la conexión");
        }
        return result;
    }
	
	public int SacarSaldo(int idCuenta, int cantidad) throws Exception {
        int result = 0;
        
        con = utils.Connect.getConnect();
        
        if(con != null) {
            	int ingresar = cuentaByID(idCuenta).getSaldo() - cantidad;
               
                try {
                    PreparedStatement q = con.prepareStatement(INGRESAR_DINERO);
                    q.setFloat(1, ingresar);
                    q.setInt(2, idCuenta);
                    result = q.executeUpdate();
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new Exception("Error en SQL", e);
                }

        }else {
            System.out.println("No se ha podido hacer la transacción, fallo en la conexión");
        }
        return result;
    }
	
	/**
     * Metodo para eliminar una cuenta
     * @param idCuenta
     * @return
     * @throws Exception
     */
    public int eliminarCuenta(int idCuenta) throws Exception {
        int result = 0;
        con = utils.Connect.getConnect();
        if(con != null) {
            try {
                PreparedStatement ps = con.prepareStatement(ELIMINARCUENTA);
                ps.setInt(1, idCuenta);
                result = ps.executeUpdate();
                this.id=-1;
                this.nCuenta=0;
                this.saldo=0;
                this.u=null;
                
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new Exception("Error en SQL", e);
            }
        }else {
            System.out.println("No se ha podido crear cuenta, fallo en la conexión");
        }
        return result;
    }
    
    /**
     * Metodo para crear una nueva cuenta
     * @return
     * @throws Exception
     */
    public int crearCuenta(Cuenta c) throws Exception {
        int result = 0;
        con = utils.Connect.getConnect();
        if(con != null) {
            
            try {
                PreparedStatement ps = con.prepareStatement(CREARCUENTA);
                ps.setInt(1, c.getnCuenta());
                ps.setFloat(2, c.getSaldo());
                ps.setInt(3, c.getU().getId());                
                result = ps.executeUpdate();
                
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new Exception("Error en SQL", e);
            }
        }else {
            System.out.println("No se ha podido crear cuenta, fallo en la conexión");
        }
	 
        return result;
    }


}
