package model;

import java.io.Serial;
import java.io.Serializable;

public class Cuenta implements Serializable{

	@Serial
	private static final long serialVersionUID = 1L;


	protected int id;
	protected int nCuenta;
	protected int saldo;
	protected Usuario u;
	
	public Cuenta() {}
	
	public Cuenta(int id, int nCuenta, int saldo, Usuario u) {
		super();
		this.id = id;
		this.nCuenta = nCuenta;
		this.saldo = saldo;
		this.u = u;
	}

	public Cuenta(int nCuenta, int saldo, Usuario u) {
		super();
		this.nCuenta = nCuenta;
		this.saldo = saldo;
		this.u = u;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getnCuenta() {
		return nCuenta;
	}

	public void setnCuenta(int nCuenta) {
		this.nCuenta = nCuenta;
	}

	public int getSaldo() {
		return saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}

	public Usuario getU() {
		return u;
	}

	public void setU(Usuario u) {
		this.u = u;
	}

	@Override
	public String toString() {
		return "Cuenta [id=" + id + ", nCuenta=" + nCuenta + ", saldo=" + saldo + ", u=" + u + "]\n";
	}
	
	
	
	
	
}
