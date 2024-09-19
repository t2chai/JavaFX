package main;

import java.io.InputStream;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import javafx.util.Duration;

public class Settlement {

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
            //加载字体

            Font fon2 = Font.font("Microsoft YaHei",FontWeight.BOLD,FontPosture.REGULAR,18);

            InputStream fontStream = getClass().getResourceAsStream("/Font/f0.ttf");
            Font font = Font.loadFont(fontStream, 30);

            //点击音效
            AudioClip sound = new AudioClip(ReadResourceUtil.readMusic("click.wav").toString());
            //按钮样式
            Button restart = new Button("重新开始");
            Button exit = new Button("退出游戏");
            restart.setFont(fon2);
            restart.setStyle("-fx-background-color: #EF852F;" +
                    "-fx-effect: dropshadow(three-pass-box, #000, 10, 0, 0, 0);"+
                    "-fx-border-color: #FBB9AB; -fx-border-width: 2px; -fx-border-radius: 5px; ");
            exit.setFont(fon2);
            exit.setStyle("-fx-background-color: #EF852F;" +
                    "-fx-effect: dropshadow(three-pass-box, #000, 10, 0, 0, 0);"+
                    "-fx-border-color: #FBB9AB; -fx-border-width: 2px; -fx-border-radius: 5px; ");
            HBox hb1 = new HBox(restart,exit);
            hb1.setSpacing(30);
            hb1.setAlignment(Pos.BASELINE_CENTER);
            hb1.setPadding(new Insets(0,0,80,0));
            map.setBottom(hb1);
            //按钮动画

            restart.setOnAction(e->{
                sound.play();
                Run.gameUI();
            });

            exit.setOnAction(e->{
                sound.play();
                Run.startUI();
            });
            //文字结果
            Text title = new Text("游戏结果");
            title.setFont(font);
            VBox vb1 = new VBox(title);
            vb1.setAlignment(Pos.BASELINE_CENTER);
            vb1.setPadding(new Insets(80,0,0,0));
            map.setTop(vb1);
            System.out.println(Game.SCORE);
            Text t1 = new Text("总用时：       " + StartGame.time.getSeconds()+"s");
            Text t3 = new Text("总得分：        " + String.format("%.2f", 1.0*Game.getGS() / StartGame.time.getSeconds()));
            Text t2 = new Text("消除数量：      " + String.valueOf(Game.getGS()));
            System.out.println(Game.SCORE);

            Text t4 = new Text("再接再厉！");
            t1.setFont(font);
            t2.setFont(font);
            t3.setFont(font);
            t4.setFont(font);
            VBox vb2 = new VBox(t1,t2,t3);
            VBox vb3 = new VBox(t4);
            VBox vb4 = new VBox(vb2,vb3);
            vb2.setAlignment(Pos.BASELINE_CENTER);
            vb3.setAlignment(Pos.BASELINE_CENTER);
            vb2.setSpacing(20);
            vb4.setSpacing(30);
            vb2.setPadding(new Insets(100,0,0,0));
            map.setCenter(vb4);
            //图片
            Image img1 = new Image("/image/win.png");
            ImageView iv1 = new ImageView(img1);
            iv1.setFitWidth(100);
            iv1.setPreserveRatio(true);
            iv1.setLayoutX(150);
            iv1.setLayoutY(110);
            map.getChildren().add(iv1);
            //文字动画
            t1.setOpacity(0);
            t2.setOpacity(0);
            t3.setOpacity(0);
            t4.setOpacity(0);
            // 创建一个淡入动画
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(2), t1);
            fadeTransition1.setToValue(1); // 将透明度设置为1，即完全不透明
            FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(2), t2);
            fadeTransition2.setToValue(1); // 将透明度设置为1，即完全不透明
            FadeTransition fadeTransition3 = new FadeTransition(Duration.seconds(2), t3);
            fadeTransition3.setToValue(1); // 将透明度设置为1，即完全不透明
            FadeTransition fadeTransition4 = new FadeTransition(Duration.seconds(2), t4);
            fadeTransition4.setToValue(1); // 将透明度设置为1，即完全不透明

            // 播放动画
            fadeTransition1.play();
            fadeTransition2.play();
            fadeTransition3.play();
            fadeTransition4.play();



            //背景设置
            map.setStyle("-fx-background-image: url(" + "/image/winbackground.png" + "); " +
                    "-fx-background-position: center center; " +
                    "-fx-background-repeat: stretch;" +
                    "-fx-background-size: 400 533;"+
                    "-fx-background-color:  transparent;");
        }

}
