package ru.nsu.dmustakaev.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BorderPanel extends JPanel {
    private final Border border;
    private final JPanel innerPanel;

    public BorderPanel(String title) {
        super();

        border = BorderFactory.createTitledBorder(title);

        setBorder(border);

        innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        add(innerPanel);
    }

    public void addIn(Component component) {
        innerPanel.add(component);
    }
}
