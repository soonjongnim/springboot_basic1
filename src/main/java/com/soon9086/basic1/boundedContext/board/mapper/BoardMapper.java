package com.soon9086.basic1.boundedContext.board.mapper;

import com.soon9086.basic1.boundedContext.board.model.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void insertBoard(Board board);
    List<Board> selectBoardList();
    Board selectBoardById(Long id);
    int updateBoard(Board board);
    void delete(Long id);

}
