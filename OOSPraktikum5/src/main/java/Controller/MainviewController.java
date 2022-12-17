package Controller;

import bank.PrivateBank;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.IncomingException;
import bank.exceptions.OutgoingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class MainviewController implements Initializable {

    private Stage stage;
    private Scene scene;
    private final ObservableList<String> accList= FXCollections.observableArrayList();
    private PrivateBank Bank;

    {
        try {
            Bank = new PrivateBank("Bank", 0.12,0.1,"Bank");
        } catch (IncomingException | OutgoingException e) {
            System.out.println(e);
        }
    }

    ;

    @FXML
    private Text text;
    @FXML
    private Button button;
    @FXML
    private ListView<String> accListView;
    @FXML
    private Parent root;



    private void updateListView(){
        accList.clear();
        accList.addAll(Bank.getAllAccounts());
        accList.sort(Comparator.naturalOrder());
        accListView.setItems(accList);
    }

    public void initialize(URL url, ResourceBundle resourceBundle){
        updateListView();

        ContextMenu contextMenu=new ContextMenu();
        MenuItem view = new MenuItem("View account;");
        MenuItem delete = new MenuItem("Delete account");

        contextMenu.getItems().addAll(view,delete);
        accListView.setContextMenu(contextMenu);
        AtomicReference<String> selected = new AtomicReference<>();



        accListView.setOnMouseClicked(mouseEvent -> {
            selected.set(String.valueOf(accListView.getSelectionModel().getSelectedItems()));
            text.setText("Account with the name "+selected.toString().replace("[","").replace("]","")+" is selected");
            if(mouseEvent.getClickCount()==2){
                try{
                    FXMLLoader loader= new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("Account-view.fxml")));
                    root=loader.load();

                   AccountviewController accountviewController=loader.getController();
                   accountviewController.setupData(Bank,selected.toString().replace("[","").replace("]",""));
                }catch (IOException e){
                    e.printStackTrace();
                }
                stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });

        delete.setOnAction(event ->{
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle("Delete Account");
         alert.setContentText("Are you sure want to delete this account?");
         Optional<ButtonType> result = alert.showAndWait();

         if(result.isPresent() && result.get()==ButtonType.OK){
             try{
                 Bank.deleteAccount(selected.toString().replace("[","").replace("]",""));
             }catch (AccountDoesNotExistException e){
                 System.out.println(e);
             }catch (IOException e){
                 e.printStackTrace();
             }

             text.setText(selected.toString().replace("[","").replace("]","")+" is deleted succesfully!");
             updateListView();
         }
        });

        view.setOnAction(event ->{
            stage = (Stage) root.getScene().getWindow();
            try{
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("Account-view.fxml")));
                root = loader.load();

                AccountviewController accountviewController=loader.getController();
                accountviewController.setupData(Bank,selected.toString().replace("[","").replace("]",""));
            }catch (IOException e){
                e.printStackTrace();
            }
            scene = new Scene(root);
            stage.setTitle(selected.toString().replace("[","").replace("]",""));
            stage.setScene(scene);
            stage.show();
        });
        button.setOnMouseClicked(event -> {
            text.setText("");
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Add new account");
            dialog.setHeaderText("Add a new account to bank");
            dialog.getDialogPane().setMinWidth(300);


            Label nameLabel = new Label("Name: ");
            TextField nameTextField = new TextField();

            GridPane grid = new GridPane();
            grid.add(nameLabel, 2, 1);
            grid.add(nameTextField, 3, 1);
            dialog.getDialogPane().setContent(grid);
            dialog.setResizable(true);

            ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

            dialog.setResultConverter(buttonType -> {
                if (buttonType == buttonTypeOk) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);

                    if (!Objects.equals(nameTextField.getText(), "")) {

                        try {
                            Bank.createAccount(nameTextField.getText());
                            text.setText("Account [" + nameTextField.getText() + "] is added to the bank");
                        } catch (AccountAlreadyExistsException | IOException e) {
                            alert.setContentText("Duplicated account!");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                text.setText("Account [" + nameTextField.getText() + "] is already in the bank!");
                            }
                            System.out.println(e.getMessage());
                        }
                        updateListView();
                    }
                    else {
                        alert.setContentText("Please insert a valid name!");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            text.setText("No new account was added!");
                        }
                    }

                }
                return null;
            });

            dialog.show();
        });
    }


}
