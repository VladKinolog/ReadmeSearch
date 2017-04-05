package sample;

import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.prefs.Preferences;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<DocFileData> fileInPath = FXCollections.observableArrayList();
    private String path;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Поиск файлов");

        initLayout();
        showInerLayout();
    }


    private void initLayout (){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

            this.path = getPersonFilePath();
            if (this.path.length() > 0 || this.path != null ) {
                primaryStage.setTitle("Пошук - " + path);
            } else {
                primaryStage.setTitle("Пошук");
            }

            RootLayoutController controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showInerLayout () {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("inerLayout.fxml"));
            AnchorPane inerLayout = (AnchorPane) loader.load();

            rootLayout.setCenter(inerLayout);

            // привязка контроллера и передача ему ссылку на себя (класс main)
            Controller controller = loader.getController();
            controller.setMain(this);



        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Возвращает preference файла адресатов, то есть, последний открытый файл.
     * Этот preference считывается из реестра, специфичного для конкретной
     * операционной системы. Если preference не был найден, то возвращается null.
     *
     * @return
     */
    public String getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return filePath;
        } else {
            return null;
        }
    }

    /**
     * Задаёт путь текущему загруженному файлу. Этот путь сохраняется
     * в реестре, специфичном для конкретной операционной системы.
     *
     * @param  - файл или null, чтобы удалить путь
     */
    public void setPersonFilePath(String path) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (path != null) {
            prefs.put("filePath", path);

            // Обновление заглавия сцены.
            primaryStage.setTitle("Пошук - " + path);
        } else {
            prefs.remove("filePath");

            // Обновление заглавия сцены.
            primaryStage.setTitle("Пошук");
        }
    }

    /**
     * Геттер для списка файлов
     * @return список нажваний файлов
     */
    public ObservableList<DocFileData> getFileInPath () {
        return fileInPath;
    }

    public String getUserPath(){
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
