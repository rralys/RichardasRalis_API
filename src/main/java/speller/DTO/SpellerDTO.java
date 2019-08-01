package speller.DTO;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SpellerDTO {
    private String code;
    private String pos;
    private String row;
    private String col;
    private String len;
    private String word;
    private List<String> s;
}
