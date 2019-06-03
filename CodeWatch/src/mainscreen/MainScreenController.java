package mainscreen;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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


public class MainScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteButton;

    @FXML
    private AnchorPane mainScreenWindow;
/** ������ ���������*/
    @FXML
    private TreeView<File> navigationPanel;

    @FXML
    private Button editButton;

    @FXML
    private MenuButton menuButton;

    @FXML
    private Button copyButton;

    @FXML
    private MenuItem menuSettingButton; 

    @FXML
    private ListView<?> commentsQueueList;

    @FXML
    private TextArea textCode;

    @FXML
    private MenuItem menuLoadButton;

    @FXML
    private MenuItem editorLaunchButton;
    
    Stack<TreeItem> stack = new Stack<TreeItem>(); 

    @FXML
    public void initialize() {
    	System.out.println("��� �����");
    	 navigationPanel.setOnMouseClicked(new EventHandler<MouseEvent>()
 	    {
 	        @Override
 	        public void handle(MouseEvent mouseEvent)
 	        {
 	            if(mouseEvent.getClickCount() == 2)
 	            {
 	                TreeItem<File> item = navigationPanel.getSelectionModel().getSelectedItem();

 	                System.out.println("Selected file is : " + item.getValue().getAbsolutePath());
 	            }
 	        }
 	    });
    }
    public void loadProject(){
    	/*
    	FileChooser directoryChooser = new FileChooser();
        directoryChooser.setTitle("Select path to your project");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File dir = directoryChooser.showOpenDialog(null);
        if (dir != null) {
           
        }*/

    	read();
    	}
    	
    void read(){
    	// �����-����������
    	DirectoryChooser directoryChooser = new DirectoryChooser();
    	//��������� ��������� ��������� ����� ���� �� �����������
    	        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    	// ����� ������ �����
    	        File dir = directoryChooser.showDialog(null);
    	       
    	        if (dir != null) {
    			try {
					//���������� �� ���� ������ � ������� ��� ���
    				Files.walk(Paths.get(dir.getAbsolutePath())).forEach(this::buildTree);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	        }
    	}
 

    	    void buildTree(Path path) {
    	    	//  
    	    	
    	        File file = path.toFile();
    	        System.out.println(file);
    	        //���� ���� �������� ������
    	        if (file.isDirectory()) {
    	        	if(stack.isEmpty()) {
    	        		 TreeItem mainRoot = new TreeItem<File>(file);
    	     	        navigationPanel.setRoot(mainRoot);
    	     	        stack.push(mainRoot);
    	     	       File rootIconFile = new File("res/rootItem.png");
   	                ImageView rootIcon;
   					try {
   						rootIcon = new ImageView(new Image(rootIconFile.toURI().toURL().toString()));
   						 rootIcon.setFitHeight(32);
   			                rootIcon.setFitWidth(32);
   			                mainRoot.setGraphic(rootIcon);
   					} catch (MalformedURLException e) {
   						// TODO Auto-generated catch block
   						e.printStackTrace();
   					}
    	        	}else {
    	            //������� �������. ���� file �������� ������ -  �� �� ���������� �����.
    	        	TreeItem secondaryRoot = new TreeItem<>(file);
    	        	TreeItem<File> rootItem = (TreeItem<File>) stack.peek();// ���������� ����� �������� ������ � ���� ������.
    	    		rootItem.getChildren().addAll(secondaryRoot);//�������� � ������������� ������ ��� �����
    	    		File rootIconFile = new File("res/folderItem.png");
	                ImageView rootIcon;
					try {
						rootIcon = new ImageView(new Image(rootIconFile.toURI().toURL().toString()));
						 rootIcon.setFitHeight(32);
			                rootIcon.setFitWidth(32);
			                secondaryRoot.setGraphic(rootIcon);
		     	        	stack.push(secondaryRoot);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	               }
    	        } else {//����� file  ������������� � root
	    	      
		    		System.out.println(file+" is File"); 
		    		stack.forEach((item)->{System.out.println("Stack item is : "+item); });
		    		
	    	        boolean isContains = false;
	                while(!isContains){
	                    for(File temp :  ((File) stack.peek().getValue()).listFiles()){
	                        if(file.getAbsolutePath().equals(temp.getAbsolutePath())){
	                            isContains = true;
	                              //����������� ����� File � ������ TreeItem, ��� ������ ����� ������� ������ TreeItem
	                            TreeItem<File> testItem = new TreeItem<File>(file);
	                            TreeItem<File> rootItem = (TreeItem<File>) stack.peek();// ���������� ����� �������� ������ � ���� ������.
	                            rootItem.getChildren().addAll(testItem);//�������� � ������������� ������ ��� �����
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
	              //�������� ��� �������� �������
	                
    	        }
    	    }
    	   
}
