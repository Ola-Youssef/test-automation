package com.graduation_project.street2shelter.DTO;

public class NgoRequestUpdateDto {
    private Long ngoRequestUpdatesId;
    private int assignmentId;

    // ✅ Constructor matching the query output


    public NgoRequestUpdateDto(Long ngoRequestUpdatesId, int assignmentId) {
        this.ngoRequestUpdatesId = ngoRequestUpdatesId;
        this.assignmentId = assignmentId;
    }

    // Getters and Setters
    public Long getNgoRequestUpdatesId() { return ngoRequestUpdatesId; }
    public void setNgoRequestUpdatesId(Long ngoRequestUpdatesId) { this.ngoRequestUpdatesId = ngoRequestUpdatesId; }

    public int getAssignmentId() { return assignmentId; }
    public void setAssignmentId(int assignmentId) { this.assignmentId = assignmentId; }


}