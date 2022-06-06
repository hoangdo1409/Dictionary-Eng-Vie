package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import apiTranslator.Languages;
import application.APITrans;
import dbase.databaseWord;

import dictionary.Dictionary;
import dictionary.DictionaryManagement;
import dictionary.Standardize;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import speech.TextToSpeech;
import task.LoadListTask;

public class HomeController implements Initializable{
	@FXML
	private JFXTextField searchField; 

	@FXML
	private JFXTextField fieldTu;
	
	@FXML
	private JFXTextField fieldTuLoai;
	
	@FXML
	private JFXTextField fieldPhienAm;
	
	@FXML
	private JFXTextField fieldNghia;
	
	@FXML
	private JFXTextField searchDB;
	
	@FXML
	private JFXTextArea resultArea;
	
	@FXML
	private JFXTextArea resultDB;
	
	@FXML
	private JFXTextArea resultAPI;
	
	@FXML
	private JFXTextArea searchAPI;
	
	@FXML
	private JFXButton butTrans;
	
	@FXML
	private JFXButton butOff;
	
	@FXML
	private JFXButton butFav;
	
	@FXML
	private JFXButton butTrash;
	
	@FXML
	private JFXButton butDB;
	
	@FXML
	private JFXButton butAPI;
	
	@FXML 
	private JFXButton butSpeech;
	
	@FXML 
	private JFXButton butAdd;
	
	@FXML 
	private JFXButton butAgree;
	
	@FXML 
	private JFXButton butAddFav;
	
	@FXML 
	private JFXButton butReset;
	
	@FXML 
	private JFXButton butDel;
	
	@FXML
	private JFXButton butXFav;
	
	@FXML
	private JFXButton butReturn;
	
	@FXML
	private JFXButton butXTrash;
	
	@FXML
	private JFXButton butFvSpeech;
	
	@FXML
	private JFXButton butTransAPI;
	
	@FXML
	private JFXButton butEditDB;
	
	@FXML
	private JFXButton butSpeechDB;
	
	@FXML
	private JFXButton butDelDB;
	
	@FXML
	private JFXButton butPinDB;
	
	@FXML
	private JFXButton butUnPinDB;
	
	@FXML
	private JFXButton butSpeechAPI1;
	
	@FXML
	private JFXButton butSpeechAPI2;
	
	@FXML 
	private AnchorPane paneControl;
	
	@FXML 
	private AnchorPane paneTrans;
	
	@FXML 
	private AnchorPane paneFav;
	
	@FXML 
	private AnchorPane paneAdd;
	
	@FXML 
	private AnchorPane paneTrash;
	
	@FXML 
	private AnchorPane paneDB;
	
	@FXML 
	private AnchorPane paneAPI;
	
	@FXML
	private ListView<String> listTrans;
	
	@FXML
	private ListView<String> listFav; 
	
	@FXML
	private ListView<String> listTrash;
	
	@FXML
	private ListView<String> listPinDB;
	
	@FXML
	private ListView<String> listHintDB;
	
	@FXML
	private Label labelWarnning;
	
	@FXML
	private Label labelSuccess;
	
	@FXML
	private ChoiceBox<Languages> langFrom;
	
	@FXML
	private ChoiceBox<Languages> langTo;
	
	@FXML
	private ProgressBar progress;
	
	private String wordTarget = "";
	private String wordExplain = "";
	private String wordResult = "";
	
	private int indexSelect = -1;
	private int indexSelectDB = -1;
	private int indexHintDB = -1;
	private String favTarget = "";
	private String trashTarget = "";
	
	private String DBTarget = "";
	
	private final String urlDic = "D:\\CodeJava\\DictionaryProject\\src\\data\\Word2.txt";
	private final String urlDicFav = "D:\\CodeJava\\DictionaryProject\\src\\data\\favorite.txt";
	
	private DictionaryManagement dictionary = new DictionaryManagement();
	private DictionaryManagement dicFavorite = new DictionaryManagement();
	private DictionaryManagement dicTrash = new DictionaryManagement();
	
	private boolean checkAddTo = true;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadData();
		setEventSearchField();
		setEventSearchDB();
		setEventAPI();
		hintDB();
	}
	
	private void loadData() {
		dictionary.insertFromFile(urlDic);
		dicFavorite.insertFromFile(urlDicFav);
		
		loadListThread(listTrans, dictionary.getDictionary(), false);
		loadListThread(listFav, dicFavorite.getDictionary(), true);
	}
	
	private void loadListThread(ListView<String> list, Dictionary dic, boolean getAll) {
		LoadListTask lishTask = new LoadListTask(dic, getAll);
		list.itemsProperty().bind(lishTask.valueProperty());
		new Thread(lishTask).start();
	}
	
	@FXML
	private void clickedList(MouseEvent e) {
		
		if (e.getSource() == listTrans && listTrans.getSelectionModel().getSelectedIndex() >= 0) {
			if (listTrans.getSelectionModel().isSelected(indexSelect)) {
				listTrans.getSelectionModel().clearSelection();
				wordTarget = "";
				resultArea.setText("");
			} else {
				wordTarget = listTrans.getSelectionModel().getSelectedItem();
		    	wordExplain = dictionary.dictionaryLookup(wordTarget);
		    	wordResult = wordTarget + " - " + wordExplain.replace("\t", "\n◦");
		    	
				resultArea.setText(wordResult);
			}
			
			indexSelect = listTrans.getSelectionModel().getSelectedIndex();	
		}
		
		if (e.getSource() == listTrash && listTrash.getSelectionModel().getSelectedIndex() >= 0) {
			trashTarget = listTrash.getSelectionModel().getSelectedItem();
			int index = trashTarget.indexOf("\t");
			trashTarget = trashTarget.substring(0, index);
		}
		
		if (e.getSource() == listFav && listFav.getSelectionModel().getSelectedIndex() >= 0) {
			favTarget = listFav.getSelectionModel().getSelectedItem();
			int index = favTarget.indexOf("\t");
			favTarget = favTarget.substring(0, index);
		}
		
		if (e.getSource() == listPinDB && listPinDB.getSelectionModel().getSelectedIndex() >= 0) {
			if (listPinDB.getSelectionModel().isSelected(indexSelectDB)) {
				listPinDB.getSelectionModel().clearSelection();
				DBTarget = "";
				resultDB.setText("");
			} else {
				DBTarget = listPinDB.getSelectionModel().getSelectedItem();
				DBTarget = DBTarget.replace("◦", "").trim();
				resultDB.setText(databaseWord.showWordNeedFind(DBTarget));
				listHintDB.getSelectionModel().clearSelection();
			}
			
			indexSelectDB = listPinDB.getSelectionModel().getSelectedIndex();
		}
		
		if (e.getSource() == listHintDB && listHintDB.getSelectionModel().getSelectedIndex() >= 0) {	
			if (listHintDB.getSelectionModel().isSelected(indexHintDB)) {
				listHintDB.getSelectionModel().clearSelection();
				DBTarget = "";
				resultDB.setText("");
			} else {
				DBTarget = listHintDB.getSelectionModel().getSelectedItem();
				DBTarget = DBTarget.trim();
				resultDB.setText(databaseWord.showWordNeedFind(DBTarget));
				listPinDB.getSelectionModel().clearSelection();
			}
			
			indexHintDB = listHintDB.getSelectionModel().getSelectedIndex();
		}
	}

	private void setEventSearchDB() {
		searchDB.setOnKeyPressed(
				event -> {
					switch (event.getCode()) {
						case ENTER:
							DBTarget = searchDB.getText().trim();
							resultDB.setText(databaseWord.showWordNeedFind(DBTarget));
							if (resultDB.getText().isEmpty()) {
								resultDB.setText(DBTarget);
							}
							break;
						case BACK_SPACE:
							DBTarget = "";
							resultDB.setText("");
						default:
							DBTarget = "";
							break;
					}
				}
		);
		
		searchDB.setOnKeyReleased(
			event -> {
				hintDB();
		});
	}
	
	private void setEventSearchField() {
		searchField.setOnKeyPressed(
				event -> {
					switch (event.getCode()) {
						case ENTER:
							wordTarget = searchField.getText().trim();
							wordExplain = dictionary.dictionaryLookup(wordTarget);
							wordResult = wordTarget + " - " +wordExplain.replace("\t", "\n◦");
							resultArea.setText(wordResult);
							loadListThread(listTrans, dictionary.getDictionary(), false);
							break;
						
						case BACK_SPACE:
							wordTarget = "";
							resultArea.setText("");
						default:
							break;
					}
				}
		);

		searchField.setOnKeyReleased(
			event -> {
				switch (event.getCode()) {
					case ENTER:
						break;
					default:
						String str = searchField.getText().trim();
						if (!dictionary.dictionaryFilter(str).isEmpty()) {
							loadListThread(listTrans, dictionary.dictionaryFilter(str), false);	
						}
						break;
				}
		});
	}
	
	private void setEventAPI() {
		langFrom.getItems().addAll(Languages.values());
		langTo.getItems().addAll(Languages.values());

		langFrom.setValue(Languages.English);
		langTo.setValue(Languages.Vietnamese);
		
		searchAPI.setOnKeyPressed(
				event -> {
					switch (event.getCode()) {
						case BACK_SPACE:
							resultAPI.textProperty().unbind();
							resultAPI.setText("");
							break;
						default:
							break;
					}
				}
		);
	}

	@FXML
	private void eventFunctionButton(ActionEvent actionEvent) {
		//PaneTrans
		if (actionEvent.getSource() == butSpeech) {
			TextToSpeech.speech(wordTarget);
		}
		
		if (actionEvent.getSource() == butDel) {
			if (!wordTarget.isEmpty()) {
				dictionary.dictionaryDelWord(wordTarget);
				dicTrash.dictionaryAddWord(wordTarget, wordExplain);
				
				listTrans.getItems().remove(wordTarget);
				listTrash.getItems().add(wordTarget + "\t" + wordExplain);	
				
				listTrans.getSelectionModel().clearSelection();
				
				showAlert(AlertType.INFORMATION, wordTarget + " đã được xóa!");
				resultArea.setText("");
				wordTarget = "";
				wordExplain = "";
			}
		}
		
		if (actionEvent.getSource() == butAddFav) {		
			if (!wordTarget.isEmpty()) {
				if (!dicFavorite.dictionaryLookup(wordTarget).isEmpty()) {
					showAlert(AlertType.INFORMATION, wordTarget + " đã có trong mục ưa thích!");
				} else {
					dicFavorite.dictionaryAddWord(wordTarget, wordExplain);
					listFav.getItems().add(wordTarget + "\t" + wordExplain);
					showAlert(AlertType.INFORMATION, wordTarget + " đã được thêm vào mục ưa thích!");
				}
			}
		}
		
		//PaneFav		
		if (actionEvent.getSource() == butFvSpeech) {
			TextToSpeech.speech(favTarget);
		}
		
		if (actionEvent.getSource() == butXFav) {
			if (!favTarget.isEmpty()) {
				int index = listFav.getSelectionModel().getSelectedIndex();
				dicFavorite.dictionaryDelWord(favTarget);
				listFav.getItems().remove(index);

				listFav.getSelectionModel().clearSelection();
			}
		}
		
		//PaneAdd
		if (actionEvent.getSource() == butAgree && checkAddTo) {
			if (fieldTu.getText().trim().isEmpty() || fieldNghia.getText().trim().isEmpty()) {
				showAlert(AlertType.ERROR, "Vui lòng điền đầy đủ thông tin bắt buộc*!");
			} else {
				
				String Target = fieldTu.getText().toLowerCase().trim();
				String Explain = fieldTuLoai.getText() + '\t' 
						+ fieldPhienAm.getText() + '\t'
						+ fieldNghia.getText();
				
				boolean check = true;
				if (!dictionary.dictionaryLookup(Target).isEmpty()) {
					check = showAlert(AlertType.WARNING, "Từ này đã tồn tại! \n "
									+ "Bạn có muốn thay thế từ này không?");
				}
				
				if(check) {
					dictionary.dictionaryAddWord(Target, Explain);
					listTrans.getItems().add(Target);
					showAlert(AlertType.INFORMATION, "Thêm từ mới thành công!");
				}

			}
		} 
		
		if (actionEvent.getSource() == butAgree && !checkAddTo) {
			if (fieldTu.getText().trim().isEmpty() || fieldNghia.getText().trim().isEmpty()) {
				showAlert(AlertType.ERROR, "Vui lòng điền đầy đủ thông tin bắt buộc*!");
			} else {
				String wordNeedAdd = fieldTu.getText().toLowerCase().trim();
				String spellNeedAdd = fieldPhienAm.getText().trim();
				String meanNeedAdd = fieldTuLoai.getText().trim() + " " + fieldNghia.getText().trim();
								
				if (databaseWord.checkWordExisted(wordNeedAdd)) {
					if (showAlert(AlertType.WARNING, "Từ này đã tồn tại! \n "
							+ "Bạn có muốn sửa từ này không?")) {
						paneAdd.setVisible(false);
						paneDB.setVisible(true);
						
						resultDB.setEditable(true);
						searchDB.setEditable(false);
						
						DBTarget = wordNeedAdd;
						searchDB.setText(DBTarget);
						resultDB.setText(databaseWord.showWordNeedFind(DBTarget));
						
						resultDB.setStyle("-fx-background-color : #ddffff;"
										+ "-fx-background-radius : 2em");
						butEditDB.setStyle("-fx-background-color : #ddffff");
					}
				} else {
					databaseWord.addWord(wordNeedAdd, spellNeedAdd, meanNeedAdd);
					showAlert(AlertType.INFORMATION, "Thêm từ mới thành công!");
				}
			}
		}
		
		if (actionEvent.getSource() == butReset) {
			fieldTu.setText("");
			fieldTuLoai.setText("");
			fieldPhienAm.setText("");
			fieldNghia.setText("");
		}
		
		//PaneTrash
		if (actionEvent.getSource() == butReturn) {
			if (!trashTarget.isEmpty()) {
				dictionary.dictionaryAddWord(trashTarget, dicTrash.dictionaryLookup(trashTarget));
				dicTrash.dictionaryDelWord(trashTarget);
				
				listTrans.getItems().add(trashTarget);
				
				int index = listTrash.getSelectionModel().getSelectedIndex();
				dicTrash.dictionaryDelWord(trashTarget);
				listTrash.getItems().remove(index);

				listTrash.getSelectionModel().clearSelection();
			}
		}
		
		if (actionEvent.getSource() == butXTrash) {
			if (!trashTarget.isEmpty()) {
				int index = listTrash.getSelectionModel().getSelectedIndex();
				dicTrash.dictionaryDelWord(trashTarget);
				listTrash.getItems().remove(index);
				
				listTrash.getSelectionModel().clearSelection();
			}

		}	
	}
	
	@FXML
	private void eventButtonDB(ActionEvent actionEvent) {
		//PaneDB
		if (actionEvent.getSource() == butSpeechDB) {
			TextToSpeech.speech(DBTarget);
		}
		
		if (actionEvent.getSource() == butEditDB) {
			if (!resultDB.getText().isEmpty()) {
				if (!resultDB.isEditable()) {
					resultDB.setEditable(true);
					searchDB.setEditable(false);
					resultDB.setStyle("-fx-background-color : #ddffff;"
									+ "-fx-background-radius : 2em");
					butEditDB.setStyle("-fx-background-color : #ddffff");
				} else {
					if (showAlert(AlertType.WARNING, "Bạn có muốn lưu thay đổi?")) {
						String str = resultDB.getText();
						String wordNeedEdit = searchDB.getText();
						
						int indexSpell = str.indexOf("-") + 2;
						int indexMean = str.indexOf("\n");
						if (indexSpell > 0 && indexMean > indexSpell) {
							String spell = str.substring(indexSpell, indexMean);
							String mean = str.substring(indexMean).replace("\n", "");	
							mean = mean.replace("\t", "");	
							mean = mean.replace("- ", "-");	
							mean = mean.replace("= ", "=");

							databaseWord.editSpellAndMean(wordNeedEdit, mean, true);
							databaseWord.editSpellAndMean(wordNeedEdit, spell, false);
							showAlert(AlertType.INFORMATION, "Lưu thành công!");
						} else {
							showAlert(AlertType.ERROR, "Vui lòng không xóa định dạng ban đầu!");
						}
						
						butEditDB.setStyle("-fx-background-color : #0000");
						resultDB.setStyle("-fx-background-color : #c1dde2; "
								+ "-fx-background-radius : 2em");
						resultDB.setEditable(false);
						searchDB.setEditable(true);
					}
				}
			}
		}
		
		if (actionEvent.getSource() == butDelDB) {
			if (!DBTarget.isEmpty()) {
				if (showAlert(AlertType.WARNING, "Bạn có chắc muốn xóa từ này?")) {
					databaseWord.deleteWord(DBTarget);
					resultDB.setText("");
					searchDB.setText("");
				}
			}
		}
		
		if (actionEvent.getSource() == butPinDB) {
			if (!DBTarget.isEmpty()) {
				String str = "◦ " + DBTarget.toLowerCase();
				if (!listPinDB.getItems().contains(str)) {
					listPinDB.getItems().add(str);
				}
			}
		}
		
		if (actionEvent.getSource() == butUnPinDB) {
			if (!DBTarget.isEmpty()) {
				String str = "◦ " + DBTarget.toLowerCase();
				resultDB.setText("");
				listPinDB.getItems().remove(str);
				listPinDB.getSelectionModel().clearSelection();
			}
		}
	}
	
	@FXML
	private void eventButtonAPI(ActionEvent actionEvent) {
		//PaneAPI
		if (actionEvent.getSource() == butTransAPI) {
			APITrans apiTrans = new APITrans(searchAPI.getText(),
									langTo.getValue(),
									langFrom.getValue());
			
			Region veil = new Region();
	        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.15); "
	        			+ "-fx-background-radius : 2em");
	        
	        veil.setLayoutX(38);
	        veil.setLayoutY(295);
	        veil.setPrefSize(600, 200);
	        veil.visibleProperty().bind(apiTrans.runningProperty());
	        
	        ProgressIndicator p = new ProgressIndicator();
	        p.setLayoutX(300);
	        p.setLayoutY(360);
	        p.visibleProperty().bind(apiTrans.runningProperty());
	        
			resultAPI.textProperty().bind(apiTrans.valueProperty());
			paneAPI.getChildren().addAll(veil, p);
			
			new Thread(apiTrans).start();
		}
		
		if (actionEvent.getSource() == butSpeechAPI1) {
			TextToSpeech.speech(searchAPI.getText());
		}
		
		if (actionEvent.getSource() == butSpeechAPI2) {
			TextToSpeech.speech(resultAPI.getText());
		}
	}
	
	private boolean showAlert(AlertType type, String content) {
		Alert alert = new Alert(type);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.setContentText(content);
		alert.setHeaderText(null);
		
		if (type == AlertType.ERROR) {
			alert.getDialogPane().setStyle("-fx-background-color : #ffdddd ;" 
										+ "-fx-font-size : 16");
			alert.showAndWait();
		}
		
		if (type == AlertType.INFORMATION) {
			alert.getDialogPane().setStyle("-fx-background-color : #ddffff;"
										+ "-fx-font-size : 16");
			alert.showAndWait();
		}

		if (type == AlertType.WARNING) {
			ButtonType No = new ButtonType("No");
			ButtonType Yes = new ButtonType("Yes");
			alert.getButtonTypes().clear();
			alert.getButtonTypes().addAll(Yes, No);
			alert.getDialogPane().setStyle("-fx-background-color : #ffffcc;"
										+ "-fx-font-size : 16");

			Optional<ButtonType> option = alert.showAndWait();
			
			if ( option.get() == Yes) {
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}

	public void addTo(ActionEvent e) {
		checkAddTo =  e.getSource().toString().contains("File");
	}
	
	private void hintDB() {
		listHintDB.getItems().clear();
		String str = searchDB.getText().trim();
		String[] hint = databaseWord.hintWord(str);
		
		for (int i = 0; i < hint.length; i++) {
			if (hint[i] != null) {
				listHintDB.getItems().add(hint[i]);
			}
		}
	}
	
	@FXML
	private void eventTabButton(ActionEvent actionEvent) {
		if(actionEvent.getSource() == butTrans) {
			paneTrans.setVisible(!paneTrans.isVisible());
			paneFav.setVisible(false);
			paneAdd.setVisible(false);
			paneTrash.setVisible(false);
			paneDB.setVisible(false);
			paneAPI.setVisible(false);
		}
		
		if(actionEvent.getSource() == butFav) {
			paneTrans.setVisible(false);
			paneFav.setVisible(!paneFav.isVisible());
			paneAdd.setVisible(false);
			paneTrash.setVisible(false);
			paneDB.setVisible(false);
			paneAPI.setVisible(false);
		}
		
		if(actionEvent.getSource() == butAdd) {
			paneTrans.setVisible(false);
			paneFav.setVisible(false);
			paneAdd.setVisible(!paneAdd.isVisible());
			paneTrash.setVisible(false);
			paneDB.setVisible(false);
			paneAPI.setVisible(false);
		}
		
		if(actionEvent.getSource() == butTrash) {
			paneTrans.setVisible(false);
			paneFav.setVisible(false);
			paneAdd.setVisible(false);
			paneTrash.setVisible(!paneTrash.isVisible());
			paneDB.setVisible(false);
			paneAPI.setVisible(false);
		}
		
		if(actionEvent.getSource() == butDB) {
			paneTrans.setVisible(false);
			paneFav.setVisible(false);
			paneAdd.setVisible(false);
			paneTrash.setVisible(false);
			paneAPI.setVisible(false);
			paneDB.setVisible(!paneDB.isVisible());
		}
		
		if(actionEvent.getSource() == butAPI) {
			paneTrans.setVisible(false);
			paneFav.setVisible(false);
			paneAdd.setVisible(false);
			paneTrash.setVisible(false);
			paneDB.setVisible(false);
			paneAPI.setVisible(!paneAPI.isVisible());
		}
		
		if(actionEvent.getSource() == butOff) {
			dicFavorite.writeToFile(urlDicFav);
			dictionary.writeToFile(urlDic);
			System.exit(0);
		}
	}
}

