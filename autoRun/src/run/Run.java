package run;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class Run extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    ObservableList<FileModel> fileObvList = FXCollections.observableArrayList();//数据源

    String jarFileName;
    int idx = 0;
    int num = 0;

    Path absolutePath;
    Path AAPath;
    Path APath ;
    Path BBPath;
    Path BPath;
    Path FPath;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //菜单
        MenuItem menuItem_open = new MenuItem("打开文件");

        Menu menu_file = new Menu("文件");
        menu_file.getItems().add(menuItem_open);

        MenuBar menuBar_main = new MenuBar(menu_file);

        //文件列表
        TableView<FileModel> fileTableView = new TableView<>(fileObvList);
        fileTableView.setEditable(true);


        TableColumn<FileModel, String> nameColumn = new TableColumn<>("Name");
        TableColumn<FileModel, String> scoreColum = new TableColumn<>("Score");

        nameColumn.setCellValueFactory(new PropertyValueFactory<FileModel,String>("name"));
        nameColumn.setMinWidth(400);
        scoreColum.setCellValueFactory(new PropertyValueFactory<FileModel,String>("score"));
        scoreColum.setMinWidth(400);

        fileTableView.getColumns().add(nameColumn);
        fileTableView.getColumns().add(scoreColum);

        //目录选择器
        DirectoryChooser directoryChooser = new DirectoryChooser();

        //主界面

        Button bt_run = new Button("运行");
        Button bt_next = new Button("下一个");
        bt_next.setMinWidth(100);


        Label lb_num = new Label("总数：");
        Label lb_idx = new Label("已批改：");
        Label lb_wait = new Label("待批改：");

        ToggleGroup tg_score = new ToggleGroup();

        RadioButton rb_AA = new RadioButton("A+");
        RadioButton rb_A = new RadioButton("A");
        RadioButton rb_BB = new RadioButton("B+");
        RadioButton rb_B = new RadioButton("B");
        RadioButton rb_F = new RadioButton("F");

        rb_AA.setSelected(true);

        rb_AA.setToggleGroup(tg_score);
        rb_A.setToggleGroup(tg_score);
        rb_BB.setToggleGroup(tg_score);
        rb_B.setToggleGroup(tg_score);
        rb_F.setToggleGroup(tg_score);

        VBox vb_main = new VBox(lb_num,lb_idx,lb_wait,bt_run,rb_AA,rb_A,rb_BB,rb_B,rb_F,bt_next);
        vb_main.setSpacing(30);

        BorderPane bp_main = new BorderPane();
        bp_main.setTop(menuBar_main);
        bp_main.setCenter(fileTableView);
        bp_main.setRight(vb_main);

        Scene scene_main = new Scene(bp_main,800,800);

        primaryStage.setScene(scene_main);
        primaryStage.show();


        menuItem_open.setOnAction(e->{

            File selectedDirectory = directoryChooser.showDialog(primaryStage);

            if (selectedDirectory != null) {

                absolutePath = Paths.get(selectedDirectory.getAbsolutePath());

                num = selectedDirectory.list().length;

                System.out.println("选中的目录: " + selectedDirectory.getAbsolutePath());

                for (String fileName : selectedDirectory.list()) {
                    fileObvList.add(new FileModel(fileName,"D"));
                    System.out.println("添加文件："+fileName);
                }
                fileTableView.refresh();

                createDir();

            } else {
                System.out.println("未选中目录！");
            }

        });

        //运行当前文件
        bt_run.setOnAction(e->runJar());

        //下一个
        bt_next.setOnAction(e->{

            if (rb_AA.isSelected()){
                fileObvList.get(idx).setScore("A+");
            } else if (rb_A.isSelected()) {
                fileObvList.get(idx).setScore("A");
            } else if (rb_BB.isSelected()) {
                fileObvList.get(idx).setScore("B+");
            } else if (rb_B.isSelected()) {
                fileObvList.get(idx).setScore("B");
            }

            fileTableView.refresh();

            moveFile();

            idx++;
            if (idx == num){
                bt_next.setDisable(true);
                idx=0;
            }

        });

        fileTableView.setOnMouseClicked(event -> {
            System.out.println(idx);
        });

    }

    //运行jar文件
    public void runJar(){
        try {
            jarFileName = fileObvList.get(idx).getName();
            String jarPath = absolutePath+"\\"+jarFileName;
            Runtime.getRuntime().exec("java -jar " + jarPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createDir(){

        AAPath = Paths.get(absolutePath+"\\"+"A+");
        APath = Paths.get(absolutePath+"\\"+"A");
        BBPath = Paths.get(absolutePath+"\\"+"B+");
        BPath = Paths.get(absolutePath+"\\"+"B");
        FPath = Paths.get(absolutePath+"\\"+"F");

        if (!Files.exists(AAPath)&&!Files.exists(APath)&&!Files.exists(BBPath)&&!Files.exists(BPath)&&!Files.exists(FPath)) {
            try {
                // 创建目录
                Files.createDirectories(AAPath);
                Files.createDirectories(APath);
                Files.createDirectories(BBPath);
                Files.createDirectories(BPath);
                Files.createDirectories(FPath);

                System.out.println("目录创建成功");

            } catch (IOException e) {
                System.err.println("Failed to create directory: " + e.getMessage());
            }
        } else {
            System.out.println("目录已存在");
        }


    }

    public void moveFile(){

        //A+类
        try {

            if (fileObvList.get(idx).getScore().equals("A+")) {

                Path tempPath = Paths.get(absolutePath + "\\" + fileObvList.get(idx).getName());

                System.out.println(tempPath);
                System.out.println(AAPath);

                Files.move(tempPath, AAPath, StandardCopyOption.REPLACE_EXISTING);

            }

            System.out.println("File moved successfully.");

        } catch (IOException e) {
            // 处理异常
            System.err.println("Failed to move file: " + e.getMessage());

        }

    }

}
