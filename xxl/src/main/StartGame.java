package main;

import java.net.URI;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class StartGame {
    public int width = 400;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public BorderPane getMap() {
        return map;
    }

    public void setMap(BorderPane map) {
        this.map = map;
    }

    public int Height = 533;
    public BorderPane map = new BorderPane();

    private static final double ANIMATION_DURATION = 1000;
    private MediaPlayer mediaPlayer;
    public int musicflag = 1;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final int LEAF_WIDTH = 50;
    private static final int LEAF_HEIGHT = 50;

    private static final int NUM_LEAVES = 15;
    public static Time time = new Time();
    public void init_map() {
        //点击音效设置
        AudioClip sound = new AudioClip(ReadResourceUtil.readMusic("click.wav").toString());

        //背景
        map.setStyle("-fx-background-image: url(" + "/image/background2.png" + "); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;" +
                "-fx-background-size: 400 533;" +
                "-fx-background-color:  transparent;");
        //背景音乐
        Media media = new Media(ReadResourceUtil.readMusic("bgmusic.mp3").toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // 设置循环播放

        mediaPlayer.play();

        //控制背景音乐按钮
        Image control = new Image("/image/bgm.png");

        ImageView imageView = new ImageView(control);

        imageView.setFitWidth(20); // 设置宽度
        imageView.setPreserveRatio(true); // 保持原比例;
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), imageView);
        rotateTransition.setFromAngle(0); // 起始角度为0度
        rotateTransition.setToAngle(360); // 终止角度为360度
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE); // 无限循环
        rotateTransition.setAutoReverse(false); // 不反向旋转
        rotateTransition.play();
        Button bt1 = new Button();
        bt1.setGraphic(imageView);
        bt1.setStyle("-fx-background-insets: 0.0;" +
                "-fx-background-color: transparent;");
        bt1.setOnAction(e -> {
            if (musicflag == 1) {
                System.out.print("1111");
                mediaPlayer.stop();
                rotateTransition.stop();
                musicflag = 0;
                sound.play(); // 播放音效
            } else {
                mediaPlayer.play();
                rotateTransition.play();
                musicflag = 1;
                sound.play(); // 播放音效
            }
        });
        bt1.setPadding(new Insets(0, 0, 0, 0));

        map.setTop(bt1);

        //标题
        Image font = new Image("/image/title.png");
        ImageView iv = new ImageView(font);
        VBox fontbox = new VBox(iv);
        fontbox.setAlignment(Pos.BASELINE_CENTER);
        fontbox.setPadding(new Insets(95, 0, 0, 0));

        //开始游戏按钮
        Font fon1 = Font.font("Microsoft YaHei", FontWeight.BOLD, FontPosture.REGULAR, 18);
        Button startgame = new Button("开始游戏");
        startgame.setStyle("-fx-background-color: #F9DDC9;" +
                "-fx-effect: dropshadow(three-pass-box, #000, 10, 0, 0, 0);" +
                "-fx-border-color: #FBB9AB; -fx-border-width: 2px; -fx-border-radius: 5px; ");

        startgame.setFont(fon1);

        startgame.setOnAction(e -> {
            sound.play(); // 播放音效
            Run.gameUI();
            mediaPlayer.stop();
            time.start();
        });

        //设置按钮
        Button set = new Button("基础设置");
        set.setStyle("-fx-background-color: #F9DDC9;" +
                "-fx-effect: dropshadow(three-pass-box, #000, 10, 0, 0, 0);" +
                "-fx-border-color: #FBB9AB; -fx-border-width: 2px; -fx-border-radius: 5px; ");

        set.setFont(fon1);
        set.setOnAction(e -> {
            sound.play(); // 播放音效
            Run.setUI();
            mediaPlayer.stop();
        });
        
        //关于按钮
        Button about = new Button("关于游戏");
        about.setStyle("-fx-background-color: #F9DDC9;" +
                "-fx-effect: dropshadow(three-pass-box, #000, 10, 0, 0, 0);" +
                "-fx-border-color: #FBB9AB; -fx-border-width: 2px; -fx-border-radius: 5px; ");

        about.setFont(fon1);
        about.setOnAction(e -> {
            sound.play(); // 播放音效
            Run.aboutUI();
            mediaPlayer.stop();
        });

        VBox buttonbox = new VBox(startgame, set, about);
        buttonbox.setSpacing(15);
        buttonbox.setAlignment(Pos.CENTER);
        buttonbox.setPadding(new Insets(0, 0, 80, 0));
        map.setBottom(buttonbox);
        //图片中心动画
        ImageView[] fivs = new ImageView[5];
        for (int i = 0; i < 5; i++) {
            Image image = new Image("/image/" + i + ".png");
            fivs[i] = new ImageView(image);
            fivs[i].setFitWidth(65);
            fivs[i].setFitHeight(65);
        }

        TranslateTransition tt1 = new TranslateTransition(Duration.millis(ANIMATION_DURATION), fivs[0]);
        tt1.setToY(40);
        tt1.setAutoReverse(true);
        tt1.setCycleCount(Animation.INDEFINITE);
        tt1.play();
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(ANIMATION_DURATION), fivs[1]);
        tt2.setToY(40);
        tt2.setAutoReverse(true);
        tt2.setCycleCount(Animation.INDEFINITE);
        tt2.play();
        TranslateTransition tt3 = new TranslateTransition(Duration.millis(ANIMATION_DURATION), fivs[2]);
        tt3.setToY(40);
        tt3.setAutoReverse(true);
        tt3.setCycleCount(Animation.INDEFINITE);
        tt3.play();
        TranslateTransition tt4 = new TranslateTransition(Duration.millis(ANIMATION_DURATION), fivs[3]);
        tt4.setToY(40);
        tt4.setAutoReverse(true);
        tt4.setCycleCount(Animation.INDEFINITE);
        tt4.play();
        TranslateTransition tt5 = new TranslateTransition(Duration.millis(ANIMATION_DURATION), fivs[4]);
        tt5.setToY(40);
        tt5.setAutoReverse(true);
        tt5.setCycleCount(Animation.INDEFINITE);
        tt5.play();
        HBox imagebox = new HBox(fivs);
        imagebox.setAlignment(Pos.BASELINE_CENTER);
        imagebox.setPadding(new Insets(30, 0, 0, 0));
        VBox img_title = new VBox(fontbox, imagebox);
        map.setCenter(img_title);
        //樱花树叶落下的动画
        Image leafImage = new Image("image/free.png");
        Image flowerImage = new Image("image/yinhua.png");
        for (int i = 0; i < NUM_LEAVES; i++) {
            ImageView leafView = new ImageView(leafImage);
            leafView.setFitWidth(LEAF_WIDTH);
            leafView.setFitHeight(LEAF_HEIGHT);

            // 随机设置树叶的位置
            Random random = new Random();
            int x = random.nextInt(WIDTH - LEAF_WIDTH);
            int y = -LEAF_HEIGHT;
            leafView.setLayoutX(x);
            leafView.setLayoutY(y);

            // 创建TranslateTransition对象并设置其持续时间、结束位置和循环次数
            TranslateTransition transition = new TranslateTransition(Duration.seconds(5 + random.nextDouble() * 5), leafView);
            transition.setToY(HEIGHT);
            transition.setCycleCount(Animation.INDEFINITE);

            // 开始动画
            transition.play();
            map.getChildren().addAll(leafView);
        }
        for (int j = 0; j < NUM_LEAVES; j++) {
            ImageView flowerView = new ImageView(flowerImage);
            flowerView.setFitWidth(LEAF_WIDTH);
            flowerView.setFitHeight(LEAF_HEIGHT);

            // 随机设置树叶的位置
            Random random = new Random();
            int x2 = random.nextInt(WIDTH - LEAF_WIDTH);
            int y2 = -LEAF_HEIGHT;
            flowerView.setLayoutX(x2);
            flowerView.setLayoutY(y2);

            // 创建TranslateTransition对象并设置其持续时间、结束位置和循环次数
            TranslateTransition transition2 = new TranslateTransition(Duration.seconds(5 + random.nextDouble() * 5), flowerView);
            transition2.setToY(HEIGHT);
            transition2.setCycleCount(Animation.INDEFINITE);

            // 开始动画
            transition2.play();

            map.getChildren().addAll(flowerView);

        }

    }
}
