package Signals;
import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class AbstractMessage implements Serializable {

	// Nom de l'utilisateur local
	protected String nickname;
	// Identifie la nature du message envoy
	protected typeContenu type;
	
	
	// Getters et Setters
	
	public String getNickname(){
		return this.nickname;
	}
	
	public typeContenu getTypeContenu(){
		return this.type;
	}
	
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	
	public void setTypeContenu(typeContenu type){
		this.type = type;
	}
}
