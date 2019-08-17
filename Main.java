package sample;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {     //Aqui hay una clase y abstraccion.

    public MenuBar rootmenu;        //Aqui hay un atributo.
    public Menu archivo;
    public MenuItem cargar;
    private FileChooser fileChooser = new FileChooser();        //Aqui se da encapsulamiento e instanciacion.
    private File selectedFile;
    private GridPane gridpane;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("CSV");

        archivo=new Menu("Archivo");

        cargar=new MenuItem("Seleccionar");

        archivo.getItems().add(cargar);

        cargar.setOnAction(new EventHandler<ActionEvent>() {        //Aqui hay un metodo.
            public void handle(ActionEvent e)
            {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File CSV", "*.csv"));
                selectedFile = fileChooser.showOpenDialog(primaryStage);
                try {
                    openFile(selectedFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        rootmenu=new MenuBar();
        rootmenu.getMenus().add(archivo);

        gridpane=new GridPane();
        gridpane.setGridLinesVisible(true);

        VBox vBox = new VBox(rootmenu,gridpane);

        ScrollPane scrollpane=new ScrollPane();
        scrollpane.setContent(vBox);

        Scene scene = new Scene(scrollpane, 500, 300);

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void openFile(File file) throws IOException {       //Aqui hay herencia.
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            if(br.readLine()!=null) {

                CleanGrid();
                int fila=0;
                String line;
                while((line = br.readLine()) !=null) {
                    int columna=0;
                    String [] x=line.split(",",-1);
                    for(String i:x) {
                        Label h=new Label(i);
                        h.setFont(new Font("Arial",15));
                        gridpane.add(h,columna,fila);
                        columna++;}
                    fila++;}
            }
        }

        catch (IOException ex) {
            Logger.getLogger(
                    Main.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    private void CleanGrid(){       //Aqui hay sobrecarga.
        Node node=gridpane.getChildren().get(0);
        gridpane.getChildren().clear();
        gridpane.getChildren().add(0,node);
    }

}
