package com.soon9086.basic1.boundedContext.board.service;

import com.soon9086.basic1.boundedContext.board.mapper.BoardMapper;
import com.soon9086.basic1.boundedContext.board.model.Board;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    public void save(Board board) {
        boardMapper.insertBoard(board);
    }

    public List<Board> list() {
        return boardMapper.selectBoardList();
    }

    public Board getBoardDetail(Long id) {
        return boardMapper.selectBoardById(id);
    }

    public void update(Board board) {
        boardMapper.updateBoard(board);
    }

    public void delete(Long id) {
        boardMapper.delete(id);
    }

}

