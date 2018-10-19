package pers.zc.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.zc.activiti.mapper.ProcessMapper;
import pers.zc.activiti.model.TaskViewModel;
import pers.zc.activiti.service.ProcessService;
import pers.zc.activiti.viewmodels.PagedFilterViewModel;
import pers.zc.activiti.viewmodels.PagedListViewModel;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * @ClassName: singleAssigneeTest
 * @Description: TODO
 * @Author: Administrator
 * @Date: 2018/10/17 10:01
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SingleAssigneeTest {
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private ProcessService processService;
    @Autowired
    private ProcessMapper processMapper;

    private final String tenantId = "A1";
    private String taskid = "22503";


    // 待办列表
    @Test
    public void doingTasks() {
        PagedFilterViewModel pagedFilterViewModel = new PagedFilterViewModel();
        pagedFilterViewModel.setStart(0);
        pagedFilterViewModel.setSize((int) Math.pow(2, 31));
        pagedFilterViewModel.setFilters(new HashMap<String, String>(1) {{
            put("currentUserId", "谢文林");
        }});
        List<pers.zc.activiti.model.viewmodels.TaskViewModel> tasks = processMapper.getDoingTasks(pagedFilterViewModel);
        Long totalCount = processMapper.getDoingTasksCount(pagedFilterViewModel);
        assertEquals(tasks.size() > 0, true);
        assertEquals(totalCount> 0, true);

    }


    public void testId() {
        Map obj1 = processMapper.doingTasks();
        TaskViewModel TaskViewModel = new TaskViewModel();
        /*Map obj = processEngine.getManagementService().executeCustomSql(new AbstractCustomSqlExecution<ProcessMapper, Map>(ProcessMapper.class) {
            @Override
            public Map execute(ProcessMapper processMapper) {
                return processMapper.doingTasks();
            }
        });*/
        System.err.print(1);

    }

    public void listLastVersion() {
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().asc()//升序
                .list();
        //定义有序map，相同的key,添加map值后，后面的会覆盖前面的值
        Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
        //遍历相同的key，替换最新的值
        for (ProcessDefinition pd : list) {
            map.put(pd.getKey(), pd);
        }

        List<ProcessDefinition> linkedList = new LinkedList<ProcessDefinition>(map.values());
        for (ProcessDefinition pd : linkedList) {
            System.out.println(pd.getId());
            System.out.println(pd.getName());
            System.out.println(pd.getVersion());
        }
    }


    public void deployByResource() {
        final String resourceName = "singleAssignee.bpmn";
        String deployId = processService.deployByResource(resourceName, tenantId);
        System.out.println("流程部署成功，当前流程部署id：" + deployId);
    }


    public void startProcess() {

        final String resourceName = "singleAssignee.bpmn";
        String deployId = processService.deployByResource(resourceName, tenantId);
        System.out.println("流程部署成功，当前流程部署id：" + deployId);

        final String processKey = "singleAssignee";
        Map<String, Object> variables = new HashMap<>();
        variables.put("applyUser", "小红");
        variables.put("user1", "小明");

        Task task = processService.startProcess(processKey, tenantId, variables);
        taskid = task.getId();
        System.out.println("流程发起成功，当前流程任务id：".concat(task.getId()) + " 流程实例id:".concat(task.getProcessInstanceId()));
    }


    public void completeTask() throws Exception {

        String task = processService.completeTask(taskid, tenantId, "", "", null, null);
        System.out.println("流程任务执行成功:" + task);
    }


    public void recallTask() throws Exception {

        processService.recallTask(taskid);
        List<Task> runTask = processEngine.getTaskService().createTaskQuery().processInstanceId("17501").list();
        if (runTask != null && runTask.size() > 0) {
            for (org.activiti.engine.task.Task task : runTask) {
                System.out.println("节点任务ID：" + task.getId());
                System.out.println("节点任务的办理人：" + task.getAssignee());
                System.out.println("节点任务名称：" + task.getName());
                System.out.println("节点任务的创建时间：" + task.getCreateTime());
                System.out.println("节点流程实例ID：" + task.getProcessInstanceId());
                System.out.println("#######################################");
            }
        }
        System.out.println("流程任务执行成功:" + runTask.size());
    }



/*
    public void setSingleAssignee() {
        // 注意 这里需要拿007来查询，key-value需要拿value来获取任务
        List<Task>  runTask = processEngine.getTaskService().createTaskQuery().taskId(taskid).list();
        if (runTask != null && runTask.size() > 0) {
            for (org.activiti.engine.task.Task task : runTask) {
                System.out.println("节点任务ID：" + task.getId());
                System.out.println("节点任务的办理人：" + task.getAssignee());
                System.out.println("节点任务名称：" + task.getName());
                System.out.println("节点任务的创建时间：" + task.getCreateTime());
                System.out.println("节点流程实例ID：" + task.getProcessInstanceId());
                System.out.println("#######################################");
            }
        }

        // 设置User Task2的受理人变量
        Map<String, Object> variables1 = new HashMap<>();
        variables1.put("user2", "Kevin");
        // 因为007只有一个代办的任务，直接完成任务，并赋值下一个节点的受理人user2为Kevin办理
       // processEngine.getTaskService().complete(runTask.get(0).getId(), variables1);
        System.out.println("User Task1被完成了，此时流程已流转到User Task2");
    }*/

}
