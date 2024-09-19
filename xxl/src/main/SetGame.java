package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SetGame {
    public int Height = 500;
    public int Width = 300;
    public BorderPane map = new BorderPane();
    public int getHeight() {
        return Height;
    }
    public void setHeight(int height) {
        Height = height;
    }
    public int getWidth() {
        return Width;
    }
    public void setWidth(int width) {
        Width = width;
    }
    public BorderPane getMap() {
        return map;
    }
    public void setMap(BorderPane map) {
        this.map = map;
    }
    public void init_map() {

        //背景设置
        map.setStyle("-fx-background-image: url(" + "/image/background2.png" + "); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;" +
                "-fx-background-size: 300 500;"+
                "-fx-background-color:  transparent;");

        //标题
        Text title = new Text("游戏设置");
        //音乐、音效
        Font fon1 = Font.font("Microsoft YaHei",FontWeight.BOLD,FontPosture.REGULAR,14);


        Text music = new Text("音乐");
        Text sound = new Text("音效");
        music.setFont(fon1);
        sound.setFont(fon1);
        RadioButton rb1 = new RadioButton();
        RadioButton rb2 = new RadioButton();
        rb1.setSelected(true);
        rb2.setSelected(true);
        HBox hb1 = new HBox(music,rb1);
        hb1.setSpacing(20);
        HBox hb2 = new HBox(sound,rb2);
        hb2.setSpacing(20);
        HBox hb3 = new HBox(hb1,hb2);
        hb3.setSpacing(50);
        hb3.setAlignment(Pos.BASELINE_CENTER);
        map.setCenter(hb3);
        //难度
        Text low = new Text("入门");
        Text media = new Text("简单");
        Text high = new Text("复杂");
        low.setFont(fon1);
        media.setFont(fon1);
        high.setFont(fon1);
        RadioButton rb3 = new RadioButton();
        RadioButton rb4 = new RadioButton();
        RadioButton rb5 = new RadioButton();
        //单选
        ToggleGroup toggleGroup1 = new ToggleGroup();
        rb3.setToggleGroup(toggleGroup1);
        rb4.setToggleGroup(toggleGroup1);
        rb5.setToggleGroup(toggleGroup1);

        rb3.setSelected(true);

        HBox hb5 = new HBox(low,rb3);
        HBox hb6 = new HBox(media,rb4);
        HBox hb7 = new HBox(high,rb5);
        HBox hb8 = new HBox(hb5,hb6,hb7);

        hb5.setSpacing(10);
        hb6.setSpacing(10);
        hb7.setSpacing(10);
        hb8.setSpacing(25);
        hb8.setAlignment(Pos.BASELINE_CENTER);
        //皮肤

        Text fruit = new Text("水果");
        Text animal = new Text("动物");
        fruit.setFont(fon1);
        animal.setFont(fon1);
        RadioButton rb6 = new RadioButton();
        RadioButton rb7 = new RadioButton();
        //单选
        ToggleGroup toggleGroup2 = new ToggleGroup();

        rb6.setToggleGroup(toggleGroup2);
        rb7.setToggleGroup(toggleGroup2);
        rb6.setSelected(true);

        VBox vb1 = new VBox(fruit,rb6);
        VBox vb2 = new VBox(animal,rb7);

        vb1.setSpacing(20);
        vb2.setSpacing(20);
        HBox hb4 = new HBox(vb1,vb2);
        hb4.setSpacing(40);
        hb4.setAlignment(Pos.BASELINE_CENTER);
        //组合
        VBox vb3 = new VBox(hb3,hb8,hb4);
        vb3.setSpacing(70);
        vb3.setAlignment(Pos.CENTER);
        map.setCenter(vb3);
        vb3.setPadding(new Insets(20,0,0,0));
        //按钮
        Image img1 = new Image("/image/0.png");
        ImageView iv1 = new ImageView(img1);
        iv1.setFitWidth(50);
        iv1.setPreserveRatio(true);
        rb6.setGraphic(iv1);
        Image img2 = new Image("/image/5.png");
        ImageView iv2 = new ImageView(img2);
        iv2.setFitWidth(50);
        iv2.setPreserveRatio(true);
        rb7.setGraphic(iv2);
        //单选按钮
        //音乐
        rb1.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // 处理 RadioButton 被选中的情况
            	System.out.println("音乐开");
            } else {
                // 处理 RadioButton 取消选中的情况
            	System.out.println("音乐关");
            }
        });

        //音效
        rb2.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // 处理 RadioButton 被选中的情况
            	System.out.println("音效开");
            } else {
                // 处理 RadioButton 取消选中的情况
            	System.out.println("音效关");
            }
        });

        //难度选择
        toggleGroup1.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == rb3) {
                // 难度低
            	System.out.println("低");
            	Game.GROUP = 1;
                Game.FLOP = 0;
            } else if (newValue == rb4) {
                // 难度中
                Game.GROUP = 2;
                Game.FLOP = 1;
            	System.out.println("中");
            } else if (newValue == rb5) {
                // 难度高
                Game.GROUP = 3;
                Game.FLOP = 2;
            	System.out.println("高");
            }
        });

        if (rb3.isSelected()) {
            // 难度低
            System.out.println("低");
            Game.GROUP = 1;
            Game.FLOP = 0;
        } else if (rb4.isSelected()) {
            // 难度中
            Game.GROUP = 2;
            Game.FLOP = 1;
            System.out.println("中");
        } else if (rb5.isSelected()) {
            // 难度高
            Game.GROUP = 3;
            Game.FLOP = 2;
            System.out.println("高");
        }

        //皮肤
        toggleGroup2.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == rb6) {
                        // 水果
                        System.out.println("水果");
                        Game.SKIN = 1;

                    } else if (newValue == rb7) {
                        // 动物
                        System.out.println("动物");
                        Game.SKIN = 2;
                    }
        });

        if (rb6.isSelected()) {
            // 水果
            System.out.println("水果");
            Game.SKIN = 1;

        } else if (rb7.isSelected()) {
            // 动物
            System.out.println("动物");
            Game.SKIN = 2;
        }


        //字体
        Font fon2 = Font.font("Microsoft YaHei",FontWeight.BOLD,FontPosture.REGULAR,15);
        //点击音效
        AudioClip sound2 = new AudioClip(ReadResourceUtil.readMusic("click.wav").toString());
        //重新开始按钮
        Button restart = new Button("返回");
        restart.setFont(fon2);
        restart.setStyle("-fx-background-color: #EF852F;" +
                "-fx-effect: dropshadow(three-pass-box, #000, 10, 0, 0, 0);"+
                "-fx-border-color: #FBB9AB; -fx-border-width: 2px; -fx-border-radius: 5px; ");
        HBox bthb = new HBox(restart);
        bthb.setSpacing(30);
        bthb.setAlignment(Pos.BASELINE_CENTER);
        bthb.setPadding(new Insets(0,0,20,0));
        map.setBottom(bthb);
        //按钮动画
        restart.setOnAction(e->{
            sound2.play();
            Run.startUI();
        });


    }
}

