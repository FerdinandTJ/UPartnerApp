class Project {
  private String dosenName;
  private String projectName;
  private String description;
  private String registrationDeadline;
  private String requirements;
  private String jobDesk;
  private String status;
  private String department;

  public Project(String dosenName, String projectName, String description, String registrationDeadline, String requirements, String department) {
      this.dosenName = dosenName;
      this.projectName = projectName;
      this.description = description;
      this.registrationDeadline = registrationDeadline;
      this.requirements = requirements;
      this.status = "Masa Pendaftaran";
      this.department = department;
  }

  public String getProjectName() {
      return projectName;
  }

  public String getDescription() {
      return description;
  }

  public String getRegistrationDeadline() {
      return registrationDeadline;
  }

  public String getRequirements() {
      return requirements;
  }

  public String getJobDesk() {
      return jobDesk;
  }

  public void setJobDesk(String jobDesk) {
      this.jobDesk = jobDesk;
  }

  public String getStatus() {
      return status;
  }

  public void setStatus(String status) {
      this.status = status;
  }

  public String getDosenName() {
      return dosenName;
  }

  public String getDepartment() {
      return department;
  }

  @Override
  public String toString() {
      return dosenName + "," + projectName + "," + description + "," + registrationDeadline + "," + requirements + "," + department + "," + status;
  }
}
