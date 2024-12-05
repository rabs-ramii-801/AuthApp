package com.authentication.rbacApp.service;

import com.authentication.rbacApp.model.Role;
import com.authentication.rbacApp.repository.RoleRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for handling operations related to roles.
 * Provides methods to create and retrieve roles in the system.
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepositry roleRepositry;

    /**
     * Creates a new role and saves it to the database.
     *
     * @param roleName the name of the role to be created
     * @return the newly created Role object after saving it to the database
     */

    public Role createRole(String roleName){
        // Create a new Role object and set its name
        Role role=new Role();
        role.setRoleName(roleName);
        // Save the role to the database and return the saved object
        return roleRepositry.save(role);
    }

    /**
            * Retrieves a role by its name from the database.
     *
             * @param roleName the name of the role to retrieve
     * @return the Role object if found, or null if no role with the given name exists
     */
    public Role getRole(String roleName){
        // Use the repository to find a role by its name
        return roleRepositry.findByroleName(roleName);
    }
}
