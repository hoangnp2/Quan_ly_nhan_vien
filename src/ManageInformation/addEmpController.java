package ManageInformation;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import GeneralIformation.Ability;
import GeneralIformation.Contract;
import GeneralIformation.Position;
import Structure.Institute;
import adapter.ConnectToDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class addEmpController {
	private static Institute ins;
	private byte[] imageAv;
	@FXML
	private TextField tefName;
	@FXML
	private TextField tefID;
	@FXML
	private TextField tefNationality;
	@FXML
	private TextField tefIDcard;
	@FXML
	private TextField tefPhoneNumbers;
	@FXML
	private TextField tefPosition;
	@FXML
	private TextField tefTitle;
	@FXML
	private TextField tefInstitute;
	@FXML
	private TextField tefedl;
	@FXML
	private TextField tefIdContract;
	@FXML
	private TextField tefcertificate;
	@FXML
	private ComboBox<String> cbSex;
	@FXML
	private DatePicker dapBirthday;
	@FXML
	private DatePicker dapStart;
	@FXML
	private DatePicker dapEnd;
	@FXML
	private Button butSave;

	@FXML
	private ImageView ImageAva;

	public void initialize() {
		ObservableList<String> list = FXCollections.observableArrayList("Nam", "Nữ");

		cbSex.setItems(list);

		tefInstitute.setEditable(false);
		tefInstitute.setText(ins.getName());
		Image imageDecline = new Image(getClass().getResourceAsStream("save.PNG"));
		butSave.setGraphic(new ImageView(imageDecline));
	}

	public static void setIns(Institute ins) {
		addEmpController.ins = ins;

	}

	public static Institute getIns() {
		return ins;
	}

	public void saveCl() {
		if (tefName.getText().isEmpty() || tefNationality.getText().isEmpty() || tefID.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);

			alert.setContentText("Xin vui lòng nhập đầy đủ thông tin!");

			alert.showAndWait();
		} else {
			ConnectToDatabase conect = new ConnectToDatabase();
			Connection conn;
			conn = conect.Connec("company.db");
			Statement stmt;
			try {
				stmt = conn.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Position pos = new Position(tefID.getText(), tefPosition.getText(), tefTitle.getText(), 0);
			Contract cot = new Contract(tefIdContract.getText(), tefID.getText(), dapStart.getValue(),
					dapEnd.getValue());
			Ability abl = new Ability(tefID.getText(), tefedl.getText(), tefcertificate.getText());
			boolean sex = true;
			if (cbSex.getSelectionModel().getSelectedItem().equals("Nữ")) {
				sex = false;
			}
			try {
				Manager.getInstance(null).addEmployee(ins, tefID.getText(), tefName.getText(), dapBirthday.getValue(),
						sex, tefIDcard.getText(), tefPhoneNumbers.getText(), tefNationality.getText(), pos, cot, abl,
						imageAv);

			} catch (SQLException e) {
				e.printStackTrace();
			}
			Stage stage = (Stage) butSave.getScene().getWindow();
			stage.close();
		}
	}

	public void selectAIamge() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("file PNG", "*.PNG"));
		File selectFile = fileChooser.showOpenDialog(null);
		if (selectFile != null) {
			try {

				String localUrl = selectFile.toURI().toURL().toString();
				Image im = new Image(localUrl);
				ImageAva.setImage(im);

				ByteArrayOutputStream bos = null;

				File f = new File(selectFile.getPath());
				FileInputStream fis = new FileInputStream(f);
				byte[] buffer = new byte[1024];
				bos = new ByteArrayOutputStream();
				for (int len; (len = fis.read(buffer)) != -1;) {
					bos.write(buffer, 0, len);
				}
				imageAv = bos.toByteArray();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();

			}
		} else {

		}
	}

}
