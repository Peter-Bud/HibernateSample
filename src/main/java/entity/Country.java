package entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Country {
    @Id
    @NonNull
    private String code;

    @Column
    @NonNull
    private String name;

    @Column
    @NonNull
    private Object continent;

    @Column
    @NonNull
    private String region;

    @Column
    @NonNull
    private BigDecimal surfaceArea;

    @Column
    @NonNull
    private Short indepYear;

    @Column
    @NonNull
    private int population;

    @Column
    @NonNull
    private BigDecimal lifeExpectancy;

    @Column
    @NonNull
    private BigDecimal gnp;

    @Column
    @NonNull
    private BigDecimal gnpOld;

    @Column
    @NonNull
    private String localName;

    @Column
    @NonNull
    private String governmentForm;

    @Column
    @NonNull
    private String headOfState;

    @Column
    @NonNull
    private Integer capital;

    @Column
    @NonNull
    private String code2;

    @OneToMany(mappedBy = "country")
    private List<City> cities;
}

