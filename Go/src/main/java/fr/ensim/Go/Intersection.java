package fr.ensim.Go;

import java.io.Serializable;

public class Intersection  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private Pierre pierre;
	
	public Intersection(int x, int y){
		this.x = x;
		this.y = y;
		pierre = null;
	}
	
	public Intersection(int x, int y, Pierre p){
		this.x = x;
		this.y = y;
		pierre = p;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setPierre(Pierre p){
		pierre = p;
	}
	
	public Pierre getPierre(){
		return pierre;
	}
}
