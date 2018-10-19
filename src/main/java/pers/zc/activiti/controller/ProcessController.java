package pers.zc.activiti.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.zc.activiti.model.InstanceViewModel;
import pers.zc.activiti.model.ProcessModel;
import pers.zc.activiti.service.ProcessService;
import pers.zc.activiti.viewmodels.PagedFilterViewModel;
import pers.zc.activiti.viewmodels.PagedListViewModel;

import java.util.*;

/**
 * @ClassName: ProcessController
 * @Description: TODO
 * @Author: Administrator
 * @Date: 2018/10/18 10:54
 * @Version 1.0
 */
@RestController
@RequestMapping("/process")
public class ProcessController {
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private ProcessService processService;

    @RequestMapping("deployByResource")
    public String deployByResource(String resourceName, String tenantId) {
        return processService.deployByResource(resourceName, tenantId);
    }

    @RequestMapping("getProcessList")
    public List<ProcessModel> getProcessList() {
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().asc()//升序
                .list();
        //定义有序map，相同的key,添加map值后，后面的会覆盖前面的值
        Map<String, ProcessDefinition> map = new LinkedHashMap<>();
        //遍历相同的key，替换最新的值
        for (ProcessDefinition pd : list) {
            map.put(pd.getKey(), pd);
        }
        List<ProcessDefinition> linkedList = new LinkedList<>(map.values());
        List<ProcessModel> processModels = new ArrayList<>();
        for (ProcessDefinition pd : linkedList) {
            ProcessModel model = ProcessModel.builder()
                    .id(pd.getId())
                    .key(pd.getKey())
                    .name(pd.getName())
                    .version(pd.getVersion() + "")
                    .build();
            processModels.add(model);

        }
        return processModels;
    }

    @RequestMapping("startProcess")
    public String startProcess(String processKey, String tenantId, String userId) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("applyUser", userId);
        variables.put("user1", userId);
        Task task = processService.startProcess(processKey, tenantId, variables);
        return task.getId();
    }

    @RequestMapping("completeTask")
    public String completeTask(String taskId, String tenantId) throws Exception {
        String task = processService.completeTask(taskId, tenantId, "", "", null, null);
        return task;
    }

    @RequestMapping("recallTask")
    public void recallTask(String taskId) throws Exception {
        processService.recallTask(taskId);
    }


    @RequestMapping("doingTasks")
    public PagedListViewModel<pers.zc.activiti.model.viewmodels.TaskViewModel> doingTasks(String userId) throws Exception {

        PagedFilterViewModel pagedFilterViewModel = new PagedFilterViewModel();
        pagedFilterViewModel.setStart(0);
        pagedFilterViewModel.setSize((int) Math.pow(2, 31));
        pagedFilterViewModel.setFilters(new HashMap<String, String>(1) {{
            put("currentUserId", userId);
        }});
        // TODO 当前用户
        //filter.put("currentUserId", "待定");
        return processService.getDoingTasks(pagedFilterViewModel);
    }


   /* @RequestMapping("doingTasks")
    public List<TaskViewModel> doingTasks(String userId) throws Exception {
        List<TaskViewModel> taskViewModels = new ArrayList<>();
        List<Task> runTask = processEngine.getTaskService().createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        if (runTask != null && runTask.size() > 0) {
            for (org.activiti.engine.task.Task task : runTask) {
                TaskViewModel model = TaskViewModel.builder()
                        .id(task.getId())
                        .assignee(task.getAssignee())
                        .name(task.getName())
                        .createTime(task.getCreateTime().toString())
                        .processInstanceId(task.getProcessInstanceId() + "")
                        .build();
                taskViewModels.add(model);
                System.out.println("节点任务ID：" + task.getId());
                System.out.println("节点任务的办理人：" + task.getAssignee());
                System.out.println("节点任务名称：" + task.getName());
                System.out.println("节点任务的创建时间：" + task.getCreateTime());
                System.out.println("节点流程实例ID：" + task.getProcessInstanceId());
                System.out.println("#######################################");
            }

        }
        return taskViewModels;
    }*/

    @RequestMapping("doneTasks")
    public List<InstanceViewModel> doneTasks(String userId) throws Exception {
        List<InstanceViewModel> instanceViewModels = new ArrayList<>();
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .taskAssignee(userId)
                .orderByHistoricTaskInstanceStartTime().desc()
                .finished() // 查询已经完成的任务
                .list();
        for (HistoricTaskInstance hti : list) {
            InstanceViewModel model = InstanceViewModel.builder()
                    .id(hti.getId())
                    .assignee(hti.getAssignee())
                    .name(hti.getName())
                    .createTime(hti.getCreateTime().toString())
                    .processInstanceId(hti.getProcessInstanceId() + "")
                    .endTime(hti.getEndTime().toString())
                    .build();
            System.out.println("任务ID:" + hti.getId());
            System.out.println("流程实例ID:" + hti.getProcessInstanceId());
            System.out.println("办理人：" + hti.getAssignee());
            System.out.println("创建时间：" + hti.getCreateTime());
            System.out.println("结束时间：" + hti.getEndTime());
            System.out.println("===========================");
            instanceViewModels.add(model);
        }
        return instanceViewModels;
    }


    @RequestMapping("getInstances")
    public void getInstances() throws Exception {

    }


}
