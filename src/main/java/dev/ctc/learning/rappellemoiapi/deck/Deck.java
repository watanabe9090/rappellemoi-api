package dev.ctc.learning.rappellemoiapi.deck;

import dev.ctc.learning.rappellemoiapi.auditing.BaseAuditableEntity;
import dev.ctc.learning.rappellemoiapi.flashcard.Flashcard;
import dev.ctc.learning.rappellemoiapi.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "deck")
public class Deck extends BaseAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    @ManyToOne
    private User user;
}
