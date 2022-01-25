package br.com.rest.entities.enuns;

import java.util.Arrays;

public enum Role{
    ROLE_OPERATOR(1, "OPERATOR"),
    ROLE_ADMIN(2, "ADMIN"),
    ROLE_CLIENT(3, "CLIENT");

    private Integer id;
    private String name;

    Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Role from(Integer id){
        return Arrays.stream(Role.values())
                .filter(r -> r.id.equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found idRole: "+ id));
    }
}
