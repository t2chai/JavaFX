package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Run extends javafx.application.Application{

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;
        startCGUI();
        stage.getIcons().add(new Image("image/5.png"));
    }

    public static void startCGUI(){
        StartCG startCG = new StartCG();
        startCG.init_map();
        Scene scCG = new Scene(startCG.map,startCG.Width,startCG.Height);
        stage.setTitle("启动！");
        stage.setScene(scCG);
        stage.show();
    }

    public static void startUI(){

        stage.close();

        StartGame sg = new StartGame();

        sg.init_map();

        Scene startScene = new Scene(sg.map,sg.width, sg.Height);

        stage.setScene(startScene);

        stage.show();

    }

    public static void gameUI(){

        stage.close();

        Game game = new Game();

        game.initGame();

        Scene sc_Main = new Scene(game.map,game.getWIDTH() + game.getXGAP(),game.getHEIGHT()+ game.getYGAP());

        stage.setScene(sc_Main);

        stage.setTitle("桃源深处有人家");

        stage.show();

    }

    public static void setUI(){

        stage.close();

        SetGame setGame = new SetGame();

        setGame.init_map();

        Scene scene = new Scene(setGame.map,setGame.Width, setGame.Height);

        stage.setScene(scene);
        stage.setTitle("游戏设置");
        stage.show();

    }

    public static void settlementUI(){

        stage.close();

        Settlement settlement = new Settlement();

        settlement.init_map();

        Scene scene = new Scene(settlement.map,settlement.Width, settlement.Height);

        stage.setScene(scene);
        stage.show();

    }

    public static void aboutUI(){

        stage.close();

        About about = new About();

        about.init_map();

        Scene scene = new Scene(about.map,about.Width, about.Height);

        stage.setScene(scene);
        stage.setTitle("关于");
        stage.show();

    }



    public static void main(String[] args) {
        Application.launch(args);
    }

}
