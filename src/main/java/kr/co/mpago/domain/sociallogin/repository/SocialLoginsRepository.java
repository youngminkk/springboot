package kr.co.mpago.domain.sociallogin.repository;

import kr.co.mpago.domain.sociallogin.entity.SocialLogins;
import kr.co.mpago.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialLoginsRepository extends JpaRepository<SocialLogins, Long> {
    Optional<SocialLogins> findByProviderAndProviderId(SocialLogins.Provider provider, String providerId);
    Optional<SocialLogins> findByUser(User user);
}
