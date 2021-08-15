package ttzv.demo.ecommerce.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Country {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;

    @OneToMany(mappedBy = "country")
    //No need for @JsonIgnore because we have StateRepository
    private List<State> state;

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<State> getState() {
        return state;
    }

    public void setState(List<State> state) {
        this.state = state;
    }
}
