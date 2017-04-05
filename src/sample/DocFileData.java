package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by VLAD on 05.04.2017.
 */
public class DocFileData {

    private StringProperty fileName;
    private StringProperty textInFile;

    public DocFileData(){
        this(null,null);
    }

    public DocFileData(String fileName,String textInFile) {
        this.fileName = new SimpleStringProperty (fileName);
        this.textInFile = new SimpleStringProperty (textInFile);
    }

    public String getFileName() {
        return fileName.get();
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public String getTextInFile() {
        return textInFile.get();
    }

    public StringProperty textInFileProperty() {
        return textInFile;
    }

    public void setTextInFile(String textInFile) {
        this.textInFile.set(textInFile);
    }
}
