package pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class Message {


    private Addition addition;

    private List<Integer> important_numbers;

    private String title;

    private Boolean verified;

}
