package au.com.normist.capital.repository.cap.resultset2entity;

import au.com.normist.capital.core.annotation.cap.PersistEntity;

import javax.persistence.Column;

@PersistEntity
public class TestEntityForResultSetMap {
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
