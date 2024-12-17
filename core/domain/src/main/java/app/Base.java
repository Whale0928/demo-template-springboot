package app;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Base {
    @Id
    private String id;
}
