package ru.mike.crudappjdbc.models;

import java.math.BigDecimal;
import java.util.Date;

public class Employee {
    private Long id;
    private String name;
    private Date birthday;
    private Long positionId;
    private BigDecimal salary;

    public Employee(){}

    public Employee(String name, Date birthday, Long positionId, BigDecimal salary) {
        this.name = name;
        this.birthday = birthday;
        this.positionId = positionId;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", positionId=" + positionId +
                ", salary=" + salary +
                '}';
    }
}
