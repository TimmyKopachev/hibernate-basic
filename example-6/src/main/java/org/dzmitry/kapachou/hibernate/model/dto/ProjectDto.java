package org.dzmitry.kapachou.hibernate.model.dto;

import lombok.Data;

@Data
public class ProjectDto {
    private Long id;
    private String title;
    private String description;

    private String creator;

}
