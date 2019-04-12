package com.mp.api.dto;

public class TableRowDto {

    private String label;

    private String group;

    private Double value;

    public TableRowDto(String label, String group, Double value) {
        this.label = label;
        this.group = group;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
