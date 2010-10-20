/*
 * RomRaider Open-Source Tuning, Logging and Reflashing
 * Copyright (C) 2006-2010 RomRaider.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.romraider.swing;

import com.romraider.maps.Rom;
import com.romraider.maps.Table;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Vector;

public class JTableChooser extends JOptionPane {

    private static final long serialVersionUID = 5611729002131147882L;
    JPanel displayPanel = new JPanel();
    DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Open Images");
    JTree displayTree = new JTree(rootNode);

    public boolean showChooser(Vector<Rom> roms, Component parent, Table targetTable) {

    	int nameLength = 0;
    	for (int i = 0; i < roms.size(); i++) {
            Rom rom = roms.get(i);
            DefaultMutableTreeNode romNode = new DefaultMutableTreeNode(rom.getFileName());
            rootNode.add(romNode);

            for (int j = 0; j < rom.getTables().size(); j++) {
                Table table = rom.getTables().get(j);
                // use the length of the table name to set the width of the displayTree
                // so the entire name can be read without being cut off on the right
                if (table.getName().length() > nameLength) {
                	nameLength = table.getName().length();
                }
                TableChooserTreeNode tableNode = new TableChooserTreeNode(table.getName(), table);

                // categories
                boolean categoryExists = false;
                for (int k = 0; k < romNode.getChildCount(); k++) {
                    if (romNode.getChildAt(k).toString().equalsIgnoreCase(table.getCategory())) {
                        ((DefaultMutableTreeNode) romNode.getChildAt(k)).add(tableNode);
                        categoryExists = true;
                        break;
                    }
                }

                if (!categoryExists) {
                    DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(table.getCategory());
                    romNode.add(categoryNode);
                    categoryNode.add(tableNode);
                }
            }
        }

//        displayPanel.setPreferredSize(new Dimension(400, 400));
//        displayPanel.setMinimumSize(new Dimension(400, 400));
        displayTree.setPreferredSize(new Dimension(nameLength*7, 5000));	// setting this breaks the automatic resizing of the scroll bar of the enclosing JScrollPane  
//        displayTree.setMinimumSize(new Dimension(nameLength*7, 5000));	// the minimum width doesn't seem to be honoured without setting preferred size

        displayTree.setRootVisible(true);
        displayTree.updateUI();
        displayPanel.add(new JScrollPane(displayTree));

        Object[] values = {"Compare", "Cancel"};

        if ((showOptionDialog(parent, displayPanel, "Select a Map", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, values, values[0]) == 0 &&
                (displayTree.getLastSelectedPathComponent() instanceof TableChooserTreeNode))) {
            ((TableChooserTreeNode) displayTree.getLastSelectedPathComponent()).getTable().copyTable();
            return true;
        } else {
            return false;
        }
    }
}