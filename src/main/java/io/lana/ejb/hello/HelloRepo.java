package io.lana.ejb.hello;

import io.lana.ejb.lib.repo.CrudRepository;
import io.lana.ejb.lib.repo.JpaRepository;

public class HelloRepo extends JpaRepository<HelloEntity> implements CrudRepository<HelloEntity> {
    protected HelloRepo() {
        super(HelloEntity.class);
    }
}
