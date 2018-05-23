/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class MenuBox extends VBox {
    
    private MenuItem[] menu;
    
    /**
     * Constructor del MenuBox
     * @param items conjunto de MenuItem
     */
    public MenuBox(MenuItem... items){
        super.getChildren().add(createSeperator());
        
        menu = items;
        for(MenuItem item: items){
            super.getChildren().addAll(item, createSeperator());
        }
    }
    
    private Line createSeperator() {
        Line separator = new Line();
        separator.setEndX(210);
        separator.setStroke(Color.DARKGREY);
        return separator;
    }
    
    /**
     * Deuelve el menu del MenuBox
     * @return MENU
     */
    public MenuItem[] getMenu(){
        return menu;
    }
}
