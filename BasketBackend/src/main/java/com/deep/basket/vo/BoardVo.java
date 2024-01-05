package com.deep.basket.vo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("BoardVo")
public class BoardVo {
    private Integer no; //시퀀스 생성
    private String boardTitle;
    private Integer boardCost;
    private String boardCategory;
    private String subcategory;
    private String boardContent;
    private String picture;

}
