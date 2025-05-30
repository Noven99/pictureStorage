package com.yupi.yupicturebackend.model.vo;

import cn.hutool.json.JSONUtil;
import com.yupi.yupicturebackend.model.entity.Picture;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class PictureTagCategory {
    //标签列表
    private List<String> tagList;
    //分类列表
    private List<String> categoryList;
}
