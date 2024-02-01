package lk.ijse.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.ClientHandler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXTextField txtName;
    private ServerSocket serverSocket;
    static String name;
    //static String filePath;

    public void initialize(){
        //txtName.setStyle("-fx-text-fill: White; -fx-font-size: 15; -fx-border-color: white; -fx-background-color:Black");
        try {
            serverSocket = new ServerSocket(999);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!serverSocket.isClosed()){
                    try{
                        Socket socket = serverSocket.accept();
                        System.out.println("New user connected");
                        ClientHandler clientHandler = new ClientHandler(socket);
                        clientHandler.listenMessage();

                    }catch(IOException e){

                    }


                }
            }
        }).start();

    }

    @FXML
    void btnJoinOnAction(ActionEvent event) throws IOException {
        name = txtName.getText();

        if(name.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please enter your user name").show();
            return;
        }

        txtName.clear();
        //Profile.setImage(new Image("/images/user-image-with-black-background.png"));


        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/ChatRoomForm.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(name);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) throws IOException {
        btnJoinOnAction(event);

    }

}
