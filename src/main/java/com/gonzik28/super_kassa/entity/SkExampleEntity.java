package com.gonzik28.super_kassa.entity;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "sk_example_table")
public class SkExampleEntity {

    @Id
    private Integer id;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonbObj obj;

    public JsonbObj getObj() {
        return obj;
    }

    public void setObj(JsonbObj obj) {
        this.obj = obj;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
