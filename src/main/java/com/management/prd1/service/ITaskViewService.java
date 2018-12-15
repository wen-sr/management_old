package com.management.prd1.service;

import com.management.common.EasyuiTableResponse;
import com.management.prd1.dto.TaskViewDto;

public interface ITaskViewService {
    EasyuiTableResponse getData(TaskViewDto taskView);
}
