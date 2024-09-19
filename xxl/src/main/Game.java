package main;


import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

import java.util.*;

@FunctionalInterface
interface ProofFunction {//如何消除卡片，按序与不按序两种,可自定义其他的方法
    Boolean proof(List<Card> cards,Pane pane);
}

public class Game {

    public Pane map = new Pane();//游戏界面

    AudioClip click;
    AudioClip fail;
    AudioClip win;

    ProofFunction proofFunction;

    Timeline timeline = new Timeline();

    List<Card> mainList = new ArrayList<>();//主卡区卡片列表
    List<Card> viceList = new ArrayList<>();//副卡区卡片列表
    List<Card> proofList = new ArrayList<>();//验卡区卡片列表

    private  static int WIDTH = 420;//卡区宽度
    private static int HEIGHT = 420;//卡区高度
    private static int XGAP = 400;//
    private  static int YGAP = 500;
    private  static int LEVEL = 10;//主卡区层数
    public  static int FLOP = 0;//副卡区每种组数
    public static int TYPE = 12;//卡片种类
    public static int GROUP = 1;//主卡区每种组数
    private static int GS = 0;//消除数量
    public static int SKIN = 1;//皮肤选择1或2

    public static int SCORE = 0;//得分
    
    public int getGROUP() {
		return GROUP;
	}

	public void setGROUP(int gROUP) {
		GROUP = gROUP;
	}

	public static int getSCORE() {
		return SCORE;
	}

	public static void setSCORE(int sCORE) {
		SCORE = sCORE;
	}

	public static int getGS() {
		return GS;
	}

	public static void setGS(int gS) {
		GS = gS;
	}

	public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getXGAP() {
        return XGAP;
    }

    public int getYGAP() {
        return YGAP;
    }

    public int getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(int LEVEL) {
        this.LEVEL = LEVEL;
    }

    public int getFLOP() {
        return FLOP;
    }

    public void setFLOP(int FLOP) {
        this.FLOP = FLOP;
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

   

    //初始化游戏-默认设置
    public void initGame(){
        //初始化场景
        initMap();
        //读取音频
        readAudio();
        //默认无序验证
        initProof(unorderProof);
        //初始化主卡区
        initMainCardArea();
        //初始化副卡区
        initViceCardArea();
        //初始化验卡区
        initProofCardArea();

    }

    //初始化游戏-自定义游戏设置
    public void initProof(ProofFunction proofFunction){
        this.proofFunction = proofFunction;
    }

    public void initMap(){

        //设置大小
        map.setPrefSize(WIDTH + XGAP,HEIGHT + YGAP);
        //背景图片
        ImageView background = new ImageView( new Image(ReadResourceUtil.getUri("game.png").toString()));
        map.getChildren().add(background);

        //属性绑定，背景图自适应
        map.widthProperty().addListener((obs, oldVal, newVal) -> {
            background.setFitWidth(newVal.doubleValue());
        });
        map.heightProperty().addListener((obs, oldVal, newVal) -> {
            background.setFitHeight(newVal.doubleValue());
        });

    }

    //初始化验证卡区
    public void initProofCardArea(){

    }

    public void readAudio(){
        click = new AudioClip(ReadResourceUtil.readAudio("click.wav").toString());
        fail = new AudioClip(ReadResourceUtil.readAudio("fail.wav").toString());
        win = new AudioClip(ReadResourceUtil.readAudio("win.mp3").toString());
    }

    //初始化主卡区
    public void initMainCardArea(){

        //计算集合坐标
        double startX =XGAP / 2.0;
        double startY =YGAP / 4.0;

        //添加卡片至主卡区
        for(int i=0;i<TYPE;i++){
            for(int j=0;j<GROUP * 3;j++){
                Card card = new Card(new Image("/skin/"+"a"+SKIN+"/" +i+".png"),""+i);
                card.setOnMouseClicked(event -> mainCardClick(card));
                mainList.add(card);
            }

        }

        //打乱顺序
        Collections.shuffle(mainList);

        //计算层数
        LEVEL  = (int) Math.ceil(mainList.size() / (WIDTH / 70.0 * HEIGHT / 70.0));

        //设置主卡区卡牌坐标
        for (int k=0,n=0;k<LEVEL;k++) {
            for (int i = 0; i < WIDTH; i += 70) {
                for (int j = 0; j < HEIGHT; j += 70) {
                    System.out.println(i + " " + " " + j + " " + n);
                    Random random = new Random();

                    double x = startX + i + 35 * random.nextInt(2);
                    double y = startY + j + 35 * random.nextInt(2);

                    mainList.get(n).setLayoutX(x);
                    mainList.get(n).setLayoutY(y);

                    for (int ii = 0; ii < n; ii++) {
                        double xx = mainList.get(ii).getLayoutX();
                        double yy = mainList.get(ii).getLayoutY();
                        if (xx >= x - 35 && xx <= x + 35 && yy >= y - 35 && yy <= y + 35) {
                            mainList.get(ii).isCover(true);
                        }
                    }

                    mainList.get(n).setImageNo(n);
                    n++;
                }
            }
        }
        map.getChildren().addAll(mainList);
    }

    //初始化副卡区
    public void initViceCardArea(){

        //计算集合坐标
        double startX =XGAP / 2.0;
        double startY =YGAP / 4.0;

        //添加至副卡区
        for(int i=0;i<TYPE;i++){
            for(int j=0;j<FLOP * 3;j++){
                Card card = new Card(new Image("/skin/"+"a"+SKIN+"/" +i+".png"),""+i);
                card.setOnMouseClicked(event -> viceCardClick(card));
                viceList.add(card);
            }

        }

        //打乱顺序
        Collections.shuffle(viceList);

        //设置副卡区坐标
        for (int i=0;i<viceList.size();i++){

            viceList.get(i).isCover(true);

            if (i==0||i==viceList.size()-1){
                viceList.get(i).isCover(false);
            }

            viceList.get(i).setLayoutX((WIDTH + XGAP) / 2.0 + (viceList.size() / 2.0 - i) * 3);
            viceList.get(i).setLayoutY(HEIGHT + YGAP / 2.5);
        }

        //添加到map
        map.getChildren().addAll(viceList);

        //第一张卡片放在最前面
        if (viceList.size()>1){
            viceList.get(0).toFront();
        }

    }

    //主卡区卡牌点击事件
    public void mainCardClick(Card card){

        if (!card.isCover){//卡片没有被覆盖

            click.play();

            //从主卡区移除该卡片
            mainList.remove(card);

            //解除被覆盖的卡片 - 这里可以改进算法
            for (int i = 0; i< mainList.size(); i++){//1 先全部设为未被覆盖，但外观不变
                mainList.get(i).setCover(false);
            }

            for (int i = 0; i< mainList.size(); i++){//2 再依次判断是否被覆盖

                double x = mainList.get(i).getLayoutX();
                double y = mainList.get(i).getLayoutY();
                int z = mainList.get(i).getImageName();

                for (int j = 0; j< mainList.size(); j++){

                    double xx = mainList.get(j).getLayoutX();
                    double yy = mainList.get(j).getLayoutY();
                    int zz = mainList.get(j).getImageName();

                    if (xx>=x-35&&xx<=x+35&&yy>=y-35&&yy<=y+35 && z > zz){//i与j的图片存在重叠
                        mainList.get(j).setCover(true);
                    }

                }

                for (Card c : mainList){//3未被覆盖的则变为可选中的颜色
                    c.isCover(c.isCover);
                }

            }
            addCardToProof(card,proofFunction);
        }
    }

    //副卡区卡牌点击事件
    public void viceCardClick(Card card){

        if (!card.isCover){

            click.play();

            //移除卡片
            viceList.remove(card);

            //重新排列
            if (viceList.size()>1){

                viceList.get(0).isCover(false);
                viceList.get(0).toFront();
                viceList.get(viceList.size()-1).isCover(false);

                if ((int)viceList.get(0).getLayoutX() - (int)viceList.get(viceList.size()-1).getLayoutX() > 70) {

                    for (int i = 0; i < viceList.size(); i++) {
                        viceList.get(i).setLayoutX((WIDTH + XGAP) / 2.0 + (viceList.size() / 2.0 - i +1) * 3);
                    }

                }else{//只显示两张

                    for (int i = 0;i< viceList.size() / 2;i++){
                        viceList.get(i).setLayoutX((WIDTH + XGAP) / 2.0 -70);
                    }

                    for (int i = viceList.size() / 2;i< viceList.size();i++){
                        viceList.get(i).setLayoutX((WIDTH + XGAP) / 2.0);
                    }

                    viceList.get(0).toFront();
                }
            }
            addCardToProof(card,proofFunction);
        }

    }

    //添加卡片到验卡区
    public void addCardToProof(Card card,ProofFunction proofFunction){

        //添加到验卡区
        proofList.add(card);

        card.setCover(true);

        //设置坐标
        card.setLayoutY(HEIGHT+YGAP/1.5);
        card.setLayoutX(XGAP / 2.0 + (proofList.size()-1) * 70);

        //验证卡片是否符合消除规则
        if (proofFunction.proof(proofList,map)){
            for (int i=0;i < proofList.size();i++){
                proofList.get(i).setLayoutX(XGAP / 2.0 + i * 70);
            }

            if (proofList.size()==0&&mainList.size()==0&&viceList.size()==0){
                gameOver(true);
            }else if(proofList.size()>0&&mainList.size()==0&&viceList.size()==0){
                gameOver(true);
            }

        }else{
            gameOver(false);
        }

    }

    public void gameOver(boolean status){
        timeline.stop();
        StartGame.time.stop();
        Run.settlementUI();
        if (status){
            win.play();
            SCORE = TYPE * (GROUP + FLOP) * 3;
        }else{
            fail.play();
            SCORE = 0;
        }

    }


    //按序验证-连续三张才能消除
    ProofFunction orderProof = (List<Card> cards,Pane map) -> {

        if (cards.size()>2){
            for (int i=0;i<cards.size()-2;i++){
                if (cards.get(i).getType().equals(cards.get(i+1).getType())&&cards.get(i+1).getType().equals(cards.get(i+2).getType())){
                    for (int j=0;j<3;j++){
                        map.getChildren().remove(cards.get(i));
                        cards.remove(i);
                        GS++;
                    }
                }
            }
        }

        return cards.size() < 8;

    };

    //无序验证,只要卡池满3个就判定为消除
    ProofFunction unorderProof = (List<Card> cards,Pane map) -> {
        if (cards.size()>2){
            cards.sort(Comparator.comparing(Card::getType));
            for (int i=0;i<cards.size()-2;i++){
                if (cards.get(i).getType().equals(cards.get(i+1).getType())&&cards.get(i+1).getType().equals(cards.get(i+2).getType())){
                    for (int j=0;j<3;j++){
                        map.getChildren().remove(cards.get(i));
                        cards.remove(i);
                        GS++;
                    }
                }
            }
        }
        return cards.size() < 8;
    };


}
