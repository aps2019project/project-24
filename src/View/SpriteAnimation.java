package View;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class SpriteAnimation extends Transition {

    private final ArrayList<Integer> x;
    private final ArrayList<Integer> y;
    private final int count;
    private final int size;
    private final ImageView imageView;

    private int lastIndex;
    private int indexCoord;

    public SpriteAnimation(int count , ArrayList<Integer> x , ArrayList<Integer> y , int size , ImageView imageView ,Duration duration) {
        this.imageView = imageView;
        this.count = count;
        this.size = size;
        this.x =  x;
        this.y = y;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex && indexCoord < count) {
            imageView.setViewport(new Rectangle2D(x.get(indexCoord), y.get(indexCoord), size, size));
            lastIndex = index;
            indexCoord = (indexCoord+1)%count;
        }
    }
}