package com.website.loveconnect.Exception.ExceptionModel;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
