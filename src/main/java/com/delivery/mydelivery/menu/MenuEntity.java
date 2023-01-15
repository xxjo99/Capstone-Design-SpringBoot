package com.delivery.mydelivery.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "menu")
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuId;

    private int storeId;
    private String menuName;
    private int menuPrice;
    private String menuPicUrl;
    private String menuInfo;
}
