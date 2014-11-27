package Signals;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;


public class mainTEST {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		/*
		 * PORTS COMMUNS À TOUS :
		 * UDP RECEIVER : 9876
		 * TCP SERVER : 6789
		 * 
		 */
		
		
		Hello hello = new Hello();
		Goodbye goodbye = new Goodbye();
		TextMessage message = new TextMessage("Envoi d'un message test");
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ByteArrayInputStream bin = null;
		ObjectOutput out = null;
		ObjectInput in = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(hello);
		  byte[] byteArray = bos.toByteArray();
		  
		  ByteArrayInputStream byteIn = new ByteArrayInputStream(byteArray);
		  in = new ObjectInputStream(byteIn);
		  AbstractMessage aMessage = (AbstractMessage) in.readObject();
		  if (aMessage.getTypeContenu() == typeContenu.HELLO){
			  Hello helloSerialise = (Hello) aMessage;
			  System.out.println("C'est un HELLO ! " + helloSerialise.getNickname());
		  }
		} finally {
		  try {
		    if (out != null) {
		      out.close();
		    }
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		  try {
		    bos.close();
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		}
		

	}

}
