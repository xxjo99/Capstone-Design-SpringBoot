package com.delivery.mydelivery.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "menuOption")
public class MenuOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuOptionId; // pk

    private int menuId; // menu fk
    private int storeId; // menu fk
    private String optionName;
    private String optionType;
    private Integer maximumSelection;
}
