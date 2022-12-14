/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tabledatasample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tabledatasample.model.Vacante;

/**
 *
 * @author afelipelc
 */
public class MainController implements Initializable {

    @FXML
    private TableView<Vacante> vacantesTable;
    @FXML
    private TableColumn<Vacante,String> empresaColumn;
    @FXML
    private TableColumn<Vacante, String> especialidadColumn;
    @FXML
    private TableColumn<Vacante, Float> salarioColumn;    
    @FXML
    private TextField empresaText;
    @FXML
    private TextField especialidadText;
    @FXML
    private TextField salarioText;
    @FXML
    private Button oKayButton;
    
    private ObservableList<Vacante> listaVacantes;
    private Vacante selectedItem;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // setup Collums
        this.empresaColumn.setCellValueFactory(new PropertyValueFactory("empresa"));
        this.especialidadColumn.setCellValueFactory(new PropertyValueFactory("especialidad"));
        this.salarioColumn.setCellValueFactory(new PropertyValueFactory("salario"));
        
        //Definir objetos de inicio como prueba
        this.listaVacantes = FXCollections.observableArrayList(
                new Vacante("Asosa Personnel", "Instaladores de GPS",70000),
                new Vacante("Addon Technologies Inc.", "Desarllo .NET",60000),
                new Vacante("Compañía Integra Soluciones", "Administración de proyectos PMI",60000)
                );
        //aplicar lista de objetos Vacante a la tabla
        vacantesTable.setItems(listaVacantes);
        
        //Control de selección de elemento de la lista
        vacantesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                selectedItem = vacantesTable.getSelectionModel().getSelectedItem()!=null? vacantesTable.getSelectionModel().getSelectedItem() : null;
                if(selectedItem != null)
                    setTextFieldData();
            }
        });
        
        
        this.oKayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                boolean nuevo;
                nuevo = selectedItem == null;
                selectedItem = nuevo ? new Vacante() : selectedItem;
                
                setVacanteData();
                
                if(nuevo && !selectedItem.empresaProperty().getValue().equals("") && !selectedItem.especialidadProperty().getValue().equals(""))
                    listaVacantes.add(selectedItem);
                //clear
                selectedItem = null;
                setTextFieldData();
                
                empresaText.requestFocus();
            }
        });
        
    }
    
    private void setTextFieldData()
    {
        this.empresaText.setText(selectedItem != null ? selectedItem.empresaProperty().getValue() : "");
        this.especialidadText.setText(selectedItem != null ? selectedItem.especialidadProperty().getValue() : "");
        this.salarioText.setText(selectedItem != null ? selectedItem.salarioProperty().getValue()+"" : "");
    }
    
    private void setVacanteData()
    {
        this.selectedItem.setEmpresa(this.empresaText.getText());
        this.selectedItem.setEspecialidad(this.especialidadText.getText());
        try
        {
            this.selectedItem.setSalario(Float.valueOf(this.salarioText.getText()));
        }catch(Exception ex)
        {
            this.selectedItem.setSalario(Float.NaN);
            System.err.println("Error parsing Float: " + ex.getCause());
        }
    }
}
