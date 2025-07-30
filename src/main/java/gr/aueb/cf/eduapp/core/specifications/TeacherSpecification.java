package gr.aueb.cf.eduapp.core.specifications;

import gr.aueb.cf.eduapp.model.Teacher;
import gr.aueb.cf.eduapp.model.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class TeacherSpecification {

    private TeacherSpecification() {

    }

    public static Specification<Teacher> teacherUserVatis(String vat) {
        return ((root, query, criteriaBuilder) -> {
            if (vat == null || vat.isBlank())
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            Join<Teacher, User> user = root.join("user");
            return criteriaBuilder.equal(user.get("vat"), vat);
        });
    }

    public static Specification<Teacher> trUserIsActive(Boolean isActive) {
        return (root, query, builder) -> {
            if (isActive == null) {
                return builder.isTrue(builder.literal(true));
            }

            // Join the User entity related to the Teacher entity
            Join<Teacher, User> user = root.join("user");

            // Return the condition where the user's isActive matches the input isActive
            return builder.equal(user.get("isActive"), isActive);
        };
    }
}
