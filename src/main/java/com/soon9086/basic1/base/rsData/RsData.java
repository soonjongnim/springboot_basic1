package com.soon9086.basic1.base.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class RsData {
    private final String resultCode;
    private final String msg;

    public static RsData of(String resultCode, String msg) {
        return new RsData(resultCode, msg);
    }
}
