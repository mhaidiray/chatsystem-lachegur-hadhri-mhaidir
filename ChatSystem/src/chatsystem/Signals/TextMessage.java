package chatsystem.Signals;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class TextMessage extends AbstractMessage{

	// Structure d'un nom : NOM@ADRESSEIP (ex: "charles@192.168.1.2") (interdire le @ dans le nom)
	private ArrayList<String> listNicknamesDest;
	private String message;
	
	
	/* Constructeur principal
	 * La valeur test est mise par d�faut mais ce sera le nom de l'utilisateur local.
	 * @param message : le message � envoyer
	 * Remarque : Une nouvelle liste de nickname (vide) est cr��e, il faut ajouter un par un les destinataires via une m�thode
	 */
	public TextMessage(String message){
		this.nickname = "TEST";
		this.type = typeContenu.TEXTMESSAGE;
		this.message = message;
		this.listNicknamesDest = new ArrayList<String>();
	}
	
	/*
	 * Constructeur secondaire au cas o� l'on poss�de d�j� une liste d'utilisateurs
	 */
	public TextMessage(String message, ArrayList<String> list){
		this.nickname = "TEST";
		this.type = typeContenu.TEXTMESSAGE;
		this.message = message;
		this.listNicknamesDest = list;
	}
	
	/*
	 * Constructeur tertiaire avec tous les paramètres
	 */
	public TextMessage(String name, String message, ArrayList<String> list){
		this.nickname = name;
		this.type = typeContenu.TEXTMESSAGE;
		this.message = message;
		this.listNicknamesDest = list;
	}
	
	/*
	 * Constructeur n°4 avec nom et message
	 */
	public TextMessage(String name, String message){
		this.nickname = name;
		this.type = typeContenu.TEXTMESSAGE;
		this.message = message;
		this.listNicknamesDest = new ArrayList<String>();
	}
	
	// Getters et Setters
	
	public ArrayList<String> getListNicknamesDest(){
		return this.listNicknamesDest;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	
	// M�thodes
	
	public void addNickame(String nicknameDest){
		this.listNicknamesDest.add(nicknameDest);
	}
	
	public void removeNickname(String nicknameDest){
		this.listNicknamesDest.remove(nicknameDest);
	}
	
	
	
}
