package model;

@SuppressWarnings("serial")
public class ModelException extends Exception {

	public ModelException(String msg){
		super("[Exceção]: " + msg);
	}
}
