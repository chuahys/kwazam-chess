import java.awt.*;
import javax.swing.*;

public class Component extends JButton {
    private int row;
    private int col;

    public Component(int row, int col) {
        this.row = row;
        this.col = col;
        initButton();
    }

    private void initButton() {
        setPreferredSize(new Dimension(100,100));
        setBackground(Color.LIGHT_GRAY);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    public void setPieceImage(String imagePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image image = icon.getImage(); // Get the image from the icon
        Image scaledImage = image.getScaledInstance(50,50, Image.SCALE_SMOOTH); // Scale the image to fit the button
        this.setIcon(new ImageIcon(scaledImage)); // Set the scaled image as the icon
    }
    
    public void clearPieceImage() {
        this.setIcon(null);
    }

    // public void setPieceSymbol(String symbol, Color color) {
    //     this.setText(symbol);
    //     this.setForeground(color);
    // }

    // public void clearPieceSymbol() {
    //     this.setText("");
    // }
}