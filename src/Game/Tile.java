package Game;

import javax.swing.*;
import java.awt.*;

public class Tile extends JLabel{

    private int value;

    /**
     * Constructor
     * @param value initial value of the tile - 2 or 4
     */
    public Tile(int value){
        super(""+value);

        setForeground(getColor(value)[0]); // text color
        setBackground(getColor(value)[1]); // block color

        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("default",Font.BOLD,40));
        setOpaque(true);

        this.value = value;
    }

    /**
     * This method is called whenever this piece is being combined with another to double its value.
     * It doubles the value of the piece and updates the colors and font accordingly.
     */
    public void upgrade(){
        value*=2;
        setText(""+value);
        setForeground(getColor(value)[0]); // text color
        setBackground(getColor(value)[1]); // block color
        if(value>100){
            setFont(new Font("default",Font.BOLD,30));
        }
    }

    /**
     * @return value of the tile
     */
    public int getValue(){
        return value;
    }

    /**
     * Returns an Array of Colors corresponding to the block type
     * @param value 2 = "2" , 4 = "4", 8 = "8", 16 = "16", 32 = "32", 64 = "64" ,
     *              128 = "128", 256 = "256", 512 = "512", 1024 = "1024", 2048 = "2048"
     * @return - Array of size 2: 1st index is color of text, 2nd index is color of background
     */
    private Color[] getColor(int value){
        Color text = (value<5) ? new Color(158,149,140) : new Color(248,246,242);

        return switch (value) {
            case 2 -> new Color[]{text, new Color(238, 228, 218)};
            case 4 -> new Color[]{text, new Color(237, 224, 199)};
            case 8 -> new Color[]{text, new Color(241, 177, 122)};
            case 16 -> new Color[]{text, new Color(245, 149, 99)};
            case 32 -> new Color[]{text, new Color(255, 116, 85)};
            case 64 -> new Color[]{text, new Color(244, 95, 59)};
            case 128 -> new Color[]{text, new Color(240, 207, 95)};
            case 256 -> new Color[]{text, new Color(243, 202, 73)};
            case 512 -> new Color[]{text, new Color(242, 200, 44)};
            case 1024 -> new Color[]{text, new Color(237, 197, 63)};
            default -> new Color[]{text, new Color(234, 184, 0)};
        };
    }
}

