package com.prashik.splitvise.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AllGroups {
    private Set<Group> allGroups;
    private HashMap<String, Group> nameMapping;

    public AllGroups() {
        this.allGroups = new HashSet<>();
        this.nameMapping = new HashMap<>();
    }

    public AllGroups(Set<Group> allGroups) {
        this.allGroups = allGroups;
        this.nameMapping = new HashMap<>();
        for(Group group: allGroups) {
            this.nameMapping.put(group.getName(), group);
        }
    }

    public Set<Group> getAllGroups() {
        return allGroups;
    }

    public void setAllGroups(Set<Group> allGroups) {
        this.allGroups = allGroups;
    }

    public HashMap<String, Group> getNameMapping() {
        return nameMapping;
    }

    public void setNameMapping(HashMap<String, Group> nameMapping) {
        this.nameMapping = nameMapping;
    }

    private boolean isPresent(Group newGroup) {
        for(Group group: allGroups) {
            if(Group.isEqual(newGroup, group)) {
                return true;
            }
        }
        return false;
    }

    public boolean addGroup(Group group) {
        if(!this.isPresent(group)) {
            this.allGroups.add(group);
            this.nameMapping.put(group.getName(), group);
            return true;
        }
        return false;
    }

    public boolean deleteGroup(Group group) {
        if(this.isPresent(group)) {
            this.allGroups.removeIf(group1 -> Group.isEqual(group1, group));
            this.nameMapping.remove(group.getName());
            return true;
        }
        return false;
    }

    public Group getGroupFromName(String groupName) {
        return this.nameMapping.get(groupName);
    }
}
