package chatsystem.Signals;

@SuppressWarnings("serial")
public class HelloAck extends AbstractMessage{

	
	// Constructeur principal
	// La valeur test est mise par d�faut mais ce sera le nom de l'utilisateur local.
	public HelloAck(){
		this.nickname = "TEST";
		this.type = typeContenu.HELLOACK;
	}
	
	// Ou en passant le nom d'utilisateur en paramètre
	public HelloAck(String name){
		this.nickname = name;
		this.type = typeContenu.HELLOACK;
	}
	
}
