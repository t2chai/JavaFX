package main;

import java.net.URL;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class About {
    public int Height = 533;
    public int Width = 400;
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
        //音效
        AudioClip sound2 = new AudioClip(ReadResourceUtil.readMusic("click.wav").toString());
        //背景设置
        map.setStyle("-fx-background-image: url(" + "/image/background2.png" + "); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;" +
                "-fx-background-size: 400 533;"+
                "-fx-background-color:  transparent;");
        //字体
        Font fon1 = Font.font("Microsoft YaHei",FontWeight.BOLD,FontPosture.REGULAR,18);
        Font fon2 = Font.font("Microsoft YaHei",FontWeight.BOLD,FontPosture.REGULAR,15);

        System.out.println("https://github.com/kbcha1");

        //文字介绍
        Text title = new Text("“今朝圆梦有几时，平生爱做桃源梦。”");
        Text t1 = new Text("桃树下埋酒，杏花微雨时分别");
        Text t2 = new Text("桃源是一生心灵的归处，桃源是一片山水");
        Text t3 = new Text("生活在此处与彼处，本没有太大的区别。");
        Text t4 = new Text("遇到不同的人和故事，恰是它本来的意义。");
        //Text t5 = new Text("制作这款游戏的初心是希望可以拾起每个人心中桃花源，将宁静与安详带给大家。");
        Text t6 = new Text("请尽自己所能的收集三款相同的卡片进行消除吧");

        VBox hb1 = new VBox(t1,t2,t3,t4,t6);
        VBox hb2 = new VBox(title);

        map.setCenter(hb1);
        map.setTop(hb2);
        hb2.setAlignment(Pos.BASELINE_CENTER);
        hb2.setPadding(new Insets(80,0,0,0));
        hb1.setAlignment(Pos.CENTER);
        hb1.setPadding(new Insets(20,0,60,0));
        hb1.setSpacing(10);
        //
        title.setFont(fon1);
        t1.setFont(fon2);
        t2.setFont(fon2);
        t3.setFont(fon2);
        t4.setFont(fon2);
        //t5.setFont(fon2);
        t6.setFont(fon2);
        //返回
        Button backButton = new Button("返回");
        backButton.setFont(fon2);
        backButton.setStyle("-fx-background-color: #EF852F;" +
                "-fx-effect: dropshadow(three-pass-box, #000, 10, 0, 0, 0);"+
                "-fx-border-color: #FBB9AB; -fx-border-width: 2px; -fx-border-radius: 5px; ");
        HBox bthb = new HBox(backButton);
        bthb.setSpacing(30);
        bthb.setAlignment(Pos.BASELINE_CENTER);
        bthb.setPadding(new Insets(0,0,80,0));
        map.setBottom(bthb);
        //按钮动画
        backButton.setOnAction(e->{
            sound2.play();
            Run.startUI();
        });
        //文字动画
        //文字动画
        t1.setOpacity(0);
        t2.setOpacity(0);
        t3.setOpacity(0);
        t4.setOpacity(0);
        t6.setOpacity(0);
        // 创建一个淡入动画
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(2), t1);
        fadeTransition1.setToValue(1); // 将透明度设置为1，即完全不透明
        FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(2), t2);
        fadeTransition2.setToValue(1); // 将透明度设置为1，即完全不透明
        FadeTransition fadeTransition3 = new FadeTransition(Duration.seconds(2), t3);
        fadeTransition3.setToValue(1); // 将透明度设置为1，即完全不透明
        FadeTransition fadeTransition4 = new FadeTransition(Duration.seconds(2), t4);
        fadeTransition4.setToValue(1); // 将透明度设置为1，即完全不透明
        FadeTransition fadeTransition5 = new FadeTransition(Duration.seconds(2), t6);
        fadeTransition5.setToValue(1); // 将透明度设置为1，即完全不透明

        // 播放动画
        fadeTransition1.play();
        fadeTransition2.play();
        fadeTransition3.play();
        fadeTransition4.play();
        fadeTransition5.play();
    }
}

