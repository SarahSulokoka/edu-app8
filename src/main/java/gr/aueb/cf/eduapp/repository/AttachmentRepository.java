package gr.aueb.cf.eduapp.repository;

import gr.aueb.cf.eduapp.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AttachmentRepository extends JpaRepository<Attachment, Long>,
        JpaSpecificationExecutor<Attachment> {

}
