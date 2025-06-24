import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

public class FunctionPanel extends JPanel {

    private final int WIDTH = 800;
    private final int HEIGHT = 400;
    private Function<Double, Double> func;

    private double resultX = Double.NaN;
    private Color pointColor = Color.RED;
    private String infoText = "";

    // Per la visualizzazione del percorso (trace)
    private List<Double> pathX = null;

    public FunctionPanel(Function<Double, Double> func) {
        this.func = func;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void setResultX(double x, Color color, String infoText) {
        this.resultX = x;
        this.pointColor = color;
        this.infoText = infoText;
        repaint();
    }

    public void setPath(List<Double> pathX) {
        this.pathX = pathX;
        repaint();
    }

    public void clearPath() {
        this.pathX = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Background bianco
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        // Asse x da 0 a 10
        double xMin = 0;
        double xMax = 10;

        // Troviamo min e max f(x) per scala verticale
        double yMin = Double.MAX_VALUE;
        double yMax = -Double.MAX_VALUE;
        for (double x = xMin; x <= xMax; x += 0.01) {
            double y = func.apply(x);
            if (y < yMin) yMin = y;
            if (y > yMax) yMax = y;
        }

        // Disegna la funzione
        g2.setColor(Color.BLUE);
        for (double x = xMin; x < xMax; x += 0.01) {
            int x1 = (int) ((x - xMin) / (xMax - xMin) * WIDTH);
            int y1 = (int) (HEIGHT - ((func.apply(x) - yMin) / (yMax - yMin) * HEIGHT));
            double x2d = x + 0.01;
            if (x2d > xMax) break;
            int x2 = (int) ((x2d - xMin) / (xMax - xMin) * WIDTH);
            int y2 = (int) (HEIGHT - ((func.apply(x2d) - yMin) / (yMax - yMin) * HEIGHT));
            g2.drawLine(x1, y1, x2, y2);
        }

        // Disegna i punti del percorso, se presenti
        if (pathX != null && !pathX.isEmpty()) {
            int n = pathX.size();
            for (int i = 0; i < n; i++) {
                double xVal = pathX.get(i);
                double yVal = func.apply(xVal);
                int xPix = (int) ((xVal - xMin) / (xMax - xMin) * WIDTH);
                int yPix = (int) (HEIGHT - ((yVal - yMin) / (yMax - yMin) * HEIGHT));
                // Colore graduale da blu a rosso (progressione)
                float ratio = (float) i / (n - 1);
                Color c = blend(Color.BLUE, Color.RED, ratio);
                g2.setColor(c);
                g2.fillOval(xPix - 4, yPix - 4, 8, 8);
            }
        }

        // Disegna il punto risultato finale
        if (!Double.isNaN(resultX)) {
            int xPix = (int) ((resultX - xMin) / (xMax - xMin) * WIDTH);
            int yPix = (int) (HEIGHT - ((func.apply(resultX) - yMin) / (yMax - yMin) * HEIGHT));
            g2.setColor(pointColor);
            g2.fillOval(xPix - 7, yPix - 7, 14, 14);
        }

        // Disegna testo info sotto al grafico
        if (infoText != null && !infoText.isEmpty()) {
            g2.setColor(Color.DARK_GRAY);
            g2.drawString(infoText, 20, HEIGHT - 10);
        }
    }

    private Color blend(Color c1, Color c2, float ratio) {
        float ir = 1 - ratio;
        float[] rgb1 = c1.getComponents(null);
        float[] rgb2 = c2.getComponents(null);
        float r = rgb1[0] * ir + rgb2[0] * ratio;
        float g = rgb1[1] * ir + rgb2[1] * ratio;
        float b = rgb1[2] * ir + rgb2[2] * ratio;
        return new Color(r, g, b);
    }
}
