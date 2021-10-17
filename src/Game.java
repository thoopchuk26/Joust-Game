import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class Game {

    @FXML
    public Pane Space;

    @FXML
    public BorderPane Anchor;

    private Character player1 = new Character();
    private Character player2 = new Character();

    private Rectangle box1 = new Rectangle(75, 150);
    private Rectangle box2 = new Rectangle(75, 150);

    private Text health1 = new Text();
    private Text health2 = new Text();
    private Text info = new Text();
    private Text win = new Text();
    private Text title = new Text();

    private Map<Node, String> map = new HashMap<Node, String>();

    private AnimationTimer timer = new AnimationTimer() {

        @Override
        public void handle(long now) {
                    //update health text
                    health1.setText(String.valueOf(player1.health));
                    health2.setText(String.valueOf(player2.health));
                    //update movement
                    if (box1.getTranslateX() >= 0 && box1.getTranslateX() <= Anchor.getPrefWidth() - box1.getWidth()) {
                        player1.x += player1.velx;
                        box1.setTranslateX(player1.x);
                        player1.set(box1);
                    } else if (box1.getTranslateX() < 0) {
                        box1.setTranslateX(0);
                        player1.stop();
                    } else if (box1.getTranslateX() > 525) {
                        box1.setTranslateX(525);
                        player1.stop();
                    }
                    if (box2.getTranslateX() >= 0 && box2.getTranslateX() <= Anchor.getPrefWidth() - box1.getWidth()) {
                        player2.x += player2.velx;
                        box2.setTranslateX(player2.x);
                        player2.set(box2);
                    } else if (box2.getTranslateX() < 0) {
                        box2.setTranslateX(0);
                        player2.stop();
                    } else if (box2.getTranslateX() > 525) {
                        box2.setTranslateX(525);
                        player2.stop();
                    }
                    //update jump velocity
                    player1.y += player1.vely;
                    box1.setTranslateY(player1.y);
                    player1.set(box1);
                    if (box1.getTranslateY() <= Anchor.getPrefHeight() - 150) {
                        player1.vely += player1.grav;
                    } else {
                        box1.setTranslateY(Anchor.getPrefHeight() - 150);
                    }

                    player2.y += player2.vely;
                    box2.setTranslateY(player2.y);
                    player2.set(box2);
                    if (box2.getTranslateY() <= Anchor.getPrefHeight() - 150){
                        player2.vely += player2.grav;
                    }else{
                        box2.setTranslateY(Anchor.getPrefHeight() - 150);
                    }
                    //check for winner
                    if(player1.health == 0) {
                        win.setText("Player 2 Wins!");
                        Space.getChildren().add(win);
                        timer.stop();
                    } else if(player2.health == 0){
                        win.setText("Player 1 Wins!");
                        Space.getChildren().add(win);
                        timer.stop();
            }
        }

    };

    public void initialize(){
        box1.setTranslateX(0);
        box1.setTranslateY(Anchor.getPrefHeight() - 150);
        box1.setFill(Color.BLUE);
        player1.set(box1);
        map.put(box1, "The Blue Rectangle is Player 1, control movement using WASD and SHIFT to stab");
        box2.setTranslateX(525);
        box2.setTranslateY(Anchor.getPrefHeight() - 150);
        player2.set(box2);
        box2.setFill(Color.RED);
        map.put(box2, "The Red Rectangle is Player 2, control movement using arrow keys and / to stab");
        health1.setText(String.valueOf(player1.health));
        health1.setTranslateX(50);
        health1.setTranslateY(50);
        health1.setScaleX(5);
        health1.setScaleY(5);
        map.put(health1, "The number in the top left is Player 1's health");
        health2.setText(String.valueOf(player2.health));
        health2.setTranslateX(Anchor.getPrefWidth() - 50);
        health2.setTranslateY(50);
        health2.setScaleX(5);
        health2.setScaleY(5);
        map.put(health2, "The number in the top right is Player 2's health");
        win.setTranslateY(Anchor.getPrefHeight()/2);
        win.setTranslateX(Anchor.getPrefWidth()/2);
        win.setScaleX(5);
        win.setScaleY(5);
        title.setText("Joust");
        title.setScaleY(5);
        title.setScaleX(5);
        title.setTranslateY(100);
        title.setTranslateX(275);
        Space.getChildren().add(title);
    }

    @FXML
    public void play() {
        Space.getChildren().clear();
        Space.getChildren().addAll(box1, box2, health1, health2);
        keyCheck();
    }

    @FXML
    public void info(){
        info.setText(map.get(box1) + "\n" + map.get(box2) + "\n" + map.get(health1) + "\n" + map.get(health2) + "\n" + "Reduce the enemies health to 0 to win");
        info.setTranslateX(100);
        info.setTranslateY(300);
        info.setScaleX(1.25);
        info.setScaleY(1.25);
        Space.getChildren().add(info);
    }

    public void keyCheck(){
        Space.setFocusTraversable(true);
        Space.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    player1.left(box1);
                    break;
                case D:
                    player1.right(box1);
                    break;
                case LEFT:
                    player2.left(box2);
                    break;
                case RIGHT:
                    player2.right(box2);
                    break;
                case SHIFT:
                    player1.stab(box1, player2, Space);
                    break;
                case SLASH:
                    player2.stab(box2, player1, Space);
                    break;
                case W:
                    if(box1.getTranslateY() == Anchor.getPrefHeight() - 150)
                        player1.jump(box1);
                    break;
                case UP:
                    if(box2.getTranslateY() == Anchor.getPrefHeight() - 150)
                        player2.jump(box2);
                    break;
            }
        });

        Space.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case A:
                    player1.stop();
                    break;
                case D:
                    player1.stop();
                    break;
                case LEFT:
                    player2.stop();
                    break;
                case RIGHT:
                    player2.stop();
                    break;
            }
        });
        timer.start();
    }

}
