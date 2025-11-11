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
    private final Object data;

    public static RsData of(String resultCode, String msg) {
        return new RsData(resultCode, msg, null);
    }
    public static RsData of(String resultCode, String msg, Object data) {
        return new RsData(resultCode, msg, data);
    }

    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }
}
