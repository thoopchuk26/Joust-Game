import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;

public class Character {
    public int health = 3;
    public double x,y = 0;
    public double velx, vely = 0;
    public double grav = 0.03;
    public Boolean canBeHit = true;

    private AnimationTimer timer;

    public void set(Rectangle player){
        x = player.getTranslateX();
        y = player.getTranslateY();
    }

    public void left(Rectangle player){
        velx = -3;
    }

    public void right(Rectangle player){
        velx = 3;
    }

    public void jump(Rectangle player){
        vely = -3;
    }

    public void stop(){
        velx = 0;
    }

    public void hit_detect(Rectangle stab, Character oppositePlayer, Pane pane){
        timer = new AnimationTimer(){
            long now;
            @Override
            public void handle(long l) {
                if(now == 0){
                    now = l;
                }
                if(l - now < 100000000) {
                    if (stab.getTranslateX() >= oppositePlayer.x && stab.getTranslateX() <= oppositePlayer.x + 75 && canBeHit && stab.getTranslateY() >= oppositePlayer.y && stab.getTranslateY() <= oppositePlayer.y + 150) {
                        oppositePlayer.health--;
                        canBeHit = false;
                    }
                    if (stab.getTranslateX() + 75 >= oppositePlayer.x && stab.getTranslateX() + 75 <= oppositePlayer.x + 75 && canBeHit && stab.getTranslateY() >= oppositePlayer.y && stab.getTranslateY() <= oppositePlayer.y + 150) {
                        oppositePlayer.health--;
                        canBeHit = false;
                    }
                } else{
                    pane.getChildren().remove(stab);
                    now = 0;
                    timer.stop();
                }
            }
        };
        timer.start();
    }

    public void stab(Rectangle player, Character oppositePlayer, Pane pane){
        Rectangle stab = new Rectangle(75, 25);
        stab.setFill(player.getFill());
        stab.setTranslateY(player.getTranslateY()+25);
        stab.setTranslateX(player.getTranslateX());
        if(x < oppositePlayer.x){
            stab.setTranslateX(x+75);
        } else{
            stab.setTranslateX(x-75);
        }
        pane.getChildren().add(stab);
        hit_detect(stab, oppositePlayer, pane);
        canBeHit = true;
    }
}
