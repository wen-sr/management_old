package com.management.prd1.dao;

import com.management.prd1.dto.TaskViewDto;
import com.management.prd1.pojo.TaskView;

import java.util.List;

public interface TaskViewMapper {
    int insert(TaskView record);

    int insertSelective(TaskView record);

    List<TaskViewDto> getData(TaskViewDto taskView);
}