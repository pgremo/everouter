package pgremo.pathfinding;

import org.junit.jupiter.api.Test;
import pgremo.pathfinding.AStar;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AStarTest {

    @Test
    public void shouldFindRoute() {
        Map<String, Map<String, Float>> graph = new HashMap<>();
        {
            Map<String, Float> row = new HashMap<>();
            row.put("green", 10.0F);
            row.put("blue", 5.0F);
            row.put("orange", 8.0F);
            graph.put("red", row);
        }
        {
            Map<String, Float> row = new HashMap<>();
            row.put("red", 10.0F);
            row.put("blue", 3.0F);
            graph.put("green", row);
        }
        {
            Map<String, Float> row = new HashMap<>();
            row.put("green", 3.0F);
            row.put("red", 5.0F);
            row.put("purple", 7.0F);
            graph.put("blue", row);
        }
        {
            Map<String, Float> row = new HashMap<>();
            row.put("blue", 7.0F);
            row.put("orange", 2.0F);
            graph.put("purple", row);
        }
        {
            Map<String, Float> row = new HashMap<>();
            row.put("purple", 2.0F);
            row.put("red", 2.0F);
            graph.put("orange", row);
        }
        Iterable<String> path = new AStar().findPath("red", x -> 0.0F, graph::get, "green"::equals);
        assertThat(path).containsExactly("red", "blue", "green");
    }
}