package mainscreen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Stack;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
public class MainScreenController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    /*
     * кнопка обрабатывающая событие нажатия
     * позволяет удалить комментарий
     */
    private Button deleteButton;
    @FXML
    // панель с предустановленным менеджером компоновки
    private AnchorPane mainScreenWindow;
/** Дерево навигации*/
    @FXML
    private TreeView<NavigationNode> navigationPanel;
    @FXML
    //кнопка позволяющая редактирвоать комментарии
    private Button editButton;
    @FXML
    //кнопка выпадающего меню
    private MenuButton menuButton;
    @FXML
    //копировать
    private Button copyButton;
    @FXML
    // настройки в меню
    private MenuItem menuSettingButton; 
    @FXML
    // список комментариев
    private ListView<?> commentsQueueList;
    @FXML
    //многострочная текстовая область
    private TextArea textCode;
    @FXML
    // загрузка проекта
    private MenuItem menuLoadButton;
    @FXML
    //редактор по умолчанию
    private MenuItem editorLaunchButton;
    Stack<TreeItem> stack = new Stack<TreeItem>(); 
    @FXML
   
    //
    public void initialize() {
    	//регистрирую обработчик события нажатия
    	menuSettingButton.setOnAction(event-> {
    	//функция Window
    	window();
       	}
    	);
    	
    	//регистрация события на двойное нажатие по элементу дерева
    	 navigationPanel.setOnMouseClicked(new EventHandler<MouseEvent>()
 	    {
 	        @Override
 	        public void handle(MouseEvent mouseEvent)
 	        {
 	            if(mouseEvent.getClickCount() == 2)
 	            {
 	                TreeItem<NavigationNode> item = navigationPanel.getSelectionModel().getSelectedItem();
 	               try {
 	            	  textCode.clear();
 	                  Scanner s = new Scanner(new File(item.getValue().source.getAbsolutePath())).useDelimiter("\\s+");
 	                  while (s.hasNext()) {
 	                      if (s.hasNextInt()) { // check if next token is an int
 	                    	 textCode.appendText(s.nextInt() + " "); // display the found integer
 	                    	 //.wrapText.isBound();
 	                    	textCode.setWrapText(true);
 	                      } else {
 	                    	 textCode.appendText(s.next() + " "); // else read the next token
 	                    	textCode.setWrapText(true);
 	                      }
 	                  }
 	              } catch (FileNotFoundException ex) {
 	                  System.err.println(ex);
 	              }
 	            }
 	        }
 	    });
    }
    public void loadProject(){
    	read();
    	}
    // Метод открытия окна загрузки
    void window() {
    	FXMLLoader loader = new FXMLLoader();
    	// путь fxml
    	loader.setLocation(getClass().getResource("/settings/Settings.fxml"));
    	try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Parent root=loader.getRoot();
    	Stage stage = new Stage();
    	stage.setScene(new Scene(root));
    	stage.showAndWait();
    	
    }
    void read(){
    	// Папко-выбиратель
    	DirectoryChooser directoryChooser = new DirectoryChooser();
    	//Установка начальной выбранной папки куда мы заглядываем
    	        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    	// вызов выбора папки
    	        File dir = directoryChooser.showDialog(null);
    	        if (dir != null) {
    	        	//Исключение
    			try {
					//проходимся по всем файлам с помощью фор ича
    				Files.walk(Paths.get(dir.getAbsolutePath())).forEach(this::buildTree);
    				//отчистка
    				stack.clear();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	        }
    	}
 
    		//метод для дерева
    	    void buildTree(Path path) {
    	    	// присваиваем поступающий аргумент file
    	        File file = path.toFile();
    	        //выводим в консоль файл
    	        System.out.println(file);
    	        //создаем обьект NavigationNode node присваивая файл
    	        NavigationNode node = new NavigationNode(file);
    	        //Если файл является папкой
    	        if (file.isDirectory()) {
    	        	//Если стак пуст
    	        	if(stack.isEmpty()) {
    	        		//Обьект дерева mainRoot присваивает node
    	        		TreeItem mainRoot = new TreeItem< NavigationNode>(node);
    	     	        //отображает корень дерева
    	        		navigationPanel.setRoot(mainRoot);
    	        		//добавить
    	     	        stack.push(mainRoot);
    	     	        //путь для картинки корня
    	     	       File rootIconFile = new File("res/rootItem.png");
   	                ImageView rootIcon;
   					//исключение 
   	                try {
   						rootIcon = new ImageView(new Image(rootIconFile.toURI().toURL().toString()));
   						//размер Height 
   						rootIcon.setFitHeight(32);
   			            // размер Width  
   						rootIcon.setFitWidth(32);
   			                mainRoot.setGraphic(rootIcon);
   			                //если try не выполнится
   					} catch (MalformedURLException e) {
   						// TODO Auto-generated catch block
   						e.printStackTrace();
   					}
   	                //иначе
    	        	}else {
    	            //делаешь пакетом. Если file является папкой -  то он становится рутом.
    	        	TreeItem secondaryRoot = new TreeItem<NavigationNode>(node);
    	        	// Приведение типов абстракт дерева к файл дерева.
    	        	TreeItem rootItem = (TreeItem) stack.peek();
    	        	//добавляю к существующему дереву еще итемы
    	    		rootItem.getChildren().addAll(secondaryRoot);
    	    		// путь для картинки  папок
    	    		File rootIconFile = new File("res/folderItem.png");
	                ImageView rootIcon;
	                //исключение
					try {
						rootIcon = new ImageView(new Image(rootIconFile.toURI().toURL().toString()));
						//размер Height 
						rootIcon.setFitHeight(32);
						// размер Width 
			                rootIcon.setFitWidth(32);
			                //отрисовываем картинку
			                secondaryRoot.setGraphic(rootIcon);
			                //добавляем в стак 
		     	        	stack.push(secondaryRoot);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	               }
    	        	//иначе file  присваивается к root
    	        } else {
	    	      // выводим в консоль получаемый файл
		    		System.out.println(file+" is File");
		    		/*
		    		 * Пробегаем по стаку методом foreach
		    		 * Выводим на экран его элементы
		    		 * */
		    		stack.forEach((item)->{System.out.println("Stack item is : "+item); });
		    		// переменной присваиваем значение false
	    	        boolean isContains = false;
	    	        //Цикл
	                while(!isContains){
	                    for(File temp :  (( NavigationNode) stack.peek().getValue()).source.listFiles()){
	                        if(file.getAbsolutePath().equals(temp.getAbsolutePath())){
	                            isContains = true;
	                              //оборачиваем оъект File в объект TreeItem, ибо дерево может хранить только TreeItem
	                            TreeItem< NavigationNode> testItem = new TreeItem< NavigationNode>(node);
	                            TreeItem< NavigationNode> rootItem = (TreeItem<NavigationNode>) stack.peek();// Приведение типов абстракт дерева к файл дерева.
	                            rootItem.getChildren().addAll(testItem);//добавляю к существующему дереву еще итемы
	                            File rootIconFile = new File("res/fileItem.png");
	        	                ImageView rootIcon;
								try {
									rootIcon = new ImageView(new Image(rootIconFile.toURI().toURL().toString()));
									rootIcon.setFitHeight(16);
		        	                rootIcon.setFitWidth(16);
		        	                testItem.setGraphic(rootIcon);
								} catch (MalformedURLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                        }
	                    }
	                    if(isContains == false) stack.pop();
	                }
	                //украшаем рут красивой иконкой
	                }
    	    }
    	  //Класс-обёртка
    	    public class NavigationNode{
    	    	//файл, который надо хранить в ноде
    	    	public File source;
    	    	//конструктор. просто чтобы быстрее крафтить объекты класса - в принципе, особо не нужен
    	    	public NavigationNode(File source) {
    	    		this.source = source;
    	    	}
    	    	//ради этого метода и существует весь класс;
    	    	//то, что он вернёт отобразится как имя узла дерева
    	    	@Override
    	    	public String toString() {
    	    		return source.getName();
    	    	}
    	    }
    	   
}
