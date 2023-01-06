package com.delivery.mydelivery.menu;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "menu")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuId;

    private int storeId;
    private String menuName;
    private String menuPrice;
    private String menuPicUrl;
    private String menuInfo;

    @Override
    public String toString() {
        return "MenuEntity{" +
                "menuId=" + menuId +
                ", storeId=" + storeId +
                ", menuName='" + menuName + '\'' +
                ", menuPrice='" + menuPrice + '\'' +
                ", menuPicUrl='" + menuPicUrl + '\'' +
                ", menuInfo='" + menuInfo + '\'' +
                '}';
    }
}
