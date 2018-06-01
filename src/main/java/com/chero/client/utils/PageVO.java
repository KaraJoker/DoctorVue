package com.chero.client.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by hxh on 2017/12/14.
 *
 * @author hxh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVO {
    private int size;
    private long totalElements;
    private int totalPages;
    @JsonProperty("pageId")
    private int number;
}
