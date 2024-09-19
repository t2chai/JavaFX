package main;

import javafx.animation.*;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class StartCG {
    public int Width = 400;

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
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

    public void init_map() {

        Image flower = new Image("/image/flower.png");

        ImageView iv1 = new ImageView(flower);
        iv1.setFitHeight(50);
        iv1.setPreserveRatio(true); // 保持原比例;

        //进度条
        ProgressBar pb = new ProgressBar();
        pb.setPrefHeight(20);
        pb.setPrefWidth(400);
        pb.setStyle("-fx-accent: green;");

        Duration duration = Duration.seconds(3.0);  // 持续时间

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(pb.progressProperty(),0)),  // 起始关键帧
                new KeyFrame(duration, new KeyValue(pb.progressProperty(), 1.0))  // 结束关键帧
        );

        timeline.setOnFinished(e->Run.startUI());

        //雏菊的动画
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.seconds(3));
        tt.setFromX(-200);
        tt.setToX(400);
        tt.setFromY(150);
        tt.setToY(100);
        tt.setCycleCount(Animation.INDEFINITE);

        RotateTransition rt = new RotateTransition();
        rt.setDuration(Duration.seconds(1));
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);

        ParallelTransition pt = new ParallelTransition();
        pt.setNode(iv1);
        pt.getChildren().addAll(timeline,tt,rt);

        pt.setOnFinished(e->Run.startUI());

        map.setBottom(pb);

        map.setStyle("-fx-background-image: url(" + "/image/background5.png" + "); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;" +
                "-fx-background-size: 400 533;" +
                "-fx-background-color:  transparent;");

        map.setCenter(iv1);

        pt.play();

    }
}

