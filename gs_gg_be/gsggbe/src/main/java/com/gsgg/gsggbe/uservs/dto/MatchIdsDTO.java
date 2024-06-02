package com.gsgg.gsggbe.uservs.dto;
import lombok.Data;

import java.util.List;
@Data
public class MatchIdsDTO {
    private List<String> myMatches;
    private List<String> yourMatches;
}
