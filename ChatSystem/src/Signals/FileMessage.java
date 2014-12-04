package Signals;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author compagnon
 */
public class FileMessage extends AbstractMessage {

    private String namefile;
    private File file;
    private ArrayList<String> dest;
    private long fileSize;//taille en bytes du fichier
    private String extension;

    
    public FileMessage(String namefile, ArrayList<String> dest, File file){
        this.namefile = namefile;
        this.dest = dest;
        this.fileSize=file.length();
        String nom=file.getAbsolutePath(); 
        this.extension=nom.substring(nom.lastIndexOf("."));
    }
        

    public String getNamefile() {
        return namefile;
    }
    
    public long getSize() {
        return fileSize;
    }

    /**
     * @param namefile the namefile to set
     */
    public void setNamefile(String namefile) {
        this.namefile = namefile;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
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
    
    /**
     * @returns the extension
     */
    public String getExtension() {
        return extension;
    }
    
}