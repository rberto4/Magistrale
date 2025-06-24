import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class OptimizationDemoApp extends JFrame {

    private FunctionPanel functionPanel;
    private ValuePlotPanel valuePlotPanel;
    private JTextField startField, stepField, tempField, coolingField, iterField;
    private JButton runHCButton, runSAButton;

    // Colori per più esecuzioni
    private Color[] colors = {Color.RED, Color.GREEN.darker(), Color.MAGENTA, Color.ORANGE, Color.CYAN.darker()};
    private int runCount = 0;

    public OptimizationDemoApp() {
        setTitle("Simulated Annealing & Hill Climbing Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        functionPanel = new FunctionPanel(OptimizationAlgorithms::f);
        valuePlotPanel = new ValuePlotPanel();

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 6, 5, 5));

        startField = new JTextField("5");
        stepField = new JTextField("0.1");
        tempField = new JTextField("10");
        coolingField = new JTextField("0.95");
        iterField = new JTextField("1000");

        runHCButton = new JButton("Run Hill Climbing");
        runSAButton = new JButton("Run Simulated Annealing");

        controlPanel.add(new JLabel("Start x:"));
        controlPanel.add(new JLabel("Step:"));
        controlPanel.add(new JLabel("Temp:"));
        controlPanel.add(new JLabel("Cooling:"));
        controlPanel.add(new JLabel("Max Iter:"));
        controlPanel.add(new JLabel(""));

        controlPanel.add(startField);
        controlPanel.add(stepField);
        controlPanel.add(tempField);
        controlPanel.add(coolingField);
        controlPanel.add(iterField);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(runHCButton);
        buttonsPanel.add(runSAButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(controlPanel, BorderLayout.CENTER);
        topPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(functionPanel, BorderLayout.CENTER);
        add(valuePlotPanel, BorderLayout.SOUTH);

        runHCButton.addActionListener(this::runHillClimbing);
        runSAButton.addActionListener(this::runSimulatedAnnealing);

        setSize(820, 700);
        setLocationRelativeTo(null);
    }

    private void runHillClimbing(ActionEvent e) {
        try {
            double start = Double.parseDouble(startField.getText());
            double step = Double.parseDouble(stepField.getText());
            int maxIter = Integer.parseInt(iterField.getText());
            OptimizationAlgorithms.Result res = OptimizationAlgorithms.hillClimbing(start, step, maxIter);

            Color c = colors[runCount % colors.length];
            runCount++;

            functionPanel.setPath(res.pathX);
            functionPanel.setResultX(res.bestX, c, "Hill Climbing Result: x=" + String.format("%.3f", res.bestX) + " f(x)=" + String.format("%.3f", OptimizationAlgorithms.f(res.bestX)));

            valuePlotPanel.setValues(res.pathF, c, "Hill Climbing f(x) over iterations");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input non valido");
        }
    }

    private void runSimulatedAnnealing(ActionEvent e) {
        try {
            double start = Double.parseDouble(startField.getText());
            double temp = Double.parseDouble(tempField.getText());
            double cooling = Double.parseDouble(coolingField.getText());
            int maxIter = Integer.parseInt(iterField.getText());
            OptimizationAlgorithms.Result res = OptimizationAlgorithms.simulatedAnnealing(start, temp, cooling, maxIter);

            Color c = colors[runCount % colors.length];
            runCount++;

            functionPanel.setPath(res.pathX);
            functionPanel.setResultX(res.bestX, c, "Simulated Annealing Result: x=" + String.format("%.3f", res.bestX) + " f(x)=" + String.format("%.3f", OptimizationAlgorithms.f(res.bestX)));

            valuePlotPanel.setValues(res.pathF, c, "Simulated Annealing f(x) over iterations");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input non valido");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new OptimizationDemoApp().setVisible(true);
        });
    }
}
