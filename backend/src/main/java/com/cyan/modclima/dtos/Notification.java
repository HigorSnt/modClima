package com.cyan.modclima.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    private String message;

    private String redirectURL;

}
