/**
 * 可添加plan的接口
 * PlanTable和HistoryPlanTable实现该接口，为了在PlanManage中进行管理时方法复用
 */
public interface PlanAddible {
    int addPlan(Plan plan);
}
