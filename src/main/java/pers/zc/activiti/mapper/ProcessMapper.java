package pers.zc.activiti.mapper;


import pers.zc.activiti.model.viewmodels.InstanceViewModel;
import pers.zc.activiti.model.viewmodels.TaskViewModel;
import pers.zc.activiti.viewmodels.PagedFilterViewModel;

import java.util.List;
import java.util.Map;

/**
 * @Description: 流程任务列表
 * @Author: xiewl
 * @Date: 2018/10/19 15:13
 * @Version 1.0
 */
public interface ProcessMapper {

    /**
     * 待办列表
     */
    List<TaskViewModel> getDoingTasks(PagedFilterViewModel filter);
    /**
     * 已办列表
     */
    List<InstanceViewModel> getDoneTasks(PagedFilterViewModel filter);
    /**
     * 已发列表
     */
    List<InstanceViewModel> startedInstances(PagedFilterViewModel filter);
    /**
     * 实例列表
     */
    List<InstanceViewModel> getInstances(PagedFilterViewModel filter);

    Long getDoingTasksCount(PagedFilterViewModel filter);

    Long getDoneTasksCount(PagedFilterViewModel filter);

    Long startedInstancesCount(PagedFilterViewModel filter);

    Long getInstancesCount(PagedFilterViewModel filter);


    Map doingTasks();

    String testId();


}
