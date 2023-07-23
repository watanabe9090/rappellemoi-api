package dev.ctc.learning.rappellemoiapi.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("select t from Token t inner join User u on t.user.id=u.id where u.id=:userId and (t.expired=false or t.revoked=false) ")
    List<Token> findAllValidTokensByUser(@Param("userId") UUID userId);

    List<Token> deleteByExpired(boolean expired);

    Optional<Token> findByToken(String token);
}
