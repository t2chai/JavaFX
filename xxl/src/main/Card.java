package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Card extends Pane {

    // 卡片上放置的图形
    Image image = null;
    // 卡片序号
    int imageNo;
    //卡片种类
    String type;
    // 卡片圆角边框
    int arc = 10;
    // 透明度
    double alpha = 1;

    Boolean isCover = false;//默认不被遮盖

    //卡片底色
    Color bgColor = new Color(138 / 255.0, 158 / 255.0, 58 / 255.0, 1);

    Rectangle rectangle = new Rectangle();

    public Card(Image image,String  type) {

        this.setPrefSize(70,70);

        this.type = type;
        this.image = image;

        //大小
        rectangle.setWidth(70);
        rectangle.setHeight(70);

        //圆角
        rectangle.setArcHeight(arc);
        rectangle.setArcWidth(arc);

        //边框
        rectangle.setStrokeWidth(5);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(bgColor);

        this.getChildren().add(rectangle);

        // 添加图片
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        imageView.fitWidthProperty().bind(rectangle.widthProperty());
        imageView.fitHeightProperty().bind(rectangle.heightProperty().divide(1.1));

        getChildren().add(imageView);

    }

    public Boolean getCover() {
        return isCover;
    }

    public void setCover(Boolean cover) {
        isCover = cover;
    }

    public int getImageName() {
        return imageNo;
    }

    public void setImageNo(int imageNo) {
        this.imageNo = imageNo;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
        setOpacity(alpha);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void isCover(Boolean status){//改变状态
        if (status){
            isCover = true;
            rectangle.setFill(Color.LIGHTGRAY);
            setAlpha(.9);
        }else {
            isCover = false;
            rectangle.setFill(Color.WHITE);
            rectangle.setStroke(bgColor);
        }
    }

}