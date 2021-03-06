package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class Controller {

    @FXML
    private TableView<DocFileData> tableView;

    @FXML
    private TableColumn<DocFileData, String> fileName;

    @FXML
    private Text choiceFileText;

    @FXML
    private TextField searchText;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private CheckBox checkBoxFinder;

    //cсылка на главное приложение.
    private Main main;
    private String path;


    //Конструктор класса ??
    public Controller() {

    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    public void initialize(){
        fileName.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());

        showTextInFile(null);

        //добавляем слушателя событий про клику на строку с названием файла
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showTextInFile(newValue));

    }

    public void setMain(Main main) {
        this.main = main;

        tableView.setItems(main.getFileInPath());
    }

    /**
     * Отображение данных при выборе
     * @param docFileData
     */
    private void showTextInFile (DocFileData docFileData){
        if (docFileData != null){
            fileName.setText(docFileData.getFileName());
            choiceFileText.setText(docFileData.getTextInFile());
            scrollPane.setHvalue(0.0);
            scrollPane.setVvalue(0.0);
        } else {
            fileName.setText("");
            choiceFileText.setText("");
        }
    }

    @FXML
    private void onFindButtonClick() throws FileNotFoundException {
        progressIndicator.setOpacity(1);
        String text = searchText.getText().trim();
        String [] findText = arrayTextFinder(text);
        System.out.println("Кнопка поиск нажата");
        path = main.getUserPath();

        if (validText ()){
            File docFile = new File(path);
            // file input stream with docFile

            File[] files = docFile.listFiles((dir, name) ->  name.toLowerCase().endsWith(".doc"));
            //Runtime.getRuntime().exec("cmd /c start winword " + "\""+ files[2].getCanonicalFile() +"\"");
            main.getFileInPath().clear();
            for (File file : files) {
                int numOfCoincidences = 0;
                System.out.println(file.getName());

                try (FileInputStream finStream = new FileInputStream(file.getAbsolutePath())) {

                    HWPFDocument doc = new HWPFDocument(finStream);
                    // import  org.apache.poi.hwpf.extractor.WordExtractor
                    WordExtractor wordExtract = new WordExtractor(doc);

                    // text stores the each line from the document
                    String fileText = wordExtract.getText();


                    // для каждого элемента массива если хотя бы 1 строка совпадет значит файл отображается в таблице
                    for (String fText : findText) {
                        int i = fileText.toLowerCase().indexOf(fText.toLowerCase().trim());
                        if (i >= 0) {
                            //fileText = fileText.replaceAll(fText,">>>"+fText+"<<<");
                            numOfCoincidences ++;
                        }

                    }
                    if (checkBoxFinder.isSelected()){
                        if (numOfCoincidences > 0) main.getFileInPath().add(new DocFileData(file.getName(), fileText));
                        System.out.println("choiceFileTex isPressed");
                    } else {
                        if (numOfCoincidences == findText.length) main.getFileInPath().add(new DocFileData(file.getName(), fileText));
                        System.out.println("choiceFileTex isRealised");
                    }

                    // printing lines from the array

                    //closing fileinputstream
                    finStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

                System.out.println("коректный текст");
        }
        progressIndicator.setOpacity(0);
    }

    private boolean validText (){
        String errorMessage = "";

        if (searchText.getText() == null || searchText.getText().trim().length() == 0) {
            errorMessage += "Перед пошуком введіть, будь ласка, текст!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Помилка вводу данних");
            alert.setHeaderText("Некоректний ввод данних");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    private String [] arrayTextFinder (String findText) {
        return findText.split(",");
    }

    private ArrayList<Text> textSplitter(String [] arryFindWord, String text) {
        ArrayList<Text> textArrayList= new ArrayList<Text>();
        String [] str = new String[0];
        Text t = new Text();
        t.setFill(Color.RED);
        for (String s:arryFindWord) {
            str = text.split(s);
            for (String string:str) {
                textArrayList.add(new Text(string));
                t.setText(s);
                textArrayList.add(t);
            }
        }
        return textArrayList;
    }
}
