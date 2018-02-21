package kr.co.ezenac.cjy.teamproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Administrator on 2018-02-09.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Main {
    private Integer id;
    private String name;
    private String room_img;
    private String path;
    private String content;
}
