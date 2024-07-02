package com.sparta.spartime.config;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FailoverIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;

import java.util.Arrays;

public class FixtureMonkeyUtil {

    private FixtureMonkeyUtil() {}

    public static FixtureMonkey get() {
        return FixtureMonkey.builder()
                .objectIntrospector(new FailoverIntrospector((
                        Arrays.asList(
                                FieldReflectionArbitraryIntrospector.INSTANCE,
                                ConstructorPropertiesArbitraryIntrospector.INSTANCE,
                                BuilderArbitraryIntrospector.INSTANCE
                        )
                )
                ))
                .build();
    }
}
