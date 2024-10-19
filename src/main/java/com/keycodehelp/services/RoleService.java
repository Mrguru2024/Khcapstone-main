package com.keycodehelp.services;

import com.keycodehelp.entities.Role;
import com.keycodehelp.repositories.RoleRepository;
import com.keycodehelp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Create or save a new role
    public Role createOrSaveRole(Role role) {
        return roleRepository.save(role);
    }

    // Get role by ID
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    }

    // Get role by name
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));
    }

    // Get all roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Update a role
    public Role updateRole(Long id, Role roleDetails) {
        Role role = getRoleById(id);
        role.setName(roleDetails.getName());
        return roleRepository.save(role);
    }

    // Delete a role
    public boolean deleteRole(Long id) {
        Role role = getRoleById(id);
        roleRepository.delete(role);
        return true;  // Return true on successful deletion
    }

    public Role createRole(Role role) {
        return role;
    }
}
