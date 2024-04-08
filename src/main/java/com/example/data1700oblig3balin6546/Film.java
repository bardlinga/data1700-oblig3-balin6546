package com.example.data1700oblig3balin6546;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Film {
    private @Setter String tittel;
    private @Setter String sjanger;
    private @Setter int prodAar;
}
