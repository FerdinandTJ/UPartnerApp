public class Project {
  private String dosenName;
  private String projectName;
  private String description;
  private String registrationDeadline;
  private String requirements;
  private String programStudi;

  public Project(String dosenName, String projectName, String description, String registrationDeadline, String requirements, String programStudi) {
      this.dosenName = dosenName;
      this.projectName = projectName;
      this.description = description;
      this.registrationDeadline = registrationDeadline;
      this.requirements = requirements;
      this.programStudi = programStudi;
  }

  public String getDosenName() {
      return dosenName;
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

  public String getProgramStudi() {
      return programStudi;
  }
}
