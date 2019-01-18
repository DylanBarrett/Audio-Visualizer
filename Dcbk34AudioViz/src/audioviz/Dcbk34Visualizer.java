/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 *
 * @author Dylan Barrett
 */
public class Dcbk34Visualizer implements Visualizer {
    private final String name = "Dcbk34Visualizer";
    
    private Integer numBands;
    private AnchorPane vizPane;
    private String vizPaneInitialStyle = "";
    
    private final Double bandHeightPercentage = 1.3;
    private final Double minRectangleHeight = 10.0;  // 10.0
    private final Double halfMinRectangleHeight = minRectangleHeight / 2;
    
    private Double width = 0.0;
    private Double height = 0.0;
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;
    private final Double startHue = 260.0;
    private Rectangle[] rec;
    private Rectangle[] rec1;
    
    public Dcbk34Visualizer() {
        
    }
    
    @Override
    public void start(Integer numBands, AnchorPane vizPane) {
        end(); 
        this.numBands = numBands;
        this.vizPane = vizPane;
        vizPaneInitialStyle = vizPane.getStyle();
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        Rectangle clip = new Rectangle(width, height);
        clip.setLayoutX(0);
        clip.setLayoutY(0);
        vizPane.setClip(clip);
        
        bandWidth = width / numBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
        
        rec = new Rectangle[numBands];
        rec1 = new Rectangle[numBands];
        
        for (int i = 0; i < numBands; i++) {
            Rectangle rectangle = new Rectangle();
            rectangle.setX(bandWidth + bandWidth * i);
            rectangle.setY(height / 2 - halfMinRectangleHeight);
            rectangle.setWidth(bandWidth);
            rectangle.setHeight(minRectangleHeight);
            rectangle.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().add(rectangle);
            rec[i] = rectangle;
        }
        for (int i = 0; i < numBands; i++) {
            Rectangle rectangle1 = new Rectangle();
            rectangle1.setX(bandWidth + bandWidth * i);
            rectangle1.setY(height / 2 + halfMinRectangleHeight);
            rectangle1.setWidth(bandWidth);
            rectangle1.setHeight(minRectangleHeight);
            rectangle1.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().add(rectangle1);
            rec1[i] = rectangle1;
        }
    }
    
    @Override
    public void end() {
         if (rec != null || rec1 != null) {
             for (Rectangle rectangle : rec) {
                 vizPane.getChildren().remove(rectangle);
             }
             for (Rectangle rectangle1 : rec1) {
                 vizPane.getChildren().remove(rectangle1);
             }
            rec = null;
            rec1 = null;
            vizPane.setClip(null);
            vizPane.setStyle(vizPaneInitialStyle);
        }       
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases) {
        if (rec == null || rec1 == null) {
            return;
        }
        Integer num = min(rec.length, magnitudes.length);
        
        for (int i = 0; i < num; i++) {
            rec[i].setHeight( ((60.0 + magnitudes[i])/60.0) * halfBandHeight + minRectangleHeight);
            rec[i].setTranslateY(- rec[i].getHeight());
            rec[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            
            rec1[i].setHeight( ((60.0 + magnitudes[i])/60.0) * halfBandHeight + minRectangleHeight);
//            rec1[i].setTranslateY(rec1[i].getHeight());
            rec1[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
        }
    }
}