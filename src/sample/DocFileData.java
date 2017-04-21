package sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by VLAD on 05.04.2017.
 */
public class DocFileData {

    private StringProperty fileName;
    private StringProperty textInFile;
    private ObjectProperty<ArrayList<Text>> textArryInFile;

    public DocFileData(){
        this.fileName = null;
        this.textInFile = null;
        this.textArryInFile = null;
    }

    public DocFileData(String fileName,String textInFile) {
        this.fileName = new SimpleStringProperty (fileName);
        this.textInFile = new SimpleStringProperty (textInFile);

    }

    /**
     * Конструктор обьекта по списку обьектов {@link javafx.scene.text.Text}
     * @param fileName Название файла
     * @param textList список обьектов {@link javafx.scene.text.Text}
     */
    public DocFileData(String fileName, ArrayList<Text> textList) {
        this.fileName = new SimpleStringProperty(fileName);
        this.textArryInFile = new SimpleObjectProperty<>(textList);
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

    public ArrayList<Text> getTextArryInFile() {
        return textArryInFile.get();
    }

    public ObjectProperty<ArrayList<Text>> textArryInFileProperty() {
        return textArryInFile;
    }

    public void setTextArryInFile(ArrayList<Text> textArryInFile) {
        this.textArryInFile.set(textArryInFile);
    }
}
