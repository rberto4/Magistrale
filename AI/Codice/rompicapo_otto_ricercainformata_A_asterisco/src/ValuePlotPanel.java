import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ValuePlotPanel extends JPanel {
    private final int WIDTH = 10;
    private final int HEIGHT = 400;
    private List<Double> values = null; // valori f(x) per iterazione
    private Color plotColor = Color.RED;
    private String title = "";

    public ValuePlotPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void setValues(List<Double> values, Color color, String title) {
        this.values = values;
        this.plotColor = color;
        this.title = title;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (values == null || values.size() < 2) return;

        Graphics2D g2 = (Graphics2D) g;

        // sfondo bianco
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        // Titolo
        g2.setColor(Color.BLACK);
        g2.drawString(title, 10, 20);

        // Trova min max valori
        double minVal = values.stream().min(Double::compare).orElse(0.0);
        double maxVal = values.stream().max(Double::compare).orElse(1.0);

        int n = values.size();

        // Disegna assi
        g2.setColor(Color.GRAY);
        g2.drawLine(40, HEIGHT - 30, WIDTH - 10, HEIGHT - 30); // asse x
        g2.drawLine(40, 10, 40, HEIGHT - 30); // asse y

        // Disegna linee valori
        g2.setColor(plotColor);
        int prevX = 40;
        int prevY = (int) (HEIGHT - 30 - ((values.get(0) - minVal) / (maxVal - minVal) * (HEIGHT - 40)));

        for (int i = 1; i < n; i++) {
            int x = 40 + (int) ((WIDTH - 50) * (double) i / (n - 1));
            int y = (int) (HEIGHT - 30 - ((values.get(i) - minVal) / (maxVal - minVal) * (HEIGHT - 40)));
            g2.drawLine(prevX, prevY, x, y);
            prevX = x;
            prevY = y;
        }
    }
}
