package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;


/**
 * Created by VLAD on 04.04.2017.
 */
public class RootLayoutController {

// ссылка на главное приложение
    private Main main;
    private String path;


    /**
     * вызывается главным приложением
     * @param main
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * Обработка диалогового окна выбора пути к файлу
     */

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Doc files (*.doc)", "*.doc");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(main.getPrimaryStage());

        path = file.getPath();
        int i = path.lastIndexOf("\\");
        path = path.substring(0,i);
        main.setPath(path);
        main.setPersonFilePath(path);


        System.out.println(path);



    }

    /**
     * Открывает окно с информацией о программе
     */

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Пошук файлів");
        alert.setHeaderText("");
        alert.setContentText("Програма пошуку файлив");

        alert.showAndWait();
    }

    /**
     * Закрывает приложение.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
