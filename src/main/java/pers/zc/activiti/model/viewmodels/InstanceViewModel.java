package pers.zc.activiti.model.viewmodels;

import java.util.Date;

public class InstanceViewModel {
    private String id;
    private String name;
    private String started;
    private Date startedTime;
    private Date endTime;
    private String taskId;
    private String titleHtml;
    private String taskName;
    private String taskAssignee;
    private String instanceState;
    private String title;
    private String formId;
    private String icon;
    private String listIcon;

    public String getListIcon() {
        return listIcon;
    }

    public void setListIcon(String listIcon) {
        this.listIcon = listIcon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public Date getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Date startedTime) {
        this.startedTime = startedTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        if (getEndTime() == null) {
            return "进行中";
        } else if ("0".equals(getInstanceState())) {
            return "已删除";
        } else if ("1".equals(getInstanceState())) {
            return "进行中";
        } else if ("2".equals(getInstanceState())) {
            return "已完成";
        } else if ("3".equals(getInstanceState())) {
            return "已终止";
        } else if ("4".equals(getInstanceState())) {
            return "已作废";
        } else {
            return "已完成";
        }
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskAssignee() {
        return taskAssignee;
    }

    public void setTaskAssignee(String taskAssignee) {
        this.taskAssignee = taskAssignee;
    }

    public String getInstanceState() {
        return instanceState;
    }

    public void setInstanceState(String instanceState) {
        this.instanceState = instanceState;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleHtml() {
        return titleHtml;
    }

    public void setTitleHtml(String titleHtml) {
        this.titleHtml = titleHtml;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
