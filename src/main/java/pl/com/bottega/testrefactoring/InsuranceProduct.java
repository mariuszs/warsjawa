package pl.com.bottega.testrefactoring;

import java.util.List;

public class InsuranceProduct {

	private String productId;
	private int maximumPersonAge;
	private InsurancePlanType planType;
	private List<InsurancePlanGoalType> goals;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getMaximumPersonAge() {
		return maximumPersonAge;
	}

	public void setMaximumPersonAge(int maximumPersonAge) {
		this.maximumPersonAge = maximumPersonAge;
	}

	public InsurancePlanType getPlanType() {
		return planType;
	}

	public void setPlanType(InsurancePlanType planType) {
		this.planType = planType;
	}

	public List<InsurancePlanGoalType> getGoals() {
		return goals;
	}

	public void setGoals(List<InsurancePlanGoalType> goals) {
		this.goals = goals;
	}
}