package io.lana.ejb.customer;

import io.lana.ejb.lib.repo.CrudRepository;
import io.lana.ejb.lib.repo.JpaRepository;

public class CustomerRepo extends JpaRepository<Customer> implements CrudRepository<Customer> {
}
