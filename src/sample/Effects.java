package sample;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;


public class Effects {

    public static void inflateDeflate(Node node){

        node.setOnMouseEntered(event -> {
            ScaleTransition scaleTransition = new ScaleTransition();
            scaleTransition.setNode((Node)event.getSource());
            scaleTransition.setDuration(Duration.millis(100));
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();

        });

        node.setOnMouseExited(event -> {
            ScaleTransition scaleTransition = new ScaleTransition();
            scaleTransition.setNode((Node)event.getSource());
            scaleTransition.setDuration(Duration.millis(100));
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();

        });


    }
}
