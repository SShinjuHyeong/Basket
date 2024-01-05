package com.deep.basket.service;

import com.deep.basket.dao.BoardDao;
import com.deep.basket.vo.BoardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardDao boardDao;

    public int publish(BoardVo boardVo) {
        int status = boardDao.publish(boardVo);
        return status;
    }

    public List<BoardVo> total_views() {
        List<BoardVo> boardVoList = boardDao.total_views();
        return boardVoList;
    }

    public List<BoardVo> total_views_stories() {
        List<BoardVo> boardVoList = boardDao.total_views_stories();
        return boardVoList;
    }
}
