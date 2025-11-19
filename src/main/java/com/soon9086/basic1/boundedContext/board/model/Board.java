package com.soon9086.basic1.boundedContext.board.model;

import lombok.Data;

@Data
public class Board {
    private int id;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;  // 추가됨
}

