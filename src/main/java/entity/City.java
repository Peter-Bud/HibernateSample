package entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class City implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NonNull
    private String name;


    @ManyToOne
    @JoinColumn(name = "countryCode", referencedColumnName = "code")
    @NonNull
    private Country country;

    @NonNull
    @Column
    private String district;

    @Column
    @NonNull
    private int population;
}

