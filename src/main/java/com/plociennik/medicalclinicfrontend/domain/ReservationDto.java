package com.plociennik.medicalclinicfrontend.domain;

public class ReservationDto {
    private Long id;
    private String time;
    private Long patientId;
    private Long doctorId;

    public ReservationDto(Long id, String time, Long patientId, Long doctorId) {
        this.id = id;
        this.time = time;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public ReservationDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public String toString() {
        return "ReservationDto{" +
                "id=" + id +
                ", \ntime='" + time + '\'' +
                ", \npatientId=" + patientId +
                ", \ndoctorId=" + doctorId +
                '}';
    }
}
