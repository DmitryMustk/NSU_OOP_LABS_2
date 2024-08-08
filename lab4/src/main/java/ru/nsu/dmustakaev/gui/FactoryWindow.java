package ru.nsu.dmustakaev.gui;

import ru.nsu.dmustakaev.factory.Factory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Hashtable;

public class FactoryWindow extends JFrame {
    private static final int DELAY_VARIETY = 10;

    private Factory factory;

    private JLabel priceLabel;
    private JLabel bodyStoreSizeLabel;
    private JLabel engineStoreSizeLabel;
    private JLabel accessoryStoreSizeLabel;

    private JLabel totalSoldLabel;
    private JLabel totalGainLabel;
    private JLabel buildStateLabel;

    public FactoryWindow() {
        super("Factory");
    }

    public void start() throws IOException {
        factory = new Factory();
        factory.start();

        SwingUtilities.invokeLater(this::init);
    }

    public interface SliderListener {
        void valueChanged(int newValue);
    }

    private Box createSlider(Integer startingDelay, String descr, SliderListener listener) {
        Box panel = new Box(BoxLayout.Y_AXIS);

        int min = 0;
        int max = startingDelay * DELAY_VARIETY;
        JSlider slider = new JSlider(min, max, startingDelay);

        Hashtable<Integer, JLabel> labelHashtable = new Hashtable<>();

        labelHashtable.put(min, new JLabel(Integer.toString(min)));
        labelHashtable.put(max, new JLabel(Integer.toString(max)));

        slider.setLabelTable(labelHashtable);
        slider.setPaintLabels(true);

        JLabel label = new JLabel(descr + startingDelay);

        slider.addChangeListener(e -> {
            int value = ((JSlider) e.getSource()).getValue();
            label.setText(descr + value);
            listener.valueChanged(value);
        });

        panel.add(label);
        panel.add(slider);

        return panel;
    }

    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        setResizable(false);
        getContentPane().add(mainPanel);

        //
        BorderPanel storageStatePanel = new BorderPanel("Storage state");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        mainPanel.add(storageStatePanel, gridBagConstraints);

        bodyStoreSizeLabel = new JLabel();
        storageStatePanel.addIn(bodyStoreSizeLabel);

        engineStoreSizeLabel = new JLabel();
        storageStatePanel.addIn(engineStoreSizeLabel);

        accessoryStoreSizeLabel = new JLabel();
        storageStatePanel.addIn(accessoryStoreSizeLabel);
        //
        //
        BorderPanel factoryStatePanel = new BorderPanel("Factory state");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        mainPanel.add(factoryStatePanel, gridBagConstraints);

        priceLabel = new JLabel();
        factoryStatePanel.addIn(priceLabel);

        totalSoldLabel = new JLabel();
        factoryStatePanel.addIn(totalSoldLabel);

        totalGainLabel = new JLabel();
        factoryStatePanel.addIn(totalGainLabel);

        buildStateLabel = new JLabel();
        factoryStatePanel.addIn(buildStateLabel);
        //
        //
        BorderPanel factoryControlPanel = new BorderPanel("Factory controls");
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        mainPanel.add(factoryControlPanel, gridBagConstraints);
        factoryControlPanel.addIn(createSlider(
                factory.getBodySupplier().getDelay(),
                "Car body delay: ",
                (int value) -> factory.setBodySupplierDelay(value)));
        factoryControlPanel.addIn(createSlider(
                factory.getEngineSupplier().getDelay(),
                "Car engine delay: ",
                (int value) -> factory.setEngineSupplierDelay(value)));
        factoryControlPanel.addIn(createSlider(
                factory.getAccessorySupplier().getDelay(),
                "Car accessory delay: ",
                (int value) -> factory.setAccessorySupplierDelay(value)));

        Timer timer = new Timer(200, (ActionEvent e) -> updateInformation());
        timer.start();

        updateInformation();
        pack();
        setVisible(true);
    }

    private void updateInformation() {
        priceLabel.setText("Car price: " + new DecimalFormat("#0.##").format(factory.getDealer().getCarPrice()));
        bodyStoreSizeLabel.setText("Car body storage size: " + factory.getBodyStore().getSize() + " / " + factory.getBodyStore().getCapacity());
        engineStoreSizeLabel.setText("Car engine storage size: " + factory.getEngineStore().getSize() + " / " + factory.getEngineStore().getCapacity());
        accessoryStoreSizeLabel.setText("Car accessory storage size: " + factory.getAccessoryStore().getSize() + " / " + factory.getAccessoryStore().getCapacity());
        totalSoldLabel.setText("Total sold: " + factory.getTotalSold());
        totalGainLabel.setText("Total gain: " + new DecimalFormat("#0.#").format(factory.getTotalGain()));
        buildStateLabel.setText("Build state: " + (factory.isBuildingPaused() ? "paused" : "running"));
    }
}
