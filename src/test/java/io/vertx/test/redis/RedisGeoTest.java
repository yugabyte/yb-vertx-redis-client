package io.vertx.test.redis;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import org.junit.*;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

@RunWith(VertxUnitRunner.class)
public class RedisGeoTest {

    private final static Vertx vertx = Vertx.vertx();

    @ClassRule
    public static GenericContainer redis = new GenericContainer("docker.io/redis:3.2.0").withExposedPorts(6379);

    private RedisClient client = null;

    @Before
    public void setUp() {
        if (client == null) {
            client = RedisClient.create(vertx, new RedisOptions().setHost(redis.getContainerIpAddress()).setPort(redis.getMappedPort(6379)));
        }
    }

    @Test
    public void test(TestContext ctx) {
        final Async async = ctx.async();

        client.geoadd("Sicily", 13.361389, 38.115556, "Catania", res -> {
            ctx.assertTrue(res.succeeded());
            ctx.assertEquals(1L, res.result());
            async.complete();
        });

        async.await();
    }

    @AfterClass
    public static void afterClass() {
        vertx.close();
    }
}
