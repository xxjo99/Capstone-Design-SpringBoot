package com.delivery.mydelivery.firebase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {

    private String targetToken;
    private String title;
    private String body;
}
