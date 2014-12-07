package Signals;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author compagnon
 */
public class FileMessage extends AbstractMessage {

	private String fileName;
	private ArrayList<String> dest;
	private long fileSize;//taille en bytes du fichier


	public FileMessage(String fileName, ArrayList<String> dest,long fileSize){
		this.fileName = fileName;
		this.dest = dest;
		this.fileSize=fileSize;
	}


	public String getNamefile() {
		return fileName;
	}

	public long getSize() {
		return fileSize;
	}

	/**
	 * @param namefile the namefile to set
	 */
	public void setNamefile(String namefile) {
		this.fileName = namefile;
	}


	/**
	 * @return the dest
	 */
	public ArrayList<String> getDest() {
		return dest;
	}

	/**
	 * @param dest the dest to set
	 */
	public void setDest(ArrayList<String> dest) {
		this.dest = dest;
	}

	/**
	 * @returns the file size
	 */
	public long getFileSize() {
		return fileSize;
	}



}