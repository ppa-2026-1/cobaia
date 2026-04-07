


public class Util {
    private Util() {
        // Construtor privado para evitar instância
    }

    public static String generateHandle(String email) {
        String handle = email.split("@")[0];
        return handle.replaceAll("[^a-zA-Z0-9]", "");
    }
    
    public static String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static Set<Role> getDefaultRoles(RoleRepository roleRepository, List<String> defaultRoleNames) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : defaultRoleNames) {
            Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Papel " + roleName + " não encontrado"));
            roles.add(role);
        }
        return roles;
    }

    public static void validateRolesExist(RoleRepository roleRepository, List<String> roleNames) {
        for (String roleName : roleNames) {
            if (!roleRepository.existsByName(roleName)) {
                throw new IllegalArgumentException("Papel " + roleName + " não existe");
            }
        }
    }

    public static void validateUserHasRoles(Set<Role> roles) {
        if (roles.isEmpty()) {
            throw new IllegalArgumentException("O usuário deve ter pelo menos um papel");
        }
    }
}


class StringUtil {
    public static String generateHandle(String email) {
        String handle = email.split("@")[0];
        return handle.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }


}