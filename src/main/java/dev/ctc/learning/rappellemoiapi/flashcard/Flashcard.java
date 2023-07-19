package dev.ctc.learning.rappellemoiapi.flashcard;

import dev.ctc.learning.rappellemoiapi.auditing.BaseAuditableEntity;
import dev.ctc.learning.rappellemoiapi.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flashcards")
public class Flashcard extends BaseAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String front;
    private String back;
    private Date nextRevision;
    private Integer gradient;

    @ManyToOne
    private User user;

}
