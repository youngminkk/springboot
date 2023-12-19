package com.mpago.mountain.springboot;

import com.mpago.mountain.springboot.web.ProfileController;
import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileControllerUnitTests {

    @Test
    public void read_real_profile() {

        //given
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment(); //Environment 가짜 구현체
        //profile 추가
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile(); //필터링된 profile 추출

        //then
        assertThat(profile).isEqualTo(expectedProfile);

    }

    @Test
    public void read_first_if_not_real_profile() {
        //given
        String expectedProfile = "oauth";
        MockEnvironment env = new MockEnvironment(); //Environment 가짜 구현체

        //profile 추가
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile(); //필터링된 profile 추출

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void read_default_if_not_active_profile() {
        //given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment(); //Environment 가짜 구현체
        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile(); //필터링된 profile 추출

        //then
        assertThat(profile).isEqualTo(expectedProfile);

    }

}