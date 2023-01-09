package com.delivery.mydelivery.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "menuOptionContent")
public class MenuOptionContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuOptionContentId; // pk

    private int menuOptionId; // menuoption fk
    private int menuId; // menuoption fk
    private int storeId; // menuoption fk
    private String optionContentName;
    private int optionPrice;
}
