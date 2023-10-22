package com.stc.task.automation.dataModels;

import java.util.ArrayList;

public class PlansDm extends MainDataModel {
   public ArrayList<PlanDm> getPlanDm() {
      return planDm;
   }

   public void setPlanDm(ArrayList<PlanDm> planDm) {
      this.planDm = planDm;
   }

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   private ArrayList<PlanDm> planDm;
    private String country;
}
