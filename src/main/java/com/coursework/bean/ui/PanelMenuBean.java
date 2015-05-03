/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursework.bean.ui;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.richfaces.event.ItemChangeEvent;

/**
 *
 * @author User
 */
@ManagedBean
@SessionScoped
public class PanelMenuBean {
    private String current;
//    private boolean singleMode;
//
//    public boolean isSingleMode() {
//        return singleMode;
//    }
//
//    public void setSingleMode(boolean singleMode) {
//        this.singleMode = singleMode;
//    }

    /**
     * Получить текущий выбор
     * @return текущий выбор
     */
    public String getCurrent() {
        return this.current;
    }

    /**
     * Установить текущий выбор
     * @param current новое значание текущего выбора
     */
    public void setCurrent(String current) {
        this.current = current;
    }

    /**
     * Обработчик события ItemChangeEvent
     * @param event
     */
    public void updateCurrent(ItemChangeEvent event) {
        setCurrent(event.getNewItemName());
    }
}