package Signals;

@SuppressWarnings("serial")
public class Hello extends AbstractMessage {

	
	// Constructeur principal
	// La valeur test est mise par d�faut mais ce sera le nom de l'utilisateur local.
	public Hello(){
		this.nickname = "TEST";
		this.type = typeContenu.HELLO;
	}
	
	// Ou en passant le nom d'utilisateur en paramètre
	public Hello(String name){
		this.nickname = name;
		this.type = typeContenu.HELLO;
	}
	
}
