package pers.zc.activiti.service;

import org.activiti.engine.task.Task;
import pers.zc.activiti.model.viewmodels.InstanceViewModel;
import pers.zc.activiti.model.viewmodels.TaskViewModel;
import pers.zc.activiti.viewmodels.PagedFilterViewModel;
import pers.zc.activiti.viewmodels.PagedListViewModel;

import java.util.List;
import java.util.Map;

/**
 * @Description: 流程服务
 * @Author: xiewl
 * @Date: 2018/10/16 15:48
 * @Version 1.0
 */
public interface ProcessService {
    /**
     * 通过流程id，以及流程部署xml部署流程
     *
     * @param flowId         流程id
     * @param descriptionXml 流程定义xml
     * @param args           扩展参数
     * @return 返回流程部署id
     */
    String deploy(String flowId, String descriptionXml, String tenantId, String... args);

    /**
     * 通过流程id，以及流程部署xml部署流程
     *
     * @param resourceName 资源名称
     * @return 返回流程部署id
     */
    String deployByResource(String resourceName, String tenantId);

    /**
     * 通过流程id，以及流程部署xml部署生成流程
     *
     * @param definitionId 流程定义id
     * @param tenantId     租户id
     *                     *@param variables 变量处理人(包含当前租户ID,key对应-tenantId)
     * @return 返回当前任务对象
     */
    Task startProcess(String definitionId, String tenantId, Map<String, Object> variables);

    /**
     * 完成当前流程任务
     *
     * @param taskId       任务id
     * @param tenantId     租户id
     * @param btnName      任务实例标识
     * @param actionId     执行按钮标识
     * @param variables    表单参数
     * @param nodeExecutor 节点执行人
     */
    String completeTask(String taskId, String tenantId, String btnName,
                        String actionId,
                        Map<String, Object> variables,
                        List<Map<String, Object>> nodeExecutor) throws Exception;

    /**
     * 撤回当前流程实例任务
     *
     * @param taskId 任务id
     */
    void recallTask(String taskId);

    /**
     * 终止当前流程实例任务
     *
     * @param instanceId 实例id
     */
    void suspendTask(String instanceId) throws Exception;

    /**
     * 作废当前流程实例
     *
     * @param instanceId 实例id
     */
    void cancelProcess(String instanceId) throws Exception;

    /**
     * 修改流程状态
     *
     * @param instanceId 实例id
     * @param state      状态名称
     */
    int modifyProcessState(String instanceId, String state) throws Exception;

    /**
     * 待办列表
     */
    PagedListViewModel<TaskViewModel> getDoingTasks(PagedFilterViewModel filter);

    /**
     * 已办列表
     */
    PagedListViewModel<InstanceViewModel> getDoneTasks(PagedFilterViewModel filter);

    /**
     * 已发列表
     */
    PagedListViewModel<InstanceViewModel> startedInstances(PagedFilterViewModel filter);

    /**
     * 实例列表
     */
    PagedListViewModel<InstanceViewModel> getInstances(PagedFilterViewModel filter);


    List<Map<String, Object>> getAllFlowList(String userId) throws Exception;
}



