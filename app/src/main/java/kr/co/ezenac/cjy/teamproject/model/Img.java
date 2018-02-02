package kr.co.ezenac.cjy.teamproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Administrator on 2018-02-02.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Img {
    private Integer id;
    private Integer room_id;
    private String path;
    private String content;
}
