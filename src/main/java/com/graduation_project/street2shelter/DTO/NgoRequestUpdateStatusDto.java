package com.graduation_project.street2shelter.DTO;

public class NgoRequestUpdateStatusDto {
    private Long ngoRequestUpdatesId;
    private int assignmentId;
    private String status;

    // ✅ Constructor matching the query output

    public NgoRequestUpdateStatusDto(Long ngoRequestUpdatesId, int assignmentId, String status) {
        this.ngoRequestUpdatesId = ngoRequestUpdatesId;
        this.assignmentId = assignmentId;
        this.status = status;
    }

    // Getters and Setters
    public Long getNgoRequestUpdatesId() { return ngoRequestUpdatesId; }
    public void setNgoRequestUpdatesId(Long ngoRequestUpdatesId) { this.ngoRequestUpdatesId = ngoRequestUpdatesId; }

    public int getAssignmentId() { return assignmentId; }
    public void setAssignmentId(int assignmentId) { this.assignmentId = assignmentId; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}