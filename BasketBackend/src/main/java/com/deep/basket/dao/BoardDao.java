package com.deep.basket.dao;

import com.deep.basket.vo.BoardVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BoardDao {
    int publish(BoardVo boardVo);

    List<BoardVo> total_views();

    List<BoardVo> total_views_stories();
}
