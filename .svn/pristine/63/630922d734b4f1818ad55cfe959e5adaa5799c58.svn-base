package com.chero.client.vo;

import com.chero.client.utils.CustomPageable;
import com.chero.client.utils.PageVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @author Outcaster
 */
@Data
@ApiModel(value="ResultVO",description="通用返回类型")
public class ResultVO<T> {
	
	@ApiModelProperty(value="状态码",required=true)
    private Integer status;
	@ApiModelProperty(value="消息",required=true)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="内容",required=true)
    private T content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value="分页",required=true)
//    @JsonIgnore
    private PageVO page;
}
