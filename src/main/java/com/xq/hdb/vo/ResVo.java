package com.xq.hdb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResVo {
	private String code;
	private String msg;
	private Object data;
}
