package com.deep.basket.controller.rest;

import com.deep.basket.service.BoardService;
import com.deep.basket.vo.BoardVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardRestController extends BaseRestController {

    private final Logger logger = (Logger) LogManager.getLogger(BoardRestController.class);

    @Autowired
    public BoardService boardService;

    //게시글 작성 - insert
    @PostMapping("/register")
    public String publish(@RequestBody BoardVo boardVo, HttpServletResponse res){
        logger.info(">>> >>> publish controller 들어감...");
        int status =boardService.publish(boardVo);
        logger.info(">>> >>> insert > "+status);
        return "200";
    }

    //게시물 전체 조회 - posts
    @GetMapping("/content")
    public ResponseEntity<?> total_views(HttpServletResponse res){
        logger.info(">>> >>> total_views controller 들어감...");
        List<BoardVo> boardVoList = boardService.total_views();
        logger.info(">>> >>> select ..."+boardVoList);
        return success(boardVoList);
    }

    //게시물 최근 업데이트 7개 조회 - stories
    @GetMapping(value = "/latest")
    public ResponseEntity<?> total_views_stories(){
        logger.info(">>> >>> total_views_stories controller 들어감...");
        List<BoardVo> boardVoList = boardService.total_views_stories();
        logger.info(">>> >>> select ..."+boardVoList);
        return success(boardVoList);
    }

    //?
    @PostMapping("/upload")
    public String uplooad(){
        return "200";
    }
}
