package Signals;

@SuppressWarnings("serial")
public class Goodbye extends AbstractMessage{

	
	// Constructeur principal
	// La valeur test est mise par d�faut mais ce sera le nom de l'utilisateur local (vous pouvez directement le mettre ici)
	public Goodbye(){
		this.nickname = "TEST";
		this.type = typeContenu.GOODBYE;
	}
	
	// On peut créer un Goodbye en donnant le nom de l'utilisateur local en paramètre
	public Goodbye(String name){
		this.nickname = name;
		this.type = typeContenu.GOODBYE;
	}

}
